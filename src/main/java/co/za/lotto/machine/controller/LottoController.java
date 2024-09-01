package co.za.lotto.machine.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.za.lotto.machine.model.ConcreteLottoTicket;
import co.za.lotto.machine.model.InvalidAmountException;
import co.za.lotto.machine.service.LottoService;
import co.za.lotto.machine.service.WalletService;

@Controller
public class LottoController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("balance", walletService.getBalance());
        return "index";
    }

    @PostMapping("/buySingleLotto")
    public String buySingleLotto(@RequestParam Set<Integer> selectedNumbers, Model model) {
        if (walletService.deductFunds(5)) {
            ConcreteLottoTicket ticket = lottoService.createSingleLottoTicket(selectedNumbers);
            model.addAttribute("ticket", ticket);
            model.addAttribute("balance", walletService.getBalance());
        } else {
            model.addAttribute("error", "Insufficient balance");
        }
        return "ticket";
    }

    @PostMapping("/buyRandomLotto")
    public String buyRandomLotto(@RequestParam int numOfBalls, @RequestParam int maxBallNumber, Model model) {
        if (walletService.deductFunds(5)) {
            ConcreteLottoTicket ticket = lottoService.createRandomLottoTicket(numOfBalls, maxBallNumber);
            model.addAttribute("ticket", ticket);
            model.addAttribute("balance", walletService.getBalance());
        } else {
            model.addAttribute("error", "Insufficient balance");
        }
        return "ticket";
    }

    @PostMapping("/buyQuickFive")
    public String buyQuickFive(@RequestParam Set<Set<Integer>> selectedNumbers, Model model) {
        if (walletService.deductFunds(25)) {
            ConcreteLottoTicket ticket = lottoService.createQuickFiveTicket(selectedNumbers);
            model.addAttribute("ticket", ticket);
            model.addAttribute("balance", walletService.getBalance());
        } else {
            model.addAttribute("error", "Insufficient balance");
        }
        return "ticket";
    }

    @PostMapping("/buyRandomFive")
    public String buyRandomFive(@RequestParam int numOfBalls, @RequestParam int maxBallNumber, Model model) {
        if (walletService.deductFunds(25)) {
            ConcreteLottoTicket ticket = lottoService.createRandomFiveTicket(5, numOfBalls, maxBallNumber);
            model.addAttribute("ticket", ticket);
            model.addAttribute("balance", walletService.getBalance());
        } else {
            model.addAttribute("error", "Insufficient balance");
        }
        return "ticket";
    }


    @PostMapping("/refund")
    public String refund(Model model) {
        walletService.refund();
        model.addAttribute("balance", walletService.getBalance());
        return "redirect:/";
    }
}
