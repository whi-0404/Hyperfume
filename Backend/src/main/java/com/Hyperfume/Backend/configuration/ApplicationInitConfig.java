package com.Hyperfume.Backend.configuration;

import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.RoleRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args ->
        {
          if(userRepository.findByUsername("admin").isEmpty()){

              com.Hyperfume.Backend.entity.Role adminrole = roleRepository.findByName("ADMIN")
                      .orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED));
              User user=User.builder()
                      .username("admin")
                      .password(passwordEncoder.encode("admin12345"))
                      .email("admin@gmail.com")
                      .phone("010101")
                      .role(adminrole)
                      .build();

              userRepository.save(user);
              log.warn("admin user has been created with password: admin, please change it!");
          }
        };
    }
}
