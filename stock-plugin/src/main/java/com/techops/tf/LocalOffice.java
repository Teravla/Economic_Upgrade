package com.techops.tf;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LocalOffice {
    private final Economy economy; // Référence à l'instance de l'économie
    private final Market market; // Référence à l'instance du marché
    private final Map<Player, Double> localBalances; // Stocke les soldes locaux des joueurs

    public LocalOffice(Economy economy, Market market) {
        this.economy = economy;
        this.market = market;
        this.localBalances = new HashMap<>();
    }

    // Ouvre un bureau local pour un joueur
    public void openOffice(Player player) {
        if (!localBalances.containsKey(player)) {
            localBalances.put(player, 0.0); // Initialise le solde local
            player.sendMessage("Votre bureau local a été ouvert !");
        } else {
            player.sendMessage("Vous avez déjà un bureau local ouvert.");
        }
    }

    // Dépose de l'argent dans le bureau local
    public boolean deposit(Player player, double amount) {
        if (economy.getBalance(player) >= amount) {
            economy.transferFunds(player, Bukkit.getPlayer("market"), amount); // Transfert à la banque du marché
            localBalances.put(player, localBalances.get(player) + amount); // Met à jour le solde local
            player.sendMessage("Vous avez déposé " + amount + " au bureau local.");
            return true; // Dépôt réussi
        }
        player.sendMessage("Solde insuffisant pour déposer.");
        return false; // Dépôt échoué
    }

    // Retire de l'argent du bureau local
    public boolean withdraw(Player player, double amount) {
        double localBalance = localBalances.getOrDefault(player, 0.0);
        if (localBalance >= amount) {
            localBalances.put(player, localBalance - amount); // Met à jour le solde local
            economy.transferFunds(Bukkit.getPlayer("market"), player, amount); // Transfert à l'utilisateur
            player.sendMessage("Vous avez retiré " + amount + " de votre bureau local.");
            return true; // Retrait réussi
        }
        player.sendMessage("Solde local insuffisant pour retirer.");
        return false; // Retrait échoué
    }

    // Achète un objet à partir du bureau local
    public boolean buyItem(Player player, String itemType, int quantity) {
        double price = market.getPrice(itemType) * quantity;
        if (localBalances.getOrDefault(player, 0.0) >= price) {
            localBalances.put(player, localBalances.get(player) - price); // Met à jour le solde local
            market.giveItemToPlayer(player, itemType, quantity); // Donne l'objet au joueur
            player.sendMessage("Vous avez acheté " + quantity + " " + itemType + "(s).");
            return true; // Achat réussi
        }
        player.sendMessage("Solde local insuffisant pour acheter cet objet.");
        return false; // Achat échoué
    }

    // Vends un objet au bureau local
    public boolean sellItem(Player player, String itemType, int quantity) {
        if (market.playerHasItem(player, itemType, quantity)) {
            double price = market.getPrice(itemType) * quantity;
            market.removeItemFromPlayer(player, itemType, quantity); // Retire l'objet du joueur
            localBalances.put(player, localBalances.getOrDefault(player, 0.0) + price); // Met à jour le solde local
            player.sendMessage("Vous avez vendu " + quantity + " " + itemType + "(s).");
            return true; // Vente réussie
        }
        player.sendMessage("Vous ne possédez pas cet objet.");
        return false; // Vente échouée
    }
}
