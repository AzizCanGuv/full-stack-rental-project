package com.comp3102.backend.core;

import com.comp3102.backend.brand.Brand;
import com.comp3102.backend.brand.BrandRepository;
import com.comp3102.backend.car.Transmission;
import com.comp3102.backend.color.Color;
import com.comp3102.backend.color.ColorRepository;
import com.comp3102.backend.s3.S3Service;
import com.comp3102.backend.core.securityConfiguration.JwtService;
import com.comp3102.backend.car.dto.request.CarPostDetailsDto;
import com.comp3102.backend.token.Token;
import com.comp3102.backend.token.TokenRepository;
import com.comp3102.backend.token.TokenType;
import com.comp3102.backend.user.Role;
import com.comp3102.backend.user.User;
import com.comp3102.backend.user.UserRepository;
import com.comp3102.backend.car.CarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartupConfig implements CommandLineRunner {
    private final UserRepository service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final ColorRepository colorService;
    private final BrandRepository brandService;
    private final CarService carService;

    Logger logger = LoggerFactory.getLogger(StartupConfig.class);

    @Override
    public void run(String... args) throws InterruptedException {

        //s3BucketFileUploadTest();


        if (colorService.findByColorName("Black").isEmpty()) {
            List<String> colorList = Arrays.asList("Black", "Red", "White", "Orange", "Blue", "Yellow", "string");
            colorList.forEach((c) -> colorService.save(new Color(null, c)));
        }
        if (brandService.findByBrandName("Mercedes").isEmpty()) {
            List<String> brandList = Arrays.asList("Mercedes", "BMW", "Nissan", "Ford", "Renault", "Hyundai", "Toyota"
                    , "Bugatti", "Ferrari", "Mclaren", "Subaru", "string");
            brandList.forEach((b) -> brandService.save(new Brand(null, b)));
        }


        if (carService.getAll(0, 10, "carId").getTotalElements() == 0) {
            carService.addNewCar(new CarPostDetailsDto("Mercedes", "Black", "", 1.00, "1000", "2000", "Ankara", Transmission.AUTOMATIC), null);
            carService.addNewCar(new CarPostDetailsDto("Mercedes", "Black", "", 2.00, "1000", "2000", "Ankara", Transmission.AUTOMATIC), null);
            carService.addNewCar(new CarPostDetailsDto("Mercedes", "Black", "", 1.99, "1000", "2000", "Ankara", Transmission.AUTOMATIC), null);
        }


        if (service.findById(1).isEmpty()) {
            var userCustomer = User.builder()
                    .id(1)
                    .firstName("Ahmet")
                    .lastName("Yılmaz")
                    .email("user@user.com")
                    .password(passwordEncoder.encode("1"))
                    .driverLicense("A1")
                    .birthDate(LocalDateTime.now())
                    .identityNumber("35487415")
                    .phoneNumber("987123747")
                    .registeredAt(LocalDateTime.now())
                    .role(Role.USER)
                    .build();
            User savedUser = service.save(userCustomer);
            var jwtToken = jwtService.generateToken(userCustomer);
            logger.error(jwtToken);
            saveUserToken(savedUser, jwtToken);
        }

        if (service.findById(2).isEmpty()) {
            var user = User.builder()
                    .id(2)
                    .firstName("Test FirstName")
                    .lastName("Test Last Name")
                    .email("test@test.com")
                    .password(passwordEncoder.encode("1"))
                    .driverLicense("A")
                    .birthDate(LocalDateTime.now())
                    .identityNumber("35621123456")
                    .phoneNumber("5343947775")
                    .registeredAt(LocalDateTime.now())
                    .role(Role.ADMIN)
                    .build();
            User savedUser = service.save(user);
            var jwtToken = jwtService.generateToken(user);
            logger.error(jwtToken);
            saveUserToken(savedUser, jwtToken);
        }
    }

    private void s3BucketFileUploadTest(S3Service s3Service, String bucketName) {
        s3Service.putObject(bucketName, "foo2", "Hello World".getBytes());
        byte[] obj = s3Service.getObject(bucketName, "foo2");

        System.out.println("Ho: " + new String(obj));
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
}