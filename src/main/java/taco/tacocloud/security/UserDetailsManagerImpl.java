package taco.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import taco.tacocloud.TacoUser;
import taco.tacocloud.data.UserRepository;

@Configuration
public class UserDetailsManagerImpl implements UserDetailsService{
    private UserRepository userRepo;
    public UserDetailsManagerImpl(UserRepository userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TacoUser user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(                "User '" + username + "' not found");
    }
    /*

    @Bean public UserDetailsService userDetailsService(UserRepository userRepo){
    @Bean public UserDetailsService userDetailsService(){
        return username -> {
          TacoUser tacoUser =userRepo.findByUsername(username);
          if(tacoUser !=null)return tacoUser;
          throw new UsernameNotFoundException("User: "+username+" not found");
        };
    }
     */

}
