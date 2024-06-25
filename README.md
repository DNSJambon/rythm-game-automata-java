# README - Moteur de jeu 
Ce fichier fournit principalement les instructions pour la compilation sous Eclipse, la structuration du code en utilisant le modèle Model-View-Controller avec les Bots. Vous y trouverez également les instructions de lancement (pour les fichiers de configuration, les automates GAL), un lien vers la vidéo de démonstration ainsi que le pourcentage de participation de chaque membre du groupe.

## 1) Compilation sous Eclipse
Importer le dossier info3.game en projet puis lancer Game.java

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

## 4) Lien vers la video 


## 5) Pourcentage de participation 

