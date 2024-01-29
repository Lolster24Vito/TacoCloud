package taco.tacocloud;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class Ingredient {
    @Id
    private  String id;
    private  String name;
    @Enumerated(EnumType.STRING)
    private  Type type;
    public  enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
