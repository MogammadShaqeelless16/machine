package co.za.lotto.machine.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import co.za.lotto.machine.model.ConcreteLottoTicket;
import co.za.lotto.machine.model.Lotto;

@Service
public class LottoService {

    private final Lotto lotto;

    public LottoService() {
        this.lotto = new Lotto();
    }

    public ConcreteLottoTicket createSingleLottoTicket(Set<Integer> numbers) {
        return lotto.createSingleLottoTicket(numbers);
    }

    public ConcreteLottoTicket createRandomLottoTicket(int numOfBalls, int maxBallNumber) {
        Set<Integer> numbers = generateRandomNumbers(numOfBalls, maxBallNumber);
        return lotto.createSingleLottoTicket(numbers);
    }

    public ConcreteLottoTicket createQuickFiveTicket(Set<Set<Integer>> numbers) {
        // Assumes that numbers contain exactly 5 sets of numbers
        return lotto.createQuickFiveTicket(numbers);
    }

    public ConcreteLottoTicket createRandomFiveTicket(int numOfTickets, int numOfBalls, int maxBallNumber) {
        Set<Set<Integer>> tickets = new HashSet<>();
        for (int i = 0; i < numOfTickets; i++) {
            Set<Integer> numbers = generateRandomNumbers(numOfBalls, maxBallNumber);
            tickets.add(numbers);
        }
        return lotto.createQuickFiveTicket(tickets);
    }

    private Set<Integer> generateRandomNumbers(int numOfBalls, int maxBallNumber) {
        Set<Integer> numbers = new HashSet<>();
        Random random = new Random();
        while (numbers.size() < numOfBalls) {
            numbers.add(random.nextInt(maxBallNumber) + 1);
        }
        return numbers;
    }
}
