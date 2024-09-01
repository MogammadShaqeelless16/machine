package co.za.lotto.machine.model;

import java.util.Set;

public class Ticket {
    private final Set<Integer> numbers;
    private final int amount;
    private final String user;
    private final long timestamp;

    public Ticket(Set<Integer> numbers, int amount, String user, long timestamp) {
        this.numbers = numbers;
        this.amount = amount;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Set<Integer> getNumbers() {
        return numbers;
    }

    public int getAmount() {
        return amount;
    }

    public String getUser() {
        return user;
    }

    public long getTimestamp() {
        return timestamp;
    }
}