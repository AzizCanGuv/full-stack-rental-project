package com.comp3102.backend.user;

import com.comp3102.backend.core.exceptions.BadRequestException;
import com.comp3102.backend.core.exceptions.EntityAlreadyExistsException;
import com.comp3102.backend.core.exceptions.UnacceptableOperationException;
import com.comp3102.backend.reservation.dto.response.ReservationWithDetailsDto;
import com.comp3102.backend.s3.S3Service;
import com.comp3102.backend.core.securityConfiguration.JwtService;
import com.comp3102.backend.reservation.ReservationService;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.comp3102.backend.user.dto.converter.User2UserResponseDtoConverter;
import com.comp3102.backend.token.Token;
import com.comp3102.backend.token.TokenType;
import com.comp3102.backend.token.TokenRepository;
import com.comp3102.backend.core.mailConfiguration.EmailSenderService;
import com.comp3102.backend.user.dto.request.*;
import com.comp3102.backend.user.dto.response.AuthenticationResponseDto;
import com.comp3102.backend.user.dto.response.UserForgotPasswordResponseDto;
import com.comp3102.backend.user.dto.response.UserResponseDto;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.comp3102.backend.core.constant.EmailConstants.bodyExecutor;
import static com.comp3102.backend.core.constant.EmailConstants.emailSubject;
import static com.comp3102.backend.core.constant.ErrorConstant.*;
import static com.comp3102.backend.core.utils.GenericUtilities.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final User2UserResponseDtoConverter user2UserResponseDtoConverter;
    private final EmailSenderService emailSenderService;
    private final ReservationService reservationService;
    private final S3Service s3Service;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.user.picture.end.point}")
    private String userPictureEndPoint;
    public AuthenticationResponseDto register(RegisterRequestDto request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(s -> {
            throw new EntityAlreadyExistsException(errorMessageParser(USER_EMAIL_ALREADY_EXISTS, request.getEmail()));
        });
        userRepository.findByIdentityNumber(request.getIdentityNumber()).ifPresent(s -> {
            throw new EntityAlreadyExistsException(errorMessageParser(USER_IDENTITY_NUMBER_ALREADY_EXISTS, request.getIdentityNumber()));
        });
        userRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(s -> {
            throw new EntityAlreadyExistsException(errorMessageParser(USER_PHONE_NUMBER_ALREADY_EXISTS, request.getPhoneNumber()));
        });


        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .identityNumber(request.getIdentityNumber())
                .birthDate(request.getBirthDate())
                .identityNumber(request.getIdentityNumber())
                .driverLicense(request.getDriverLicense())
                .phoneNumber(request.getPhoneNumber())
                .registeredAt(LocalDateTime.now())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .id(user.getId())
                .token(jwtToken)
                .name(user.getFirstName())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .lastName(user.getLastName())
                .driverLicense(user.getDriverLicense())
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponseDto authenticate(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponseDto.builder()
                .id(user.getId())
                .token(jwtToken)
                .name(user.getFirstName())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .lastName(user.getLastName())
                .driverLicense(user.getDriverLicense())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDto changeUserRole(Integer userId, RoleChangeRequestDto roleChangeRequestDto) {
        User user = userRepository.findByEmail(roleChangeRequestDto.getUserEmail()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, roleChangeRequestDto.getUserEmail())));
        List<ReservationWithDetailsDto> responseDtoList = reservationService.getReservationsByUserId(userId);

        if (responseDtoList.size() > 0) {
            throw new BadRequestException(errorMessageParser(USER_HAS_PAID_RESERVATIONS_ERROR_MESSAGE, userId));
        }
        if (user.getRole().name().equals(Role.ADMIN.name())) {
            throw new UnacceptableOperationException(ADMIN_ROLE_CHANGE_ERROR_MESSAGE);
        }

        user.setRole(roleChangeRequestDto.getRole());
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return UserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .driverLicense(user.getDriverLicense())
                .role(user.getRole())
                .birthDate(user.getBirthDate())
                .isEnabled(user.isEnabled())
                .build();
    }

    public List<UserResponseDto> getAllUsers() {
        return user2UserResponseDtoConverter.convert(userRepository.findAll());
    }

    public UserResponseDto getUserById(Integer id) {
        return user2UserResponseDtoConverter.convert(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, id))));
    }

    public AuthenticationResponseDto editUserProfile(Integer id, UserEditRequestDto userEditRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, id)));

        revokeAllUserTokens(user);

        user.setPassword(passwordEncoder.encode(userEditRequestDto.getNewPassword()));
        user.setPhoneNumber(userEditRequestDto.getPhoneNumber());
        user.setDriverLicense(userEditRequestDto.getDriverLicense());
        var updatedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(updatedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .id(user.getId())
                .token(jwtToken)
                .name(user.getFirstName())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .lastName(user.getLastName())
                .driverLicense(user.getDriverLicense())
                .build();
    }

    public UserForgotPasswordResponseDto userForgotPasswordRequest(UserForgotPasswordRequestDto userForgotPasswordRequestDto) throws MessagingException {
        String userEmail = userForgotPasswordRequestDto.getEmail();
        userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_EMAIL_NOT_FOUND_ERROR_MESSAGE, userEmail)));

        String encodedEmail = encode(userEmail);
        String body = bodyExecutor(userEmail, encodedEmail);
        emailSenderService.sendMail(userEmail, body, emailSubject);

        return new UserForgotPasswordResponseDto("OK");
    }

    public UserForgotPasswordResponseDto changeUserPassword(String uniqueItem, UserPasswordChangeRequestDto userPasswordChangeRequestDto) {
        String decodedMail = decodeUrl(uniqueItem);
        User user = userRepository.findByEmail(decodedMail).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_EMAIL_NOT_FOUND_ERROR_MESSAGE, decodedMail)));
        user.setPassword(passwordEncoder.encode(userPasswordChangeRequestDto.getPassword()));
        userRepository.save(user);
        return new UserForgotPasswordResponseDto("OK");
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, id)));
        List<ReservationWithDetailsDto> responseDtoList = reservationService.getReservationsByUserId(id);

        if (responseDtoList.size() > 0) {
            throw new BadRequestException(errorMessageParser(USER_HAS_PAID_RESERVATIONS_ERROR_MESSAGE, id));
        }

        if (user.getRole().name().equals(Role.ADMIN.name())) {
            throw new UnacceptableOperationException(ADMIN_ROLE_CHANGE_ERROR_MESSAGE);
        }
        revokeAllUserTokens(user);
        tokenRepository.findAllByUserId(id).forEach(t -> tokenRepository.deleteById(t.getId()));
        userRepository.deleteById(id);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void userTicketSend(UserTicketRequestDto userTicketRequestDto) throws MessagingException {
        emailSenderService.sendTicketMail(userTicketRequestDto.getFirstName(), userTicketRequestDto.getLastName(), userTicketRequestDto.getEmail(), userTicketRequestDto.getContent());
    }

    public void uploadUserProfileImage(Integer userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, userId)));
        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    bucketName,
                    userPictureEndPoint.formatted(userId, profileImageId),
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setProfilePictureId(profileImageId);
        userRepository.save(user);
    }

    public byte[] getUserProfileImage(Integer userId) {

        var customer = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, userId)));

        if (customer.getProfilePictureId() == null){
            throw new BadRequestException(errorMessageParser(PROFILE_PICTURE_ERROR_MESSAGE, userId));
        }
        var profileImageId = customer.getProfilePictureId();

        return s3Service.getObject(
                bucketName,
                userPictureEndPoint.formatted(userId, profileImageId)
        );

    }
}
