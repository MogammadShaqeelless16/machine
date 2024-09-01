package co.za.lotto.machine.model;

import java.util.Set;

public class ConcreteLottoTicket implements LottoTicket {
    private final Set<Integer> numbers;

    public ConcreteLottoTicket(Set<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public Set<Integer> getNumbers() {
        return numbers;
    }
}
