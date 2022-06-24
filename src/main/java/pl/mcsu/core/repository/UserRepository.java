package pl.mcsu.core.repository;

import pl.mcsu.core.model.economy.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {

    private static UserRepository instance = new UserRepository();

    public UserRepository() {
        instance = this;
    }

    public static UserRepository getInstance() {
        return instance;
    }

    /**
     * User repository
     * */

    private final Map<UUID, User> userMap = new HashMap<>();

    public Map<UUID, User> getUserMap() {
        return userMap;
    }

}
