package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidateTest {
    @Nested
    class Integer {
        int VALID_CAP = 5;
        boolean actual;

        @AfterEach
        void afterEach() {
            actual = false;
        }

        @Test
        @DisplayName("Returns false if int less than 0")
        void falseIfLessThan0() {
            //Arrange
            int testInput = -1;

            //Act
            boolean actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertFalse(actual);
        }
    }
}
