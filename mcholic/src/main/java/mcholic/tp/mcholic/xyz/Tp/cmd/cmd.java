package mcholic.tp.mcholic.xyz.Tp.cmd;

import mcholic.tp.mcholic.xyz.Tp.data.Area;
import mcholic.tp.mcholic.xyz.Tp.data.ConfigManager;
import mcholic.tp.mcholic.xyz.Tp.data.Guild;
import mcholic.tp.mcholic.xyz.Tp.mcholic;
import mcholic.tp.mcholic.xyz.Tp.util.Util;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;


public class cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Guild guild = new Guild(player);
        ConfigManager playerdata = null;
        ConfigManager GuildData = null;
        String name = null;
        Player target = null;
        Economy economy = mcholic.getEconomy();

        if (args.length != 0) {
            switch (args[0]) {
                /***
                 *
                 *  구현완료
                 */
                case "생성":
                    double hasmoney = economy.getBalance(player);
                    double Require = Double.valueOf(mcholic.config.get("money").toString());

                    if (hasmoney >= Require) {
                        EconomyResponse response = economy.withdrawPlayer(player, Require);
                        if (response.transactionSuccess()) {
                            name = ChatColor.translateAlternateColorCodes('&', args[1]);

                            GuildData = new ConfigManager("data/guilds/" + name);

                            playerdata = Util.PlayerConfig(player.getUniqueId().toString());

                            playerdata.getConfig().set("Guild", name);
                            playerdata.getConfig().set("Player", player);
                            playerdata.saveConfig();

                            GuildData.getConfig().set("Guilds.name", name);
                            GuildData.getConfig().set("Guilds.owner", player);
                            GuildData.getConfig().set("Guilds.players", new ArrayList<>());

                            GuildData.getConfig().set("Areas.name", new ArrayList<>());
                            GuildData.getConfig().set("Areas.Pos", new ArrayList<>());
                            GuildData.getConfig().set("Areas.Pos2", new ArrayList<>());
                            List<Object> players = (List<Object>) GuildData.getConfig().getList("Guilds.players");

                            players.add(player);
                            GuildData.saveConfig();

                            List<String> guildList = (List<String>) mcholic.config.getConfig().getList("GuildList");
                            guildList.add(name);
                            mcholic.config.saveConfig();
                            player.sendMessage(name + " 길드가 생성되었습니다!");
                        }
                    } else {
                        player.sendMessage(Util.replace(mcholic.notEnoughMoney, player));
                    }
                    break;
                case "목록":
                    player.sendMessage("길드 목록입니다!");
                    List<String> guildList = (List<String>) mcholic.config.getConfig().getList("GuildList");
                    for (String names : guildList) {
                        player.sendMessage(names);
                    }
                    break;

                case "가입신청":
                    if (args(player, args, 2)) {
                        name = ChatColor.translateAlternateColorCodes('&', args[1]);

                        guild.ReQuest(name);
                    }
                    break;
                case "탈퇴":
                    guild.leaveGuild();
                    break;
                case "추방":
                    if (args(player, args, 2)) {
                        target = Bukkit.getPlayer(args[1]);
                        guild.ban(target);
                    }
                        break;
                case "가입수락":
                    if (args(player, args, 2)) {
                        target = Bukkit.getPlayer(args[1]);
                        guild.AcceptRequest(target);
                    }
                    break;
                case "가입거절":
                    if (args(player, args, 2)) {
                        target = Bukkit.getPlayer(args[1]);
                        guild.DenyRequest(target);
                    }
                    break;

                /***
                 *   구현완료
                 *  구역 생성 & 불러오기 알고리즘
                 *  구역 이름으로 우선 인덱스를 불러온 다음, EX)  int i = areas.indexOf(areaname);
                 *  해당 인댁스로 Pos1, Pos2값을 불러온다.
                 *
                 */
                case "구역생성":
                    name = ChatColor.translateAlternateColorCodes('&', args[1]);

                    if (Area.dataloc.containsKey(player.getUniqueId()) && Area.dataloc2.containsKey(player.getUniqueId())) {

                        GuildData = new ConfigManager("data/guilds/" + name);
                        String areaname = ChatColor.translateAlternateColorCodes('&', args[2]);

                        GuildData.addObject("Areas.name", areaname);
                        GuildData.addObject("Areas.Pos", Area.dataloc.get(player.getUniqueId()));
                        GuildData.addObject("Areas.Pos2", Area.dataloc2.get(player.getUniqueId()));

                        player.sendMessage("§a성공적으로 " + name + " &a길드의 " + areaname + " &a구역을 생성하였습니다!");

                    } else {
                        player.sendMessage("§c구역이 미지정 상태입니다.");
                    }
                    break;
                case "reload":
                    player.sendMessage("리로드가 되었습니다.");
                    mcholic.config.reloadConfig();
                    break;

                case "구역삭제":
                    if (args(player, args, 3)) {

                        name = ChatColor.translateAlternateColorCodes('&', args[1]);

                        String areanmae = ChatColor.translateAlternateColorCodes('&', args[2]);

                        GuildData = new ConfigManager("data/guilds/" + name);
                        List<String> AreaNames = (List<String>) GuildData.getConfig().getList("Areas.name");

                        int index = AreaNames.indexOf(areanmae);

                        List<Location> pos1 = (List<Location>) GuildData.getConfig().getList("Areas.Pos");
                        List<Location> pos2 = (List<Location>) GuildData.getConfig().getList("Areas.Pos2");
                        List<Location> Names = (List<Location>) GuildData.getConfig().getList("Areas.name");
                        pos1.remove(index);
                        pos2.remove(index);
                        Names.remove(index);

                        GuildData.saveConfig();

                        player.sendMessage("§c삭제가 완료되었습니다! §b길드 이름 : " + name + "");
                    }
                    break;
            }
        } else {
            sendMessage(player);
        }
        return false;
    }
    public boolean args(Player player, String[] args,int index){
        if(args.length == index ){
            return true;
        } else{

            sendMessage(player);
            return false;
        }
    }

    public void sendMessage(Player player) {
        if (player.isOp()) {
            player.sendMessage("§a/길드 생성 §7<길드이름>");
            player.sendMessage("§a/길드 탈퇴");
            player.sendMessage("§c(길드장이 탈퇴 시 길드가 사라짐 주의)");
            player.sendMessage("§a/길드 추방 §7<플레이어>");
            player.sendMessage("§b/길드 가입신청 §7<길드이름>");
            player.sendMessage("§b/길드 목록");
            player.sendMessage("§e/길드 구역생성 <길드이름> <구역이름>");
            player.sendMessage("§e/길드 reload");
        } else {
            player.sendMessage("§a/길드 생성 §7<길드이름>");
            player.sendMessage("§a/길드 탈퇴");
            player.sendMessage("§c(길드장이 탈퇴 시 길드가 사라짐 주의)");
            player.sendMessage("§a/길드 추방 §7<플레이어>");
            player.sendMessage("§b/길드 가입신청 §7<길드이름>");
            player.sendMessage("§b/길드 목록");
        }
    }
}
