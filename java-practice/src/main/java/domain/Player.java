package domain;

public class Player {
    private long cash;

    public Player() {
        this.cash = 1_000_000;
    }

    public boolean spendCash(long amount) {
        if (this.cash >= amount) {
            this.cash -= amount;
            return true;
        }
        return false;
    }

    public void earnCash(long amount) {
        this.cash += amount;
    }

    public long getCash() {
        return this.cash;
    }
}
