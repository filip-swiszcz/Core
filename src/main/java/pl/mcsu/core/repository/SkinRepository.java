package pl.mcsu.core.repository;

import pl.mcsu.core.model.skin.Skin;

import java.util.ArrayList;
import java.util.List;

public class SkinRepository {

    private static SkinRepository instance = new SkinRepository();

    public SkinRepository() {
        instance = this;
    }

    public static SkinRepository getInstance() {
        return instance;
    }

    /**
     * Stores used skins
     * */
    private final List<Skin> skinList = new ArrayList<>();

    public List<Skin> getSkinList() {
        return skinList;
    }

}
