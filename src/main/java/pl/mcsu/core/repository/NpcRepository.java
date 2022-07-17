package pl.mcsu.core.repository;

import pl.mcsu.core.model.npc.Npc;

import java.util.ArrayList;
import java.util.List;

public class NpcRepository {

    private static NpcRepository instance = new NpcRepository();

    public NpcRepository() {
        instance = this;
    }

    public static NpcRepository getInstance() {
        return instance;
    }

    /**
     * Stores retrieved npc
     * */
    private final List<Npc> npcList = new ArrayList<>();

    public List<Npc> getNpcList() {
        return npcList;
    }

}
