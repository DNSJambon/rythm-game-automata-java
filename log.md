# <span style="color: #d54020 ">***13 juin 2024***</span>

## A faire lundi 17
-Hit / PV
-Clé / porte
-Etats mort ()
-Fin de partie




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
