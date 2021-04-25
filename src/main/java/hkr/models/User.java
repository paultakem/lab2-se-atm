package hkr.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class User implements Cloneable {

    private final int id;
    private final String email;
    private final BigDecimal balance;

    public User(int id, String email, BigDecimal balance){
        this.id = id;
        this.email = email;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
