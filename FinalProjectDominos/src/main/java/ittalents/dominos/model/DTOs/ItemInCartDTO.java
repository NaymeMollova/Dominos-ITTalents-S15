package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.Pizza;
import ittalents.dominos.model.entities.PizzaSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ItemInCartDTO {
        private int id;
        private String name;
        private double price;
        private boolean isPizza;

        private Pizza pizza;
        private DoughType doughType;
        private PizzaSize pizzaSize;

        public ItemInCartDTO(int id, double price, boolean b) {
                this.id = id;
                this.price = price;
                this.isPizza=b;
                if (!this.isPizza){
//                        this.pizza=null;
//                        this.doughType=null;
//                        this.pizzaSize=null;
                }
        }

        public ItemInCartDTO(int id, String name, double price, boolean b) {
                this.id = id;
                this.price = price;
                this.isPizza=b;
                this.name=name;
        }

        public ItemInCartDTO(int id, double price, boolean b, PizzaSize pizzaSize, DoughType doughType) {
                this.id = id;
                this.price = price;
                this.isPizza=b;
                this.doughType=doughType;
                this.pizzaSize=pizzaSize;
        }

        public ItemInCartDTO(int id, String name, double price, boolean b, PizzaSize pizzaSize, DoughType doughType) {
                this.id = id;
                this.price = price;
                this.name=name;
                this.isPizza=b;
                this.doughType=doughType;
                this.pizzaSize=pizzaSize;
        }

        @Override
        public boolean equals(Object o) {
                ItemInCartDTO that = (ItemInCartDTO) o;
                if(!isPizza) {
                        if (o == null || getClass() != o.getClass()) return false;
                        return Objects.equals(id, that.id);
                }
                return  Objects.equals(id, that.id) &&
                        Objects.equals(pizzaSize.getId(), that.pizzaSize.getId()) &&
                        Objects.equals(doughType.getId(), that.doughType.getId());
        }

        @Override
        public int hashCode() {
                if (!isPizza) {
                        return Objects.hash(id);
                }
                        return Objects.hash(id, doughType.getId(),pizzaSize.getId());

        }
}
