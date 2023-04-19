package ittalents.dominos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithQuantityDTO {
        private int id;
      //  private BigDecimal price;
        private int quantity;

}
