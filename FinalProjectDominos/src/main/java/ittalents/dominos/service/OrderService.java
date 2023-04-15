package ittalents.dominos.service;
import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.model.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public List<OrderInfoDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(u -> modelMapper().map(u, OrderInfoDTO.class))
                .collect(Collectors.toList());
    }
}
