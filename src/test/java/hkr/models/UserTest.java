package hkr.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    Database database = new Database();
    User user2 = database.getUser(2);
    User user1 = database.getUser(1);
    @Test
    void getEmail() {
        String expectedEmail = "paul@gmail.com";
        String expectedEmail2 = "raghu@gmail.com";
        String actualEmail = user1.getEmail();
        String actualEmail2 = user2.getEmail();

        assertEquals(expectedEmail, actualEmail);
        assertEquals(expectedEmail2, actualEmail2);
    }

    @Test
    void getId() {
        int expectedID = 1;
        int expectedID2 = 2;
        int actualID = user1.getId();
        int actualID2 = user2.getId();

        assertEquals(expectedID, actualID);
        assertEquals(expectedID2, actualID2);
    }

    @Test
    void getBalance() {
        double expectedBalance = 25.65;
        double expectedBalance2 = 75.42;
        double actualBalance = user1.getBalance().doubleValue();
        double actualBalance2 = user2.getBalance().doubleValue();

        assertEquals(expectedBalance, actualBalance);
        assertEquals(expectedBalance2, actualBalance2);
    }
}