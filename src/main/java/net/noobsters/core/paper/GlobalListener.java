package net.noobsters.core.paper;

import com.destroystokyo.paper.Title;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class GlobalListener implements Listener{
    Core instance;

    public GlobalListener(Core instance){
        this.instance = instance;

    }

    /*@EventHandler
    public void onSpawn(CreatureSpawnEvent e){
        var entity = e.getEntity();
        if(e.getSpawnReason().toString().contains("NATURAL") &&
            !(entity instanceof Villager 
            || entity instanceof Wither 
            || entity instanceof EnderDragon 
            || entity instanceof Shulker
            || entity instanceof IronGolem
            || entity instanceof ElderGuardian)){

            entity.setRemoveWhenFarAway(true);
            
        }
    }*/
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        var player = e.getPlayer();
        var loc = player.getLocation();

        var world = Bukkit.getWorld("world") == player.getWorld() ? "overworld" : player.getWorld().getName().toString();

        if(player.hasPlayedBefore()){
            e.setJoinMessage(ChatColor.of("#16dfdb") + player.getName() + " se uni\u00F3");

        }else{
            e.setJoinMessage(ChatColor.of("#16dfdb") + player.getName() + " acaba de aterrizar en FerbsLand");

            var command1 = "execute in minecraft:" + world + " run summon firework_rocket %d %d %d {LifeTime:32,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:2,Flicker:1,Trail:1,Colors:[I;16724882],FadeColors:[I;16718105]},{Type:2,Flicker:1,Trail:1,Colors:[I;4587513],FadeColors:[I;12939007]},{Type:2,Flicker:1,Trail:1,Colors:[I;2424612],FadeColors:[I;16738037]},{Type:2,Flicker:1,Trail:1,Colors:[I;16768304],FadeColors:[I;8909823]}]}}}}";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(command1, loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ()));

            player.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder(ChatColor.of("#df16a6") + "Bienvenid@ a FerbsLand!").bold(true).create()).build());
        }

        if(player.getGameMode() != GameMode.SURVIVAL){
            e.setJoinMessage("");

            var command2 = "execute in minecraft:" + world + " run particle minecraft:flash %d %d %d .1 .1 .1 1 30 normal";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(command2, loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ()));
    
            player.playSound(loc, Sound.BLOCK_BEACON_ACTIVATE, 1, 2);
        }
        
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        var player = e.getPlayer();
        e.setQuitMessage(ChatColor.of("#16dfdb") + player.getName() + " se fu\u00E9");

        if(player.getGameMode() != GameMode.SURVIVAL){
            e.setQuitMessage("");
        }

    }

    @EventHandler
    public void onJoinHide(PlayerJoinEvent e) {
        var player = e.getPlayer();
        // If gamemode is Spectator, then hide him from all other non spectators
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            Bukkit.getOnlinePlayers().stream().filter(all -> all.getGameMode() == GameMode.SURVIVAL)
                    .forEach(all -> all.hidePlayer(instance, player));
        } else {
            // If gamemode isn't Spectator, then hide all spectators for him.
            Bukkit.getOnlinePlayers().stream().filter(it -> it.getGameMode() != GameMode.SURVIVAL)
                    .forEach(all -> player.hidePlayer(instance, all.getPlayer()));
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        var player = e.getPlayer();
        // If gamemode to change is spectator
        if (e.getNewGameMode() != GameMode.SURVIVAL) {

            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // If players are not specs, hide them the player
                if (all.getGameMode() == GameMode.SURVIVAL) {
                    all.hidePlayer(instance, player);
                } else {
                    // If players are specs, then show them to the player
                    player.showPlayer(instance, all);
                }
            });
        } else {
            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // When switching to other gamemodes, show them if not visible to player
                if (!all.canSee(player)) {
                    all.showPlayer(instance, player);
                }
                // If one of the players is a spec, hide them from the player
                if (all.getGameMode() != GameMode.SURVIVAL) {
                    player.hidePlayer(instance, all);
                }
            });
        }
    }
    
}
