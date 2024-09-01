package co.za.lotto.machine.service;

import org.springframework.stereotype.Service;

import co.za.lotto.machine.model.InvalidAmountException;
import co.za.lotto.machine.model.Wallet;

@Service
public class WalletService {

    private final Wallet wallet;

    public WalletService() {
        this.wallet = new Wallet();
    }

    public void addFunds(int amount) throws InvalidAmountException {
        wallet.addFunds(amount);
    }

    public boolean deductFunds(int amount) {
        try {
            wallet.withdraw(amount);
            return true;
        } catch (InvalidAmountException e) {
            // Log the exception or handle it as needed
            e.printStackTrace(); // Example handling
            return false;
        }
    }

    public void refund() {
        try {
            int balance = wallet.getBalance();
            wallet.clear(); // Clear the wallet balance
            wallet.addFunds(balance); // Add the previously existing balance back to the wallet
        } catch (InvalidAmountException e) {
            // Handle exception, possibly log an error
            e.printStackTrace(); // Example handling
        }
    }

    public int getBalance() {
        return wallet.getBalance();
    }
}
