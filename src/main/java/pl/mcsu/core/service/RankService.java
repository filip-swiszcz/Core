package pl.mcsu.core.service;

import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.mcsu.core.Core;
import pl.mcsu.core.controller.RankController;
import pl.mcsu.core.model.rank.Prefix;
import pl.mcsu.core.model.rank.Rank;
import pl.mcsu.core.util.Color;

public class RankService {

    public static void apply(Player player, String name) {
        Scoreboard scoreboard = Core.getInstance().getScoreboard();

        //Get rank from User
            // Transfer all repositories to one repository

        Rank rank = new Rank("Admin", 0);
        rank.setPrefix(new Prefix("[A]", Color.RED.asHexString()));

        PlayerTeam team;
        if (scoreboard.getTeamNames().contains(rank.getValue() + player.getName()))
            team = scoreboard.getPlayerTeam(rank.getValue() + player.getName());
        else {
            team = new PlayerTeam(scoreboard, rank.getValue() + player.getName());
        }
        if (team == null) return;
        team.setNameTagVisibility(Team.Visibility.ALWAYS);
        team.setDeathMessageVisibility(Team.Visibility.NEVER);
        team.setPlayerPrefix(rank.getPrefix().getPrefix());

        // set suffix from level

        team.getPlayers().add(player.getName());

        ((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createRemovePacket(team));
        ((CraftPlayer) player).getHandle().connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true));
    }

}
