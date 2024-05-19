package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ContactPrinterTests {
    @Nested
    class PrintAllContacts {
        @Test
        @DisplayName("Throws exception if List is null")
        void exceptionIfNull() {
            //Arrange, Act, Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printAllContacts(null));
        }

        @Test
        @DisplayName("Calls printContact() for each element in contacts")
        void printsEachElement() {
            //Arrange
            Contact contactMock1 = mock(Contact.class);
            Contact contactMock2 = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>() {};
            testList.add(contactMock1);
            testList.add(contactMock2);
            try(MockedStatic<ContactPrinter> printerMock = mockStatic(ContactPrinter.class)) {
                printerMock.when(() -> ContactPrinter.printAllContacts(any())).thenCallRealMethod();
                //Act
                ContactPrinter.printAllContacts(testList);

                //Assert
                printerMock.verify(() -> ContactPrinter.printContact(contactMock1));
                printerMock.verify(() -> ContactPrinter.printContact(contactMock2));
            }
        }
    }

    @Nested
    class PrintContact {
        @Test
        @DisplayName("Throws exception if Contact is null")
        void exceptionIfNull() {
            //Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printContact(null));
        }

        @Test
        @DisplayName("Prints contact correctly when no additional details")
        void printsContactCorrectly() throws Exception {
            //Arrange
            String testFirstName = "Joseph";
            String testLastName = "Mitchell";
            String testPhone = "01234567890";
            String testEmail = "joseph@example.com";
            LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();

            Contact contactMock = mock(Contact.class);
            when(contactMock.getFirstName()).thenReturn(testFirstName);
            when(contactMock.getLastName()).thenReturn(testLastName);
            when(contactMock.getPhone()).thenReturn(testPhone);
            when(contactMock.getEmail()).thenReturn(testEmail);
            when(contactMock.getDetails()).thenReturn(testDetails);

            //Act
            String actual = tapSystemOutNormalized(() -> ContactPrinter.printContact(contactMock));

            //Assert
            assertEquals("""
                    --------------------
                    First Name: %s
                    
                    Last Name: %s
                    
                    Phone: %s
                    
                    Email: %s
                    --------------------""".formatted(testFirstName, testLastName, testPhone, testEmail), actual);
        }

        @Test
        @DisplayName("Prints contact correctly with details")
        void printsContactCorrectlyWithDetails() throws Exception {
            //Arrange
            String testFirstName = "Joseph";
            String testLastName = "Mitchell";
            String testPhone = "01234567890";
            String testEmail = "joseph@example.com";

            String testKey1 = "Nickname";
            String testValue1 = "Joe";
            String testKey2 = "Address";
            String testValue2 = "5 Somewhere Street";
            LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();
            testDetails.put(testKey1, testValue1);
            testDetails.put(testKey2, testValue2);

            Contact contactMock = mock(Contact.class);
            when(contactMock.getFirstName()).thenReturn(testFirstName);
            when(contactMock.getLastName()).thenReturn(testLastName);
            when(contactMock.getPhone()).thenReturn(testPhone);
            when(contactMock.getEmail()).thenReturn(testEmail);
            when(contactMock.getDetails()).thenReturn(testDetails);

            //Act
            String actual = tapSystemOutNormalized(() -> ContactPrinter.printContact(contactMock));

            //Assert
            assertEquals("""
                    --------------------
                    First Name: %s
                    
                    Last Name: %s
                    
                    Phone: %s
                    
                    Email: %s
                    
                    %s: %s
                    
                    %s: %s
                    --------------------""".formatted(testFirstName, testLastName, testPhone, testEmail,
                                                      testKey1, testValue1, testKey2, testValue2),
                    actual);
        }
    }

    @Nested
    class PrintMatchingContacts {
        @Test
        @DisplayName("Throws exception if name empty")
        void exceptionIfEmpty() {
            //Arrange
            String testInput = "";
            ArrayList<Contact> testList = new ArrayList<>();

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printMatchingContacts(testList, testInput));
        }

        @Test
        @DisplayName("Throws exception if name only whitespace")
        void exceptionIfBlank() {
            //Arrange
            String testInput = " ";
            ArrayList<Contact> testList = new ArrayList<>();

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printMatchingContacts(testList, testInput));
        }

        @Test
        @DisplayName("Throws exception if name null")
        void exceptionIfNull() {
            //Arrange
            String testInput = null;
            ArrayList<Contact> testList = new ArrayList<>();

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printMatchingContacts(testList, testInput));
        }
    }
}
