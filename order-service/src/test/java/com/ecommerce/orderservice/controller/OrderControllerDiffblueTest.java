package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.DTO.OrderRequestDTO;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.*;

class OrderControllerDiffblueTest {
  /**
   * Method under test: {@link OrderController#placeOrder(OrderRequestDTO)}
   */
  @Test
  void testPlaceOrder() {
    //   Diffblue Cover was unable to write a Spring test,
    //   so wrote a non-Spring test instead.
    //   Reason: R026 Failed to create Spring context.
    //   Attempt to initialize test context failed with
    //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@2b92b82f testClass = com.ecommerce.orderservice.controller.DiffblueFakeClass3, locations = [], classes = [com.ecommerce.orderservice.controller.OrderController, com.ecommerce.orderservice.service.OrderService], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@240e8d7f, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5f5616f3, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@27209b21], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
    //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
    //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
    //       at java.base/java.util.Optional.map(Optional.java:260)
    //   See https://diff.blue/R026 to resolve this issue.

    // Arrange
    ProducerFactory<String, OrderPlacedEvent> producerFactory = mock(ProducerFactory.class);
    when(producerFactory.transactionCapable()).thenReturn(true);
    OrderController orderController = new OrderController(new OrderService(mock(OrderRepository.class),
        mock(WebClient.Builder.class), new KafkaTemplate<>(producerFactory)));

    // Act
    orderController.placeOrder(new OrderRequestDTO());

    // Assert
    verify(producerFactory).transactionCapable();
  }
}
