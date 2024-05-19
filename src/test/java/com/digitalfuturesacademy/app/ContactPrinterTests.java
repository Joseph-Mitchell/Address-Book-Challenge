package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        void printsContactCorrectly() {
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

            ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOutput));

            //Act
            ContactPrinter.printContact(contactMock);

            //Assert
            String actual = testOutput.toString();
            assertEquals("""
                    --------------------
                    First Name: %s
                    
                    Last Name: %s
                    
                    Phone: %s
                    
                    Email: %s
                    --------------------""".formatted(testFirstName, testLastName, testPhone, testEmail), actual);
        }
    }
}
