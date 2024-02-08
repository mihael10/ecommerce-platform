package com.ecommerce.orderservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WebClientConfigurationDiffblueTest {
    @Autowired
    private WebClientConfiguration webClientConfiguration;

    /**
     * Method under test: {@link WebClientConfiguration#webClientBuilder()}
     */
    @Test
    void testWebClientBuilder() {
        // TODO: Complete this test.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     DefaultWebClientBuilder.baseUrl
        //     DefaultWebClientBuilder.connector
        //     DefaultWebClientBuilder.defaultCookies
        //     DefaultWebClientBuilder.defaultHeaders
        //     DefaultWebClientBuilder.defaultRequest
        //     DefaultWebClientBuilder.defaultUriVariables
        //     DefaultWebClientBuilder.exchangeFunction
        //     DefaultWebClientBuilder.filters
        //     DefaultWebClientBuilder.observationConvention
        //     DefaultWebClientBuilder.observationRegistry
        //     DefaultWebClientBuilder.statusHandlers
        //     DefaultWebClientBuilder.strategies
        //     DefaultWebClientBuilder.strategiesConfigurers
        //     DefaultWebClientBuilder.uriBuilderFactory

        // Arrange and Act
        webClientConfiguration.webClientBuilder();
    }
}
