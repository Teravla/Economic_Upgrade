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
     "Tuff", "Dripstone", "Basalt", "Blackstone", "Netherrack", "End Stone", "Sand", "Gravel"): 200, 
    ("Glass",): 400,
    ("Obsidian",): 20,
}

FLOWER = {
    ("Dandelion", "Allium", "Bluet", "Orchid", "Cornflower", "Lily of the Valley", "Oxeye Daisy", 
     "Poppy", "Tulip", "Sunflower", "Lilac", "Rose Bush", "Peony", "Mushroom", "Fungus", "Sprouts", "Propagule"): 200,
    ("Wither Rose",): 1,
    ("Spore Blossom",): 30,
    ("Sugar Cane",): 100,
}

DIRT = {
    ("Dirt", "Podzol", "Mycelium", "Grass", "Mud", "Nylium", "Fern", 
     "Seagrass", "Pickle", "Path", "Farmland"): 200,
}

WOOD = {
    ("Log", "Planks", "Sapling", "Roots", "Leaves", "Vines", "Stem", 
     "Wood", "Hyphae", "Bush", "Azalea"): 100,
}

MISC = {
    "Sponge": 20 * 30,
    "Wool": 4 * 200,
    "Egg": 0.05 * 200,
    "Dragon Egg": 2 * 20,
    "* Egg": 20 * 1,
    "Disc": 200,
    "Pattern": 200,
    "Echo Shard": 9 * 1,
    "Froglight": 20,
    "Candle": 20,
    "Respawn Anchor": 1,
    "Honey": 20,
    "Potion": 20,
}


# Fusionner les dictionnaires pour faciliter la recherche
PRICE_MAP = {}

# Remplir le dictionnaire PRICE_MAP
for price, items in ORE.items():
    for item in items:
        PRICE_MAP[item] = price

for items, price in STONE.items():
    for item in items:
        PRICE_MAP[item] = price

for items, price in FLOWER.items():
    for item in items:
        PRICE_MAP[item] = price

for items, price in DIRT.items():
    for item in items:
        PRICE_MAP[item] = price

for items, price in WOOD.items():
    for item in items:
        PRICE_MAP[item] = price

PRICE_MAP.update(MISC)  # Ajouter les éléments de MISC

# Charger le CSV
df = pd.read_csv("IDEA.csv")

# Vérifier si la colonne "Price" existe, sinon la créer
if "Price" not in df.columns:
    df["Price"] = None

# Mettre à jour les prix en prenant en compte les sous-chaînes, les cas particuliers et les blocs
for index, row in df.iterrows():
    survival = row["Survival Obtainable"]
    if survival == "NO":
        continue  # Ignorer les items non-obtenables en survie
    name = row["Name"]
    
    # Vérifier les cas généraux "{object}" ou "* {object}"
    price = PRICE_MAP.get(name, None)  # Vérifie le prix exact
    
    # Vérifier les cas "* {object}"
    if price is None:
        for key in PRICE_MAP.keys():
            if key.startswith("* ") and key[2:] == name:
                price = PRICE_MAP[key]
                break
    
    # Vérifier si c'est un cas d'item se terminant par "Egg"
    if price is None and name.lower().endswith("egg"):
        price = 2 * PRICE_MAP.get("Egg", 0)  # Prix plus cher pour les œufs spéciaux
    
    # Vérifier si c'est un bloc (ex: "Block of Iron")
    if price is None and name.lower().startswith("block "):
        item_name = name[6:]  # Enlever "Block " pour obtenir le nom de l'item
        item_price = PRICE_MAP.get(item_name, 0)  # Obtenir le prix de l'item
        price = item_price * 9  # Prix du bloc = prix de l'item * 9
    
    # Si le prix n'est toujours pas trouvé, chercher par sous-chaînes
    if price is None:
        price = next((value for key, value in PRICE_MAP.items() if key.lower() in name.lower()), 0)

    df.at[index, "Price"] = price
    if price == 0:
        print(f"Item: {name}, Price: {price}")

# Sauvegarder le CSV mis à jour
df.to_csv("items_with_prices.csv", index=False)

print("CSV mis à jour avec les prix ! ✅")
