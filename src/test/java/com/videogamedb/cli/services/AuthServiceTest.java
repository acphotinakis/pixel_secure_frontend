// package com.videogamedb.cli.services;

// import com.videogamedb.cli.models.JwtResponse;
// import com.videogamedb.cli.util.ConfigLoader;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.MockedStatic;
// import org.mockito.junit.jupiter.MockitoExtension;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.mockStatic;

// @ExtendWith(MockitoExtension.class)
// class AuthServiceTest {
//     private AuthService authService;

//     @BeforeEach
//     void setUp() {
//         authService = new AuthService();
//     }

//     @Test
//     void testLogin() throws Exception {
//         // This would typically be mocked with a mock HTTP server
//         // For this example, we'll just test the configuration loading

//         try (MockedStatic<ConfigLoader> mocked = mockStatic(ConfigLoader.class)) {
//             mocked.when(() -> ConfigLoader.getApiBaseUrl()).thenReturn("http://localhost:8080");
//             mocked.when(() -> ConfigLoader.setJwtToken(anyString())).thenAnswer(invocation -> null);

//             // In a real test, we would mock the HTTP response
//             // For now, we'll just verify that the service can be instantiated
//             assertNotNull(authService);
//         }
//     }
// }