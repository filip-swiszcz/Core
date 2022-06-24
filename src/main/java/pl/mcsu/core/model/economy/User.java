package pl.mcsu.core.model.economy;


import pl.mcsu.core.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private int coins;
    private int limit;
    private List<Transaction> history;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.coins = (int) Core.getInstance().getSettingsMap().get("coins");
        this.limit = (int) Core.getInstance().getSettingsMap().get("limit");
        this.history = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins) {
        this.coins = this.coins + coins;
    }

    public void removeCoins(int coins) {
        if (coins > this.coins) return;
        this.coins = this.coins - coins;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Transaction> getHistory() {
        return history;
    }

    public void setHistory(List<Transaction> history) {
        this.history = history;
    }

}
