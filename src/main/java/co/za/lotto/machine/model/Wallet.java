package co.za.lotto.machine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private int balance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    // Default constructor required by JPA
    public Wallet() {
        this.balance = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Methods for managing wallet funds
    public void addFunds(int amount) throws InvalidAmountException {
        // Validation for allowed denominations
        if (amount != 1 && amount != 2 && amount != 5 && amount != 10 && amount != 50 && amount != 100) {
            throw new InvalidAmountException("Invalid denomination");
        }
        balance += amount;
    }

    public void withdraw(int amount) throws InvalidAmountException {
        if (amount > balance) {
            throw new InvalidAmountException("Insufficient funds");
        }
        balance -= amount;
    }

    public void clear() {
        this.balance = 0;
    }
}
