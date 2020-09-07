package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognixia.jump.service.MyUserDetailService;

/**
 * Configuration for Security for the Users of Restaurant Reviews API.
 * @author David Morales and Lori White
 * @version v3 (08/29/2020)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    MyUserDetailService userDetailsService;
    
    /**
     * Configures the Authentication with the custom user detail service.
     * @author David Morales
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    /**
     * Retrieves the password encoder.
     * @author David Morales
     * @return PasswordEncoder - the instance of the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    /**
     * Configures the Http Security for all API requests.
     * @author David Morales and Lori White
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/allUsers").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/update/user").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/add/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/login/username/{email}/password/{password}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/restaurants").permitAll()
                .antMatchers(HttpMethod.GET, "/api/restaurant/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/add/restaurant").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/name").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/imageurl").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/menulink").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/description").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/rating").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/owner").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/phonenumber").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/restaurant/address").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/delete/restaurant").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/add/review").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/reviews/{id}/restaurant").permitAll()
                .antMatchers(HttpMethod.GET, "/api/address/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/address/{street}/street/{zip}/zip").permitAll()
                .antMatchers(HttpMethod.POST, "/api/add/address").hasAnyRole("ADMIN", "USER")
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
				.and().formLogin().permitAll()
				.and().httpBasic()
				.and().logout().permitAll();
    }
}
