package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(false, true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(testScanner, times(2)).nextInt();
        }

        @Test
        @DisplayName("Retakes user input if input.nextInt() throws exception")
        void retakesInputIfException() {
            //Arrange
            Scanner testScanner = mock(Scanner.class);
            when(testScanner.nextInt()).thenThrow(InputMismatchException.class).thenReturn(0);
            InputReceiver.setInput(testScanner);

            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(testScanner, times(2)).nextInt();
        }

        @Test
        @DisplayName("Returns correct int if Validate.int() returns true and no exceptions thrown")
        void returnsCorrectly() {
            //Arrange
            int testInput = 0;
            Scanner testScanner = mock(Scanner.class);
            when(testScanner.nextInt()).thenReturn(testInput);
            InputReceiver.setInput(testScanner);

            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            int actual = InputReceiver.receiveInt(testCap);

            //Assert
            assertEquals(testInput, actual);
        }
    }
}
