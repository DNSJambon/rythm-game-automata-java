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
