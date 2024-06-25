# README - Moteur de jeu 
Ce fichier fournit principalement les instructions pour la compilation sous Eclipse, la structuration du code en utilisant le modèle Model-View-Controller avec les Bots. Vous y trouverez également les instructions de lancement (les fichiers de configuration, les automates GAL), un lien vers la vidéo de démonstration ainsi que le pourcentage de participation de chaque membre du groupe.

## 1) Compilation sous Eclipse
Importer le dossier info3.game en projet puis lancer Game.java

## 2) Structuration du code (M, V, C, B)
Le code du projet est structuré selon le modèle Model-View-Controller (MVC) avec l'ajout de Bots. Il est organisé dans le dossier game/info3/game qui contient principalement deux sous-dossiers : controller et model.

* Le dossier controller : Ce dossier contient le sous-dossier Actions où se trouvent toutes les actions utilisées par les entités du moteur de jeu. Chaque action est représentée par une classe distincte (par exemple, Move, Turn, Egg, Hit). Ces actions sont principalement utilisées par nos automates.
Vous trouverez également un sous-dossier Conditions qui regroupe toutes les conditions nécessaires pour le fonctionnement de nos automates.
* Le dossier model : Ce dossier comprend le sous-dossier Automates qui contient le fichier automates.gal. Ce fichier décrit les comportements des différentes entités (joueurs, ennemis, obstacles, etc.) au sein du moteur de jeu. Le sous-dossier Entities qui contient les classes représentant les différentes entités du jeu. Chaque entité a sa propre classe. Le fichier Grille.java gère l'affichage graphique des actions à travers les avatars associés aux entités.

## 3) Instruction de lancement
- Fichiers de configurations 
    - Fichier de configuration Jeu 1 :
    ```json
    {
    "seed": 7,
    "difficulty": 2,
    "mob_multiplier": 2,
    "rythm": 1,
    "automate_file" : "automates.gal",
    "entities" : [
        {   
            "name" : "Joueur1",
            "automate" : "Player1"
        },
        {
            "name" : "Joueur2",
            "automate" : "Player2"
        },
        {
            "name" : "MurIncassable",
            "automate" : "Wall"

        },
        {
            "name" : "Suiveur",
            "automate" : "Suiveur"

        },
        {
            "name" : "Slime",
            "automate" : "Slime"
        },
        {
            "name" : "Squelette",
            "automate" : "Squelette"
        },
        {
            "name" : "Trap",
            "automate" : "Trap"
        },
        {
            "name" : "Key",
            "automate" : "Key"
        },
        {       
            "name" : "Door",
            "automate" : "Door"
        },
        {
            "name": "Mage",
            "automate": "Mage"
        },
        {
            "name": "Projectile",
            "automate": "Projectile"
        },
        {
            "name": "Wall_Breakable",
            "automate": "Wall_Breakable"
        },
        {
            "name": "Sourischauve",
            "automate": "Sourischauve"
        }
    ]

    }
    ```

    - Fichier de configuration Jeu 2 :
    ```json
    {
    "seed": 7,
    "difficulty": 2,
    "mob_multiplier": 3,
    "rythm": 0,
    "automate_file" : "automates.gal",
    "entities" : [
        {   
            "name" : "Joueur1",
            "automate" : "Player1"
        },
        {
            "name" : "Joueur2",
            "automate" : "Player2"
        },
        {
            "name" : "MurIncassable",
            "automate" : "Wall"

        },
        {
            "name" : "Suiveur",
            "automate" : "Suiveur"

        },
        {
            "name" : "Slime",
            "automate" : "Slime"
        },
        {
            "name" : "Squelette",
            "automate" : "Squelette"
        },
        {
            "name" : "Trap",
            "automate" : "Trap"
        },
        {
            "name" : "Key",
            "automate" : "Key"
        },
        {       
            "name" : "Door",
            "automate" : "Door"
        },
        {
            "name": "Mage",
            "automate": "Mage"
        },
        {
            "name": "Projectile",
            "automate": "Projectile"
        },
        {
            "name": "Wall_Breakable",
            "automate": "Wall_Breakable"
        },
        {
            "name": "Sourischauve",
            "automate": "Sourischauve"
        }
    ]
    
    }
    ```

- Automates GAL : Tous les automates sont définies dans model/Automates/automates.gal :

```gal
Player1(S1){
*(S1):
| !Got(Power) ? Rest : ()
| Key(m) & Cell(R, V)/Cell(R, P) ? Move(R) : (S1)
| Key(m) & Cell(R, E) ? Hit(R) : (S1)
| Key(o) & Cell(F, V)/Cell(F, P) ? Move(F) : (S1)
| Key(o) & Cell(F, E) ? Hit(F) : (S1)
| Key(l) & Cell(B, V)/Cell(B, P) ? Move(B) : (S1)
| Key(l) & Cell(B, E) ? Hit(B) : (S1)
| Key(k) & Cell(L, V)/Cell(L, P) ? Move(L) : (S1)
| Key(k) & Cell(L, E) ? Hit(L) : (S1)
| True ? Wait : (S1)

* ()
}
```

