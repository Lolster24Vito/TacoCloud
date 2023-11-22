package taco.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //antMatchers have been deprecated and removed with Spring Security 6.0

   /*
   //book not working version
   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http
                .authorizeRequests()
                .requestMatchers("/design", "/orders").hasAnyRole("USER")
                .requestMatchers("/","/**").permitAll()
                .and()
                .build();
    }*/
    //working version
   @Bean
   MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
       return new MvcRequestMatcher.Builder(introspector);
   }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        HttpSecurity httpSecurity = http


                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher("/design")).hasRole("USER")
                        .requestMatchers(antMatcher("/orders")).hasRole("USER")
                        .requestMatchers(antMatcher("/login")).permitAll()
                        .requestMatchers(mvc.pattern("/**")).permitAll()

                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").defaultSuccessUrl("/design",true)
                        .permitAll()
                )

                ;


        return httpSecurity.build();
    }



/* example
@Bean
MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
}

@Bean
SecurityFilterChain appSecurity(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(antMatcher("/my-servlet/*")).hasRole("USER")
            .requestMatchers(mvc.pattern("/spring-mvc-controller/**")).hasRole("USER")
            .anyRequest().authenticated()
        )
        // ...
    return http.build();
}
 */
}
