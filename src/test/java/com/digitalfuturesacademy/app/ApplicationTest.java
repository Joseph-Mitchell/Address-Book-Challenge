package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction;
import org.mockito.MockedConstruction.Context;
import org.mockito.MockedStatic;

import java.util.LinkedHashMap;

@DisplayName("Application")
public class ApplicationTest {
    private MockedStatic<InputReceiver> receiverMock;

    @BeforeEach
    void beforeEach() {
        receiverMock = mockStatic(InputReceiver.class);
    }

    @AfterEach
    void afterEach() {
        receiverMock.close();
    }

    @Nested
    class Main {
        @Test
        @DisplayName("Calls mainMenu")
        void CallsMainMenu() {
            //Arrange
            try(MockedStatic<Application> appMock = mockStatic(Application.class)) {
                appMock.when(() -> Application.main(new String[] {})).thenCallRealMethod();

                //Act
                Application.main(new String[] {});

                //Assert
                appMock.verify(Application::mainMenu);
            }
        }
    }

    @Nested
    class MainMenu {
        private MockedStatic<Application> appMock;

        @BeforeEach
        void beforeEach() {
            appMock = mockStatic(Application.class);
            appMock.when(Application::mainMenu).thenCallRealMethod();
        }

        @AfterEach
        void afterEach() {
            appMock.close();
        }

        @Test
        @DisplayName("Doesn't call addContact() if InputReceiver.receiveInt() returns unmatching int")
        void doesNotCallAddContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(-1);

            //Act
            Application.mainMenu();

            //Assert
            appMock.verify(Application::addContact, times(0));
        }

        @Test
        @DisplayName("Only calls addContact() if InputReceiver.receiveInt() returns matching int")
        void callsAddContact() {
            //Arrange
            receiverMock.when(() -> InputReceiver.receiveInt(anyInt())).thenReturn(1);

            //Act
            Application.mainMenu();

            //Assert
            appMock.verify(Application::addContact);
            appMock.verify(Application::displayContacts, times(0));
            appMock.verify(Application::removeContact, times(0));
            appMock.verify(Application::editContact, times(0));
            appMock.verify(Application::findContact, times(0));
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
        @DisplayName("Calls Contact constructor with expected parameters")
        void CallsContactConstructor() {
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
                Application.addContact();

                //Assert
                assertEquals(testFirstName, contactMock.constructed().get(0).getFirstName());
                assertEquals(testLastName, contactMock.constructed().get(0).getLastName());
                assertEquals(testPhone, contactMock.constructed().get(0).getPhone());
                assertEquals(testEmail, contactMock.constructed().get(0).getEmail());
                assertEquals(testDetails, contactMock.constructed().get(0).getDetails());
            }
        }
    }
}
