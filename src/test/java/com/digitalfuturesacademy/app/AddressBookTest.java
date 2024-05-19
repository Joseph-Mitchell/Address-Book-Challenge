package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

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
        Contact contactMock1 = mock(Contact.class);
        Contact contactMock2 = mock(Contact.class);

        @BeforeEach
        void beforeEach() {
            testAddressBook = new AddressBook();
            contactMock1 = mock(Contact.class);
            contactMock2 = mock(Contact.class);
            testAddressBook.addContact(contactMock1);
            testAddressBook.addContact(contactMock2);
        }

        @AfterEach
        void afterEach() {
            testAddressBook = null;
            contactMock1 = null;
            contactMock2 = null;
        }

        @Test
        @DisplayName("Throws exception if index more than list size")
        void exceptionIfIndexMoreThanListSize() {
            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> testAddressBook.removeContact(2));
        }

        @Test
        @DisplayName("Throws exception if index less than 0")
        void exceptionIfIndexLessThan0() {
            //Act, Assert
            assertThrows(IllegalArgumentException.class, () -> testAddressBook.removeContact(-1));
        }

        @Test
        @DisplayName("Removes expected element from contacts")
        void removesContact() {
            //Arrange
            ArrayList<Contact> expected = new ArrayList<>();
            expected.add(contactMock1);

            //Act
            testAddressBook.removeContact(1);

            //Assert
            assertEquals(expected, testAddressBook.getContacts());
        }
    }
}
