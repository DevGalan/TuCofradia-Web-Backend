package com.devgalan.tucofradia.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.devgalan.tucofradia.dtos.user.LoginUserDto;
import com.devgalan.tucofradia.dtos.user.RegisterUserDto;
import com.devgalan.tucofradia.models.User;
import com.devgalan.tucofradia.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Value("${api.endpoint.base-url}/users")
    private String baseURL;

    private void registerUser(int userNumber, String gmailPrefix) {
        String userNumberStr = String.valueOf(userNumber);
        RegisterUserDto registerUserDto = new RegisterUserDto(
                "User " + userNumberStr,
                gmailPrefix + userNumberStr + "@gmail.com",
                "password" + userNumberStr);
        userController.register(registerUserDto);
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        for (int i = 1; i <= 20; i++) {
            registerUser(i, "user");
        }
    }

    @AfterAll
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallGetRandomUsers_thenReturnRandomUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseURL + "/random")
                .with(httpBasic("user", "user"))
                .param("limit", "8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @Order(1)
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallGetUserById_withValidId_thenReturnUser() throws Exception {
        var newUser = new User();
        newUser.setUsername("delete");
        newUser.setEmail("delete@gmail.com");
        newUser.setPassword("password");
        newUser.setLastLogin(new Date(System.currentTimeMillis()));
        newUser.setRegisterDate(new Date(System.currentTimeMillis()));
        var savedUser = userRepository.save(newUser);
        mockMvc.perform(MockMvcRequestBuilders.get(baseURL + "/{userId}", savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallGetUserById_withInvalidId_thenReturnNotFound() throws Exception {
        Long userId = 100L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseURL + "/{userId}", userId)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallGetUsersByUsername_thenReturnUsers() throws Exception {
        registerUser(1, "secondUser");
        String username = "User 1";
        mockMvc.perform(MockMvcRequestBuilders.get(baseURL + "/search/{username}", username)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallLogin_withValidCredentials_thenReturnUser() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto("user1@gmail.com", "password1");
        mockMvc.perform(MockMvcRequestBuilders.post(baseURL + "/login")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(loginUserDto.getEmail()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallLogin_withInvalidCredentials_thenReturnNotFound() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto("user1@gmail.com", "wrongpassword");
        mockMvc.perform(MockMvcRequestBuilders.post(baseURL + "/login")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginUserDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallRegister_withValidUser_thenReturnCreatedUser() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("John Doe", "test@example.com", "password");
        mockMvc.perform(MockMvcRequestBuilders.post(baseURL + "/register")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallRegister_withExistingEmail_thenReturnBadRequest() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("User 1", "user1@gmail.com", "password");
        mockMvc.perform(MockMvcRequestBuilders.post(baseURL + "/register")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallUpdateUser_withValidId_thenReturnUpdatedUser() throws Exception {
        RegisterUserDto registerUser = new RegisterUserDto("Juan", "juan@gmail.com", "password");

        userController.register(registerUser).getBody();

        var user = userRepository.findByEmail(registerUser.getEmail()).get();

        User updatedUser = new User();

        updatedUser.setId(user.getId());
        updatedUser.setUsername("Updated User");
        updatedUser.setProfileMessage("Updated profile message");
        // updatedUser.setProfilePicture("updated_profile_picture.jpg");
        updatedUser.setPassword("updated_password");

        mockMvc.perform(MockMvcRequestBuilders.put(baseURL + "/{id}", updatedUser.getId())
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedUser)))
                .andExpect(jsonPath("$.id").value(updatedUser.getId()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.profileMessage").value(updatedUser.getProfileMessage()));
                // .andExpect(jsonPath("$.profilePicture").value(updatedUser.getProfilePicture()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallUpdateUser_withInvalidId_thenReturnNotFound() throws Exception {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("Updated User");
        updatedUser.setProfileMessage("Updated profile message");
        // updatedUser.setProfilePicture("updated_profile_picture.jpg");
        updatedUser.setPassword("updated_password");

        mockMvc.perform(MockMvcRequestBuilders.put(baseURL + "/{id}", userId)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallDeleteUser_withValidId_thenReturnUserDeleted() throws Exception {
        var newUser = new User();
        newUser.setUsername("delete");
        newUser.setEmail("delete@gmail.com");
        newUser.setPassword("password");
        newUser.setLastLogin(new Date(System.currentTimeMillis()));
        newUser.setRegisterDate(new Date(System.currentTimeMillis()));
        var savedUser = userRepository.save(newUser);
        mockMvc.perform(MockMvcRequestBuilders.delete(baseURL + "/{id}", savedUser.getId())
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "ADMIN" })
    public void whenCallDeleteUser_withInvalidId_thenReturnNotFound() throws Exception {
        Long userId = 100L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseURL + "/{id}", userId)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Helper method to convert object to JSON string
    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}