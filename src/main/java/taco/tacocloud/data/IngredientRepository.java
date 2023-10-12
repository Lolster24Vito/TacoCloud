package taco.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import taco.tacocloud.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {

}
