package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorityInputDto;
import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.UsernameAlreadyExistsException;
import com.mf.minutefictionbackend.models.Authority;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorProfileRepository authorProfileRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {

        user = User.builder()
                .username("testuser")
                .password("encodedpassword123")
                .email("testuseremail@email.com")
                .subscribedToMailing(false)
                .authorities(new HashSet<>(Set.of(new Authority("READER"))))
                .build();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    @DisplayName("Should create correct user")
    void createUserTest() {

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("newuser");
        userInputDto.setPassword("password456");
        userInputDto.setEmail("newuseremail@email.com");
        userInputDto.setSubscribedToMailing(false);

        AuthorityInputDto authorityInputDto = new AuthorityInputDto();
        authorityInputDto.setAuthority("READER");
        userInputDto.setAuthorities(Set.of(authorityInputDto));

        Mockito.when(passwordEncoder.encode(userInputDto.getPassword())).thenReturn("encodedPassword456");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.<User>getArgument(0));

        UserOutputDto createdUserDto = userService.createUser(userInputDto);

        assertEquals("newuser", createdUserDto.getUsername());
        assertEquals("newuseremail@email.com", createdUserDto.getEmail());
        assertFalse(createdUserDto.isSubscribedToMailing());
        assertTrue(createdUserDto.getAuthorities().contains("READER"));

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password456");

    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void createUserWithExistingUsernameTest() {

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("testuser");

        Mockito.when(userRepository.existsByUsername(userInputDto.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(userInputDto));
    }


    @Test
    void getAllUsersTest() {

        User user2 = User.builder()
                .username("justken")
                .password("encodedpassword456")
                .email("kensemail@email.com")
                .subscribedToMailing(false)
                .authorities(Set.of(new Authority("READER")))
                .build();

        List<User> mockUserList = List.of(user, user2);
        Mockito.when(userRepository.findAll()).thenReturn(mockUserList);

        List<UserOutputDto> userList = userService.getAllUsers();

        assertEquals(userList.size(), mockUserList.size());
        assertEquals("testuser", mockUserList.get(0).getUsername());
        assertEquals("justken", mockUserList.get(1).getUsername());
        assertEquals("kensemail@email.com", mockUserList.get(1).getEmail());

    }

    @Test
    @DisplayName("Should return correct user")
    public void getUserByUsernameTest() {

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserOutputDto userDto = userService.getUserByUsername("testuser");

        assertEquals("testuser", userDto.getUsername());
        assertEquals("testuseremail@email.com", userDto.getEmail());
        assertFalse(userDto.isSubscribedToMailing());
        assertFalse(userDto.isHasAuthorProfile());
        assertTrue(userDto.getAuthorities().contains("READER"));

    }

    @Test
    @DisplayName("Should remove correct user from database")
    void deleteUser() {

        String username = "testuser";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        if(user.getAuthorProfile() != null) {
            Mockito.doNothing().when(authorProfileRepository).delete(user.getAuthorProfile());
        }

        userService.deleteUser(username);

        assertTrue(user.getAuthorities().isEmpty());

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);

    }

    @Test
    @DisplayName("Should update correct fields for correct user")
    void updateUserTest() {

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setPassword("password1234");
        userInputDto.setEmail("newemail@email.com");
        userInputDto.setSubscribedToMailing(true);

        Mockito.when(passwordEncoder.encode(userInputDto.getPassword())).thenReturn("encodedPassword1234");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserOutputDto updatedUserDto = userService.updateUser(user.getUsername(), userInputDto);

        assertEquals("testuser", updatedUserDto.getUsername());
        assertEquals("newemail@email.com", updatedUserDto.getEmail());
        assertTrue(updatedUserDto.isSubscribedToMailing());

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password1234");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    @DisplayName("Should return correct user")
    void getUserTest() {

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.getUser(user.getUsername());

        assertEquals("testuser", user.getUsername());

    }

    @Test
    @DisplayName("Should add correct authority to user")
    void addAuthorityTest() {

        String authority = "EDITOR";

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.addAuthority(user.getUsername(), authority);

        assertTrue(user.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("EDITOR")));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    @DisplayName("Should get correct authorities")
    void getAuthoritiesTest() {

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<String> authorityList = userService.getAuthorities(user.getUsername());
        List<String> expectedAuthorities = user.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toList());

        assertEquals(expectedAuthorities, authorityList);

    }

    @Test
    @DisplayName("Should remove correct authority")
    void removeAuthorityTest() {

        User user3 = User.builder()
                .username("barbiereads")
                .password("encodedpassword789")
                .email("barbie@email.com")
                .subscribedToMailing(false)
                .authorities(new HashSet<>(Set.of(new Authority("READER"), new Authority("EDITOR"))))
                .build();

        Mockito.when(userRepository.findByUsername(user3.getUsername())).thenReturn(Optional.of(user3));

        userService.removeAuthority("barbiereads", "EDITOR");

        assertFalse(user.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("EDITOR")));

        Mockito.verify(userRepository, Mockito.times(1)).save(user3);

    }
}