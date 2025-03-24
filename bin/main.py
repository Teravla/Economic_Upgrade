import pandas as pd


# Définition des prix des ressources
ORE = {
    200: ["Coal"],
    100: ["Copper"],
    40:  ["Iron", "Gold"],
    30:  ["Lapis", "Quartz", "Amethyst", "Redstone"],
    20:  ["Diamond", "Emerald"],
    1:   ["Netherite", "Ancient Debris"]
}

STONE = {
    ("Stone", "Cobblestone", "Diorite", "Granite", "Andesite", "Deepslate", "Calcite", 
     "Tuff", "Dripstone", "Basalt", "Blackstone", "Netherrack", "End Stone", "Sandstone", "Gravel"): 200, 
    ("Glass",): 400,
}

FLOWER = {
    ("Dandelion", "Allium", "Bluet", "Orchid", "Cornflower", "Lily of the Valley", "Oxeye Daisy", 
     "Poppy", "Tulip", "Sunflower", "Lilac", "Rose Bush", "Peony", "Mushroom", "Fungus", "Sprouts"): 200,
    ("Wither Rose",): 1,
    ("Spore Blossom",): 30,
    ("Sugar Cane",): 100,
}

DIRT = {
    ("Dirt", "Podzol", "Mycelium", "Grass", "Mud", "Nylium", "Fern", 
     "Seagrass", "Pickle", "Path", "Farmland"): 200,
}

WOOD = {
    ("Log", "Planks", "Sapplings", "Roots", "Leaves", "Vines", "Stem", 
     "Wood", "Hyphae", "Bush", "Azalea"): 100,
}

MISC = {
    "Sponge": 20 * 30,
    "Wool": 4 * 200,
}

# Fusionner les dictionnaires pour faciliter la recherche
PRICE_MAP = {}


# Charger le CSV
df = pd.read_csv("IDEA.csv")

# Vérifier si la colonne "Price" existe, sinon la créer
if "Price" not in df.columns:
    df["Price"] = None  # Initialisation avec des valeurs nulles

# Remplacer uniquement les valeurs NaN ou nulles par les prix correspondants
df["Price"] = df["Price"].fillna(df["Name"].map(PRICE_MAP)).fillna(0)

# Sauvegarder le CSV mis à jour
df.to_csv("items_with_prices.csv", index=False)

print("CSV mis à jour avec les prix ! ✅")
