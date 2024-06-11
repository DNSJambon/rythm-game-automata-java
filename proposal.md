# Moteur de jeu synchrone
## But du jeu
Trouver une clé puis sortir.
## Caractéristiques du moteur de jeu
### Grille
- La grille est composée de cases pouvant gérer plusieurs entités sur une même case.
- Vue de dessus
- Génération aléatoire de la map, incluant des murs et des bots de type labyrinthe.
### Gestion de la difficulté
- Nombre de mobs ajustable pour varier la difficulté.
### Paramètres de synchronisation
- Parametre synchro au lancement du jeu.
- Deux jeux basés sur le temps de réflexion :
  - Jeu 1 : Beaucoup de temps pour réfléchir avant le déroulement des actions.
  - Jeu 2 : Peu de temps pour réfléchir/réagir.
### Ordre des actions (fairness) : 
- Les actions sont exécutées par entité dans l'ordre suivant : 
  - Joueur 1
  - Ennemies
  - Joueur 2
### Joueurs
- Joueur 1 :
  - Se déplace à l'aide des flèches.
  - A des points de vie.
- Joueur 2 :
  - C'est un curseur. 
  Se déplace à l'aide des touches ZQSD.
  - Peut spawn des monstres avec les touches E,A,R,F.
  - Se déplace sans collision et peut placer un mob toutes les X étapes grâce à Egg.
  - Ne peut placer des mobs que sur des cases vides.
### ViewPort (vue) : 
- Viewport centré sur Joueur 1.
- Mini-map pour Joueur 2.

### Ennemies
- Mur cassable
- Mur incassable
- Slim qui bouge haut-bas
- Squelette qui bouge gauche-droite
- Piège : 
  - Pique
  - Punit si immobile 
- Ennemi qui suit
- Ennemi qui tire des boules de feu

## Fichiers de configuration 
### Jeu 1
```json
{
  
  "germe" : 734,
  "difficulty" : 2,  
  "temps de décision" : 10000,
  "entities": [
    { "name" : "PLAYER 1",  
      "behaviour": "player1.gal",
      "featurs" : "Warrior",
      "sprite" : "warrior.png", 
    },
    { "name" : "PLAYER 2",  
      "behaviour": "player2.gal",
      "features" : "Cursor",
      "sprite" : "cursor.png", 
    },
    { "name" : "WALL breakable", 
      "%density" : 23, 
      "behaviour" : "",
      "features" : "Wall1",
      "sprite" : "wall1.png",
    },
    { "name" : "WALL unbreakable", 
      "%density" : 23, 
      "behaviour" : "",
      "features" : "Wall2",
      "sprite" : "wall2.png",
    },
    { "name" : "SLIME", 
      "%density" : 5, 
      "behaviour" : "SLIME.gal",
      "features" : "Slime",
      "sprite" : "slime.png",
    },
    { "name" : "", 
      "%density" : 10, 
      "behaviour" : ".gal",
      "features" : "",
      "sprite" : ".png",
    }
  ]
}
```
### Jeu 2
```json
{
  
  "germe" : 732,
  "difficulty" : 2,  
  "temps de décision" : 1,
  "entities": [
    { "name" : "PLAYER 1",  
      "behaviour": "player1.gal",
      "featurs" : "Warrior",
      "sprite" : "warrior.png", 
    },
    { "name" : "PLAYER 2",  
      "behaviour": "player2.gal",
      "features" : "Cursor",
      "sprite" : "cursor.png", 
    },
    { "name" : "WALL breakable", 
      "%density" : 23, 
      "behaviour" : "",
      "features" : "Wall1",
      "sprite" : "wall1.png",
    },
    { "name" : "WALL unbreakable", 
      "%density" : 23, 
      "behaviour" : "",
      "features" : "Wall2",
      "sprite" : "wall2.png",
    },
    { "name" : "SLIME", 
      "%density" : 5, 
      "behaviour" : "SLIME.gal",
      "features" : "Slime",
      "sprite" : "slime.png",
    },
    { "name" : "", 
      "%density" : 10, 
      "behaviour" : ".gal",
      "features" : "",
      "sprite" : ".png",
    }
  ]
}
```
## Automates en GAL
### Joueur 1
```gal
Player1(Init){
* (Init):
| pressKey(FU) & eval_cell(P1, N, Ennemy) ? Hit(N) :(Init)
| pressKey(FD) & eval_cell(P1, S, Ennemy) ? Hit(S) :(Init)
| pressKey(FR) & eval_cell(P1, E, Ennemy) ? Hit(E) :(Init)
| pressKey(FL) & eval_cell(P1, O, Ennemy) ? Hit(O) :(Init)

| pressKey(FU) & eval_cell(P1, N, Void) ? Move(N) :(Init)
| pressKey(FU) & eval_cell(P1, N, Key) ? Move(N) :(Init)

| pressKey(FD) & eval_cell(P1, S, Void) ? Move(S) :(Init)
| pressKey(FD) & eval_cell(P1, S, Key) ? Move(S) :(Init)

| pressKey(FR) & eval_cell(P1, E, Void) ? Move(E) :(Init)
| pressKey(FR) & eval_cell(P1, E, Key) ? Move(E) :(Init)

| pressKey(FL) & eval_cell(P1, O, Void) ? Move(O) :(Init)
| pressKey(FL) & eval_cell(P1, O, Key) ? Move(O) :(Init)
}
```
### Joueur 2
```gal
Player2(Init){
* (Init):
| pressKey(Z) ? Move(N) :(Init)
| pressKey(Q) ? Move(O) :(Init)
| pressKey(S) ? Move(S) :(Init)
| pressKey(D) ? Move(E) :(Init)

| pressKey(A) ? Pop() :(Init)

| pressKey(R) ? Egg() :(Init)

| pressKey(E) ? Wizz() :(Init)
}
```
### Piege (pique)
```gal
Piege(1){
* (1):
| Cell(H,P) ? Wait() :(2)

* (2):
| Cell(H,P) ? Hit(H) :(2)
}
```
### Ennemy suiveur
```gal
Suiveur(1){
* (1):
| Closest(d,P) & Cell(d,Obstacle) ? Wait :(1)
| Closest(d,P) & Cell(d,Ennemy) ? Wait :(1)
| Closest(d,P) & Cell(d,Void) ? Move(d) :(1)
| Cell(d,P) ? Hit(d) :(1)
}
```
### Mage
```gal
Mage(1){
*(1):
| Cell(F,O) ? Turn(D) : (1)
| Cell(F,V) ? Move(F) : (2)
 
* (2):
| Cell(F,O) ? Wait() : (3)
| Cell(F,V) ? Egg() : (3)

* (3):
| True() ? Wait() : (1)

*()
}
```
### Ennemy Projectile
```gal
Projectile(Alive){

* (Alive):
| Cell(F,V) ? Move(F) :(Alive) 
| Cell(F,P) ?  Hit()  :()
| True() ? :()
 
* ()

}
```
## Point Technique - Synchronisation
### Jeu Synchrone (Jeu 1)
La durée du jeu est infinie. Les actions sont synchronisées et se déroulent une fois le joueur 1 fait son action. 

### Jeu Asynchrone (Jeu 2)
Déroulement des actions : Les actions sont asynchrones, le joueur 1 a un temps limité pour faire son action.

