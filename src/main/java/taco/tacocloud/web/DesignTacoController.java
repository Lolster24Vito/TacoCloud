package taco.tacocloud.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import taco.tacocloud.Ingredient;
import taco.tacocloud.Ingredient.Type;
import taco.tacocloud.Taco;
import taco.tacocloud.TacoOrder;
import taco.tacocloud.data.IngredientRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients=ingredientRepo.findAll();
        Type[] types=Ingredient.Type.values();

        for (Type type:types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
    }
/*
    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
    return ingredients..stream().filter(x->x.getType().equals(type))
            .collect(Collectors.toList());
    }*/
private Iterable<Ingredient> filterByType(
        Iterable<Ingredient> ingredients, Type type) {
    return StreamSupport.stream(ingredients.spliterator(), false)
            .filter(i -> i.getType().equals(type))
            .collect(Collectors.toList());
}
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }
    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){
       if(errors.hasErrors()){
           return "design";
       }
        tacoOrder.addTaco(taco);
       log.info("Processing taco: {}",taco);
       return "redirect:/orders/current";
    }

}
