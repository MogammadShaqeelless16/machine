package co.za.lotto.machine.model;

import java.util.Map;
import java.util.HashMap;

import java.util.Set;

public class Lotto {

    public ConcreteLottoTicket createSingleLottoTicket(Set<Integer> selectedNumbers) {
        // Implementation for creating a single lotto ticket
        return new ConcreteLottoTicket(selectedNumbers);
    }

    public ConcreteLottoTicket createRandomLottoTicket(int numOfBalls) {
        // Implementation for creating a random lotto ticket
        return null;
    }

    public ConcreteLottoTicket createQuickFiveTicket(Set<Set<Integer>> selectedNumbers) {
        // Implementation for creating a quick five lotto ticket
        return null;
    }

    public ConcreteLottoTicket createRandomFiveTicket(int numOfTickets, int numOfBalls) {
        // Implementation for creating random five lotto tickets
        return null;
    }
}