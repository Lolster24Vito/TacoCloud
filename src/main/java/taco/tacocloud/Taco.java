package taco.tacocloud;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Taco {

    private Date createdAt=new Date();
    @NotNull
    @Size(min = 3,message = "Name must be at least 3 character")
    private String name;

    //UDT=User Defined type
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients=new ArrayList<>();

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
}
