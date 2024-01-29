package taco.tacocloud;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import taco.tacocloud.data.IngredientRepository;
import taco.tacocloud.Ingredient.Type;
import taco.tacocloud.data.UserRepository;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo) {
        return args -> {
           // repo.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
          //  userRepo.save(new TacoUser("admin",	"$2a$10$oBEqTTScb6BEQbvg.RG9nu1x8g3n3Za.72p2uFhJDYfvB01cVTnbe","admin","0998761220","admin","admin","admin","123456"));
                    //
        };
    }

}
