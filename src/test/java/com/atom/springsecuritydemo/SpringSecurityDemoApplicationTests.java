package com.atom.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        System.err.println(encode);
        boolean matches = passwordEncoder.matches("123", "$2a$10$rvBGitDbNsBch9jbFWWybO2vTGQD0Z80sWVYi825t09hgTW3jJpIq");
        System.err.println(matches);
    }



}
