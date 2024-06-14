package info3.game.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.imageio.ImageIO;

import gal.ast.AST;
import gal.ast.Automaton;
import gal.parser.Parser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.controller.Conditions.Cell;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.MazeSolver;
import info3.game.model.Entities.Obstacle;
import info3.game.model.Entities.Player1;


public class Grille implements IGrille{
    cell[][] grille;
    int rows;
    int cols;
    long m_imageElapsed;

    Control m_control;
    HashMap<String, Automate> automates;

    // Viewport
    BufferedImage[] m_images;
    Entity main_Entity;
    int viewport_size = 7;

    //Synchro
    boolean authorised;
    char touche;
    

    public Grille(int rows, int cols, Control m_control, int seed, HashMap<String, Automate> automates) throws IOException, ClassNotFoundException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        this.rows = rows;
        this.cols = cols;
        this.m_control = m_control;
        this.automates = automates;
        this.authorised = true;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, j, i);
            }
        }

        
        //======generation du labyrinthe======
        //pourcentage_aleatoire_obstacle(this, 90, 666, debut_entre_X, debut_entre_Y, fin_X, fin_Y); // Exemple de pourcentage et de seed
        remplir_obstacle();
        cree_des_salles(seed,5,5, 10, 3);

        //======generation des entités======
        
        
        cell c = randomCell_libre();
        Player1 p = new Player1(this,c.getCol(),c.getRow(), automates.get("Joueur1"));
        m_control.addEntity(p);
        main_Entity = p;
        x_main_old = p.getX();
        y_main_old = p.getY();

        place_monstre(10);
        //case vide random
        c = randomCell_libre();
        //MazeSolver m = new MazeSolver(this, c.getCol(), c.getRow());
        MazeSolver m = new MazeSolver(this, c.getCol(), c.getRow(), automates.get("MazeSolver"));
        
        m_control.addEntity(m);
    }

    
    


    private int pourcentage_aleatoire_obstacle(Grille grille, int pourcentage, long seed, int startX, int startY,
            int endX, int endY) {
        if (pourcentage < 0 || pourcentage > 100) {
            return -1;
        }

        int totalCells = grille.rows * grille.cols;
        int numObstacles = (totalCells * pourcentage) / 100;

        Random random = new Random(seed);
        List<cell> emptyCells = new ArrayList<>();

        for (int i = 0; i < grille.rows; i++) {
            for (int j = 0; j < grille.cols; j++) {
                if (grille.grille[i][j].pas_obstacle() && !(i == 0 && j == 0)) {
                    emptyCells.add(grille.grille[i][j]);
                }
            }
        }
        
        Collections.shuffle(emptyCells, random);
        // System.out.println("print cells : "+emptyCells);
        boolean[][] tempObstacles = new boolean[grille.rows][grille.cols];
        for (int i = 0; i < grille.rows; i++) {
            for (int j = 0; j < grille.cols; j++) {
                tempObstacles[i][j] = !grille.grille[i][j].pas_obstacle();
            }
        }

        int obstaclesPlaced = 0;
        for (int i = 0; i < emptyCells.size() && obstaclesPlaced < numObstacles; i++) {
            cell c = emptyCells.get(i);
            tempObstacles[c.getRow()][c.getCol()] = true;

            if (chemin_existe(tempObstacles, startX, startY, endX, endY) ) {
                Obstacle o = new Obstacle(this, c.getCol(), c.getRow(), automates.get("MurIncassable"));
                m_control.addEntity(o);
                c.setEntity(o);
                obstaclesPlaced++;
            } else {
                tempObstacles[c.getRow()][c.getCol()] = false;
            }
        }
        return 0;
    }

    // L'idéale est d'utiliser un algo de parcours en largeur afin de vérifier
    // qu'une grille est faisable
    // pour l'instant on fait que pour 2 cases // 3 cases à faire après( porte, clée
    // , case de départ )
    private boolean chemin_existe(boolean[][] obstacles, int startX, int startY, int endX, int endY) {
        if (startX == endX && startY == endY) {
            return true;
        }
        boolean[][] visitedFromStart = new boolean[rows][cols];
        boolean[][] visitedFromEnd = new boolean[rows][cols];
        Queue<int[]> queueStart = new LinkedList<>();
        Queue<int[]> queueEnd = new LinkedList<>();

        queueStart.add(new int[] { startX, startY });
        queueEnd.add(new int[] { endX, endY });

        visitedFromStart[startX][startY] = true;
        visitedFromEnd[endX][endY] = true;

        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; 

        while (!queueStart.isEmpty() && !queueEnd.isEmpty()) {
            if (avancer_largeur(queueStart, visitedFromStart, visitedFromEnd, obstacles, directions)) {
                return true;
            }
            if (avancer_largeur(queueEnd, visitedFromEnd, visitedFromStart, obstacles, directions)) {
                return true;
            }
        }
        return false;
    }

    private boolean avancer_largeur(Queue<int[]> queue, boolean[][] visited, boolean[][] visitedOther,
            boolean[][] obstacles, int[][] directions) {
        int[] current = queue.poll();
        int x = current[0];
        int y = current[1];

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (Valid(newX, newY) && !obstacles[newX][newY] && !visited[newX][newY]) {
                if (visitedOther[newX][newY]) {
                    return true;
                }

                visited[newX][newY] = true;
                queue.add(new int[] { newX, newY });
            }
        }

        return false;
    }
    private boolean Valid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
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
                Obstacle o = new Obstacle(this, j, i , automates.get("MurIncassable"));
               
                m_control.addEntity(o);
                grille[i][j].setEntity(o);
            }
        }
    }

    //place les monstres dans la grille a des endroit vide et aléatoire
    public void place_monstre(int nb_monstre) {
        
        for (int i = 0; i < nb_monstre; i++) {
            cell c = randomCell_libre();
            MazeSolver m = new MazeSolver(this, c.getCol(), c.getRow(), automates.get("MazeSolver"));
            m_control.addEntity(m);
            c.setEntity(m);
        }
    }



    /* ======================Partie Synchro========================== */
    public char getTouche() {
        return touche;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }
    
    public boolean IsAuthorised(){
        return this.authorised;
    }

    public void switchAuthorised(){
        this.authorised = !this.authorised;
    }

    public void resetTouche() {
        this.touche = ' ';
    }

    public void key(char touche) {
        if (this.authorised==true){
            this.touche = touche;
            this.authorised = false;
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

    public BufferedImage getImage(int index) {
        return m_images[index];
    }
    

    public cell randomCell_libre() {
        int x = (int) (Math.random() * rows);
        int y = (int) (Math.random() * cols);
        while (grille[x][y].getType() != cellType.Vide) {
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
    
    public void tick(long elapsed) {
        m_imageElapsed += elapsed;

        for (Entity e : m_control.getEntities()) {
            e.tick(elapsed);
        }

    }
    

    /*=========================Paint et ticks=============================*/

    int x_main_old;
    int y_main_old;
    int frames_anim = 8;
    int mouvement = frames_anim; //nombre de frame pour le decalage de la vue
    Color vide = new Color(223, 208, 184);
    Color obstacle = new Color(139, 127, 109);
    Color player = new Color(60, 91, 111);
    Color pickable = new Color(240, 194, 80);

   
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
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
                    g.drawImage(m_images[0],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / frames_anim,
                            (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / frames_anim,
                            width / viewport_size,
                            height / viewport_size,
                            null);
                else
                    g.drawImage(m_images[21],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / frames_anim,
                            (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / frames_anim,
                            width / viewport_size,
                            height / viewport_size,
                            null);
            }
        }
        //on dessine les entités
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                grille[j][i].paint(g,
                        (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / frames_anim,
                        (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / frames_anim,
                        width / viewport_size,
                        height / viewport_size);
            }
        }

        if (offset_x != 0 || offset_y != 0) {
            mouvement--;
            if (mouvement == -1){
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
    
    void drawMinimap(Graphics g, int x, int y, int width, int height) { 
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                switch (grille[j][i].getCategory()) {
                    case 'V':
                        g.setColor(vide);
                        break;
                    case 'O':
                        g.setColor(obstacle);
                        break;
                    case 'P':
                        g.setColor(pickable);
                        break;
                    case 'T':
                        g.setColor(player);
                        break;

                }
                g.fillRect(x + (i * width / cols), y + (j * height / rows), width / cols, height / rows);
            }
        }
    }
    

    BufferedImage[] coeur = loadSprite("resources/coeur.png", 2, 3);
    void drawATH_haut(Graphics g, int x, int y, int width, int height) {
        //TODO:
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.drawImage(coeur[0], 1000, 20, null);

    }
    
    void drawATH_bas(Graphics g, int x, int y, int width, int height) {
        
        if (authorised) {
            g.setColor(Color.GREEN);
            g.drawImage(null, x, y, null);

        }
        else {
        g.setColor(Color.RED);
    }
        g.fillRect(x, y, width, height);

    }
    

}
