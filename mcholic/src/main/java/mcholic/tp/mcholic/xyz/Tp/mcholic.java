package mcholic.tp.mcholic.xyz.Tp;

import mcholic.tp.mcholic.xyz.Tp.cmd.cmd;
import mcholic.tp.mcholic.xyz.Tp.data.ConfigManager;
import mcholic.tp.mcholic.xyz.Tp.event.BlockBreak;
import mcholic.tp.mcholic.xyz.Tp.event.BlockClick;
import mcholic.tp.mcholic.xyz.Tp.event.BlockPlace;
import mcholic.tp.mcholic.xyz.Tp.event.PlayerJoinEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public final class mcholic extends JavaPlugin implements Listener{

    public static ConfigManager config = new ConfigManager("config");

    public static String prefix ="";
    public static mcholic plugin;
    public static String accept = "";
    public static String createGuild = "";
    public static String quit = "";
    public static String createArea = "";
    public static String request = "";
    public static String setArea = "";
    public static String AlreadyRequest = "";


    public static String notEnoughMoney = "";
    public static String AreaName = "";
    public static int requiremoney;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("길드").setExecutor(new cmd());
        Listener[] events = {new BlockClick(), new BlockPlace(),new BlockBreak(), new PlayerJoinEvent()};
        PluginManager pm = Bukkit.getPluginManager();
        Arrays.stream(events).forEach(classes->{pm.registerEvents(classes,this);});
        config = new ConfigManager("config");

        config.getConfig().addDefault("messages.prefix","");
        config.getConfig().addDefault("messages.accept","");
        config.getConfig().addDefault("messages.createGuild","");
        config.getConfig().addDefault("messages.SetAreaName","");
        config.getConfig().addDefault("messages.quit","");
        config.getConfig().addDefault("messages.createArea","");
        config.getConfig().addDefault("messages.setArea","");
        config.getConfig().addDefault("messages.request","");
        config.getConfig().addDefault("messages.alreadyreqeust","");
        config.getConfig().addDefault("messages.notEnoughMoney","");
        config.getConfig().addDefault("money","");
        config.getConfig().addDefault("GuildList",new ArrayList<>());
        config.getConfig().options().copyDefaults(true);

        config.saveDefualtConfig();
        config.saveConfig();

        prefix = SetString( prefix, "messages.prefix");
        accept = SetString(accept,"messages.accept");
        createGuild = SetString( createGuild,"messages.createGuild");
        quit = SetString(quit,"messages.quit");
        createArea = SetString( createArea,"messages.createArea");
        setArea = SetString( setArea,"messages.setArea");
        request = SetString(request,"messages.request");
        notEnoughMoney = SetString(notEnoughMoney,"messages.notEnoughMoney");
        requiremoney = SetInt(requiremoney, "money");
        AreaName = SetString(AreaName,"messages.SetAreaName");

        if (!setupEconomy() ) {
            System.out.println("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    /**
     * SetString Method Description
     * This method getting value type of string from the config
     * @param path
     * @return
     */
    public String SetString(String original, String path){
        original= config.getConfig().getString(path);
        return original;
    }
    public Integer SetInt(int original,String path){
        original = config.getConfig().getInt(path);
        return original;
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            Bukkit.getConsoleSender().sendMessage(Bukkit.getPluginManager().getPlugin("mcholic").getName() +  " §a이코노미와 연동이 완료 되었습니다.");
        }
        return (economy != null);
    }

    public static Economy getEconomy() {
        return economy;
    }
}
