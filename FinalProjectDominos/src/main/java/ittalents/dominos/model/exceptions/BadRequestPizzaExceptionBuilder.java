package ittalents.dominos.model.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BadRequestPizzaExceptionBuilder {
    private List<String> errors;

    public BadRequestPizzaExceptionBuilder() {
        errors = new ArrayList<>();
    }

    public BadRequestPizzaExceptionBuilder addQuantityError() {
        errors.add("Quantity can't be less than 1");
        return this;
    }

    public BadRequestPizzaExceptionBuilder addPizzaNotFoundError(long id) {
        errors.add("No pizza with id " + id + " found");
        return this;
    }

    public BadRequestPizzaExceptionBuilder addSizeNotFoundError(long id) {
        errors.add("No size with id " + id + " found");
        return this;
    }

    public BadRequestPizzaExceptionBuilder addDoughTypeError(long id) {
        errors.add("No dough type with id " + id + " found");
        return this;
    }

    public void build() {
        if (!errors.isEmpty()) {
            throw new BadRequestException(String.join("; ", errors));
        }
    }
}
