package com.digitalfuturesacademy.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactPrinterTests {
    @Nested
    class PrintAllContacts {
        @Test
        @DisplayName("Throws exception if List is null")
        void exceptionIfNull() {
            //Arrange, Act, Assert
            assertThrows(IllegalArgumentException.class, () -> ContactPrinter.printAllContacts(null));
        }
    }
}
