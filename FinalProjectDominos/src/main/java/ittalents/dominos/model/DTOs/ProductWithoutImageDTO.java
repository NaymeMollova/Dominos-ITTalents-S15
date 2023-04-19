package ittalents.dominos.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductWithoutImageDTO {

    private int id;
    private String name;
    private double price;
   private int categoryId;
}
