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

    @Nested
    class RemoveContact {
        AddressBook testAddressBook = new AddressBook();
        Contact testContact1 = mock(Contact.class);
        Contact testContact2 = mock(Contact.class);

        @BeforeEach
        void beforeEach() {
            testAddressBook = new AddressBook();
            testContact1 = mock(Contact.class);
            testContact2 = mock(Contact.class);
            testAddressBook.addContact(testContact1);
            testAddressBook.addContact(testContact1);
        }

        @AfterEach
        void afterEach() {
            testAddressBook = null;
            testContact1 = null;
            testContact2 = null;
        }

        @Test
        @DisplayName("Throws exception if index more than list size")
        void exceptionIfIndexMoreThanListSize() {
            //Arrange
            AddressBook testAddressBook = new AddressBook();
            Contact testContact1 = mock(Contact.class);
            Contact testContact2 = mock(Contact.class);
            testAddressBook.addContact(testContact1);
            testAddressBook.addContact(testContact1);

            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> testAddressBook.removeContact(2));
        }
    }
}
