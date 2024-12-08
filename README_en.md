# README - Game Engine  
This file primarily provides instructions for compiling with Eclipse, structuring the code using the Model-View-Controller model with Bots, launching instructions (for configuration files, GAL automata), a link to the demo video, and the participation percentage of each group member.

## 1) Code Structure (M, V, C, B)  
The project code is structured according to the Model-View-Controller (MVC) model with the addition of Bots. It is organized in the folder `game/info3/game`, which mainly contains two subfolders: `controller` and `model`.

* The `controller` folder:  
    - This folder contains the `Actions` subfolder, which includes all the actions used by the entities in the game engine. Each action is represented by a separate class (e.g., `Move`, `Turn`, `Egg`, `Hit`). These actions are mainly used by our automata.  
    - You will also find a `Conditions` subfolder, which groups all the conditions necessary for the operation of our automata.

* The `model` folder:  
    - This folder includes the `Automates` subfolder, which contains the `automates.gal` file. This file describes the behaviors of the different entities (players, enemies, obstacles, etc.) in the game engine.  
    - The `Entities` subfolder contains the classes representing the different entities in the game. Each entity has its own class. The `Grille.java` file manages the graphical display of actions through avatars associated with the entities.

## 2) Launch Instructions  

* Configuration files:  
    - The configuration files for `jeu1` and `jeu2` are located in the main folder `game/info3/game`. They are named as follows:  
        - `jeu1`: "config.json"  
        - `jeu2`: "config2.json"  

    - To select the desired game, simply import either `config.json` or `config2.json` into the `Game.java` file at line 66:  
        ```java
        m_grille = config("game/info3/game/config.json");
        ```
    - By default, `config.json` is launched.  

* GAL Automata:  
    - All automata are defined in a single file, `model/Automates/automates.gal`.

* Important Note on Game Controls:  
    - In the file `proposal.md`, we had planned for Player 1 to move using the keys `o, k, l, m`, and Player 2 to move using the keys `z, q, s, d` and summon monsters with the keys `a, e, r`.  
    - However, for gameplay adaptation reasons, we have modified the controls as follows:  
        - Player 1 now moves with the keys `z, q, s, d`.  
        - Player 2 moves with the keys `o, k, l, m` and summons monsters with the following keys:  
            - `u` for the Slime.  
            - `p` for the Follower.  
            - `i` for the Skeleton.  
    - We left this note here to avoid modifying the `proposal.md` file.

## 3) Link to the Video  
[Demo Video](https://www.youtube.com/watch?v=z3R9XRUVyAY)  

