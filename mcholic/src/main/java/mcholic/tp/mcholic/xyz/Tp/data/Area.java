package mcholic.tp.mcholic.xyz.Tp.data;

import mcholic.tp.mcholic.xyz.Tp.mcholic;
import mcholic.tp.mcholic.xyz.Tp.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import java.util.HashMap;
import java.util.UUID;

public class Area {

    private Player player;
    public static HashMap<UUID, Location> dataloc = new HashMap<>();
    public static HashMap<UUID, Location> dataloc2 = new HashMap<>();

    public Area(Player player) {
        this.player = player;
     }

    public void setFirst(PlayerInteractEvent event){
        Location loc = event.getClickedBlock().getLocation();
        EquipmentSlot e = event.getHand();

        if(loc != null){
            if (e.equals(EquipmentSlot.HAND) && Util.inHand(player, Material.STICK)) {
                dataloc.put(player.getUniqueId(), loc);
                player.sendMessage("§7첫번째 좌표가 스폰 포인트가 §cX" + (int) loc.getX() + " §aY" + (int) loc.
                        getY() + " §eZ" + (int) loc.getZ() + "§7" +
                        "좌표로 지정 되었습니다!"  );
                event.setCancelled(true);
            }
        }
    }

    public void setSecond(PlayerInteractEvent event){
        Location loc = event.getClickedBlock().getLocation();
        EquipmentSlot e = event.getHand();

        if(loc != null){
            if (e.equals(EquipmentSlot.HAND) && Util.inHand(player, Material.STICK)) {

                dataloc2.put(player.getUniqueId(), loc);

                player.sendMessage("§b두번째 좌표가 스폰 포인트가 §cX" + (int) loc.getX() + " §aY" + (int) loc.
                        getY() + " §eZ" + (int) loc.getZ() + "§b" +
                        "좌표로 지정 되었습니다!");
                event.setCancelled(true);
            }
        }
    }
    public Location getLoc(){
        return this.dataloc.get(player.getUniqueId());
    }
    public Location getLoc2(){
        return this.dataloc2.get(player.getUniqueId());
    }
}
