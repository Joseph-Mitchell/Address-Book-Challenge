package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;
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
        @DisplayName("Retakes user input if Validate.int() returns false")
        void retakesInputIfInvalid() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("0");
            validateMock.when(() -> Validate.integer(any(), anyInt())).thenReturn(false, true);

            //Act
            muteSystemOut(() ->InputReceiver.receiveInt(testCap));

            //Assert
            verify(inputMock, times(2)).nextLine();
        }

        @Test
        @DisplayName("Retakes user input if input.nextInt() throws exception")
        void retakesInputIfException() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenThrow(InputMismatchException.class).thenReturn("0");

            validateMock.when(() -> Validate.integer(any(), anyInt())).thenReturn(true);

            //Act
            muteSystemOut(() ->InputReceiver.receiveInt(testCap));

            //Assert
            verify(inputMock, times(2)).nextLine();
        }

        @Test
        @DisplayName("Returns correct int if Validate.int() returns true and no exceptions thrown")
        void returnsCorrectly() throws Exception {
            //Arrange
            String testInput = "0";
            when(inputMock.nextLine()).thenReturn(testInput);

            validateMock.when(() -> Validate.integer(any(), anyInt())).thenReturn(true);

            //Act
            muteSystemOut(() -> {
                int actual = InputReceiver.receiveInt(testCap);

                //Assert
                assertEquals(0, actual);
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {5, 3, 6, 8})
        @DisplayName("Print message if input was invalid")
        void printMessageIfBadInput(int cap) throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("10");
            validateMock.when(() -> Validate.integer(any(), anyInt())).thenReturn(false, true);

            //Act
            String actual = tapSystemOutNormalized(() -> InputReceiver.receiveInt(cap));

            //Assert
            assertEquals("Please enter a number between 0 and %s\n".formatted(cap), actual);
        }
    }

    @Nested
    class ReceiveString {
        @Test
        @DisplayName("Retakes user input if Validate.string() returns false")
        void retakesInput() throws Exception {
            //Arrange
            validateMock.when(() -> Validate.string(any())).thenReturn(false).thenReturn(true);

            //Act
            muteSystemOut(InputReceiver::receiveString);

            //Assert
            verify(inputMock, times(2)).nextLine();
        }

        @Test
        @DisplayName("Print message if input was invalid")
        void printMessageIfInvalid() throws Exception {
            //Arrange
            validateMock.when(() -> Validate.string(any())).thenReturn(false).thenReturn(true);

            //Act
            String actual = tapSystemOutNormalized(InputReceiver::receiveString);

            //Assert
            assertTrue(actual.contains("Please enter at least one character"));
        }

        @Test
        @DisplayName("Returns correct String if Validate.string() returns true")
        void acceptsInput() {
            //Arrange
            String testInput = "test";
            when(inputMock.nextLine()).thenReturn(testInput);

            validateMock.when(() -> Validate.string(any())).thenReturn(true);

            //Act
            String actual = InputReceiver.receiveString();

            //Assert
            verify(inputMock, times(1)).nextLine();
            assertEquals(actual, testInput);
        }
    }

    @Nested
    class ReceivePhone {
        AddressBook addressBookMock;

        @BeforeEach
        void beforeEach() {
            addressBookMock = mock(AddressBook.class);
        }

        @AfterEach
        void afterEach() {
            addressBookMock = null;
        }

        @Test
        @DisplayName("Print message if input was invalid")
        void printMessageIfInvalid() throws Exception {
            //Arrange
            validateMock.when(() -> Validate.phone(any())).thenReturn(false).thenReturn(true);

            //Act
            String actual = tapSystemOutNormalized(() -> InputReceiver.receivePhone(addressBookMock.getContacts()));

            //Assert
            assertTrue(actual.contains("Please enter a number"));
        }

        @Test
        @DisplayName("Retakes user input if matched existing contact phone")
        void printMessageIfExisting() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("0").thenReturn("1");
            validateMock.when(() -> Validate.phone(any())).thenReturn(true).thenReturn(true);
            Contact contactMock = mock(Contact.class);
            when(contactMock.getPhone()).thenReturn("0");
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);
            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            String actual = tapSystemOutNormalized(() -> InputReceiver.receivePhone(addressBookMock.getContacts()));

            //Assert
            assertTrue(actual.contains("Phone already used by another contact"));
        }

        @Test
        @DisplayName("Returns correct String if Validate.phone() returns true")
        void acceptsInput() {
            //Arrange
            String testInput = "test";
            when(inputMock.nextLine()).thenReturn(testInput);

            validateMock.when(() -> Validate.phone(any())).thenReturn(true);

            //Act
            String actual = InputReceiver.receivePhone(addressBookMock.getContacts());

            //Assert
            verify(inputMock, times(1)).nextLine();
            assertEquals(actual, testInput);
        }
    }

    @Nested
    class ReceiveEmail {
        AddressBook addressBookMock;

        @BeforeEach
        void beforeEach() {
            addressBookMock = mock(AddressBook.class);
        }

        @AfterEach
        void afterEach() {
            addressBookMock = null;
        }

        @Test
        @DisplayName("Retakes user input if Validate.email() returns false")
        void retakesInput() throws Exception {
            //Arrange
            validateMock.when(() -> Validate.email(any())).thenReturn(false).thenReturn(true);

            //Act
            muteSystemOut(() -> InputReceiver.receiveEmail(addressBookMock.getContacts()));

            //Assert
            verify(inputMock, times(2)).nextLine();
        }

        @Test
        @DisplayName("Retakes user input if matched existing contact email")
        void printMessageIfExisting() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("test").thenReturn("testnew");
            validateMock.when(() -> Validate.email(any())).thenReturn(true);
            Contact contactMock = mock(Contact.class);
            when(contactMock.getEmail()).thenReturn("test");
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);
            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            String actual = tapSystemOutNormalized(() -> InputReceiver.receiveEmail(addressBookMock.getContacts()));

            //Assert
            assertTrue(actual.contains("Email already used by another contact"));
        }

        @Test
        @DisplayName("Retakes user input if Validate.email() returns false")
        void printMessageIfInvalid() throws Exception {
            //Arrange
            validateMock.when(() -> Validate.email(any())).thenReturn(false).thenReturn(true);

            //Act
            String actual = tapSystemOutNormalized(() -> InputReceiver.receiveEmail(addressBookMock.getContacts()));

            //Assert
            assertTrue(actual.contains("Please enter an email (e.g.: person@example.com)"));
        }

        @Test
        @DisplayName("Returns correct String if Validate.email() returns true")
        void acceptsInput() {
            //Arrange
            String testInput = "test";
            when(inputMock.nextLine()).thenReturn(testInput);

            validateMock.when(() -> Validate.email(any())).thenReturn(true);

            //Act
            String actual = InputReceiver.receiveEmail(addressBookMock.getContacts());

            //Assert
            verify(inputMock, times(1)).nextLine();
            assertEquals(actual, testInput);
        }
    }

    @Nested
    class ReceiveYesNo {
        @Test
        @DisplayName("Retakes user input if Validate.yesNo() returns false")
        void retakesInput() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("test");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(false).thenReturn(true);

            //Act
            muteSystemOut(InputReceiver::receiveYesNo);

            //Assert
            verify(inputMock, times(2)).nextLine();
        }

        @Test
        @DisplayName("Print message if input was invalid")
        void printMessageIfInvalid() throws Exception {
            //Arrange
            when(inputMock.nextLine()).thenReturn("test");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(false).thenReturn(true);

            //Act
            String actual = tapSystemOutNormalized(InputReceiver::receiveYesNo);

            //Assert
            assertTrue(actual.contains("Please enter 'y' or 'n'"));
        }

        @Test
        @DisplayName("Retakes user input if Validate.yesNo() returns false")
        void acceptsInput() {
            //Arrange
            when(inputMock.nextLine()).thenReturn("test");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(true);

            //Act
            InputReceiver.receiveYesNo();

            //Assert
            verify(inputMock, times(1)).nextLine();
        }

        @Test
        @DisplayName("Returns false if first character of input is not y")
        void falseIfChar1NotY() {
            //Arrange
            when(inputMock.nextLine()).thenReturn("test");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(true);

            //Act
            boolean actual = InputReceiver.receiveYesNo();

            //Assert
            assertFalse(actual);
        }

        @Test
        @DisplayName("Returns true if first character of input is lowercase y")
        void trueIfChar1LowercaseY() {
            //Arrange
            when(inputMock.nextLine()).thenReturn("yogurt");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(true);

            //Act
            boolean actual = InputReceiver.receiveYesNo();

            //Assert
            assertTrue(actual);
        }

        @Test
        @DisplayName("Returns true if first character of input is uppercase y")
        void trueIfChar1UppercaseY() {
            //Arrange
            when(inputMock.nextLine()).thenReturn("Yogurt");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(true);

            //Act
            boolean actual = InputReceiver.receiveYesNo();

            //Assert
            assertTrue(actual);
        }

        @Test
        @DisplayName("Returns true if whitespace before y")
        void trueIfWhitespaceBeforeY() {
            //Arrange
            when(inputMock.nextLine()).thenReturn(" Yogurt");
            validateMock.when(() -> Validate.yesNo(any())).thenReturn(true);

            //Act
            boolean actual = InputReceiver.receiveYesNo();

            //Assert
            assertTrue(actual);
        }
    }

    @Nested
    class ReceiveDetail {
        @Test
        @DisplayName("Returns ArrayList with values given by InputReceiver.receiveString() calls")
        void returnsCorrectly() throws Exception {
            //Arrange
            try (MockedStatic<InputReceiver> receiverMock = Mockito.mockStatic(InputReceiver.class)) {
                String testKey = "Nickname";
                String testValue = "Joe";
                receiverMock.when(InputReceiver::receiveDetail).thenCallRealMethod();
                receiverMock.when(InputReceiver::receiveString).thenReturn(testKey, testValue);

                //Act
                muteSystemOut(() -> {
                    String[] actual = InputReceiver.receiveDetail();

                    //Assert
                    receiverMock.verify(InputReceiver::receiveString, times(2));
                    assertEquals(actual[0], testKey);
                    assertEquals(actual[1], testValue);
                });
            }
        }

        @Test
        @DisplayName("Prompts user for each input")
        void promptsInput() throws Exception {
            //Arrange
            try (MockedStatic<InputReceiver> receiverMock = Mockito.mockStatic(InputReceiver.class)) {
                receiverMock.when(InputReceiver::receiveDetail).thenCallRealMethod();

                //Act
                String actual = tapSystemOutNormalized(InputReceiver::receiveDetail);

                //Assert
                assertTrue(actual.contains("Detail Type (e.g. Address, Nickname, Favourite Color):"));
                assertTrue(actual.contains("Enter Detail:"));
            }
        }
    }

    @Nested
    class ReceiveDetails {
        MockedStatic<InputReceiver> receiverMock;

        @BeforeEach
        void beforeEach() {
            receiverMock = Mockito.mockStatic(InputReceiver.class);
            receiverMock.when(InputReceiver::receiveDetails).thenCallRealMethod();
        }

        @AfterEach
        void afterEach() {
            receiverMock.close();
        }

        @Test
        @DisplayName("Does not call InputReceiver.receiveDetail() if InputReceiver.yesNo() returns false")
        void noDetailsIfYesNoFalse() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);

            //Act
            muteSystemOut(InputReceiver::receiveDetails);

            //Assert
            receiverMock.verify(InputReceiver::receiveDetail, times(0));
        }

        @Test
        @DisplayName("Does not call InputReceiver.receiveDetail() if InputReceiver.yesNo() returns false")
        void confirmNewDetail() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);

            //Act
            String actual = tapSystemOutNormalized(InputReceiver::receiveDetails);

            //Assert
            assertTrue(actual.contains("Add an extra detail? (y/n):"));
        }

        @Test
        @DisplayName("Calls InputReceiver.receiveDetail() once if InputReceiver.yesNo() returns true")
        void getDetailIfYesNoTrue() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true, false);
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {"", ""});

            //Act
            muteSystemOut(InputReceiver::receiveDetails);

            //Assert
            receiverMock.verify(InputReceiver::receiveDetail, times(1));
        }

        @Test
        @DisplayName("Confirm adding detail")
        void confirmAddDetail() throws Exception {
            //Arrange
            String testKey = "TestKey";
            String testValue = "TestValue";
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true, true, false);
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {testKey, testValue});

            //Act
            String actual = tapSystemOutNormalized(InputReceiver::receiveDetails);

            //Assert
            assertTrue(actual.contains("%s: %s".formatted(testKey, testValue)));
            assertTrue(actual.contains("Add this detail? (y/n):"));
            assertTrue(actual.contains("Detail added"));
        }

        @Test
        @DisplayName("Cancel adding detail")
        void cancelAddDetail() throws Exception {
            //Arrange
            String testKey = "TestKey";
            String testValue = "TestValue";
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true, false, false);
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {testKey, testValue});

            //Act
            String actual = tapSystemOutNormalized(InputReceiver::receiveDetails);

            //Assert
            assertTrue(actual.contains("%s: %s".formatted(testKey, testValue)));
            assertTrue(actual.contains("Add this detail? (y/n):"));
            assertTrue(actual.contains("Detail not added"));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 5, 7})
        @DisplayName("Calls InputReceiver.receiveDetail() as many times as InputReceiver.yesNo() returns true")
        void getDetailIfYesNoTrueTwice(int times) throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenAnswer(new Answer<Boolean>() {
                int count = 0;
                public Boolean answer(InvocationOnMock invocation) {return count++ < times*2;}
            });
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {"", ""});

            //Act
            muteSystemOut(InputReceiver::receiveDetails);

            //Assert
            receiverMock.verify(InputReceiver::receiveDetail, times(times));
        }

        @Test
        @DisplayName("Returns expected map")
        void returnsCorrectly() throws Exception {
            //Arrange
            String testKey = "Nickname";
            String testValue = "Joe";

            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true, true, false);
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {testKey, testValue});

            //Act
            muteSystemOut(() -> {
                LinkedHashMap<String, String> actual = InputReceiver.receiveDetails();

                //Assert
                assertEquals(testKey + "=" + testValue, actual.entrySet().toArray()[0].toString());
            });
        }
    }
}
