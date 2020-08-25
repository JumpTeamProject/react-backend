//package com.cognixia.jump.controller;
//
//import com.cognixia.jump.model.User;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@TestConfiguration
//public class SpringSecurityWebAuxTestConfig {
//
//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
//        User basicUser = new User(1L, "dav@hello.edu", "123", "David", "Mor", 1L, User.Role.ROLE_USER)
//        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_USER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ")
//        ));
//
//        User managerUser = new UserImpl("Manager User", "manager@company.com", "password");
//        UserActive managerActiveUser = new UserActive(managerUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_MANAGER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ"),
//                new SimpleGrantedAuthority("PERM_FOO_WRITE"),
//                new SimpleGrantedAuthority("PERM_FOO_MANAGE")
//        ));
//
//        return new InMemoryUserDetailsManager(Arrays.asList(
//                basicActiveUser, managerActiveUser
//        ));
//    }
//}
