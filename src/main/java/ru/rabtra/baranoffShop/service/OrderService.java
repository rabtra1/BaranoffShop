package ru.rabtra.baranoffShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabtra.baranoffShop.repository.OrderRepository;

@Service
@Transactional(readOnly=true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Integer countByUserId(Long userId) {
        return orderRepository.countByUserId(userId);
    }
}
