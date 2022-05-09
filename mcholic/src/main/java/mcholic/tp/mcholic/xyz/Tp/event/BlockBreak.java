package mcholic.tp.mcholic.xyz.Tp.event;


import mcholic.tp.mcholic.xyz.Tp.util.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(!player.isOp()){
            Util.isinArea(player, event);
        }
    }
}
