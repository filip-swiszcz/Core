package pl.mcsu.core.economy.model;

import java.util.UUID;

public record Transaction(Type type, int amount, Long time, UUID target) {

    public enum Type {
        DEPOSIT, WITHDRAW
    }

}
