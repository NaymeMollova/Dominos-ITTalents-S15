package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.Product;

public class OrderedProductDTO {
    private int id;
    private String productName;

    public OrderedProductDTO(int id, Product product){
        this.id=id;
        this.productName=product.getName();
    }

}
