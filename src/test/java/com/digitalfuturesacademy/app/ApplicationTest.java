package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@DisplayName("Application")
public class ApplicationTest {
    @Test
    @DisplayName("Calls mainMenu")
    void CallsMainMenu() {
        //Arrange
        try(MockedStatic<UserInteraction> UIMock = mockStatic(UserInteraction.class)) {
            //Act
            Application.main(new String[] {});

            //Assert
            UIMock.verify(() -> UserInteraction.mainMenu(any()));
        }
    }
}
