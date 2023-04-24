package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.OrderHistoryDTO;
import ittalents.dominos.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService{

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderHistoryDTO> getAll() {
        List<OrderHistoryDTO> orders =  orderRepository.findAll()
                .stream()
                .map(u -> new OrderHistoryDTO(u.getId(),u.getPrice(),u.getOrderingTime(),
                        u.getAddress().getId(), u.getOrderStatus().getName()))
                .collect(Collectors.toList());
        Collections.reverse(orders);

        return orders;
    }
}
