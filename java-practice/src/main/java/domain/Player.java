package domain;

public class Player {
    private long cash;

    public Player() {
        this.cash = 1_000_000;
    }

    public boolean spendCash(long amount) {
        if (amount > 0 && amount <= this.cash) {
            this.cash -= amount;
            return true;
        }
        return false;
    }

    public boolean earnCash(long amount) {
        if (amount > 0) {
            this.cash += amount;
			return true;
        }
		return false;
    }

    public long getCash() {
        return this.cash;
    }
}
