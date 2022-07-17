package pl.mcsu.core.listener;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.*;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.mcsu.core.Core;
import pl.mcsu.core.repository.Repository;
import pl.mcsu.core.service.SkinService;
import pl.mcsu.core.util.Color;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //if (!UserController.getInstance().hasUser(event.getPlayer().getUniqueId()))
            //UserController.getInstance().setUser(event.getPlayer().getUniqueId());

        SkinService.applySkin(event.getPlayer(), event.getPlayer().getName());

        Repository.getInstance().getNpc()
                .forEach(npc -> Core.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () ->
                        npc.create(event.getPlayer()), 20));

        Repository.getInstance().getNpc().forEach(npc -> System.out.println(npc.getName()));


        run(event.getPlayer());
        scoreboard(event.getPlayer());

            // create one scoreboard for player ranks
                // create teams and add players to them

    }

    protected void scoreboard(Player player) {
        Scoreboard scoreboard = new Scoreboard();
        Objective objective = scoreboard.addObjective("mcsu.pl", ObjectiveCriteria.DUMMY, Component.literal("Zlecenia"), ObjectiveCriteria.RenderType.INTEGER);
        ClientboundSetObjectivePacket remove = new ClientboundSetObjectivePacket(objective, 1);
        ClientboundSetObjectivePacket add = new ClientboundSetObjectivePacket(objective, 0);
        ClientboundSetDisplayObjectivePacket display = new ClientboundSetDisplayObjectivePacket(1, objective);
        Score quest = new Score(scoreboard, objective, "Śledzone:");
        quest.setScore(10);
        Score name = new Score(scoreboard, objective, "Waleczne serce!");
        name.setScore(9);
        ClientboundSetScorePacket set = new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, "mcsu.pl", quest.getOwner(), quest.getScore());
        ClientboundSetScorePacket sets = new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, "mcsu.pl", name.getOwner(),name.getScore());
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().connection.send(remove);
        craftPlayer.getHandle().connection.send(add);
        craftPlayer.getHandle().connection.send(display);
        craftPlayer.getHandle().connection.send(set);
        craftPlayer.getHandle().connection.send(sets);
    }

    protected void run(Player player) {
        if (Core.getInstance().getScoreboard().getTeamNames().contains(player.getName())) {
            PlayerTeam team = Core.getInstance().getScoreboard().getPlayerTeam(player.getName());
            assert team != null;
            ((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createRemovePacket(team));
            ((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true));
            return;
        }
        PlayerTeam team = new PlayerTeam(Core.getInstance().getScoreboard(), player.getName());
        team.setNameTagVisibility(Team.Visibility.ALWAYS);
        team.setDeathMessageVisibility(Team.Visibility.NEVER);
        team.setAllowFriendlyFire(false);
        Component prefix = Component.literal("[D] ")
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor(Color.PURPLE.asHexString())));
        Component suffix = Component.literal(" <22>")
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor(Color.YELLOW.asHexString())));
        team.setPlayerPrefix(prefix);
        team.setPlayerSuffix(suffix);
        team.getPlayers().add(player.getName());
        //((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createRemovePacket(team));
        ((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true));
    }

}
