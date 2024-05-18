package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateTest {
    boolean actual;

    @AfterEach
    void afterEach() {
        actual = false;
    }

    @Nested
    class Integer {
        int VALID_CAP = 5;

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

        @Test
        @DisplayName("Returns true if int 0 and cap 0")
        void trueIfInput0AndCap0() {
            //Arrange
            int testInput = 0;

            //Act
            boolean actual = Validate.integer(testInput, 0);

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class StringTests {
        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            boolean actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            boolean actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            boolean actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string valid")
        void trueIfValid() {
            //Arrange
            String testInput = "Joseph";

            //Act
            boolean actual = Validate.string(testInput);

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class Phone {
        @Test
        @DisplayName("Returns false if string has non-numeric characters")
        void falseIfNonNumeric() {
            //Arrange
            String testInput = "abc";

            //Act
            boolean actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            boolean actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            boolean actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            boolean actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string valid")
        void trueIfValid() {
            //Arrange
            String testInput = "01234567890";

            //Act
            boolean actual = Validate.phone(testInput);

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class Email {
        @Test
        @DisplayName("Returns false if string has no @")
        void falseIfNoAt() {
            //Arrange
            String testInput = "josephexample.com";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string has no .")
        void falseIfNoDot() {
            //Arrange
            String testInput = "joseph@examplecom";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text before @")
        void falseIfNoTextBeforeAt() {
            //Arrange
            String testInput = "@example.com";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text after .")
        void falseIfNoTextAfterDot() {
            //Arrange
            String testInput = "joseph@example.";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text between @ and .")
        void falseIfNoTextBetweenAtAndDot() {
            //Arrange
            String testInput = "joseph@.com";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            boolean actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }
//
//        @Test
//        @DisplayName("Returns false if string null")
//        void falseIfNull() {
//            //Arrange
//            String testInput = null;
//
//            //Act
//            boolean actual = Validate.phone(testInput);
//
//            //Assert
//            assertFalse(actual);
//        }
//
//        @Test
//        @DisplayName("Returns true if string valid")
//        void trueIfValid() {
//            //Arrange
//            String testInput = "01234567890";
//
//            //Act
//            boolean actual = Validate.phone(testInput);
//
//            //Assert
//            assertTrue(actual);
//        }
    }
}
