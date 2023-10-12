package taco.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import taco.tacocloud.TacoUser;

public interface UserRepository extends CrudRepository<TacoUser,Long> {
        TacoUser findByUsername(String username);
}
