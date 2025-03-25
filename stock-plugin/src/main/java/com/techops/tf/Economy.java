package com.techops.tf;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class Economy {
    private final Map<String, Double> balances; // Stocke les soldes des joueurs

    // Valeurs monétaires de base
    private static final double NETHERITE_VALUE = 1.0; // Valeur de référence
    private static final double DIAMOND_VALUE = NETHERITE_VALUE / 20.0; // 1 Netherite = 20 Diamants
    private static final double GOLD_VALUE = NETHERITE_VALUE / 40.0; // 1 Netherite = 40 Or
    private static final double IRON_VALUE = NETHERITE_VALUE / 40.0; // 1 Netherite = 40 Fer
    private static final double COAL_VALUE = NETHERITE_VALUE / 200.0; // 1 Netherite = 200 Charbon
    private static final double COBBLESTONE_VALUE = NETHERITE_VALUE / 10000.0; // 1 Netherite = 10,000 Cobblestones

    public Economy() {
        balances = new HashMap<>();
    }

    // Initialise le solde d'un joueur
    public void setBalance(Player player, double amount) {
        balances.put(player.getName(), amount);
    }

    // Récupère le solde d'un joueur
    public double getBalance(Player player) {
        return balances.getOrDefault(player.getName(), 0.0);
    }

    // Transfert des fonds d'un joueur à un autre
    public boolean transferFunds(Player fromPlayer, Player toPlayer, double amount) {
        double fromBalance = getBalance(fromPlayer);
        if (fromBalance >= amount) {
            setBalance(fromPlayer, fromBalance - amount);
            setBalance(toPlayer, getBalance(toPlayer) + amount);
            return true; // Transfert réussi
        }
        return false; // Solde insuffisant
    }

    // Investit des fonds pour un joueur
    public boolean investFunds(Player player, double amount) {
        double currentBalance = getBalance(player);
        if (currentBalance >= amount) {
            setBalance(player, currentBalance - amount);
            // Logique d'investissement à ajouter ici (par exemple, augmenter les rendements)
            return true; // Investissement réussi
        }
        return false; // Solde insuffisant
    }

    // Méthode pour obtenir la valeur d'un objet en fonction de son type
    public double getValue(String itemType) {
        return switch (itemType.toLowerCase()) {
            case "netherite" -> NETHERITE_VALUE;
            case "diamond" -> DIAMOND_VALUE;
            case "gold" -> GOLD_VALUE;
            case "iron" -> IRON_VALUE;
            case "coal" -> COAL_VALUE;
            case "cobblestone" -> COBBLESTONE_VALUE;
            default -> 0.0;
        };
    }
}
