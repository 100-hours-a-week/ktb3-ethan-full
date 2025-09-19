package service;

import domain.Player;

public class PlayerService {
    private final Player player; // 사용자는 1명이라 가정

    public PlayerService() {
        this.player = new Player();
	}

    public long getCash() {
        return player.getCash();
    }

    public boolean spendCash(long amount) {
        return player.spendCash(amount);
    }

    public boolean earnCash(long amount) {
        return player.earnCash(amount);
    }
}
