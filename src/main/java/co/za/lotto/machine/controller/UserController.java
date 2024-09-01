package co.za.lotto.machine.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.za.lotto.machine.model.InvalidAmountException;
import co.za.lotto.machine.model.User;
import co.za.lotto.machine.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Display registration form
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String email, Model model) {
        User user = userService.registerUser(name, email);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String name, @RequestParam String email, Model model, HttpSession session) {
        Optional<User> userOptional = userService.findByNameAndEmail(name, email);
        if (userOptional.isPresent()) {
            session.setAttribute("user", userOptional.get());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid name or email.");
            return "login";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/{userId}/addFunds")
    public String addFunds(@PathVariable UUID userId, @RequestParam int amount, Model model) {
        try {
            userService.addFunds(userId, amount);
            return "redirect:/home"; // Redirect back to the home page
        } catch (InvalidAmountException e) {
            model.addAttribute("error", "Invalid amount.");
            return "home"; // Return to the home page with an error message
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred.");
            return "home"; // Return to the home page with a generic error message
        }
    }

    @GetMapping("/getWalletBalance")
    public Map<String, Object> getWalletBalance(@RequestParam UUID userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.findById(userId);
            if (user != null && user.getWallet() != null) {
                response.put("balance", user.getWallet().getBalance());
            } else {
                response.put("balance", 0);
            }
        } catch (Exception e) {
            response.put("balance", 0);
        }
        return response;
    }

    @PostMapping("/{userId}/withdrawFunds")
    @ResponseBody
    public String withdrawFunds(@PathVariable UUID userId, @RequestParam int amount, Model model) {
        try {
            userService.withdrawFunds(userId, amount);
            return "redirect:/home"; // Redirect back to home page
        } catch (InvalidAmountException e) {
            model.addAttribute("error", "Invalid amount.");
            return "home"; // Stay on home page and show error
        }
    }
}
