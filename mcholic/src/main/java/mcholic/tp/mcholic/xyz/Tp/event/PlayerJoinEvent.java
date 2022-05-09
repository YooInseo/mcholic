package mcholic.tp.mcholic.xyz.Tp.event;

import mcholic.tp.mcholic.xyz.Tp.data.ConfigManager;
import mcholic.tp.mcholic.xyz.Tp.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event){

        Player player = event.getPlayer();

        ConfigManager configManager = Util.PlayerConfig(player.getUniqueId().toString());
        String name = configManager.getConfig().getString("Guild");
//        if(configManager != null){
//            configManager.getConfig().set("Guild","");
//            configManager.getConfig().set("Player",player);
//            configManager.saveConfig();
//        }
    }
}
