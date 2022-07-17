package pl.mcsu.core.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import pl.mcsu.core.model.economy.User;
import pl.mcsu.core.model.npc.Npc;
import pl.mcsu.core.model.rank.Rank;
import pl.mcsu.core.model.skin.Skin;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Repository {

    private static Repository instance = new Repository();

    public Repository() {
        instance = this;
    }

    public static Repository getInstance() {
        return instance;
    }

    private final Collection<Npc> npc = new ArrayList<>();
    private final Cache<UUID, Npc> clicks = CacheBuilder.newBuilder().expireAfterWrite(40, TimeUnit.SECONDS).build();
    private final Map<String, Rank> ranks = new HashMap<>();
    private final Collection<Skin> skins = new ArrayList<>();
    private final Map<UUID, User> users = new HashMap<>();

    public Collection<Npc> getNpc() {
        return npc;
    }

    public Cache<UUID, Npc> getClicks() {
        return clicks;
    }

    public Map<String, Rank> getRanks() {
        return ranks;
    }

    public Collection<Skin> getSkins() {
        return skins;
    }

    public Map<UUID, User> getUsers() {
        return users;
    }

}
