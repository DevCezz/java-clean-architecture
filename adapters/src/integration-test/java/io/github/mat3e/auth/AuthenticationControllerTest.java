package io.github.mat3e.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        TestConfiguration.class,
        TestSecurityConfiguration.class
})
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_user_be_authenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\",\"password\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void should_user_not_be_authenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"fraud\",\"password\": \"fraud\"}"))
                .andExpect(status().isForbidden());
    }
}

class TestConfiguration {

    @Bean
    JwtConfigurationProperties jwtConfigurationProperties() {
        JwtConfigurationProperties properties = new JwtConfigurationProperties();
        properties.setSecret("test");
        properties.setValidity(300L);
        return properties;
    }

    @Bean
    TokenService tokenService(JwtConfigurationProperties jwtConfigurationProperties) {
        return new TokenService(jwtConfigurationProperties);
    }

    @Bean
    AuthenticationController authenticationController(
            AuthenticationManager authenticationManager,
            TokenService tokenService
    ) {
        return new AuthenticationController(authenticationManager, tokenService);
    }
}

class TestSecurityConfiguration extends SecurityConfiguration {

    TestSecurityConfiguration(@Autowired final TokenService tokenService) {
        super(tokenService);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User(
                        "test",
                        passwordEncoder().encode("test"),
                        Set.of()
                )
        );
    }
}