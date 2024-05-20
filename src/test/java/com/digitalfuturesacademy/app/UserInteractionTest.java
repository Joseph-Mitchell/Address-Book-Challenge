package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.github.stefanbirkner.systemlambda.SystemLambda.muteSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.*;
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
        void doesNotCallIfNoMatch() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(-1);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only displayContacts() if InputReceiver.receiveInt() returns matching int")
        void callsDisplayContacts() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(0);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.displayContacts(any()));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only addContact() if InputReceiver.receiveInt() returns matching int")
        void callsAddContact() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.addContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only removeContact() if InputReceiver.receiveInt() returns matching int")
        void callsRemoveContact() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(2);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.removeContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only editContact() if InputReceiver.receiveInt() returns matching int")
        void callsEditContact() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(3);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.editContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.findContact(any()), times(0));
        }

        @Test
        @DisplayName("Calls only findContact() if InputReceiver.receiveInt() returns matching int")
        void callsFindContacts() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(4);

            //Act
            muteSystemOut(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            UIMock.verify(() -> UserInteraction.findContact(any()));
            UIMock.verify(() -> UserInteraction.displayContacts(any()), times(0));
            UIMock.verify(() -> UserInteraction.addContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.removeContact(any()), times(0));
            UIMock.verify(() -> UserInteraction.editContact(any()), times(0));
        }

        @Test
        @DisplayName("Print message showing options")
        void printOptions() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(-1);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.mainMenu(addressBookMock));

            //Assert
            assertEquals("""
                    Please choose an option:
                    
                    0. Display contacts
                    1. Add a new contact
                    2. Remove a contact
                    3. Edit a contact
                    4. Search contacts
                    """, actual);
        }
    }

    @Nested
    class AddContact {
        private final String testFirstName = "Joseph";
        private final String testLastName = "Mitchell";
        private final String testPhone = "01234567891";
        private final String testEmail = "joseph-mitchell@example.com";
        private final LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();

        @Test
        @DisplayName("Calls all expected methods in InputReceiver")
        void callsInputReceiver() throws Exception {
            //Arrange
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                muteSystemOut(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                receiverMock.verify(InputReceiver::receiveString, times(2));
                receiverMock.verify(InputReceiver::receivePhone, times(1));
                receiverMock.verify(InputReceiver::receiveEmail, times(1));
                receiverMock.verify(InputReceiver::receiveDetails, times(1));
            }
        }

        @Test
        @DisplayName("Prompts user for each input")
        void promptForInput() throws Exception {
            //Arrange
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                String actual = tapSystemOutNormalized(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                assertTrue(actual.contains("""
                        First Name:
                        Last Name:
                        Phone:
                        Email:
                        """));
            }
        }

        @Test
        @DisplayName("Calls Contact constructor with expected parameters")
        void callsContactConstructor() throws Exception {
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
                muteSystemOut(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                assertEquals(testFirstName, contactMock.constructed().get(0).getFirstName());
                assertEquals(testLastName, contactMock.constructed().get(0).getLastName());
                assertEquals(testPhone, contactMock.constructed().get(0).getPhone());
                assertEquals(testEmail, contactMock.constructed().get(0).getEmail());
                assertEquals(testDetails, contactMock.constructed().get(0).getDetails());
            }
        }

        @Test
        @DisplayName("Confirm adding contact with user")
        void confirmWithUser() throws Exception {
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
                String actual = tapSystemOutNormalized(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                assertTrue(actual.contains("Add this contact? (y/n):"));
                printerMock.verify(() -> ContactPrinter.printContact(any()));
                receiverMock.verify(InputReceiver::receiveYesNo);
            }
        }

        @Test
        @DisplayName("Calls AddressBook.addContact()")
        void callsAddressBookAddContact() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true);
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                muteSystemOut(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                verify(addressBookMock).addContact(notNull());
            }
        }

        @Test
        @DisplayName("Print confirmation message when contact is added to address book")
        void printMessageWhenAdded() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true);
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                String actual = tapSystemOutNormalized(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                assertTrue(actual.contains("Contact was added"));
            }
        }

        @Test
        @DisplayName("Print confirmation message when process is cancelled")
        void printMessageWhenCanceled() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                String actual = tapSystemOutNormalized(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                assertTrue(actual.contains("Contact was not added"));
            }
        }

        @Test
        @DisplayName("AddressBook.addContact() not called if user cancels")
        void doNotAddIfUserCancels() throws Exception {
            //Arrange
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);
            try(MockedConstruction<Contact> contactMock = Mockito.mockConstruction(Contact.class)) {
                //Act
                muteSystemOut(() -> UserInteraction.addContact(addressBookMock));

                //Assert
                verify(addressBookMock, times(0)).addContact(any());
            }
        }
    }

    @Nested
    class DisplayContacts {
        @Test
        @DisplayName("Calls ContactPrinter.printAllContacts() with list of contacts from addressBook")
        void callPrintContactsCorrectly() throws Exception {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> contacts = new ArrayList<>();
            contacts.add(contactMock);
            when(addressBookMock.getContacts()).thenReturn(contacts);

            //Act
            muteSystemOut(() -> UserInteraction.displayContacts(addressBookMock));

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(contacts));
        }

        @Test
        @DisplayName("Print message if no contacts to print")
        void printMessageIfNoContacts() throws Exception {
            //Arrange
            ArrayList<Contact> contacts = new ArrayList<>();
            when(addressBookMock.getContacts()).thenReturn(contacts);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.displayContacts(addressBookMock));

            //Assert
            assertEquals("There are no contacts in the address book.\n", actual);
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
        void printsContracts() throws Exception {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            muteSystemOut(() -> UserInteraction.removeContact(addressBookMock));

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(any()));
        }

        @Test
        @DisplayName("Prints message if no contacts")
        void promptForChoice() throws Exception {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.removeContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Please choose contact by number"));
        }

        @Test
        @DisplayName("Calls AddressBook.removeContact() with return value of InputReceiver.receiveInt()")
        void removesChosenContact() throws Exception {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            int testInput = 1;
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(testInput);
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.removeContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Remove this contact? (y/n):"));
            verify(addressBookMock).removeContact(testInput);
            assertTrue(actual.contains("Contact removed"));
        }

        @DisplayName("Doesn't Call AddressBook.removeContact() if cancelled")
        void cancelsCorrectly() throws Exception {
            //Arrange
            Contact contactMock = mock(Contact.class);
            ArrayList<Contact> testList = new ArrayList<>();
            testList.add(contactMock);

            when(addressBookMock.getContacts()).thenReturn(testList);

            int testInput = 1;
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(testInput);
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.removeContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Remove this contact? (y/n):"));
            verify(addressBookMock, times(0)).removeContact(any());
            assertTrue(actual.contains("Contact not removed"));
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
            muteSystemOut(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            printerMock.verify(() -> ContactPrinter.printAllContacts(any()));
        }

        @Test
        @DisplayName("Calls ContactPrinter.printContact() with Contact chosen by InputReceiver.receiveInt() return value")
        void printsChosenContact() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            muteSystemOut(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            printerMock.verify(() -> ContactPrinter.printContact(any()));
        }

        @Test
        @DisplayName("Prompts choice of attribute to edit")
        void promptChooseAttribute() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Choose an attribute to edit:"));
            assertTrue(actual.contains("0: First Name\n1: Last Name\n2: Phone\n3: Email\n4: Edit a custom detail"));
        }

        @Test
        @DisplayName("Calls Contact.setFirstName() with InputReceiver.receiveString() return value if second InputReceiver.receiveInt() returns 0")
        void setsContactName() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 0);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Enter new First Name:"));
            verify(contactMock1).setFirstName(testInput);
            assertTrue(actual.contains("Attribute was edited"));
        }

        @Test
        @DisplayName("Calls Contact.setLastName() with InputReceiver.receiveString() return value if second InputReceiver.receiveInt() returns 1")
        void setsContactLastName() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 1);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Enter new Last Name:"));
            verify(contactMock1).setLastName(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setPhone() with InputReceiver.receivePhone() return value if second InputReceiver.receiveInt() returns 2")
        void setsContactPhone() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 2);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receivePhone).thenReturn(testInput);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Enter new Phone:"));
            verify(contactMock1).setPhone(testInput);
        }

        @Test
        @DisplayName("Calls Contact.setEmail() with InputReceiver.receiveEmail() return value if second InputReceiver.receiveInt() returns 3")
        void setsContactEmail() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 3);

            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveEmail).thenReturn(testInput);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Enter new Email:"));
            verify(contactMock1).setEmail(testInput);
        }

        @Test
        @DisplayName("Calls InputReceiver.yesNo() if InputReceiver.receiveInt() returns 4")
        void editDetails() throws Exception {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 4);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Do you want to add a new custom detail? (y/n):"));
            receiverMock.verify(InputReceiver::receiveYesNo, atLeast(1));
        }

        @Test
        @DisplayName("Calls Contact.addDetail with return of InputReceiver.receiveDetail() if first InputReceiver.yesNo() returns true")
        void addDetail() throws Exception {
            //Arrange
            String testKey = "TestKey";
            String testValue = "TestValue";

            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 4);
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(true);
            receiverMock.when(InputReceiver::receiveDetail).thenReturn(new String[] {testKey, testValue});

            //Act
            muteSystemOut(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            verify(contactMock1).addDetail(testKey, testValue);
        }

        @Test
        @DisplayName("Calls Contact.removeDetail() if second InputReceiver.receiveYesNo() returns true")
        void removeDetail() throws Exception {
            //Arrange
            LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();
            String testKey = "Nickname";
            testDetails.put(testKey, "Joe");
            testDetails.put("Address", "123 Place St");
            when(contactMock1.getDetails()).thenReturn(testDetails);

            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 4);
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false, true);
            receiverMock.when(InputReceiver::receiveString).thenReturn(testKey);

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            assertTrue(actual.contains("Do you want to remove a custom detail? (y/n):"));
            assertTrue(actual.contains("Input the name of the detail you want to remove:"));
            verify(contactMock1).removeDetail(testKey);
            assertTrue(actual.contains("If detail existed, it was removed:"));
        }

        @Test
        @DisplayName("Calls Contact.setDetail() if second InputReceiver.receiveYesNo() returns false")
        void editDetail() throws Exception {
            //Arrange
            LinkedHashMap<String, String> testDetails = new LinkedHashMap<>();
            String testKey = "Nickname";
            String testValue = "Test";
            testDetails.put(testKey, "Joe");
            testDetails.put("Address", "123 Place St");
            when(contactMock1.getDetails()).thenReturn(testDetails);

            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1, 4);
            receiverMock.when(InputReceiver::receiveYesNo).thenReturn(false);
            receiverMock.when(InputReceiver::receiveString).thenReturn(testKey, testValue);

            //Act
            muteSystemOut(() -> UserInteraction.editContact(addressBookMock));

            //Assert
            verify(contactMock1).setDetail(testKey, testValue);
        }
    }

    @Nested
    class FindContact {
        @Test
        @DisplayName("Prints message if no contacts")
        void messageIfNoContacts() throws Exception {
            //Arrange
            when(addressBookMock.getContacts()).thenReturn(new ArrayList<>());

            //Act
            String actual = tapSystemOutNormalized(() -> UserInteraction.findContact(addressBookMock));

            //Assert
            assertEquals("There are no contacts in the address book.\n", actual);
        }

        @Test
        @DisplayName("Calls ContactPrinter.printMatchingContacts() with return of InputReceiver.receiveString()")
        void printMatching() throws Exception {
            //Arrange
            String testInput = "Test";
            receiverMock.when(InputReceiver::receiveString).thenReturn(testInput);

            ArrayList<Contact> testList = new ArrayList<>();
            Contact contactMock = mock(Contact.class);
            testList.add(contactMock);
            when(addressBookMock.getContacts()).thenReturn(testList);

            //Act
            muteSystemOut(() -> UserInteraction.findContact(addressBookMock));

            //Assert
            printerMock.verify(() -> ContactPrinter.printMatchingContacts(testList, testInput));
        }
    }
}
