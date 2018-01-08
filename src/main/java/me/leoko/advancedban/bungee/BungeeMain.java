package me.leoko.advancedban.bungee;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import me.leoko.advancedban.Universal;
import me.leoko.advancedban.bungee.listener.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {

    private static BungeeMain instance;

    public static BungeeMain get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Universal.get().setup(new BungeeMethods());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ConnectionListenerBungee());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListenerBungee());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new InternalListener());
        ProxyServer.getInstance().registerChannel("AdvancedBan");
        if (ProxyServer.getInstance().getPluginManager().getPlugin("RedisBungee") != null) {
            Universal.get().useRedis(true);
            ProxyServer.getInstance().getPluginManager().registerListener(this, new PubSubMessageListener());
            RedisBungee.getApi().registerPubSubChannels("AdvancedBan", "AdvancedBanConnection");
            ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText("§cAdvancedBan §8» §7RedisBungee detected, hooking into it!"));
        }
    }

    @Override
    public void onDisable() {
        Universal.get().shutdown();
    }
}
