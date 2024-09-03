//package com.example.OrderService.service;
//
//import com.example.OrderService.Exception.CustomException;
//import com.example.OrderService.External.client.PaymentService;
//import com.example.OrderService.External.client.ProductService;
//import com.example.OrderService.entity.Order;
//import com.example.OrderService.model.*;
//import com.example.OrderService.repository.OrderRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.Instant;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class OrderServiceImplTest {
//
//
//    @Mock
//    private RestTemplate restTemplate;
//    @Mock
//    private ProductService prodService;
//    @Mock
//    private OrderRepository repository;
//    @Mock
//    private PaymentService paymentService;
//    @InjectMocks
//    private OrderServiceImpl service;
//
//
//    @Test
//    public void successGetOrderDetails() {
//
//        // get order
//        // get product by Rest
//        // get payment by rest
//
//        Order order = getMockOrder();
//        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
//        when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getOrderId(), ProductDetails.class)).thenReturn(getMockProduct());
//        when(restTemplate.getForObject("http://PAYMENT/payment/" + order.getOrderId(), PaymentDetails.class)).thenReturn(getMockPayment());
//
//        /// actual call the method in OrderService
//        OrderResponse orderResponse = service.getOrderDetails(1);
//        System.out.println(orderResponse.toString());
//
//        /// to check the repsone not null
//        assertNotNull(orderResponse);
//        assertEquals(order.getOrderId(), orderResponse.getOrderId());
//    }
//
//
//    @Test
//    void FaildGetOrder() {
//
//
//        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
//        CustomException exception = assertThrows(CustomException.class, () -> service.getOrderDetails(1));
//
//        assertEquals(400, exception.getStatus());
//        assertEquals("NOT_FOUND", exception.getErrorCode());
//    }
//
//    @Test
//    void successPlaceOrder() {
//        Order order = getMockOrder();
//        OrderRequest orderRequest = getMockOrderRequest();
// /// prepareing
//        repository.save(order);
//        when(prodService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
//        when(paymentService.doPayment(any(PaymentRequest.class))).thenReturn(new ResponseEntity<Long>(1L, HttpStatus.OK));
//
//      // actual call
//        Long orderId = service.placeOrder(orderRequest);
//        assertEquals(order.getOrderId(), orderId);
//    }
//
//    @Test
//    void faildPlaceOrder() {
//        Order order = getMockOrder();
//        OrderRequest orderRequest = getMockOrderRequest();
//        /// prepareing
//        repository.save(order);
//        when(prodService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
//        when(paymentService.doPayment(any(PaymentRequest.class))).thenThrow(new RuntimeException());
//
//        // actual call
//        Long orderId = service.placeOrder(orderRequest);
//        assertEquals(order.getOrderId(), orderId);
//    }
//
//    OrderRequest getMockOrderRequest() {
//
//
//        return OrderRequest.builder().productId(1).paymentMode(PaymentMode.CREDIT_CARD).quantity(1).totalAmount(100).build();
//    }
//
//
//    PaymentDetails getMockPayment() {
//
//
//        return PaymentDetails.builder().paymentId(1).paymentDate(Instant.now()).paymentMode(PaymentMode.CASH).paymentStatus("Success").build();
//    }
//
//    Order getMockOrder() {
//
//
//        return Order.builder().orderId(1).productId(1).quantity(5).orderDate(Instant.now()).orderStatus("CREATED").build();
//    }
//
//    ProductDetails getMockProduct() {
//
//
//        return ProductDetails.builder().productId(1).productName("Prod 1").quantity(5).price(10).
//                build();
//    }
//}