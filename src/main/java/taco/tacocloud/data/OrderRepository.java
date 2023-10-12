package taco.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import taco.tacocloud.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder,Long> {
   // TacoOrder save(TacoOrder order);
}
