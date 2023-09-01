package com.comp3102.backend.user;

import com.comp3102.backend.user.dto.request.*;
import com.comp3102.backend.user.dto.response.AuthenticationResponseDto;
import com.comp3102.backend.user.dto.response.UserForgotPasswordResponseDto;
import com.comp3102.backend.user.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Operation(description = "Sign Up Service", summary = "Sign Up Service")
    @ApiResponse(responseCode = "400", ref = "badRequestAPI")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "400", ref = "badRequestAPI")
    @Operation(description = "Login Service", summary = "Login Service")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PutMapping("/role/{userId}")
    @ApiResponse(responseCode = "400", ref = "badRequestAPI")
    @Operation(description = "Role Change Service", summary = "Role Point", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDto> changeUserRole(@PathVariable Integer userId, @RequestBody RoleChangeRequestDto request) {
        return ResponseEntity.ok(userService.changeUserRole(userId, request));
    }

    @GetMapping()
    @Operation(description = "Get All Users Service", summary = "Get All Users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get User By Id Service", summary = "Get User by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete User By Id Service", summary = "Delete User By ID", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/edit/{id}")
    @Operation(description = "Edit User Profile Service", summary = "Edit User Profile", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AuthenticationResponseDto> editUserProfile(@PathVariable Integer id, @RequestBody UserEditRequestDto userEditRequestDto) {
        return ResponseEntity.ok(userService.editUserProfile(id, userEditRequestDto));
    }

    @PutMapping("/change-password/{uniqueItem}")
    public ResponseEntity<UserForgotPasswordResponseDto> changeUserPassword(@PathVariable String uniqueItem, @RequestBody UserPasswordChangeRequestDto userPasswordChangeRequestDto) {
        return ResponseEntity.ok(userService.changeUserPassword(uniqueItem, userPasswordChangeRequestDto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<UserForgotPasswordResponseDto> userForgotPasswordRequest(@RequestBody UserForgotPasswordRequestDto userForgotPasswordRequestDto) throws MessagingException {
        return ResponseEntity.ok(userService.userForgotPasswordRequest(userForgotPasswordRequestDto));
    }

    @PostMapping("/ticket/send")
    public void userTicketSend(@RequestBody @Valid UserTicketRequestDto userTicketRequestDto) throws MessagingException {
        userService.userTicketSend(userTicketRequestDto);
    }

    @PostMapping(value = "/profile-image/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Upload Profile Image By Id Service", summary = "Upload Profile Image By Id Service", security = @SecurityRequirement(name = "bearerAuth"))
    public void uploadUserProfileImage(@PathVariable("userId") Integer userId, @RequestParam("file") MultipartFile file) {
        userService.uploadUserProfileImage(userId, file);
    }

    @GetMapping(value = "/profile-image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(description = "Get Profile Image By Id Service", summary = "Get Profile Image By Id Service", security = @SecurityRequirement(name = "bearerAuth"))
    public byte[] uploadUserProfileImage(@PathVariable("userId") Integer userId) {
        return userService.getUserProfileImage(userId);
    }

}