# README - Moteur de jeu 
Ce fichier fournit principalement les instructions pour la compilation sous Eclipse, la structuration du code en utilisant le modèle Model-View-Controller avec les Bots. Vous y trouverez également les instructions de lancement (pour les fichiers de configuration, les automates GAL), un lien vers la vidéo de démonstration ainsi que le pourcentage de participation de chaque membre du groupe.

## 1) Compilation sous Eclipse
- git clone git@gricad-gitlab.univ-grenoble-alpes.fr:michael_perin_private_project/2023_info3_ple/g1.git
- ouvrir le dossier g1 en tant que workspace
- importer le projet info3.game

## 2) Structuration du code (M, V, C, B)
Le code du projet est structuré selon le modèle Model-View-Controller (MVC) avec l'ajout de Bots. Il est organisé dans le dossier game/info3/game qui contient principalement deux sous-dossiers : controller et model.

* Le dossier controller :

    Ce dossier contient le sous-dossier Actions où se trouvent toutes les actions utilisées par les entités du moteur de jeu. Chaque action est représentée par une classe distincte (par exemple, Move, Turn, Egg, Hit). Ces actions sont principalement utilisées par nos automates.
    Vous trouverez également un sous-dossier Conditions qui regroupe toutes les conditions nécessaires pour le fonctionnement de nos automates.

* Le dossier model :
    
    Ce dossier comprend le sous-dossier Automates qui contient le fichier automates.gal. Ce fichier décrit les comportements des différentes entités (joueurs, ennemis, obstacles, etc.) au sein du moteur de jeu. Le sous-dossier Entities contient les classes représentant les différentes entités du jeu. Chaque entité a sa propre classe. Le fichier Grille.java gère l'affichage graphique des actions à travers les avatars associés aux entités.

## 3) Instruction de lancement
* Fichiers de configurations :

    Les fichiers de configuration pour jeu1 et  jeu2 se trouvent dans le dossier principal game/info3/game. Ils sont nommés comme suit :
    - jeu1 : "config.json"
    - jeu2 : "config2.json"

    Pour choisir le jeu souhaité, il suffit d'importer soit "config.json", soit "config2.json" dans le fichier Game.java à la ligne 66 :
     
    m_grille = config("game/info3/game/config.json");.

    Par défaut, "config.json" est lancé.
   
* Automates GAL : 
    
    Tous les automates sont définies dans un seul fichier model/Automates/automates.gal 

* Important, remarque sur les commandes du jeu :

    Dans "proposal.md", nous avions prévu que le joueur 1 se déplace à l'aide des touches o, k, l, m, le joueur 2 se déplace avec les touches z, q, s, d et peut faire apparaître des monstres avec les touches a, e, r.

    Cependant, pour des raisons d'adaptation du jeu, nous avons modifié les commandes comme suit :
    - Le joueur 1 se déplace maintenant avec les touches z, q, s, d.
    - Le joueur 2 se déplace avec les touches o, k, l, m. Il fait apparaître des monstres avec les touches suivantes : u pour le Slime, p pour le Suiveur, i pour le Squelette.

    Nous avons laissé cette remarque ici afin de ne pas toucher au fichier proposal.md.

## 4) Lien vers la video 
https://www.youtube.com/watch?v=z3R9XRUVyAY

## 5) Pourcentage de participation 
``` 
Arthur  : 17.5% 
Sami    : 16.5% 
Mathieu : 16.5% 
Mathis  : 16.5% 
Aymane  : 16.5% 
Omar    : 16.5%
```



