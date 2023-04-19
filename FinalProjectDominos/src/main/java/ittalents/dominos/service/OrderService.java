package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.ItemInCartDTO;
import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.Order;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService{

    public List<OrderInfoDTO> getAll;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<OrderInfoDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, OrderInfoDTO.class))
                .collect(Collectors.toList());
    }

    public OrderInfoDTO createOrder(Map<ItemInCartDTO, Integer> cart, User user, double price, Address address){
        String msg = "Thank you for your order!";
        Order newOrder = new Order( user, price,
                orderStatusRepository.getReferenceById(1));
        orderRepository.save(newOrder);
        return new  OrderInfoDTO(price,
               newOrder.getOrderingTime() ,
                newOrder.getOrderStatusId(), cart, msg,addressRepository.getReferenceById(address.getId()).getAddressName());

    }
}
