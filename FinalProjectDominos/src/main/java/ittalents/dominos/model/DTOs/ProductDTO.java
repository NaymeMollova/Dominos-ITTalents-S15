package ittalents.dominos.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private String image;
    private int categoryId;
}
