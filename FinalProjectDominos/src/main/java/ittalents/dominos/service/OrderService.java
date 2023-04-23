package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.OrderHistoryDTO;
import ittalents.dominos.model.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<OrderHistoryDTO> getAll() {
        return orderRepository.findAllByOrderByIdDesc()
                .stream()
                .map(u -> modelMapper.map(u, OrderHistoryDTO.class))
                .collect(Collectors.toList());



    }



}
