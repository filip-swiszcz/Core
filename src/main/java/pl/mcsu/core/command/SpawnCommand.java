package pl.mcsu.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.model.npc.Interaction;
import pl.mcsu.core.model.npc.Npc;
import pl.mcsu.core.repository.Repository;
import pl.mcsu.core.util.Color;

public class SpawnCommand extends Command {

    public SpawnCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        Npc npc = new Npc(Color.GREEN + "Queen Meve", player.getLocation());
        npc.skin("arcanedestini");
        Interaction interaction = new Interaction("say Lyria!", false);
        interaction.setClick(event -> {
            if (interaction.isEnabled()) event.getPlayer().performCommand(interaction.getCommand());
        });
        npc.setInteraction(interaction);
        npc.create(player);

        return true;
    }
}
