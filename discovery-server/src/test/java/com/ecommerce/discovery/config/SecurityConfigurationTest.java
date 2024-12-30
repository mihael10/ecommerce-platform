package com.ecommerce.discovery.config;

import com.ecommerce.discoveryserver.config.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfiguration.class)
public class SecurityConfigurationTest {

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @Value("${eureka.username}")
    private String username;

    @Value("${eureka.password}")
    private String password;

    @Mock
    private NoOpPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Autowired
    private MockMvc mockMvc;


//    void testAccessSecuredEndpointWithValidCredentials() throws Exception {
//        mockMvc.perform(get("/secured-endpoint")
//                        .with(httpBasic(username, password)))
//                .andExpect(status().isOk());
//    }
}
