package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class InputReceiverTest {
    MockedStatic<Validate> validateMock;
    final int VALID_CAP = 5;
    int testCap;

    @BeforeEach
    void beforeEach() {
        validateMock = Mockito.mockStatic(Validate.class);
    }

    @AfterEach
    void afterEach() {
        testCap = VALID_CAP;
        validateMock.close();
    }

    @Nested
    class ReceiveInt {
        @Test
        @DisplayName("Throws error if cap negative")
        void capNegativeThrowsError() {
            //Arrange
            testCap = -1;

            //Act
            //Assert
            assertThrows(IllegalArgumentException.class, () -> InputReceiver.receiveInt(testCap));
        }

        @Test
        @DisplayName("Takes user input only once if Validate.int() returns true")
        void takesInputOnceIfValid() {
            //Arrange
            Scanner testScanner = mock(Scanner.class);
            InputReceiver.setInput(testScanner);
            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(testScanner, times(1)).nextInt();
        }

        @Test
        @DisplayName("Retakes user input if Validate.int() returns false")
        void retakesInputIfInvalid() {
            //Arrange
            Scanner testScanner = mock(Scanner.class);
            InputReceiver.setInput(testScanner);
            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(false).thenReturn(true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(testScanner, times(2)).nextInt();
        }
    }
}
