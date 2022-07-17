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
        this.coins = Core.getInstance().getDefaultCoins();
        this.limit = Core.getInstance().getCoinsLimit();
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

    public void addCoins(int amount) {
        if (amount == 0) return;
        this.coins = coins + amount;
    }

    public void removeCoins(int amount) {
        if (amount == 0) return;
        if (amount > coins) return;
        this.coins = coins - amount;
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
