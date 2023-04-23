package ittalents.dominos.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import ittalents.dominos.model.entities.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class OrderHistoryDTO {
    private int id;
    private BigDecimal price;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderingTime;
    private OrderStatus orderStatus;
    private String addressName;
}
