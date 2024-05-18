package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class AddressBookTest {
    @Nested
    class AddContact {
        private Contact contactMock;

        @BeforeEach
        void beforeEach() {
            contactMock = mock(Contact.class);
        }

        @AfterEach
        void afterEach() {
            contactMock = null;
        }

        @Test
        @DisplayName("Throws exception if Contact is null")
        void exceptionIfNull() {
            //Arrange
            Contact contactMock = null;
            AddressBook addressBook = new AddressBook();

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> addressBook.addContact(contactMock));
        }

        @Test
        @DisplayName("Adds new Contact to contacts list if inputs valid")
        void addToList() {
            //Arrange
            Contact contactMock = mock(Contact.class);
            AddressBook addressBook = new AddressBook();

            //Act
            addressBook.addContact(contactMock);

            //Assert
            assertEquals(contactMock, addressBook.getContact(0));
        }
    }
}
