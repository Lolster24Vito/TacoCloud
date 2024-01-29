package taco.tacocloud.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import taco.tacocloud.TacoOrder;
import taco.tacocloud.TacoUser;
import taco.tacocloud.data.OrderRepository;
import taco.tacocloud.data.UserRepository;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final UserRepository userRepository;

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo,
                           UserRepository userRepository) {
        this.orderRepo = orderRepo;
        this.userRepository = userRepository;
    }


    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors erros,
                               SessionStatus sessionStatus, @AuthenticationPrincipal TacoUser user){

        if(erros.hasErrors()){
            log.info("Error happened in tacoOrder");
            return "orderForm";
        }
        order.setUser(user);
        orderRepo.save(order);
        log.info("Order submitted:{}",order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
