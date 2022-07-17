package pl.mcsu.core.model.rank;

import java.util.ArrayList;
import java.util.Collection;

public class Rank {

    private final String name;
    private final int value;
    private Prefix prefix;
    private final Collection<String> permissions;

    public Rank(String name, int value) {
        this.name = name;
        this.value = value;
        this.permissions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public void setPrefix(Prefix prefix) {
        this.prefix = prefix;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public void addPermission(String permission) {
        if (!hasPermission(permission)) this.permissions.add(permission);
    }

}
