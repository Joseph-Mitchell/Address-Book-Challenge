package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserInteractionTest {
    private MockedStatic<InputReceiver> receiverMock;
    private AddressBook addressBookMock;

    @BeforeEach
    void beforeEach() {
        receiverMock = Mockito.mockStatic(InputReceiver.class);

        addressBookMock = mock(AddressBook.class);
    }

    @AfterEach
    void afterEach() {
        receiverMock.close();
        addressBookMock = null;
    }

    @Nested
    class MainMenu {
        private MockedStatic<UserInteraction> UIMock;

        @BeforeEach
        void beforeEach() {
            UIMock = mockStatic(UserInteraction.class);
            UIMock.when(() -> UserInteraction.mainMenu(any())).thenCallRealMethod();
        }

        @AfterEach
        void afterEach() {
            UIMock.close();
        }

        @Test
        @DisplayName("Doesn't call any method if InputReceiver.receiveInt() returns no matching int")
        void doesNotCallIfNoMatch() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(-1);

            //Act
            UserInteraction.mainMenu(addressBookMock);

            //Assert
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only displayContacts() if InputReceiver.receiveInt() returns matching int")
        void callsDisplayContacts() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(0);

            //Act
            UserInteraction.mainMenu(addressBookMock);

            //Assert
            UIMock.verify(() -> UserInteraction.displayContacts(any()));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only addContact() if InputReceiver.receiveInt() returns matching int")
        void callsAddContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            UserInteraction.mainMenu(addressBookMock);

            //Assert
            UIMock.verify(() -> UserInteraction.addContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }
    }

    @Nested
    class AddContact {
        private String testFirstName = "Joseph";
        private String testLastName = "Mitchell";
        private String testPhone = "01234567891";
        private String testEmail = "joseph-mitchell@example.com";
        private LinkedHashMap<String, String> testDetails = new LinkedHashMap<String, String>();

        @Test
        @DisplayName("Calls all expected methods in InputReceiver")
        void callsInputReceiver() {
            //Arrange
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                UserInteraction.addContact(addressBookMock);

                //Assert
                receiverMock.verify(InputReceiver::receiveString, times(2));
                receiverMock.verify(InputReceiver::receivePhone, times(1));
                receiverMock.verify(InputReceiver::receiveEmail, times(1));
                receiverMock.verify(InputReceiver::receiveDetails, times(1));
            }
        }

        @Test
        @DisplayName("Calls Contact constructor with expected parameters")
        void callsContactConstructor() {
            //Arrange
            try (MockedConstruction<Contact> contactMock = mockConstruction(Contact.class, (mock, context) -> {
                when(mock.getFirstName()).thenReturn((String)context.arguments().get(0));
                when(mock.getLastName()).thenReturn((String)context.arguments().get(1));
                when(mock.getPhone()).thenReturn((String)context.arguments().get(2));
                when(mock.getEmail()).thenReturn((String)context.arguments().get(3));
                when(mock.getDetails()).thenReturn((LinkedHashMap<String, String>) context.arguments().get(4));
            })) {

                receiverMock.when(InputReceiver::receiveString).thenReturn(testFirstName, testLastName);
                receiverMock.when(InputReceiver::receivePhone).thenReturn(testPhone);
                receiverMock.when(InputReceiver::receiveEmail).thenReturn(testEmail);
                receiverMock.when(InputReceiver::receiveDetails).thenReturn(testDetails);

                //Act
                UserInteraction.addContact(addressBookMock);

                //Assert
                assertEquals(testFirstName, contactMock.constructed().get(0).getFirstName());
                assertEquals(testLastName, contactMock.constructed().get(0).getLastName());
                assertEquals(testPhone, contactMock.constructed().get(0).getPhone());
                assertEquals(testEmail, contactMock.constructed().get(0).getEmail());
                assertEquals(testDetails, contactMock.constructed().get(0).getDetails());
            }
        }

        @Test
        @DisplayName("Calls AddressBook.addContact()")
        void callsAddressBookAddContact() {
            //Arrange
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                UserInteraction.addContact(addressBookMock);

                //Assert
                verify(addressBookMock).addContact(notNull());
            }
        }
    }

    @Nested
    class DisplayContact {
        @Test
        @DisplayName("Calls ContactPrinter.printAllContacts() with list of contacts from addressBook")
        void callPrintContactsCorrectly() {
            //Arrange
            ArrayList<Contact> contacts = new ArrayList<>();
            when(addressBookMock.getContacts()).thenReturn(contacts);
            try(MockedStatic<ContactPrinter> printerMock = mockStatic(ContactPrinter.class)) {
                //Act
                UserInteraction.displayContacts(addressBookMock);

                //Assert
                printerMock.verify(() -> ContactPrinter.printAllContacts(contacts));
            }
        }
    }
}
