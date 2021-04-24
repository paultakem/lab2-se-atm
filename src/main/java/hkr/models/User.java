package hkr.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class User implements Cloneable {

    private int id;
    private String email;
    private BigDecimal balance;

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
