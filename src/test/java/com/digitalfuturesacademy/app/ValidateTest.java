package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        @Test
        @DisplayName("Returns false if int more than cap")
        void falseIfMoreThanCap() {
            //Arrange
            int testInput = 6;

            //Act
            boolean actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if int valid")
        void trueIfValid() {
            //Arrange
            int testInput = 4;

            //Act
            boolean actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertTrue(actual);
        }
    }
}
