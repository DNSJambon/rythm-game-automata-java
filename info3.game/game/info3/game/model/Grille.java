package info3.game.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.MazeSolver;
import info3.game.model.Entities.Obstacle;
import info3.game.model.Entities.Player1;
import info3.game.model.Entities.Snake;


public class Grille implements IGrille{
    cell[][] grille;
    int rows;
    int cols;
    long m_imageElapsed;

    Control m_control;

    // Viewport
    BufferedImage[] m_images;
    Entity main_Entity;
    int viewport_size = 7;


    //Synchro
    boolean authorised;
    char touche;
    

    public Grille(int rows, int cols, Control m_control) throws IOException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        int debut_entre_X=5;
        int debut_entre_Y=5;
        this.rows = rows;
        this.cols = cols;
        this.m_control = m_control;
        this.authorised = true;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, j, i);
            }
        }
        //ajoute player1
        Player1 p = new Player1(this);
        m_control.addEntity(p);
<<<<<<< HEAD
        MazeSolver m = new MazeSolver(this, debut_entre_X,debut_entre_Y );
=======
        MazeSolver m = new MazeSolver(this, 0, 0);
        main_Entity = m;
>>>>>>> master
        m_control.addEntity(m);
        // ajout des obstacles aléatoirements
        Obstacle o;
        for (int i = 0; i <150; i++) {
            cell c = randomCell_libre();
            o = new Obstacle(this, c.getCol(), c.getRow());
            m_control.addEntity(o);
            //c.setEntity(o);
        }
        pourcentage_aleatoire_obstacle(this, 20, 223, debut_entre_X, debut_entre_Y, 14, 14); // Exemple de pourcentage et de seed
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
            for (int j = 0; j < cols; j++) {
                if (grille.grille[i][j].pas_obstacle()) {
                    emptyCells.add(grille.grille[i][j]);
                }
            }
        }

        Collections.shuffle(emptyCells, random);

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

            if (chemin_existe(tempObstacles, startX, startY, endX, endY)) {
                Obstacle o = new Obstacle(this, c.getCol(), c.getRow());
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
    
    public Grille(int rows, int cols) throws IOException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        this.rows = rows;
        this.cols = cols;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, i, j);
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
    

    int x_main_old = 3;
    int y_main_old = 3;
    int mouvement = 8; //nombre de frame pour le decalage de la vue

    public void paint(Graphics g, int width, int height) {
        int x_main = main_Entity.getX();
        int y_main = main_Entity.getY();
        
        if (x_main < viewport_size / 2) {
            x_main = viewport_size / 2;
        }     
        if (x_main > rows - viewport_size / 2- 1) {
            x_main = rows - viewport_size / 2 - 1;
        }
        if (y_main < viewport_size / 2) {
            y_main = viewport_size / 2;
        }
        if (y_main > cols - viewport_size / 2 - 1) {
            y_main = cols - viewport_size / 2 - 1;
        }
        
        //l'offset permet de faire bouger la vue de facçon fluide quand on se déplace
        int offset_x = (x_main - x_main_old) * width / viewport_size;
        int offset_y = (y_main - y_main_old) * height / viewport_size;
       
        //pour load la ligne d'image qui va disparaitre:
        int load_x_negatif, load_x_positif, load_y_negatif, load_y_positif;
        load_x_negatif = load_x_positif = load_y_negatif = load_y_positif = 0;
        if (offset_x > 0) {
            load_x_negatif = 1;
        }
        if (offset_x < 0) {
            load_x_positif = 1;
        }
        if (offset_y > 0) {
            load_y_negatif = 1;
        }
        if (offset_y < 0) {
            load_y_positif = 1;
        }

    

        //on dessine le sol en premier
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2 + load_y_positif; j++) {
            for (int i = x_main - viewport_size/2 - load_x_negatif; i <= x_main + viewport_size / 2 + load_x_positif; i++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
                    g.drawImage(m_images[0],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                            (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / 8,
                            width / viewport_size,
                            height / viewport_size,
                                                    null);
                else
                    g.drawImage(m_images[21],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                            (j - y_main + viewport_size / 2) * height / viewport_size +offset_y * mouvement / 8,
                            width / viewport_size,
                            height / viewport_size,
                                                    null);
            }
        }
        //on dessine les entités
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2 + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2 + load_x_positif; i++) {
                grille[j][i].paint(g,
                        (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                        (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / 8,
                        width / viewport_size,
                        height / viewport_size);
            }
        }

        
        if (offset_x != 0 || offset_y != 0) {
            mouvement--;
            if (mouvement == -1) {
                mouvement = 8;
            }
        }
        if (mouvement ==8) {
            x_main_old = x_main;
            y_main_old = y_main;
        }


    
    }
    

}
