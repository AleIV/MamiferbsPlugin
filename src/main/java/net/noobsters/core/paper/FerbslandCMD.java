package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;

@CommandAlias("ferbsland")
@CommandPermission("ferbs.cmd")
public class FerbslandCMD extends BaseCommand {
    private @NonNull Core instance;

    public FerbslandCMD(Core instance) {
        this.instance = instance;

    }

    @Subcommand("stand")
    public void stand(Player sender, String text) {
        var displayName = ChatColor.translateAlternateColorCodes('&', "" + text);
        sender.sendMessage(displayName + " armor stand.");

        var loc = sender.getLocation();
        var stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setArms(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(text);

    }

    @Subcommand("head")
    @CommandCompletion("@players")
    public void head(Player sender, String target) {

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + sender.getName().toString() + " player_head{SkullOwner:" + target + "} 1");
        sender.sendMessage(ChatColor.GREEN + target + " head.");
        

    }
}
