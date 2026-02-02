package dev.imdmk.ordersystem.bootstrap.order.controller;

import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.bootstrap.order.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

    private MockMvc mockMvc;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderService = mock(OrderService.class);

        OrderController controller = new OrderController(orderService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldCreateOrder() throws Exception {
        UUID id = UUID.randomUUID();
        when(orderService.createOrder(any())).thenReturn(id);

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            {
                              "items": [
                                { "productId": "P-1", "quantity": 1, "price": "10.00" }
                              ]
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(id.toString()));
    }
}
