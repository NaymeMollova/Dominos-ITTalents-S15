package ittalents.dominos.model.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BadRequestProductExceptionBuilder {

    private List<String> errors;

    public BadRequestProductExceptionBuilder() {
        errors = new ArrayList<>();
    }

    public BadRequestProductExceptionBuilder addQuantityError() {
        errors.add("Quantity can't be less than 1");
        return this;
    }

    public BadRequestProductExceptionBuilder addProductNotFoundError(long id) {
        errors.add("No product with id " + id + " found");
        return this;
    }


    public void build() {
        if (!errors.isEmpty()) {
            throw new BadRequestException(String.join("; ", errors));
        }
    }
}