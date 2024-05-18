package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputReceiverTest {
    @Nested
    class ReceiveInt {
        @Test
        @DisplayName("Throws error if cap negative")
        void capNegativeThrowsError() {
            //Arrange
            int testInt = -1;

            //Act
            //Assert
            assertThrows(IllegalArgumentException.class, () -> InputReceiver.receiveInt(testInt));
        }
    }
}
