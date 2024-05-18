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
    Scanner inputMock;
    final int VALID_CAP = 5;
    int testCap;

    @BeforeEach
    void beforeEach() {
        validateMock = Mockito.mockStatic(Validate.class);
        inputMock = mock(Scanner.class);
        InputReceiver.setInput(inputMock);
    }

    @AfterEach
    void afterEach() {
        testCap = VALID_CAP;
        validateMock.close();
        inputMock.close();
        InputReceiver.setInput(null);
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
            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(inputMock, times(1)).nextInt();
        }

        @Test
        @DisplayName("Retakes user input if Validate.int() returns false")
        void retakesInputIfInvalid() {
            //Arrange
            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(false, true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(inputMock, times(2)).nextInt();
        }

        @Test
        @DisplayName("Retakes user input if input.nextInt() throws exception")
        void retakesInputIfException() {
            //Arrange
            when(inputMock.nextInt()).thenThrow(InputMismatchException.class).thenReturn(0);

            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            InputReceiver.receiveInt(testCap);

            //Assert
            verify(inputMock, times(2)).nextInt();
        }

        @Test
        @DisplayName("Returns correct int if Validate.int() returns true and no exceptions thrown")
        void returnsCorrectly() {
            //Arrange
            int testInput = 0;
            when(inputMock.nextInt()).thenReturn(testInput);

            validateMock.when(() -> Validate.integer(anyInt(), anyInt())).thenReturn(true);

            //Act
            int actual = InputReceiver.receiveInt(testCap);

            //Assert
            assertEquals(testInput, actual);
        }
    }

    @Nested
    class ReceiveString {
        @Test
        @DisplayName("Accepts user input if Validate.string() returns true")
        void acceptsInput() {
            //Arrange
            validateMock.when(() -> Validate.string(any())).thenReturn(true);

            //Act
            InputReceiver.receiveString();

            //Assert
            verify(inputMock, times(1)).nextLine();
        }
    }
}
