package mcholic.tp.mcholic.xyz.Tp.util;

import mcholic.tp.mcholic.xyz.Tp.data.ConfigManager;
import mcholic.tp.mcholic.xyz.Tp.data.Guild;
import mcholic.tp.mcholic.xyz.Tp.mcholic;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.List;

public class Util {
    /***
     *
     * @param player = Event's player
     * @param material = Target to compare with player's hand material
     * @return returning player and material is equal
     */
    public static boolean inHand(Player player,Material material) {
        return player.getInventory().getItemInMainHand().getType().equals(material);
    }

    /***
     *
     * @param msg Change message as %aaa% to value of
     * @param player Getting owner player in the player guild
     * @return Replace all message from message to out
     */
    public static String replace(String msg, Player player){
        String out = msg;

        Guild guild = new Guild(player);
        String guildname = guild.Guildname();

        msg = msg.replaceAll("%money%",  RequireMoney() + "" );
        msg = msg.replaceAll("%owner%",  guild.getOwner().getDisplayName());
        msg = msg.replaceAll("%player%", player.getDisplayName());
        msg = msg.replaceAll("%guild%", guildname);
        out = msg;

        out = ChatColor.translateAlternateColorCodes('&', out);
        return out;
    }

    public static double RequireMoney(){
        String Requiremoney = mcholic.config.get("money").toString();
        double Require = Double.valueOf(Requiremoney);

        return Require;
    }
    public static ConfigManager GuildConfig(String name){
         return new ConfigManager( "data/guilds/" + name);
    }
    public static  ConfigManager PlayerConfig(String name ){

        return  new ConfigManager( "data/players/" + name);
    }
    /***
     *
     * @param player
     * @param event
     */
    public static void isinArea(Player player, Cancellable event){
        ConfigManager playerdata = Util.PlayerConfig(player.getUniqueId().toString());
        if(player != null){

            String test = (String) playerdata.getConfig().get("Guild");

            ConfigManager GuildData = new ConfigManager("data/guilds/" + test );

            List<Player> players = (List<Player>) GuildData.getConfig().getList("Guilds.players");
            List<String> guildname = (List<String>) GuildData.getConfig().getList("Areas.name");
            List<Location> listpos1 = (List<Location>) GuildData.getConfig().getList("Areas.Pos");
            List<Location> listpos2 = (List<Location>) GuildData.getConfig().getList("Areas.Pos2");

                for(String names : guildname){
                    int index = guildname.indexOf(names);
                    Location pos1 = listpos1.get(index);
                    Location pos2 = listpos2.get(index);

                    Region region = new Region(pos1, pos2);

                    if(region.locationIsInRegion(player.getLocation()) && players.contains(player)){
                        event.setCancelled(false);
                    }
                }
        }
    }
}
