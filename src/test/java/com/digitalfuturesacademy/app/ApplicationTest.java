package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class ApplicationTest {
    @Nested
    @DisplayName("Main Method")
    class Main {
        @Test
        @DisplayName("Calls mainMenu")
        void CallsMainMenu() {
            //Arrange
            try(MockedStatic<Application> appMock = Mockito.mockStatic(Application.class)) {
                appMock.when(() -> Application.main(new String[] {})).thenCallRealMethod();
                appMock.when(Application::mainMenu).thenCallRealMethod();

                //Act
                Application.main(new String[] {});

                //Assert
                appMock.verify(Application::mainMenu);
            }
        }
    }
}
