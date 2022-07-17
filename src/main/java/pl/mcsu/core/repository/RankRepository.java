package pl.mcsu.core.repository;

import pl.mcsu.core.model.rank.Rank;

import java.util.HashMap;
import java.util.Map;

public class RankRepository {

    private static RankRepository instance = new RankRepository();

    public RankRepository() {
        instance = this;
    }

    public static RankRepository getInstance() {
        return instance;
    }

    /**
     * Stores server ranks
     * */
    private final Map<String, Rank> rankMap = new HashMap<>();

    public Map<String, Rank> getRankMap() {
        return rankMap;
    }

}
