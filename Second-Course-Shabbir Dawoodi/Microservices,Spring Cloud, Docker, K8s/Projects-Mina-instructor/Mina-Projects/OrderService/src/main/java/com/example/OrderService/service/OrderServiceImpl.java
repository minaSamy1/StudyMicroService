package com.example.OrderService.service;

import com.example.OrderService.Exception.CustomException;
import com.example.OrderService.External.client.PaymentService;
import com.example.OrderService.External.client.ProductService;
import com.example.OrderService.entity.Order;
import com.example.OrderService.model.*;
import com.example.OrderService.repository.OrderRepository;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductService prodService;
    @Autowired
    private OrderRepository repository;
    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED
        // Steps
        // 1- reduce the Quantity
        // save the Order
        // Call Payment
        // update the order Status after the payment

        prodService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());


        Order order = Order.builder().
                productId(orderRequest.getProductId())
                .amount(orderRequest.getTotalAmount())
                .quantity(orderRequest.getQuantity()).orderDate(Instant.now())
                .orderStatus("created").build();

        repository.save(order);

        System.out.println(" Start Callling  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+order.toString());

        PaymentRequest paymentRequest = PaymentRequest.builder().
                orderId(order.getOrderId()).
                paymentMode(orderRequest.getPaymentMode()).
                amount(order.getAmount()).build();
        String paymentStatus;
        try {
            paymentService.doPayment(paymentRequest);
            paymentStatus = "PLACED";
        } catch (Exception e) {

            paymentStatus = "PAYMENT_FAILED";
        }

        System.out.println(" Start Callling  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+order.getOrderId());
        System.out.println(" Start Callling  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+order.getOrderStatus());
        order.setOrderStatus(paymentStatus);
        return order.getOrderId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new CustomException(" Order Not Found", "NOT_FOUND", 400));

        ProductDetails productDetails = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getOrderId(), ProductDetails.class);


        PaymentDetails PaymentDetails = restTemplate.getForObject("http://PAYMENT/payment/" + order.getOrderId(), PaymentDetails.class);
        OrderResponse orderInfo = OrderResponse.builder()
                .orderId(order.getOrderId()).
                orderStatus(order.getOrderStatus())
                .amount(order.getAmount()).
                orderDate(order.getOrderDate()).
                productDetails(productDetails).
                paymentDetails(PaymentDetails).build();
        return orderInfo;
    }
}
