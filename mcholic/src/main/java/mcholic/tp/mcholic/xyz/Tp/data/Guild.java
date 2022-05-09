package mcholic.tp.mcholic.xyz.Tp.data;

import mcholic.tp.mcholic.xyz.Tp.mcholic;
import mcholic.tp.mcholic.xyz.Tp.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Guild {

    private Player player;
    /***
     *  UUID  =  길드장의 UUID
     *  Player = 길드장에게 요청 보낸 플레이어
     */
    public static HashMap<UUID,Player> request = new HashMap<>();

    private Area area;
    /***
     * 길드의 모든 기능을 저장할 클래스 입니다. 플레이어 데이타 하나로 길드의 모든 정보를
     * 열람 할 수 있습니다.
     * @param player 이벤트,커맨드를 실행한 플레이어를 불러옵니다.
     */

    public Guild(Player player) {
        this.player = player;
        area = new Area(player);
    }

    public List<Player> getPlayers() {
        return (List<Player>) getPlayerGuild().getConfig().getList("Guilds.players");
    }

    public String Guildname(){
        return getPlayerGuild().getConfig().get("Guilds.name").toString();
    }

    public void leaveGuild(){
        ConfigManager playerconfig = Util.PlayerConfig(this.player.getUniqueId().toString());
        String name = playerconfig.getConfig().getString("Guild");
        ConfigManager GuildConfig =  Util.GuildConfig(name);
        if(player.equals(getOwner())){
            for(Player players : getPlayers()){
                ConfigManager playersconfig = Util.PlayerConfig(players.getUniqueId().toString());
                playersconfig.getConfig().set("Guild","");
                playersconfig.saveConfig();
                players.sendMessage("§7길드장이 탈퇴를 하여 길드는 사라졌습니다...");
            }

            GuildConfig.Delete();
            mcholic.config.removeObject("GuildList",name);
            playerconfig.getConfig().set("Guild","");
            playerconfig.saveConfig();
        } else{
            getOwner().sendMessage(player.getDisplayName()  + "님이 길드를 탈퇴 하였습니다.");
            playerconfig.getConfig().set("Guild", "");

            playerconfig.saveConfig();
            GuildConfig.removeObject("Guilds.players", player);
        }
    }

    /***
     *
     * @param GuildName 요청을 보낼 길드의 이름을 불러와 해당 길드장과
     *                  플레이어에게 메세지를 보내고 길드 데이터에 리퀘스트 플래그를 세워 저장하게 합니다.
     */
    public void ReQuest(String GuildName){
        ConfigManager playerconfig = Util.PlayerConfig(this.player.getUniqueId().toString());
        Player owner = getGuildOwner(GuildName);
        if(!request.containsKey(owner.getUniqueId())){
                if(!owner.equals(player)){
                    owner.sendMessage(player.getDisplayName() +  " 님이 길드 가입 신청을 보내왔습니다.");
                    request.put(owner.getUniqueId(), player);
                    player.sendMessage(owner.getDisplayName() + " 님에게 길드 가입 신청을 보냈습니다.");
                    Bukkit.getConsoleSender().sendMessage("" + playerconfig.getConfig().get("Guild"));
                } else{
                    player.sendMessage("§c자기 자신에겐 초대를 보내지 못합니다!");
                }
        } else{
            player.sendMessage("§7이미 가입 요청을 기달리고 있습니다...");
        }
    }
    public void AcceptRequest(Player target){
        if(!player.equals(target)){
            ConfigManager playerconfig = Util.PlayerConfig(this.player.getUniqueId().toString());
            ConfigManager targetconfig = Util.PlayerConfig(target.getUniqueId().toString());

            String name = playerconfig.getConfig().getString("Guild");
            ConfigManager GuildData = new ConfigManager("data/guilds/" + name);
            GuildData.addObject("Guilds.players", target);
            targetconfig.getConfig().set("Guild", name);
            targetconfig.saveConfig();

            if(request.containsKey(player.getUniqueId())){
                player.sendMessage(target.getDisplayName() +  " 님의 요청을 수락 하였습니다!");
                target.sendMessage(player.getDisplayName() + " 님이 길드 수락을 하였습니다! 길드 : " + name);
            } else{
                player.sendMessage("§c해당 유저로부터 온 초대가 없습니다!");
            }
        }
    }
    public void ban(Player target) {
        if (!player.equals(target)) {
            ConfigManager targetconfig = Util.PlayerConfig(target.getUniqueId().toString());

            GuildConfig().removeObject("Guilds.players",target);
            player.sendMessage(target.getDisplayName() +  " 님을 추방하였습니다.");
            target.sendMessage("" + GuildConfig().getConfig().get("Guilds.name").toString() +  " 길드에서 추방 되었습니다.");

            targetconfig.getConfig().set("Guild","");
            targetconfig.saveConfig();

        }
    }

    public ConfigManager GuildConfig(){
        ConfigManager playerConfig = Util.PlayerConfig(player.getUniqueId().toString());

        String name = playerConfig.getConfig().getString("Guild");

        ConfigManager GuildData = new ConfigManager("data/guilds/" + name);
        return GuildData;
    }
    public void DenyRequest(Player target){
        if(request.containsKey(player.getUniqueId())){
            request.remove(player.getUniqueId());
            target.sendMessage(player.getDisplayName() + " 님이 길드 요청을 거절하였습니다.");
        } else{
            player.sendMessage("§c해당 유저로부터 온 초대가 없습니다!");
        }
    }
    public Player getOwner(){
        return (Player) getPlayerGuild().getConfig().get("Guilds.owner");
    }

    public ConfigManager getPlayerGuild(){
        ConfigManager config = new ConfigManager("data/guilds/" + GetPlayerGuildName());
        return config;
    }
    public String GetPlayerGuildName(){
        return getPlayer().getConfig().get("Guild").toString();
    }
    public ConfigManager getPlayer(){
        ConfigManager config = new ConfigManager("data/players/" + player.getUniqueId());
        return config;
    }
    public Player getGuildOwner(String GuildName){
        ConfigManager guild = getTargetGuild(GuildName);
        Player owner = (Player) guild.getConfig().get("Guilds.owner");
        return owner;
    }
    public ConfigManager getTargetGuild(String GuildName){
        return Util.GuildConfig(GuildName);
    }
}
