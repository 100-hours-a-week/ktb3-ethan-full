package service;

import domain.Player;

public class PlayerService {
    private final Player player; // 사용자는 1명이라 가정

    public PlayerService() {
        this.player = new Player();
	}

    public synchronized long getCash() {
        return player.getCash();
    }

    public synchronized boolean spendCash(long amount) {
        return player.spendCash(amount);
    }

    public synchronized boolean earnCash(long amount) {
        return player.earnCash(amount);
    }
}
