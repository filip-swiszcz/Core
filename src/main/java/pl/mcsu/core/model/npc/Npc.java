package pl.mcsu.core.model.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.mcsu.core.Core;
import pl.mcsu.core.model.rank.Prefix;
import pl.mcsu.core.model.rank.Suffix;
import pl.mcsu.core.model.skin.Skin;
import pl.mcsu.core.repository.Repository;
import pl.mcsu.core.service.SkinService;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Npc {

    private int id;
    private final String name;
    private ServerPlayer npc;
    private Location location;
    private Skin skin;
    private Prefix prefix;
    private Suffix suffix;
    //private Visibility visibility;
    private final String random;
    private PlayerTeam team;
    private Interaction interaction;

    public Npc(String name, Location location) {

        // Debug
        System.out.println("New Npc before increase id - " + Core.getInstance().getNpcId());

        this.id = Core.getInstance().getNpcId() + 1;
        Core.getInstance().setNpcId(id);

        // Debug
        System.out.println("New Npc after increase id - " + id);

        this.name = name;
        this.location = location;
        this.random = getRandom();
    }

    public void skin(String name) {
        this.skin = SkinService.getSkin(name);
    }

    public void prefix(String text, String color) {
        this.prefix = new Prefix(text, color);
    }

    public void suffix(String text, String color) {
        this.suffix = new Suffix(text, color);
    }

    public void interaction(String command, boolean enable) {
        Interaction interaction = new Interaction(command, enable);
        interaction.setClick(event -> {
            if(interaction.isEnabled()) event.getPlayer().performCommand(command);
        });
        this.interaction = interaction;
    }

    public void create(Collection<Player> players) {
        players.forEach(this::create);
    }

    public void create(Player player) {
        MinecraftServer server = ((CraftServer) Core.getInstance().getServer()).getServer();
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        Scoreboard scoreboard = Core.getInstance().getScoreboard();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        if (skin != null) profile.getProperties().put("textures",
                new Property("textures", skin.getValue(), skin.getSignature()));
        this.npc = new ServerPlayer(server, level, profile, null);
        this.npc.setPos(location.getX(), location.getY(), location.getZ());
        if (!scoreboard.getTeamNames().contains("NPC_" + name + random)) {
            this.team = new PlayerTeam(scoreboard, "NPC_" + name + random);
            this.team.setNameTagVisibility(Team.Visibility.ALWAYS);
            this.team.setDeathMessageVisibility(Team.Visibility.HIDE_FOR_OTHER_TEAMS);
        } else this.team = scoreboard.getPlayerTeam("NPC_" + name + random);
        if (team != null && prefix != null) this.team.setPlayerPrefix(prefix.getPrefix());
        if (team != null && suffix != null) this.team.setPlayerSuffix(suffix.getSuffix());
        if (team != null) this.team.getPlayers().add(name);
        if (team != null) sendPacket(player, ClientboundSetPlayerTeamPacket.createRemovePacket(team));
        if (team != null) sendPacket(player, ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true));
        sendPacket(player, getPlayerInfoPacket(PlayerAction.ADD_PLAYER));
        sendPacket(player, getAddPlayerPacket());
        rotate(player, location.getYaw(), location.getPitch());
        Core.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> sendPacket(
                player, getPlayerInfoPacket(PlayerAction.REMOVE_PLAYER)), 20);
        if (!Repository.getInstance().getNpc().contains(this))
            Repository.getInstance().getNpc().add(this);

        // Debug
        System.out.println("Npc create id - " + id);

    }

    public void destroy(Collection<Player> players) {
        players.forEach(this::destroy);
    }

    public void destroy(Player player) {
        Scoreboard scoreboard = Core.getInstance().getScoreboard();
        scoreboard.getPlayerTeams().remove(team);
        removeFromTabList(player);
        sendPacket(player, getRemoveEntitiesPacket());
    }

    public void refresh(Collection<Player> players) {
        players.forEach(this::refresh);
    }

    public void refresh(Player player) {
        destroy(player);
        create(player);
    }

    public void addToTabList(Collection<Player> players) {
        players.forEach(this::addToTabList);
    }

    public void addToTabList(Player player) {
        sendPacket(player, getPlayerInfoPacket(PlayerAction.ADD_PLAYER));
    }

    public void removeFromTabList(Collection<Player> players) {
        players.forEach(this::removeFromTabList);
    }

    public void removeFromTabList(Player player) {
        Core.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () ->
                sendPacket(player, getPlayerInfoPacket(PlayerAction.REMOVE_PLAYER)), 20);
    }

    public void teleport(Player player, Location location) {
        this.location = location;
        refresh(player);
        rotate(player, player.getLocation().getYaw(), player.getLocation().getPitch());
    }

    public void lookAtPlayer(Player player, Player target) {
        lookAtPoint(player, target.getEyeLocation());
    }

    public void lookAtPoint(Player player, Location location) {
        Location eyeLocation = getEyeLocation();
        float yaw = (float) Math.toDegrees(Math.atan2(location.getZ() - eyeLocation.getZ(), location.getX()-eyeLocation.getX())) - 90;
        yaw = (float) (yaw + Math.ceil( -yaw / 360 ) * 360);
        float deltaXZ = (float) Math.sqrt(Math.pow(eyeLocation.getX()-location.getX(), 2) + Math.pow(eyeLocation.getZ()-location.getZ(), 2));
        float pitch = (float) Math.toDegrees(Math.atan2(deltaXZ, location.getY()-eyeLocation.getY())) - 90;
        pitch = (float) (pitch + Math.ceil( -pitch / 360 ) * 360);
        this.rotate(player, yaw, pitch);
    }

    public void rotate(Player player, float yaw, float pitch) {
        this.location.setYaw(yaw);
        this.location.setPitch(pitch);
        sendPacket(player, getMoveEntityPacket());
        sendPacket(player, getRotateHeadPacket());
    }

    public int getId() {
        return id;
    }

    public void setId(int number) {
        // Debug
        System.out.println("Npc id before setting with function - " + id);

        Core.getInstance().setNpcId(id - 1);
        this.id = number;

        // Debug
        System.out.println("Npc id after setting with function - " + id);

    }

    public int getNpcId() {
        return npc.getId();
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Skin getSkin() {
        return skin;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    public boolean isInZone(Player player, int radius) {
        return location.distanceSquared(player.getLocation()) <= radius;
    }

    private ClientboundPlayerInfoPacket getPlayerInfoPacket(PlayerAction action) {
        return new ClientboundPlayerInfoPacket(action.getAction(), npc);
    }

    private ClientboundAddPlayerPacket getAddPlayerPacket() {
        return new ClientboundAddPlayerPacket(npc);
    }

    private ClientboundRemoveEntitiesPacket getRemoveEntitiesPacket() {
        return new ClientboundRemoveEntitiesPacket(npc.getId());
    }

    private ClientboundMoveEntityPacket getMoveEntityPacket() {
        return new ClientboundMoveEntityPacket
                .Rot(npc.getId(),
                (byte) ((int) (location.getYaw() * 256.0F / 360.0F)),
                (byte) ((int) (location.getPitch() * 256.0F / 360.0F)), true);
    }

    private ClientboundRotateHeadPacket getRotateHeadPacket() {
        return new ClientboundRotateHeadPacket(npc, (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
    }

    private Location getEyeLocation() {
        return location.clone().add(0, EntityType.PLAYER.getDimensions().height * 0.85F, 0);
    }

    private String getRandom() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        alphabet = alphabet + alphabet.toLowerCase(Locale.ROOT);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++)
            stringBuilder.append(
                    alphabet.charAt(
                            ThreadLocalRandom.current()
                                    .nextInt(0, alphabet.length())));
        return stringBuilder.toString();
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    private enum PlayerAction {

        ADD_PLAYER(ClientboundPlayerInfoPacket.Action.ADD_PLAYER),
        UPDATE_DISPLAY_NAME(ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME),
        UPDATE_GAME_MODE(ClientboundPlayerInfoPacket.Action.UPDATE_GAME_MODE),
        UPDATE_LATENCY(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY),
        REMOVE_PLAYER(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER);

        private final ClientboundPlayerInfoPacket.Action action;

        PlayerAction(ClientboundPlayerInfoPacket.Action action) {
            this.action = action;
        }

        public ClientboundPlayerInfoPacket.Action getAction() {
            return action;
        }

    }

}
