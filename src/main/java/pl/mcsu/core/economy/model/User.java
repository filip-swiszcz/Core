package pl.mcsu.core.economy.model;

import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private int coins;
    private int limit;
    private List<Transaction> history;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

}
