package pl.mcsu.core.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import org.apache.commons.io.IOUtils;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.mcsu.core.model.skin.Skin;
import pl.mcsu.core.repository.Repository;
import pl.mcsu.core.repository.SkinRepository;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class SkinService {

    public static void applySkin(Player player, String name) {
        Optional<Skin> result = Repository.getInstance().getSkins()
                .stream()
                .filter(skin -> skin.getName().equalsIgnoreCase(name)).findAny();
        if (result.isPresent()) {
            Skin skin = result.get();
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getProfile().getProperties().removeAll("textures");
            craftPlayer.getProfile().getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
            return;
        }
        Skin skin = getSkin(name);
        if (skin == null) return;
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getProfile().getProperties().removeAll("textures");
        craftPlayer.getProfile().getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
    }

    public static Skin getSkin(String name) {
        JsonElement profileResponse = JsonParser.parseString(getResponse("https://api.mojang.com/users/profiles/minecraft/" + name));
        if (profileResponse.isJsonNull()) return null;
        JsonObject jsonProfileResponse = profileResponse.getAsJsonObject();
        if (jsonProfileResponse.has("error")) return null;
        String uuid = jsonProfileResponse.get("id").getAsString();
        JsonElement sessionResponse = JsonParser.parseString(getResponse("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false"));
        JsonObject jsonSessionResponse = sessionResponse.getAsJsonObject();
        if (jsonSessionResponse.has("error")) return null;
        JsonElement properties = ((JsonArray) jsonSessionResponse.get("properties")).get(0);
        JsonObject jsonProperties = properties.getAsJsonObject();
        Skin skin = new Skin(name, jsonProperties.get("value").getAsString(), jsonProperties.get("signature").getAsString());
        SkinRepository.getInstance().getSkinList().add(skin);
        return skin;
    }

    private static String getResponse(String link) {
        try {
            URL url = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            String encoded = connection.getContentEncoding();
            encoded = encoded == null ? "UTF-8" : encoded;
            return IOUtils.toString(input, encoded);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
