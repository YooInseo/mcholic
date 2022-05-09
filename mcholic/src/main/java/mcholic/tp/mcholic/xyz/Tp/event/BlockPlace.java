package mcholic.tp.mcholic.xyz.Tp.event;

import mcholic.tp.mcholic.xyz.Tp.data.Guild;
import mcholic.tp.mcholic.xyz.Tp.util.Region;
import mcholic.tp.mcholic.xyz.Tp.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    /**
     *
     * @param event Priority 설정을 Highest로 해줌으로서
     *              월드가드에서 막은 블록 설치를 다시 false로 해줌
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(!player.isOp()){
            Util.isinArea(player, event);

            Guild guild = new Guild(player);
//            if(guild != null){
//                Region region = new Region(new Location(Bukkit.getWorld("world"),315,83,363),new Location(Bukkit.getWorld("world"),301,73,349));
//                if(region.locationIsInRegion(player.getLocation())){
//                    guild.getPlayers().forEach(player1 -> {
//                        if(player1.equals(player)){
//                            event.setCancelled(false);
//                        }
//                    });
//                } else{
//                    event.setCancelled(true);
//                }
//            }
        }
    }
}
