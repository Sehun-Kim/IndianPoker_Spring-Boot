package indianpoker.vo;

public enum Preemptive {
    TRUE(true), FALSE(false);

    private boolean preemptive;

    Preemptive(boolean preemptive) {
        this.preemptive = preemptive;
    }

    public boolean isPreemptive() {
        return preemptive;
    }
}
