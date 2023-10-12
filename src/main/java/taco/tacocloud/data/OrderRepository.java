package taco.tacocloud.data;

import taco.tacocloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
