package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.Pizza;
import ittalents.dominos.model.entities.PizzaSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInCartDTO {
        private int id;
        private String name;
        private BigDecimal price;
        private boolean isPizza;
        private Pizza pizza=null;
        private DoughType doughType=null;
        private PizzaSize pizzaSize=null;
        private String pizzaSizeName=null;

        public ItemInCartDTO(int id, BigDecimal price, boolean b) {
                this.isPizza=b;
                if (this.isPizza){
                         this.id = id;
                this.price = price;
                }
        }

        public ItemInCartDTO(int id, String name, BigDecimal price, boolean b) {
                this.id = id;
                this.price = price;
                this.isPizza=b;
                this.name=name;
        }

        public ItemInCartDTO(int id, BigDecimal price, boolean b, PizzaSize pizzaSize, DoughType doughType, Pizza pizza,String pizzaSizeName) {
                this.id = id;
                this.price = price;
                this.isPizza=b;
                this.doughType=doughType;
                this.pizzaSize=pizzaSize;
                this.pizza=pizza;
                this.pizzaSizeName=pizzaSizeName;
        }




        @Override
        public boolean equals(Object o) {
                if (this == o) {
                        return true;
                }
                if (o == null || getClass() != o.getClass()) {
                        return false;
                }
                ItemInCartDTO that = (ItemInCartDTO) o;
                if(!isPizza) {
                        return Objects.equals(id, that.id)&&
                                Objects.equals(isPizza, that.isPizza);
                }
                return  Objects.equals(id, that.id) &&
                        Objects.equals(isPizza, that.isPizza)&&
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

        @Override
        public String toString() {
                if(!isPizza){
                        return name;
                }
                else{
                        return String.format("%s pizza %s from %s dough", this.pizzaSize.getName(),this.name,this.doughType);
                }
        }
}
