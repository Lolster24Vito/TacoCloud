package taco.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import taco.tacocloud.TacoUser;
import taco.tacocloud.data.UserRepository;

@Configuration
public class UserDetailsManagerImpl {
    private UserRepository userRepo;
    public UserDetailsManagerImpl(UserRepository userRepo){
        this.userRepo=userRepo;
    }
    /*

    @Bean public UserDetailsService userDetailsService(UserRepository userRepo){

     */
    @Bean public UserDetailsService userDetailsService(){
        return username -> {
          TacoUser tacoUser =userRepo.findByUsername(username);
          if(tacoUser !=null)return tacoUser;
          throw new UsernameNotFoundException("User: "+username+" not found");
        };
    }
}
