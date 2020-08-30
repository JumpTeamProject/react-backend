package com.cognixia.jump.controller;

import com.cognixia.jump.controller.UserController;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private final String STARTING_URI = "http://localhost:8080/api";

    @MockBean
    private UserRepo repo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsers() throws Exception {
        String uri = STARTING_URI + "/allUsers";

        List<User> allUsers = Arrays.asList(
                new User(1L, "dav@hello.edu", "123", "Dav", "Mor", 1L, User.Role.ROLE_USER),
                new User(2L, "bav@hello.edu", "123", "bav", "Mor", 2L, User.Role.ROLE_USER)
        );

        when( repo.findAll() ).thenReturn(allUsers);

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk());

        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getUserById() throws Exception {
        String uri = STARTING_URI + "/users/{id}";

        User user = new User(1L, "bav@hello.edu", "123", "bav", "Mor", 1L, User.Role.ROLE_USER);
        long id = user.getId();

        when( repo.findById(user.getId()) ).thenReturn(Optional.of(user));

        mockMvc.perform( get(uri, id) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON) );

//        verify(repo, times(1)).findById(id);
//        verifyNoMoreInteractions(repo);
    }

    @Test
    void deleteUser() throws Exception {
        String uri = STARTING_URI + "/delete/user/{id}";
        User create = new User(1L, "bav@hello.edu", "123", "bav", "Mor", 1L, User.Role.ROLE_USER);
        long id = create.getId();
        when( repo.existsById(id) ).thenReturn(true);

        doNothing().when(repo).deleteById(id);

        mockMvc.perform( delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(create) ))
                .andExpect( status().isOk() );

//        verify(repo, times(1)).existsById(id);
//        verify(repo, times(1)).deleteById(id);
//
//        verifyNoMoreInteractions(repo);


    }

    @Test
    void addUser() throws Exception {
        String uri = STARTING_URI + "/add/user";

        User create = new User(1L, "bav@hello.edu", "123", "bav", "Mor", 1L, User.Role.ROLE_USER);
        String email = create.getEmail();

        when( repo.existByEmail(email)).thenReturn(false);
        when( repo.save(any(User.class))).thenReturn(create);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(create)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateUser() throws Exception {
        String uri = STARTING_URI + "/update/user";
        User updated = new User(1L, "bav@hello.edu", "123", "bav", "Mor", 1L, User.Role.ROLE_USER);
        long id = updated.getId();

        when( repo.findById(id) ).thenReturn(Optional.of(updated));

        when( repo.save(any(User.class))).thenReturn(updated);

        mockMvc.perform( put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(updated)))
                .andExpect( status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}