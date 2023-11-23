package taco.tacocloud.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    //public PasswordEncoder passwordEncoder(){

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private UserDetailsService userService;

    @Autowired
    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
//unneded old days of extends WebSecurityConfigurerAdapter {
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
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
                .csrf(c->c.disable()).headers(h->h.frameOptions(f->f.disable()).disable())


                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(antMatcher("/h2-console/**")).anonymous()
                        .requestMatchers(antMatcher("/design")).hasRole("USER")
                        .requestMatchers(antMatcher("/orders")).hasRole("USER")
                                .anyRequest().permitAll()
                      //  .requestMatchers(mvc.pattern("/**")).permitAll()

                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/design",true)
                        .failureUrl("/login.html?error=true").successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                System.out.println("Logged user: " + authentication.getName());

                                response.sendRedirect("/");
                            }
                        })
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
