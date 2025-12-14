# Projet Fil Rouge — Jeu Java

> **Auteurs :**
>
>  - VIGNE Nathaël
>  - LUCIEN-REINETTE Quentin
>  - BEN HAMOU Ilian
>
> **Date :** 15/12/2025

---

## Description

Jeu d'aventure-console développé en Java. Le joueur incarne un Champion qui peut équiper des armes et armures, gérer un inventaire, affronter des ennemis et gagner de l'expérience. Le coeur du gameplay repose sur les classes : `Champion`, `Player`, `Weapon`, `Armor`, `Inventory` et `Fight`.

Le projet est conçu pour la programmation orientée objet avancée (POOA) : modularité, gestion d'inventaire, niveaux et progression d'armes.

---

## Architecture et Conception

- Langage : Java (Gradle)
- Build : Gradle (wrapper fourni)
- Entrées : console (Scanner)

Principales responsabilités :
- `Champion` : structure de base pour tout combattant (vie, moveSpeed, armes, armures)
- `Player` : extension de `Champion` avec inventaire et gestion de niveau
- `Weapon` / `Armor` : objets équipables avec expérience et niveaux
- `Inventory` : stockage des armes, armures et potions
- `Fight` : logique de combat entre champions

Design notes : le code favorise la séparation des responsabilités et la testabilité unitaire.

---

## Fonctionnalités

- Gestion d'un inventaire (armes, armures, potions)
- Échange (swap) d'équipement entre personnage et inventaire
- Combat entre le joueur et des ennemis avec logique de vitesse et d'attaques
- Progression d'armes (XP / niveau)

---

## Prérequis

- JDK 21 ou supérieur
- Git (optionnel)

---

## Lancer

Pour lancer le projet
```
.\gradlew.bat build
cd app
java -cp build\classes\java\main projet.controller.App
```

---

## Tests

Les tests unitaires se trouvent sous `src/test/java`. Lancez :

  .\gradlew.bat test

Les rapports sont générés dans `build/reports/tests/`.

---

## Structure du projet

```
src
├── main
│   └── java
│       └── projet
│           └── controller/
│               ├── App.java
│               ├── Champion.java
│               ├── Player.java
│               ├── Fight.java
│           ├── Inventory.java
│           ├── weapon/
│           ├── armor/
│           └── enemies/
└── test
│   └── PlayerTest.java
```

---

## Améliorations prévues

- Ajouter un système de sauvegarde / chargement
- Implémente une logique d'IA des ennemis pour améliorer le système de combat
- Interface graphique

## Méthodologie

La démarche a été la suivante :
1. Analyse du besoin et rédaction d’un premier Notebook pour prototyper les classes principales.
2. Séparation du modèle (données et logique métier), des vues (affichage console) et des contrôleurs (logique d’interaction).
3. Documentation et génération du diagramme UML via PlantUML.

## Transparence IA

- **Claude** : Vérification de la conformité avec la grille d’évaluation, suggestions d’améliorations, aide à la génération et à la validation de tests unitaires.
- **PlantUML** : Génération automatique du diagramme de classes à partir du code.

### Usage et Critique

L’IA a été principalement utilisée pour :
- Générer et valider des exemples de tests unitaires.
- Vérifier la conformité du projet avec la grille d’évaluation.
- Suggérer des axes d’amélioration pour répondre aux critères attendus.

**Exemple concret de limitation** :
L’IA a proposé d’implémenter une méthode statique dans la classe `Player` pour gérer la création d’armures, ce qui violait le principe d’Open/Closed (SOLID) et le découplage du pattern MVC. J’ai préféré utiliser le pattern Factory via une interface dédiée (`IArmorProvider`), afin de respecter l’extensibilité et la séparation des responsabilités.

### Conclusion

En conclusion, l’IA s’est révélée globalement peu pertinente tout au long du projet. Son utilité s’est limitée à la phase finale, où elle a surtout servi à valider certains points et à vérifier la conformité avec la grille d’évaluation, sans véritable apport technique significatif.