package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.OrderHistoryDTO;
import ittalents.dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController extends AbstractController {
    @Autowired
    private OrderService orderService;

    @GetMapping("dominos/orders")
    public List<OrderHistoryDTO> getAll() {
        return orderService.getAll();
    }

}
