package taco.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import taco.tacocloud.TacoOrder;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
   // TacoOrder save(TacoOrder order);
}
