package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;

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
            String testInput = "-1";

            //Act
            actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if int more than cap")
        void falseIfMoreThanCap() {
            //Arrange
            String testInput = "6";

            //Act
            actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if int valid")
        void trueIfValid() {
            //Arrange
            String testInput = "4";

            //Act
            actual = Validate.integer(testInput, VALID_CAP);

            //Assert
            assertTrue(actual);
        }

        @Test
        @DisplayName("Returns true if int 0 and cap 0")
        void trueIfInput0AndCap0() {
            //Arrange
            String testInput = "0";

            //Act
            actual = Validate.integer(testInput, 0);

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
            actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            actual = Validate.string(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string valid")
        void trueIfValid() {
            //Arrange
            String testInput = "Joseph";

            //Act
            actual = Validate.string(testInput);

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
            actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            actual = Validate.phone(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string valid")
        void trueIfValid() {
            //Arrange
            String testInput = "01234567890";

            //Act
            actual = Validate.phone(testInput);

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
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string has no .")
        void falseIfNoDot() {
            //Arrange
            String testInput = "joseph@examplecom";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text before @")
        void falseIfNoTextBeforeAt() {
            //Arrange
            String testInput = "@example.com";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text after .")
        void falseIfNoTextAfterDot() {
            //Arrange
            String testInput = "joseph@example.";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if no text between @ and .")
        void falseIfNoTextBetweenAtAndDot() {
            //Arrange
            String testInput = "joseph@.com";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string valid")
        void trueIfValid() {
            //Arrange
            String testInput = "joseph@example.com";

            //Act
            actual = Validate.email(testInput);

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class YesNo {
        @Test
        @DisplayName("Returns false if string not 'y' or 'n'")
        void falseIfNotYOrN() {
            //Arrange
            String testInput = "g";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string more than one char")
        void falseIfMoreThanOneChar() {
            //Arrange
            String testInput = "yyy";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string numeric")
        void falseIfNumeric() {
            //Arrange
            String testInput = "0";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string special character")
        void falseIfSpecialCharacter() {
            //Arrange
            String testInput = "@";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string empty")
        void falseIfEmpty() {
            //Arrange
            String testInput = "";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string only whitespace")
        void falseIfBlank() {
            //Arrange
            String testInput = " ";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if string null")
        void falseIfNull() {
            //Arrange
            String testInput = null;

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if string 'y'")
        void trueIfY() {
            //Arrange
            String testInput = "y";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertTrue(actual);
        }

        @Test
        @DisplayName("Returns true if string 'n'")
        void trueIfN() {
            //Arrange
            String testInput = "n";

            //Act
            actual = Validate.yesNo(testInput);

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class Details {
        LinkedHashMap<String, String> testInput;

        @BeforeEach
        void beforeEach() {
            testInput = new LinkedHashMap<String, String>();
        }

        @AfterEach
        void afterEach() {
            testInput = null;
        }

        @Test
        @DisplayName("Returns false if any String in map is empty")
        void falseIfAnyEmpty() {
            //Arrange
            testInput.put("", "");

            //Act
            actual = Validate.details(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if any String in map is only whitespace")
        void falseIfAnyBlank() {
            //Arrange
            testInput.put(" ", " ");

            //Act
            actual = Validate.details(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if any String in map is null")
        void falseIfAnyNull() {
            //Arrange
            testInput.put(null, null);

            //Act
            actual = Validate.details(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns false if map is null")
        void falseIfNull() {
            //Arrange
            testInput = null;

            //Act
            actual = Validate.details(testInput);

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if map contains valid entries")
        void trueIfValid() {
            //Arrange
            testInput.put("Nickname", "Joe");

            //Act
            actual = Validate.details(testInput);

            //Assert
            assertTrue(actual);
        }

        @Test
        @DisplayName("Returns true if map is empty")
        void trueIfEmpty() {
            //Act
            actual = Validate.details(testInput);

            //Assert
            assertTrue(actual);
        }
    }
}
