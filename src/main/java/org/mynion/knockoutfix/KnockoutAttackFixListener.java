package org.mynion.knockoutfix;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

public class KnockoutAttackFixListener implements Listener {

    /**
     * CORREÇÃO DO BUG:
     *
     * O plugin Knockout não verifica se o ATACANTE está nocauteado,
     * apenas a vítima. Isso permite que um jogador caído consiga
     * registrar dano em outros jogadores (inclusive aliados caídos).
     *
     * Este listener roda com prioridade HIGHEST, antes do Knockout
     * processar o evento, e cancela o dano caso o atacante esteja
     * nocauteado (ou seja, exista como NPC no plugin Knockout).
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onAttackWhileKnockedOut(EntityDamageByEntityEvent event) {

        // Só nos interessa se o atacante for um jogador
        Entity damagerEntity = event.getDamager();
        if (!(damagerEntity instanceof Player damager)) return;

        // Verifica se o atacante está nocauteado checando os metadados
        // que o plugin Knockout armazena no jogador
        Plugin knockoutPlugin = Bukkit.getPluginManager().getPlugin("Knockout");
        if (knockoutPlugin == null || !knockoutPlugin.isEnabled()) return;

        // O plugin Knockout registra metadados ou usa um NpcManager interno.
        // A forma mais confiável de checar sem acessar as classes internas
        // é verificar o GameMode: o Knockout coloca o jogador em SPECTATOR
        // enquanto está nocauteado.
        if (damager.getGameMode() == org.bukkit.GameMode.SPECTATOR) {
            // Jogador está em modo SPECTATOR = está nocauteado pelo Knockout
            event.setCancelled(true);
        }
    }
}
