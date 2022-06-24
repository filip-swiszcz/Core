package pl.mcsu.core.controller;

import pl.mcsu.core.model.economy.User;
import pl.mcsu.core.repository.UserRepository;

import java.util.UUID;

public class UserController {

    private static UserController instance = new UserController();

    public UserController() {
        instance = this;
    }

    public static UserController getInstance() {
        return instance;
    }

    /**
     * User controller
     * */

    public User getUser(UUID uuid) {
        return UserRepository.getInstance().getUserMap().get(uuid);
    }

    public boolean hasUser(UUID uuid) {
        return UserRepository.getInstance().getUserMap().containsKey(uuid);
    }

    public void setUser(UUID uuid) {
        User user = new User(uuid);
        UserRepository.getInstance().getUserMap().put(uuid, user);
    }

}
