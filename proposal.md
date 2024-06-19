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
  - Se déplace à l'aide des touches oklm.
  - A des points de vie.
- Joueur 2 :
  - C'est un curseur. 
  - Se déplace à l'aide des touches zqsd.
  - Peut spawn des monstres avec les touches a,e,r.
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
    "seed": 5,
    "difficulty": 1,
    "rythm": 1,
    "bpm": 100,
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
            "name" : "Slime",
            "automate" : "Slime"
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
## Automates en GAL
### Joueur 1
```gal
Player1(S1){
*(S1):
| Cell(R, V)  & Key(m) ? Move(R) : (S1)
| Key(o) & Cell(F, V) ? Move(F) : (S1)
| Key(l) & Cell(B, V) ? Move(B) : (S1)
| Key(k) & Cell(L, V) ? Move(L) : (S1)
| True ? Wait : (S1)
}
```
### Joueur 2
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
Suiveur(S1){
*(S1):
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
### Slime
```gal
Slime(Monter1){
* (Monter1):
| Cell(F, V) ? Move(F) : (Monter1)
| Cell(F, O) ? Move(B) : (Descendre1)
* (Descendre1):
| Cell(B, V) ? Move(B) : (Descendre2)
| Cell(B, O) ? Move(F) : (Monter2)
* (Descendre2):
| Cell(B, V) ? Move(B) : (Monter2)
| Cell(B, O) ? Move(F) : (Monter2)
* (Monter2):
| Cell(F, V) ? Move(F) : (Monter1)
| Cell(F, O) ? Move(B) : (Descendre1)
}
```

## Point Technique - Synchronisation
### Jeu Synchrone (Jeu 1)
La durée du jeu est infinie. Les actions sont synchronisées et se déroulent une fois le joueur 1 fait son action. 

### Jeu Asynchrone (Jeu 2)
Déroulement des actions : Les actions sont asynchrones, le joueur 1 a un temps limité pour faire son action.

