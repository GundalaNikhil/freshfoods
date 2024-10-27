package com.freshfoods.Service;

import com.freshfoods.DTO.OrderItemRequest;
import com.freshfoods.DTO.OrderRequest;
import com.freshfoods.Entity.*;
import com.freshfoods.Interface.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private ProductRepository productRepository;
    private final UserRepository userRepository;

    public Order createOrder(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setTotalPrice(product.getPrice() * itemRequest.getQuantity());

            orderItems.add(orderItem);
            totalAmount += orderItem.getTotalPrice();

            // Update stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Handle payment
        Payment payment = createPayment(orderRequest, totalAmount);
        order.setPayment(payment);

        return orderRepository.save(order);
    }

    private Payment createPayment(OrderRequest orderRequest, double amount) {
        Payment payment = new Payment();
        payment.setPaymentAmount(amount);
        payment.setPaymentMethod(orderRequest.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return payment;
    }
}
