package domain;

import java.util.Arrays;

public enum RiskLevel {
    LEVEL_1(1, 4.0, 3.0),
    LEVEL_2(2, 8.0, 6.0),
    LEVEL_3(3, 12.0, 9.0);

    private final int level;
    private final double maxProfitRate;
    private final double maxLossRate;

    RiskLevel(int level, double maxProfitRate, double maxLossRate) {
        this.level = level;
        this.maxProfitRate = maxProfitRate;
        this.maxLossRate = maxLossRate;
    }

    public int getLevel() {
        return this.level;
    }

    public double getMaxProfitRate() {
        return this.maxProfitRate;
    }

    public double getMaxLossRate() {
        return this.maxLossRate;
    }

    public static RiskLevel of(int riskLevel) {
        return Arrays.stream(values())
                .filter(level -> level.level == riskLevel)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid risk level: " + riskLevel));
    }
}
