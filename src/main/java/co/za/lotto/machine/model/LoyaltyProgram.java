package co.za.lotto.machine.model;

public class LoyaltyProgram {
    private final String programName;
    private final String userId;

    public LoyaltyProgram(String programName, String userId) {
        this.programName = programName;
        this.userId = userId;
    }

    public String getProgramName() {
        return programName;
    }

    public String getUserId() {
        return userId;
    }
}