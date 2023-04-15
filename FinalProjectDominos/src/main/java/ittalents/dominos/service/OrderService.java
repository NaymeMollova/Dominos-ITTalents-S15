package ittalents.dominos.services;

import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.model.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService {
    public List<OrderInfoDTO> getAll;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper mapper;
    public List<OrderInfoDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map( u -> mapper.map(u, OrderInfoDTO.class))
                .collect(Collectors.toList());
    }
}
