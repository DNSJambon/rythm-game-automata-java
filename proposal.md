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
    - ennemis qui tir des trucs 
    - ennemis qui fonce dessus




