# Projet Fil Rouge — Jeu Java (Console)

> **Auteurs :**
>
>  - VIGNE Nathaël
>  - LUCIEN-REINETTE Quentin
>  - BEN HAMOU Ilian
>
> **Date :** 15/12/2025

---

## 📝 Description

Jeu d'aventure-console développé en Java. Le joueur incarne un Champion qui peut équiper des armes et armures, gérer un inventaire, affronter des ennemis et gagner de l'expérience. Le coeur du gameplay repose sur les classes : `Champion`, `Player`, `Weapon`, `Armor`, `Inventory` et `Fight`.

Le projet est conçu pour la programmation orientée objet avancée (POOA) : modularité, gestion d'inventaire, niveaux et progression d'armes.

---

## 🏗️ Architecture et Conception

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

## ✨ Fonctionnalités

- Gestion d'un inventaire (armes, armures, potions)
- Échange (swap) d'équipement entre personnage et inventaire
- Combat entre le joueur et des ennemis avec logique de vitesse et d'attaques
- Progression d'armes (XP / niveau)

---

## 🚀 Prérequis

- JDK 17 ou supérieur (recommandé JDK 21)
- Git (optionnel)

---

## 🚀 Installer et Lancer

Le projet utilise le wrapper Gradle fourni (`gradlew` / `gradlew.bat`). Ouvrez un terminal à la racine du projet.

Sur Windows (PowerShell / cmd) :

- Construire le projet :

  .\gradlew.bat build

- Lancer les tests unitaires :

  .\gradlew.bat test

- Lancer l'application (si la tâche `run` est configurée) :

  .\gradlew.bat run

Si `run` n'est pas disponible, vous pouvez exécuter l'artifact JAR généré :

- Trouvez le JAR dans `build/libs/` puis :

  java -jar build\libs\<nom-du-jar>.jar

---

## 🧪 Tests

Les tests unitaires se trouvent sous `src/test/java`. Lancez :

  .\gradlew.bat test

Les rapports sont générés dans `build/reports/tests/`.

---

## 📂 Structure du projet

```
src
├── main
│   └── java
│       └── projet
│           ├── App.java
│           ├── Champion.java
│           ├── Player.java
│           ├── Fight.java
│           ├── Inventory.java
│           ├── weapon/
│           ├── armor/
│           └── enemies/
└── test
```

---

## 🛠️ Améliorations prévues

- Implémenter les chances de coup critique
- Ajouter un système de sauvegarde / chargement
- Améliorer la logique d'IA des ennemis
- Interface graphique (optionnelle)

---

## Contribution

Forkez le repo, créez une branche feature et ouvrez une pull request. Respectez les conventions de nommage et commentez vos modifications.

---

Bonne exploration !