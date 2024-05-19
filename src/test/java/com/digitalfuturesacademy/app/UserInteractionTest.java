package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserInteractionTest {
    private MockedStatic<InputReceiver> receiverMock;
    private MockedStatic<ContactPrinter> printerMock;
    private AddressBook addressBookMock;

    @BeforeEach
    void beforeEach() {
        receiverMock = mockStatic(InputReceiver.class);
        printerMock = mockStatic(ContactPrinter.class);
        addressBookMock = mock(AddressBook.class);
    }

    @AfterEach
    void afterEach() {
        receiverMock.close();
        printerMock.close();
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

        @Test
        @DisplayName("Calls only removeContact() if InputReceiver.receiveInt() returns matching int")
        void callsRemoveContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(2);

            //Act
            UserInteraction.mainMenu(addressBookMock);

            //Assert
            UIMock.verify(() -> UserInteraction.removeContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only editContact() if InputReceiver.receiveInt() returns matching int")
        void callsEditContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(3);

            //Act
            UserInteraction.mainMenu(addressBookMock);

            //Assert
            UIMock.verify(() -> UserInteraction.editContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
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
    class DisplayContacts {
        @Test
        @DisplayName("Calls ContactPrinter.printAllContacts() with list of contacts from addressBook")
        void callPrintContactsCorrectly() {
            //Arrange
            ArrayList<Contact> contacts = new ArrayList<>();
            when(addressBookMock.getContacts()).thenReturn(contacts);

            //Act
            UserInteraction.displayContacts(addressBookMock);

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(contacts));
        }
    }

    @Nested
    class RemoveContact {
        @Test
        @DisplayName("Prints message if no contacts")
        void messageIfNoContacts() throws Exception {
            //Arrange
            when(addressBookMock.getContacts()).thenReturn(new ArrayList<>());

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.removeContact(addressBookMock));

            //Assert
            assertEquals("There are no contacts in the address book.\n", actual);
        }

        @Test
        @DisplayName("Calls ContactPrinter.printAllContacts()")
        void printsContracts() {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            UserInteraction.removeContact(addressBookMock);

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(any()));
        }

        @Test
        @DisplayName("Calls AddressBook.removeContact() with return value of InputReceiver.receiveInt()")
        void removesChosenContact() {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            int testInput = 1;
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(testInput);

            //Act
            UserInteraction.removeContact(addressBookMock);

            //Assert
            verify(addressBookMock).removeContact(testInput);
        }
    }

    @Nested
    class EditContact {
        Contact contactMock0;
        Contact contactMock1;
        Contact contactMock2;
        ArrayList<Contact> testList;

        @BeforeEach
        void beforeEach() {
            contactMock0 = mock(Contact.class);
            contactMock1 = mock(Contact.class);
            contactMock2 = mock(Contact.class);
            testList = new ArrayList<>();

            testList.add(contactMock0);
            testList.add(contactMock1);
            testList.add(contactMock2);

            when(addressBookMock.getContacts()).thenReturn(testList);
            when(addressBookMock.getContact(0)).thenReturn(contactMock0);
            when(addressBookMock.getContact(1)).thenReturn(contactMock1);
            when(addressBookMock.getContact(2)).thenReturn(contactMock2);
        }

        @AfterEach
        void afterEach() {
            contactMock0 = null;
            contactMock1 = null;
            contactMock2 = null;
            testList = null;
        }

        @Test
        @DisplayName("Prints message if no contacts")
        void messageIfNoContacts() throws Exception {
            when(addressBookMock.getContacts()).thenReturn(new ArrayList<>());

            //Act
            String actual = tapSystemOutNormalized(() -> {
                UserInteraction.editContact(addressBookMock);
            });

            //Assert
            assertEquals("There are no contacts in the address book.\n", actual);
        }

        @Test
        @DisplayName("Calls ContactPrinter.printAllContacts()")
        void printsAllContacts() throws Exception {
            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(any()));
        }

        @Test
        @DisplayName("Calls ContactPrinter.printContact() with Contact chosen by InputReceiver.receiveInt() return value")
        void printsChosenContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            printerMock.verify(() -> ContactPrinter.printContact(any()));
        }

        @Test
        @DisplayName("Calls Contact.setFirstName() with InputReceiver.receiveString() return value if second InputReceiver.receiveInt() returns 0")
        void setsContactName() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 0);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            verify(contactMock1).setFirstName(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setLastName() with InputReceiver.receiveString() return value if second InputReceiver.receiveInt() returns 1")
        void setsContactLastName() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 1);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            verify(contactMock1).setLastName(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setPhone() with InputReceiver.receivePhone() return value if second InputReceiver.receiveInt() returns 2")
        void setsContactPhone() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 2);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receivePhone).thenReturn(testInput);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            verify(contactMock1).setPhone(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setEmail() with InputReceiver.receiveEmail() return value if second InputReceiver.receiveInt() returns 3")
        void setsContactEmail() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 3);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveEmail).thenReturn(testInput);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            verify(contactMock1).setEmail(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setEmail() with InputReceiver.receiveEmail() return value if second InputReceiver.receiveInt() returns 3")
        void setsContactDetail() {
            //Arrange
            LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();
            String testDetailKey = "Nickname";
            testDetails.put(testDetailKey, "Joe");
            testDetails.put("Address", "123 Place St");
            when(contactMock1.getDetails()).thenReturn(testDetails);

            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 4);

            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            //Act
            UserInteraction.editContact(addressBookMock);

            //Assert
            verify(contactMock1).setDetail(testDetailKey, testInput);
        }
    }
}
