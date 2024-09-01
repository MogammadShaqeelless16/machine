package co.za.lotto.machine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.za.lotto.machine.model.InvalidAmountException;
import co.za.lotto.machine.model.User;
import co.za.lotto.machine.model.UserNotFoundException;
import co.za.lotto.machine.model.Wallet;
import co.za.lotto.machine.repository.UserRepository;
import co.za.lotto.machine.repository.WalletRepository;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    // Updated method to return Optional<User>
    public Optional<User> findByNameAndEmail(String name, String email) {
        return userRepository.findByNameAndEmail(name, email);
    }

    // Method to register a new user
    public User registerUser(String name, String email) {
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);

        User user = new User(name, email);
        user.setWallet(wallet);
        return userRepository.save(user);
    }

    // Method to get user by ID
    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    // Method to delete user by ID
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

      public void addFunds(UUID userId, int amount) throws InvalidAmountException {
        User user = getUser(userId);
        if (user != null) {
            user.getWallet().addFunds(amount);
            walletRepository.save(user.getWallet());
        }
    }

    public void withdrawFunds(UUID userId, int amount) throws InvalidAmountException {
        User user = getUser(userId);
        if (user != null) {
            user.getWallet().withdraw(amount);
            walletRepository.save(user.getWallet());
        }
    }

    @Transactional
    public User findById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
}
