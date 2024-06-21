package info3.game.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import java.util.Random;
import javax.imageio.ImageIO;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.controller.Conditions.Cell;
import info3.game.model.Entities.Door;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.Key;
import info3.game.model.Entities.Mage;

import info3.game.model.Entities.Slime;
import info3.game.model.Entities.Sourischauve;
import info3.game.model.Entities.Obstacle;
import info3.game.model.Entities.Player1;
import info3.game.model.Entities.Suiveur;
import info3.game.model.Entities.Wall_Breakable;
import info3.game.model.Entities.Trap;
import info3.game.model.Entities.Player2;
import info3.game.model.Entities.Slime;
import info3.game.model.Entities.Squelette;
import info3.game.model.Entities.Wall_Breakable;
import info3.game.model.Entities.Trap;


public class Grille implements IGrille {
    cell[][] grille;
    int rows;
    int cols;
    long m_imageElapsed;

    Control m_control;
    public HashMap<String, Automate> automates;
    public int game_over = 0; // 0 = en cours, 1 = victoire joueur 1, 2 = victoire joueur 2

    // Viewport
    BufferedImage[] m_sol;
    Entity main_Entity; //(joueur 1)
    Entity joueur2; //(joueur 2)
    int viewport_size = 7; //have to be odd

    //Synchro
    boolean authorised;
    boolean authorised2;
    char touche;
    char touche2;

