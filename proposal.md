# Moteur de jeu synchrone
But du jeu : trouver une clé puis sortir
- Grille avec des cases (peux gérer plusieur entités sur une meme case)
- Vue de dessus
- Génération aléatoire de map (mur et bot de type labyrinthe***x***s)


- gestion de difficulté :
    - plus ou moins de mob
- parametre synchro au lancement du jeu combien de temps pour réflechir avant le déroulement des actions :
    - 2 jeux: 
        - beaucoup de temps pour réflechir 
        - pas beaucoup de temps pour réflechir/réagir
- ordre des actions (fairness) : 
    - par entité
    - joueur 1 en premier puis enemies puis joueur 2
- joueur :
    - joueur 1 :
        - deplace a laide des fleches
        - point de vie
    - joueur 2 :
        - curseur 
        - deplace a laide de zqsd
        - spawn des monstre avec e a r f
        - deplace sans colision et peut placer un mob tout les x step grace a egg
        - ne peut placer que sur case vide
- viewPort : 
    - centré sur joueur 1
    - mini map pour joueur 2
- <span style="color:  #eda48f  "> ennemies : </span>

    - mur cassable
    - mur incassable
    - slim qui bouge haut bas
    - squelette qui bouge gauche droite
    - piege : 
        - pique 
        - punis si imobille 
    - ennemis qui suit
    - ennemis qui tir des boules de feu

# jeu 1
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
# jeu 2
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
# Automates en gal
Joueur 1
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
Joueur 2
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
Piege (pique)
```gal
Piege(1){
* (1):
| Cell(H,P) ? Wait() :(2)

* (2):
| Cell(H,P) ? Hit(H) :(2)
}
```
Ennemy suiveur
```gal
Suiveur(1){
* (1):
| Closest(d,P) & Cell(d,Obstacle) ? Wait :(1)
| Closest(d,P) & Cell(d,Ennemy) ? Wait :(1)
| Closest(d,P) & Cell(d,Void) ? Move(d) :(1)
| Cell(d,P) ? Hit(d) :(1)
}
```
Mage
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
Ennemy Projectile
```gal
Projectile(Alive){

* (Alive):
| Cell(F,V) ? Move(F) :(Alive) 
| Cell(F,P) ?  Hit()  :()
| True() ? :()
 
* ()

}
```
