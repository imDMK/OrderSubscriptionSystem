package dev.imdmk.ordersystem.bootstrap.order.exception;

import dev.imdmk.ordersystem.application.order.exception.OrderNotFoundException;
import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.bootstrap.order.controller.OrderController;
import dev.imdmk.ordersystem.domain.order.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderExceptionHandlerTest {

    private MockMvc mockMvc;
    private OrderService service;

    @BeforeEach
    void setup() {
        service = mock(OrderService.class);

        OrderController controller = new OrderController(service);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturn404WhenPayingNonExistingOrder() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new OrderNotFoundException(new OrderId(id)))
                .when(service).pay(any());

        mockMvc.perform(post("/orders/pay")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            { "orderId": "%s" }
                        """.formatted(id)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturn400WhenRequestBodyIsInvalidJson() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("{ this-is-not-json }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Invalid request body or malformed JSON"));
    }

    @Test
    void shouldReturn500WhenUnexpectedExceptionOccurs() throws Exception {
        doThrow(new RuntimeException("boom"))
                .when(service).createOrder(any());

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            {
                              "items": [
                                { "productId": "P-1", "quantity": 1, "price": "10.00" }
                              ]
                            }
                        """))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message")
                        .value("Unexpected server error"));
    }
}
