package mcholic.tp.mcholic.xyz.Tp.event;

import mcholic.tp.mcholic.xyz.Tp.data.Area;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class BlockClick implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Area area = new Area(player);
        if(player.isOp()){
            if (event.hasBlock()) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    area.setFirst(event);
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    area.setSecond(event);
                }
            }
        }
    }
}
