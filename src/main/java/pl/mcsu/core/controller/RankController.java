package pl.mcsu.core.controller;

import pl.mcsu.core.model.rank.Rank;
import pl.mcsu.core.repository.RankRepository;

public class RankController {

    private static RankController instance = new RankController();

    public RankController() {
        instance = this;
    }

    public static RankController getInstance() {
        return instance;
    }

    /**
     * Rank controller
     * */

    public Rank getRank(String name) {
        return RankRepository.getInstance().getRankMap().get(name);
    }

    public boolean isRank(String name) {
        return RankRepository.getInstance().getRankMap().containsKey(name);
    }

    public void addRank(String name, Rank rank) {
        RankRepository.getInstance().getRankMap().put(name, rank);
    }

}
