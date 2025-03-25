package com.techops.tf;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Market {
    private final Economy economy; // Référence à l'instance de l'économie
    private final Map<String, Double> marketPrices; // Stocke les prix des objets sur le marché

    public Market(Economy economy) {
        this.economy = economy;
        this.marketPrices = new HashMap<>();
        initializeMarket(); // Initialise les prix du marché
    }

    // Initialise les prix du marché
    private void initializeMarket() {
        marketPrices.put("netherite", 20.0); // Prix de base pour Netherite
        marketPrices.put("diamond", 1.0); // Prix de base pour Diamant
        marketPrices.put("gold", 0.5); // Prix de base pour Or
        marketPrices.put("iron", 0.5); // Prix de base pour Fer
        marketPrices.put("coal", 0.1); // Prix de base pour Charbon
        marketPrices.put("cobblestone", 0.01); // Prix de base pour Cobblestone
    }

    // Achète un objet pour un joueur
    public boolean buyItem(Player player, String itemType, int quantity) {
        double price = getPrice(itemType) * quantity;
        if (economy.getBalance(player) >= price) {
            economy.transferFunds(player, Bukkit.getPlayer("market"), price); // Transfert des fonds au marché
            // Donne l'objet au joueur
            giveItemToPlayer(player, itemType, quantity);
            return true; // Achat réussi
        }
        return false; // Solde insuffisant
    }

    // Vends un objet pour un joueur
    public boolean sellItem(Player player, String itemType, int quantity) {
        if (playerHasItem(player, itemType, quantity)) {
            double price = getPrice(itemType) * quantity;
            economy.transferFunds(Bukkit.getPlayer("market"), player, price); // Transfert des fonds au joueur
            // Retire l'objet du joueur
            removeItemFromPlayer(player, itemType, quantity);
            return true; // Vente réussie
        }
        return false; // Le joueur ne possède pas l'objet
    }

    // Obtient le prix d'un objet
    public double getPrice(String itemType) {
        return marketPrices.getOrDefault(itemType.toLowerCase(), 0.0);
    }

    // Donne un objet au joueur
    void giveItemToPlayer(Player player, String itemType, int quantity) {
        ItemStack item = createItem(itemType, quantity);
        if (item != null) {
            player.getInventory().addItem(item); // Ajoute l'objet à l'inventaire du joueur
        }
    }

    // Crée un ItemStack basé sur le type d'objet
    private ItemStack createItem(String itemType, int quantity) {
        return switch (itemType.toLowerCase()) {
            case "netherite" -> new ItemStack(Material.NETHERITE_INGOT, quantity);
            case "diamond" -> new ItemStack(Material.DIAMOND, quantity);
            case "gold" -> new ItemStack(Material.GOLD_INGOT, quantity);
            case "iron" -> new ItemStack(Material.IRON_INGOT, quantity);
            case "coal" -> new ItemStack(Material.COAL, quantity);
            case "cobblestone" -> new ItemStack(Material.COBBLESTONE, quantity);
            default -> null; // Type d'objet inconnu
        };
    }

    // Vérifie si le joueur possède l'objet
    boolean playerHasItem(Player player, String itemType, int quantity) {
        ItemStack item = createItem(itemType, quantity);
        if (item == null) return false; // Type d'objet inconnu
        int totalAmount = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.isSimilar(item)) {
                totalAmount += stack.getAmount();
            }
        }
        return totalAmount >= quantity; // Retourne vrai si le joueur possède suffisamment d'objets
    }

    // Retire l'objet du joueur
    void removeItemFromPlayer(Player player, String itemType, int quantity) {
        ItemStack item = createItem(itemType, quantity);
        if (item != null) {
            PlayerInventory inventory = player.getInventory();
            inventory.removeItem(item); // Retire l'objet de l'inventaire du joueur
        }
    }
}
