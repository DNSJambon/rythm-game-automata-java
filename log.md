# <span style="color: #d54020 ">***17 juin 2024***</span>

## A faire mardi 18
- Regler debut de la musique sur début des tick ( synchro jeu/musique)
- Fin du jeu et Game Over
- Creation et implémentation classes mage et Projectiles
- Possibilité de sortir de la grille pour joueur 2 faisant crash le jeu (sans doute du aux automates)
- implémentation de l'ajout des deux autres ennemi par le joueur 2 (+cooldown)
- Petite Refonte graphique (Player et obstacles)
- Gerer intéraction Clé et Porte
- Creation Piège et implémentation

## <span style="color:green">**Game**</span>
- Création et ajout au visteur de la classe hit (+ implémentation de do_hit fonctionnel) (Sami)
- Création, test et ajout au visiteur  de la classe Got (implémentation possible de  got(category) en plus de got(power) (got(power,int) abandonné car inutil)) (Matthieu)
- Vie du joueur et des monstres prise en compte + Etats Morts et disparition ajouté (a regler pour joueur 1) (Arthur)
- Ajout animation Hit (Mathis)
- Creation et implémentation des méthodes de Key et Door permettant la fin du jeu (pas encore fonctionel)(Aymanne et Omar)
- Ajout animation clé (Mathis)
- Fix couleur minimap (Arthur)
- Fix Bug état automates (Matthieu et Sami)

# <span style="color: #d54020 ">***14 juin 2024***</span>

## A faire lundi 17
- Hit / PV
- Clé / porte
- Etats mort ()
- Fin de partie

## <span style="color:green">**Game**</span>
- Ajout Musique
- Ajout cooldown pour le joueur 2 sur apparition ennemi
- Réorganisation de l'ordre des actions faite
- Gérer le joueur2 (son automate, ses actions) fait
- génèration des salles aléatoire sans chevauchement
- file de config fait
- Rassemblement  des automates dans le fichier automates.gal
- Déplacement du joueur 1 fonctionnel
- Closest fonctionnel (à revoir selon préférence)

# <span style="color: #d54020 ">***13 juin 2024***</span>

## <span style="color:green">**Game**</span>
- régler le déplacement du player1
- génèration des salles
- faire le file config
- écrire les automates qu'on utilise pour l'instant dans un seul fichier automates.gal
## A régler pour demain
- Réorganiser Grille.java
- Réorganiser l'ordre des actions
- Gérer le joueur2 (son automate, ses actions)

# <span style="color: #d54020 ">***12 juin 2024***</span>
## <span style="color:green">**Game**</span>
- comprendre le parser des automates GAL fourni par l'enseignant
- fixer les automates GAL en respectant la syntaxe fournie par le prof

# <span style="color: #d54020 ">***11 juin 2024***</span>
## <span style="color:green">**Game**</span>
- finition du contrat
- mini map
- ajout de wizz pop pour les entités
- player2 (début), ennemies, clé, door
- chaque cellule plusieur entité possible
- chemin aleatoire entre deux point
- view port qui suit le joueur1


# <span style="color: #d54020 ">***10 juin 2024***</span>
## <span style="color:green">**Game**</span>
- Matthieu et Sami : 
    - Creation de la condition key
    - Jeu avance selon temps gerer dans la variable Rythme dans game
    - Jeu fait un step si keyboard pressed

- Arthur et Mathis : 
    - Utilisation des ticks pour les animmation
    - Début viewport
    - Creation de la classe Player 1

- Omar et Aymanne :  
    - Génération aléatoire d'une map avec densité obstacle
    - Chemin possible entre deux point donné peut importe map

        
A faire :
    Regler certain eval qui doivent prendre une catégory au lieu d'un cellType

# <span style="color: #d54020 ">***6 juin 2024***</span>
## <span style="color:green">**snake**</span>
- avancer code snake
- egg fonctionne 
- réorganisation des dossiers
- creation buffer d'action
- creation de la queue du snake : 
    - 2 marche a partir de 3 marche pas


# <span style="color: #d54020 ">***5 juin 2024***</span>
## <span style="color:green">**snake**</span>

- début d'affichage 
- pomme : se génère aléatoirement et disparaît lorsqu'elle est mangée
- tete snake : se deplace
- queue snake : marche pas encore
- egg marche pas
- tester obstacle

## <span style="color:violet">**projet jeu idée**</span> 
### model
- Synchro -> paramertre bool
- Action Entité toujours déclenché par Automate
- Action/Rafrachissement grille en même temps
- vue du dessus
- 2D
- ViewPort : 
    - centré sur le <span style="color: #20bfd5 ">**joueur 1**</span>
    - **ou**
    - ecran partagé
- génération aléatoire de map
#### bot :

### <span style="color: #bf20d5 ">**jeu 1**</span>
#### synchro sur joueur
- action joueur déclenche tout
- temps de décision infini

### <span style="color:  #c520d5  ">**jeu 2**</span>
#### rythme imposé
- si pas action tour passé
- temps action fixe, saute pas si keyboard event
- player freeze pendant la resolution des actions d'entités
