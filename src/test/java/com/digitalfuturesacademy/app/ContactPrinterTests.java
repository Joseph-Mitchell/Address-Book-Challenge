package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

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
            ArrayList<Contact> testList = new ArrayList<Contact>() {};
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
    }
}
