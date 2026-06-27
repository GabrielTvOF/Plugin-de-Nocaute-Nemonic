package org.mynion.knockoutfix;

import org.bukkit.plugin.java.JavaPlugin;

public class KnockoutFix extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new KnockoutAttackFixListener(), this);
        getLogger().info("[KnockoutFix] Ativo! Bug de ataque de jogadores nocauteados corrigido.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[KnockoutFix] Desativado.");
    }
}
