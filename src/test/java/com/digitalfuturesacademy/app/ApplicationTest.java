package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.*;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import static org.mockito.ArgumentMatchers.anyInt;

import org.mockito.MockedStatic;

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
}
