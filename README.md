# 💡 IDEA

L'objectif ici est de créer un système économique attrayant qui permette d'ajouter des fonctionnalités intéressantes sans compromettre l'intégrité du système de base.

## 🛒 Système de Marché

Bien que l'idée initiale n'était pas d'implémenter un échange d'objets illimité, cela s'est finalement révélé judicieux. Une première version de l'échange d'objets est disponible dans le fichier CSV suivant : [CSV](IDEA.csv).

### 💰 Valeurs Monétaires

Le système repose sur les valeurs suivantes : 

- **1 Netherite = 20 Diamants = 40 Or = 40 Fer = 200 Charbon = 10,000 Cobblestones**  

Pour simplifier les échanges, nous prendrons la base **Netherite**, notée **BN** (comme les **Biscuits** 🍪).

## 📈 Système Boursier

L'ajout d'un système boursier simple peut enrichir l'équilibrage du jeu tout en apportant une dimension éducative.

### Principe

Un objet déposé dans un bureau central prend de la valeur en fonction de sa valeur d'origine et du nombre total d'exemplaires détenus. Ce bureau central est géré par un administrateur neutre, garantissant l'intégrité des échanges.

### Exemple

Imaginons que Marc et Francesco aient chacun 20 diamants.

#### Investissement Initial

**`T=0`**
| Nom       | Fonds Propre | Fonds Investi |
|-----------|--------------|---------------|
| Marc      | 10           | 10            |
| Francesco | 20           | 0             |

Après une période d'investissement :

**`T=1`**
| Nom       | Fonds Propre | Fonds Investi |
|-----------|--------------|---------------|
| Marc      | 10           | 11            |
| Francesco | 20           | 10            |

Francesco décide alors d'investir tous ses fonds.

**`T=3`**
| Nom       | Fonds Propre | Fonds Investi |
|-----------|--------------|---------------|
| Marc      | 10           | 10            |
| Francesco | 0            | 20            |

L'office central détient maintenant 30 diamants. Les revenus des investisseurs évoluent :

**`T=4`**
| Nom       | Fonds Propre | Fonds Investi |
|-----------|--------------|---------------|
| Marc      | 10           | 10.25         |
| Francesco | 0            | 20.5          |

Les valeurs d'investissement varient proportionnellement à l'investissement total et au taux de rentabilité.

## 🚫 Fraude

Pour prévenir l'enrichissement rapide via des retraits anticipés, une période minimale d'investissement est requise. En cas de retrait anticipé, un pourcentage sera déduit du montant retiré.

## 📍 Emplacement

L'office central doit être situé à un endroit accessible, idéalement près d'un point de spawn.

## 🏦 Extensions

Tout joueur peut ouvrir un bureau local sous certaines conditions :

1. L'ouverture doit être **approuvée** par l'office central.
2. Le propriétaire ne doit pas avoir été sanctionné pour fraude.
3. L'ouverture est payante, avec un montant conservé par l'office central, remboursable en cas de fermeture.
4. Un propriétaire ne peut avoir qu'un seul établissement pour éviter la création de corporations.

**Important :** <span style="color: red; font-weight: bold">Contrairement à l'office central, le propriétaire n'est soumis à aucune règle tant qu'il peut prouver qu'il peut rembourser ses clients en intégralité, intérêts compris.</span> **⚠️**

### 🔍 Cas Pratique

Lewis ouvre un établissement financier. Marc, déçu de son investissement auprès de l'office central, décide de prêter 10 diamants à Lewis. Rien n'empêche Lewis d'investir cet argent à l'office central, tant qu'il peut rembourser Marc à tout moment.

## 🏅 Règle d'Or

**Tout se vend et tout s'achète.** 💸