```gal
Player2(Init){
* (Init):
| Key(z) ? Move(F) :(Init)
| Key(q) ? Move(L) :(Init)
| Key(s) ? Move(B) :(Init)
| Key(d) ? Move(R) :(Init)
| Key(a) & Cell(H, V) ? Egg() :(Init)
| Key(r) & Cell(H, V) ? Pop() :(Init)
| Key(e) & Cell(H, V) ? Wizz() :(Init)
| True ? Wait : (Init)
}
```

```gal
Slime(Haut){
*(Haut):
| !Got(Power) ? Rest : ()
| Cell(F, V) ? Move(F) : (Waitbas)
| Cell(F, #) ? Hit(F) : (Haut)
| True ? Wait : (Waitbas)


*(Bas):
| !Got(Power) ? Rest : ()
| Cell(B, V) ? Move(B) : (Waithaut)
| Cell(B, #) ? Hit(B) : (Bas)
| True ? Wait : (Waithaut)

*(Waithaut):
| !Got(Power) ? Rest : ()
| True ? Wait : (Haut)

*(Waitbas):
| !Got(Power) ? Rest : ()  
| True ? Wait : (Bas)

*()
| True ? Wait : ()
}
```

```gal
Wall(S1){
*(S1):
| True ? Wait : (S1)
}
```

```gal
Suiveur(S1){
*(S1):
| !Got(Power) ? Rest : ()
| Closest(#, N) & Cell (F, V) ? Move(F) : (S1)
| Closest(#, S) & Cell (B, V) ? Move(B) : (S1)
| Closest(#, E) & Cell (R, V) ? Move(R) : (S1)
| Closest(#, W) & Cell (L, V) ? Move(L) : (S1)
| Cell (F, #) ? Hit (F) : (S1)
| Cell (B, #) ? Hit (B) : (S1)
| Cell (R, #) ? Hit (R) : (S1)
| Cell (L, #) ? Hit (L) : (S1)
| True ? Wait : (S1)
}
```

```gal
Squelette(Left){
* (Left):
| !Got(Power) ? Rest : ()
| Cell(L, V) ? Move(L) : (Left)
| Cell(L, #) ? Hit(L) : (Left)
| Cell(R, V) ? Move(R) : (Right)
| Cell(R, #) ? Hit(R) : (Right)
| True ? Wait : (Right)

* (Right):
| !Got(Power) ? Rest : ()
| Cell(R, V) ? Move(R) : (Right)
| Cell(R, #) ? Hit(R) : (Right)
| Cell(L, V) ? Move(L) : (Left)
| Cell(L, #) ? Hit(L) : (Left)
| True ? Wait : (Left)

* ()
}
```

```gal
Key(S1){
*(S1):
| Cell(H, #) ? Pick : (S2)
| True ? Wait : (S1)
* (S2):
| True ? Wait : (S2)
}
``` 

```gal
Mage(Hit){
    *(Hit):
    |!Got(Power) ? Rest : ()
    |Cell(F,V)? Egg() : (Turn)
    |Cell(F,#)? Hit(F) : (Turn)
    |True? Wait : (Turn)
    *(Turn):
    |!Got(Power) ? Rest : ()
    |True? Turn(R) : (Hit)
    *()
}
``` 

```gal
Projectile(S1){
    *(S1):
    |Cell(F,V)? Move(F) : (S1)
    |Cell(F,#)? Hit(F) : ()
    |True ? Rest : ()
    *()
}
``` 

```gal
Trap(Init){
* (Init):
| Cell(H, #) ? Wait : (Activate)
| True ? Wait : (Init)
* (Activate):
| Cell(H, #) ? Hit(H) : (Activate)
| True ? Pop : (Activate)
}
``` 

```gal
Door(S1){
*(S1):
| Closest(K, N)  ? Pick : (S2)
| True ? Wait : (S1)
*(S2):
| Cell(H,#) ? Egg() : (S2)
| True ? Wait : (S2)
}
``` 

```gal
Wall_Breakable(Vivant){
*(Vivant):
| !Got(Power) ? Rest : (Dead)
| True ? Wait : (Vivant)
*(Dead):
*()
}
``` 

```gal
Sourischauve(Haut){
*(Haut):
| !Got(Power) ? Rest : ()
| Cell(F, V) ? Move(F) : (Droite)
| Cell(F, #) ? Hit(F) : (Haut)
| True ? Wait : (Droite)

*(Bas):
| !Got(Power) ? Rest : ()
| Cell(B, V) ? Move(B) : (Gauche)
| Cell(B, #) ? Hit(B) : (Bas)
| True ? Wait : (Gauche)

*(Gauche):
| !Got(Power) ? Rest : ()
| Cell(L, V) ? Move(L) : (Haut)
| Cell(L, #) ? Hit(L) : (Gauche)
| True ? Wait : (Haut)

*(Droite):
| !Got(Power) ? Rest : ()
| Cell(R, V) ? Move(R) : (Bas)
| Cell(R, #) ? Hit(R) : (Droite)
| True ? Wait : (Bas)

*()
| True ? Wait : ()
}
``` 


## 4) Lien vers la video 


## 5) Pourcentage de participation 

