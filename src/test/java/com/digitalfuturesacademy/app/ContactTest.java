package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class ContactTest {
    @Nested
    class Constructor {
        private String firstName = "";
        private String lastName = "";
        private String phone = "";
        private String email = "";
        private LinkedHashMap<String, String> details = new LinkedHashMap<String, String>();
        MockedStatic<Validate> validateMock;

        @BeforeEach
        void beforeEach() {
            validateMock = Mockito.mockStatic(Validate.class);
            validateMock.when(() -> Validate.string(any())).thenReturn(true);
            validateMock.when(() -> Validate.phone(any())).thenReturn(true);
            validateMock.when(() -> Validate.email(any())).thenReturn(true);
            validateMock.when(() -> Validate.details(any())).thenReturn(true);
        }

        @AfterEach
        void afterEach() {
            validateMock.close();
        }

        @Test
        @DisplayName("Throws exception if Validate.string returns false for firstName")
        void exceptionIfFirstNameInvalid() {
            //Arrange
            validateMock.when(() -> Validate.string(any())).thenReturn(false, true);

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, phone, email, details));
        }

        @Test
        @DisplayName("Throws exception if Validate.string returns false for lastName")
        void exceptionIfLastNameInvalid() {
            //Arrange
            validateMock.when(() -> Validate.string(any())).thenReturn(true, false);

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, phone, email, details));
        }
    }
}
