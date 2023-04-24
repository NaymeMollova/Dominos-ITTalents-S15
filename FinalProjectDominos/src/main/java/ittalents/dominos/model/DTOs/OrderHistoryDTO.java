package ittalents.dominos.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {


    private int id;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderingTime;
    private String orderStatusName;
    private int addressId;


    public OrderHistoryDTO(int id, BigDecimal price,
                           LocalDateTime orderingTime, int addressId, String orderStatusName) {
        this.id = id;
        this.price = price;
        this.orderingTime = orderingTime;
        this.orderStatusName = orderStatusName;
        this.addressId = addressId;
    }
}