    public Grille(int rows, int cols, Control m_control, int seed, int difficulty, HashMap<String, Automate> automates)
            throws IOException, ClassNotFoundException {
        m_sol = loadSprite("resources/sol2.png", 1, 1);
        this.rows = rows;
        this.cols = cols;
        this.m_control = m_control;
        this.automates = automates;
        this.authorised = true;
        this.authorised2 = true;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, j, i);
            }
        }

        //======generation du labyrinthe======
        //pourcentage_aleatoire_obstacle(this, 90, 666, debut_entre_X, debut_entre_Y, fin_X, fin_Y); 
        remplir_obstacle();
        cree_des_salles(seed, 5, 5, 10, 3);
        cell c;
        //======generation des entités======

        


        //======placer le joueur 1 dans le labyrinthe======
        c = randomCell_libre();
        Player1 p1 = new Player1(this, c.getCol(), c.getRow(), automates.get("Joueur1"));
        //main_Entity;
        main_Entity = p1;

        //======placer la clé et la porte dans le labyrinthe======   
        c = randomCell_libre();
        Key k = new Key(this, c.getCol(), c.getRow(), automates.get("Key"));
        
        c = randomCell_libre();
        Door porte = new Door(this, automates.get("Door"),c.getCol(), c.getRow(),k);

        
        //======placer le joueur 2 dans le labyrinthe====== 

        c = randomCell_libre();
        Player2 p2 = new Player2(this, c.getCol(), c.getRow(), automates.get("Joueur2"));
        joueur2 = p2;
        
        //generer_mur_cassable(20+(difficulty*30));
        System.out.println("Mur : "+(20+(difficulty*20)));

        place_monstre(difficulty);

       
        
     
       

        
        x_main_old = main_Entity.getX();
        y_main_old = main_Entity.getY();
        //on s'assure que la vue ne sorte pas de la grille
        if (x_main_old < viewport_size / 2)
            x_main_old = viewport_size / 2;
        if (x_main_old > rows - viewport_size / 2 - 1)
            x_main_old = rows - viewport_size / 2 - 1;
        if (y_main_old < viewport_size / 2)
            y_main_old = viewport_size / 2;
        if (y_main_old > cols - viewport_size / 2 - 1)
            y_main_old = cols - viewport_size / 2 - 1;
    }

    public void addEntity(Entity e) {
        m_control.addEntity(e);
    }
    public void removeEntity(Entity e) {
        m_control.removeEntity(e);
    }
    public void generer_mur_cassable(int nombre) {
        cell c;
        for (int i = 0; i < nombre; i++) {
            c = randomCell_libre();
            Wall_Breakable obs = new Wall_Breakable(this, c.getCol(), c.getRow(), automates.get("Wall_Breakable"));
        }
    }
    
    
    
    //le but est de créer des salles dans le labyrinthe rempli de monstres
    //met des rectangelles de vide dans la grille
    private void cree_des_salles(long seed, int largeur,int longueur, int nb_salle, int variance) {
        Random random = new Random(seed);
        int largeur_salle = random.nextInt(variance) + largeur;
        int longueur_salle = random.nextInt(variance) + longueur;
        // tableau coordonnees des salles
        int[][] coordonnees_salle = new int[nb_salle][2];
    
        for (int i = 0; i < nb_salle; i++) {
            largeur_salle = random.nextInt(variance) + largeur;
            longueur_salle = random.nextInt(variance) + longueur;
            int x = random.nextInt(rows - largeur_salle);
            int y = random.nextInt(cols - longueur_salle);
            while (grille[x][y].getType() == cellType.Vide) {
                x = random.nextInt(rows - largeur_salle);
                y = random.nextInt(cols - longueur_salle);
            }
            coordonnees_salle[i][0] = x+largeur_salle/2;
            coordonnees_salle[i][1] = y+longueur_salle/2;
            for (int j = 0; j < largeur_salle; j++) {
                for (int k = 0; k < longueur_salle; k++) {
                    grille[x + j][y + k].resetEntity();
                }
            }
        }
    
        //creer les chemin entre les salles
    
        for (int i = 0; i < nb_salle; i++) {
            int x = coordonnees_salle[i][0];
            int y = coordonnees_salle[i][1];
            int x2 = coordonnees_salle[(i + 1) % nb_salle][0];
            int y2 = coordonnees_salle[(i + 1) % nb_salle][1];
            while (x != x2 || y != y2) {
                if (x < x2) {
                    x++;
                } else if (x > x2) {
                    x--;
                } else if (y < y2) {
                    y++;
                } else if (y > y2) {
                    y--;
                }
                grille[x][y].resetEntity();
            }
        }
    
    }
    
    //remplir la grille d'obstacle
    public void remplir_obstacle() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                new Obstacle(this, j, i , automates.get("MurIncassable"));
            }
        }
    }
    
    //place les monstres dans la grille a des endroit vide et aléatoire
    public void place_monstre(int difficulty) {
        
        //======placer les Slimes dans le labyrinthe======//
        for (int i = 0; i < (10+(difficulty-1)*5); i++) {
            cell c = randomCell_libre();
            Slime sl = new Slime(this, c.getCol(), c.getRow(), automates.get("Slime"));
        }
        System.out.println("Slimes : "+(10+(difficulty-1)*5));

        //======placer les Chauve-Souris dans le labyrinthe======//
        for (int i = 0; i < (difficulty*5); i++) {
            cell c = randomCell_libre();
            Sourischauve ch = new Sourischauve(this, c.getCol(), c.getRow(),automates.get("Sourischauve"));
        }
        System.out.println("Chauve-Souris : "+(difficulty*5));

        //======placer les Mages dans le labyrinthe======//
        for (int i=0 ; i < (1+difficulty); i++) {
            cell c = randomCell_libre();
            Mage m = new Mage(this, c.getCol(), c.getRow(), automates.get("Mage"),automates.get("Projectile"));
        }
        System.out.println("Mages : "+(1+difficulty));

        //======placer les Squelettes dans le labyrinthe======//
        for (int i = 0; i < (10+(difficulty-1)*5); i++) {
            cell c = randomCell_libre();
            Squelette sq = new Squelette(this, c.getCol(), c.getRow(),automates.get("Squelette"));
        }
        System.out.println("Squelettes : "+(10+(difficulty-1)*5));

      //======placer les Traps dans le labyrinthe======//
        for (int i = 0; i < difficulty*10; i++) {
           cell c = randomCell_libre();
            if (c.e[0]==null) {
               Trap t = new Trap(this, c.getCol(), c.getRow(),automates.get("Trap"));
            }
            else {i--;}
        }
        System.out.println("Traps : "+(difficulty*10));
    }
    
    
    
    /* ======================Partie Synchro========================== */
    public char getTouche() {
        return touche;
    }

    public char getTouche2() {
        return touche2;
    }

    public void setAuthorised(boolean auto) {
        if (this.authorised == auto) {
            this.authorised2 = auto;
        } else {
            this.authorised = auto;
        }

    }

    public boolean IsAuthorised() {
        if (this.authorised2 == this.authorised) {
            return this.authorised;
        } else {
            return this.authorised2;
        }

    }

    public void Authorised_True() {
        this.authorised = true;
        this.authorised2 = true;
    }

    public void Authorised_False() {
        this.authorised = false;
        this.authorised2 = false;
    }

    public void resetTouche() {
        this.touche = ' ';
        this.touche2 = ' ';
    }

    public void key(char touche) {
        if (IsAuthorised()) {
            if (this.touche != ' ') {
                this.touche2 = touche;
            } else {
                this.touche = touche;
            }
        }

    }

    

    public cell getCell(int col, int row) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return grille[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

   

    public Entity getMainEntity() {
        return main_Entity;
    }

    public cell randomCell_libre() {
        int x = (int) (Math.random() * rows);
        int y = (int) (Math.random() * cols);
        while (grille[x][y].getCategory() != 'V') {
            x = (int) (Math.random() * rows);
            y = (int) (Math.random() * cols);
        }
        return grille[x][y];

    }

    public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
        File imageFile = new File(filename);
        if (imageFile.exists()) {
            BufferedImage image = ImageIO.read(imageFile);
            int width = image.getWidth(null) / ncols;
            int height = image.getHeight(null) / nrows;

            BufferedImage[] images = new BufferedImage[nrows * ncols];
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    int x = j * width;
                    int y = i * height;
                    images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
                }
            }
            return images;
        }
        return null;
    }

    

    /*=========================Paint et ticks=============================*/
    public void tick(long elapsed) {
        m_imageElapsed += elapsed;

        for (Entity e : m_control.getEntities()) {
            e.tick(elapsed);
        }


    }

    int x_main_old;
    int y_main_old;
    int frames_anim = 10;
    int mouvement = frames_anim; //nombre de frame pour le decalage de la vue
    float[] slide = { 1.0f, 0.7f, 0.38f, 0.22f, 0.13f, 0.07f, 0.05f, 0.03f, 0.015f, 0.007f, 0.0f };
   

    public void paint(Graphics g, int width, int height) {
        int x_main = main_Entity.getX();
        int y_main = main_Entity.getY();

        //on s'assure que la vue ne sorte pas de la grille
        if (x_main < viewport_size / 2)
            x_main = viewport_size / 2;
        if (x_main > rows - viewport_size / 2 - 1)
            x_main = rows - viewport_size / 2 - 1;
        if (y_main < viewport_size / 2)
            y_main = viewport_size / 2;
        if (y_main > cols - viewport_size / 2 - 1)
            y_main = cols - viewport_size / 2 - 1;

        //l'offset permet de faire bouger la vue de facçon fluide quand on se déplace
        int offset_x = (x_main - x_main_old) * width / viewport_size;
        int offset_y = (y_main - y_main_old) * height / viewport_size;

        //pour load la ligne d'image qui va disparaitre:
        int load_x_negatif, load_x_positif, load_y_negatif, load_y_positif;
        load_x_negatif = load_x_positif = load_y_negatif = load_y_positif = 0;
        if (offset_x > 0)
            load_x_negatif = 1;

        if (offset_x < 0)
            load_x_positif = 1;

        if (offset_y > 0)
            load_y_negatif = 1;

        if (offset_y < 0)
            load_y_positif = 1;

        //on dessine le sol en premier
        
        g.drawImage(m_sol[0],
                ((int) ((-width / viewport_size)*(x_main-viewport_size/2) + offset_x * slide[frames_anim- mouvement])),
                ((int) ((-height / viewport_size)*(y_main-viewport_size/2) + offset_y * slide[frames_anim - mouvement])),
                width / viewport_size * 34,
                height / viewport_size * 34,
                                null);



        //on dessine les entités
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                grille[j][i].paint(g,
                        (int)((i - x_main + viewport_size / 2) * width / viewport_size + offset_x * slide[frames_anim - mouvement]),
                        (int)((j - y_main + viewport_size / 2) * height / viewport_size + offset_y * slide[frames_anim - mouvement]),
                        width / viewport_size,
                        height / viewport_size);
            }
        }

        if (offset_x != 0 || offset_y != 0) {
            mouvement--;
            if (mouvement == -1) {
                mouvement = frames_anim;
                x_main_old = x_main;
                y_main_old = y_main;
            }
        }

        //MiniMap
        drawMinimap(g, width, (height - 340) / 2, 340, 340);

        //ATH huta
         drawATH_haut(g, width, 0, 340, (height - 340) / 2);

        //ATH bas
        drawATH_bas(g, width, (height + 340) / 2, 340, (height - 340) / 2);

    }
    
    Color vide = new Color(20, 12, 28);
    Color obstacle = new Color(78, 74, 78);
    Color player = new Color(36, 63, 114);
    Color pickable = new Color(240, 194, 80);
    Color monstre = new Color(169,59,59);

    void drawMinimap(Graphics g, int x, int y, int width, int height) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                switch (grille[j][i].getCategory()) {
                    case 'V':
                        g.setColor(vide);
                        break;
                    case 'P':
                        g.setColor(vide);
                        break;
                    case 'T':
                        g.setColor(monstre);
                        break;
                    case 'E':
                        if ((grille[j][i].getType()==cellType.Wall_Breakable)||(grille[j][i].getType()==cellType.Obstacle)){
                            g.setColor(obstacle);
                            break;
                        }
                        else{
                        g.setColor(monstre);
                        break;
                        }
                    case '#':
                        g.setColor(player);
                        break;
                    case 'D':
                         g.setColor(Color.WHITE);
                         break;
                }
                g.fillRect(x + (i * width / cols), y + (j * height / rows), width / cols, height / rows);
            }
        }

        //on place la cible (jouer 2) (une croix rouge)
        g.setColor(Color.GREEN);
        g.fillRect(x + (joueur2.getX() * width / cols) + 2, y + (joueur2.getY() * height / rows) + 2, width / cols - 4,
                height / rows - 4);
    }

    BufferedImage[] coeur = loadSprite("resources/coeur.png", 2, 3);
    BufferedImage[] indicator = loadSprite("resources/indicator.png", 2, 1);
    int coeur_index = 0;
    int indice = 0;
    boolean petit_coeur = false;
    int c1 = 0;
    int c2 = 0;
    int c3 = 0;

    void drawATH_haut(Graphics g, int x, int y, int width, int height) {
        //TODO:
        indice++;
        if (petit_coeur) {
            indice += 5;
        }
        if (indice >= 20) {
            coeur_index = (coeur_index + 3) % 6;
            indice = 0;
            petit_coeur = !petit_coeur;
        }

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        //coeur
        int life = ((Player1) main_Entity).getLife();

        switch (life) {
            case 5:
                c3 = 1;
                break;

            case 4:
                c3 = 2;
                break;
            case 3:
                c2 = 1;
                c3 = 2;
                break;
            case 2:
                c2 = 2;
                c3 = 2;
                break;
            case 1:
                c1 = 1;
                c2 = 2;
                c3 = 2;
                break;
            case 0:
                c1 = 2;
                c2 = 2;
                c3 = 2;
                break;
        }
        g.drawImage(coeur[coeur_index + c1], x + 20, y + 5, 100, 100, null);
        g.drawImage(coeur[coeur_index + c2], x + 120, y + 5, 100, 100, null);
        g.drawImage(coeur[coeur_index + c3], x + 220, y + 5, 100, 100, null);

    }

    void drawATH_bas(Graphics g, int x, int y, int width, int height) {

        g.setColor(Color.BLACK);

        g.fillRect(x, y, width, height);

        //affichage cooldown
        int cooldown_egg = ((Player2) joueur2).getCooldown_egg();
        g.setColor(new Color(130, 163, 104));
        g.fillRect(x, y, width / 3, height / 3 * (3 - cooldown_egg));

        int cooldown_wizz =  ((Player2) joueur2).getCooldown_wizz();
        g.setColor(new Color(222,249,252));
        g.fillRect(x+width / 3, y, width / 3, height / 5 * (5 - cooldown_wizz));

        int cooldown_pop = ((Player2) joueur2).getCooldown_pop();
        g.setColor(new Color(66,66,143));
        g.fillRect(x+2*(width / 3), y, width / 3, height / 10 * (10 - cooldown_pop));
        


    }


}