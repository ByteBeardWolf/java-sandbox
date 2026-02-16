package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private static final String WALL = "\u2588\u2588"; //1
    private static final String PASSAGE = "  "; //0
    private static final String PATH = "//"; //2
    private static final int[][] directions = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};
    private final int height, width;
    private int[][] matrix;
    private final List<int[]> frontier = new ArrayList<>();

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        initializeMatrix();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    private void initializeMatrix() {
        this.matrix = new int[height][width];
        // Initialize all cells as walls (1)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    public void generateMaze() {
        // Below is Randomized Prim's algorithm implementation
        Random rand = new Random();

        // choose a random starting point
        int startX = rand.nextInt(width-2) + 1;
        int startY = rand.nextInt(height-2)+ 1;
        if(startY%2 == 0) startY--; // Ensure starting point is on an odd row for better maze structure
        matrix[startY][startX] = 0; // Mark as passage

        // Add frontier cells to the list
        addFrontier(startX, startY, frontier);

        while(!frontier.isEmpty()) {
            // Pick a random frontier cell and remove it from the list
            int[] cell = frontier.remove(rand.nextInt(frontier.size()));

            // Find available adjacent passages
            List<int[]> passages = new ArrayList<>();
            for (int[] dir : directions) {
                int newX = cell[0] + dir[0];
                int newY = cell[1] + dir[1];
                if (newX > 0 && newX < width - 1 && newY > 0 && newY < height - 1 && matrix[newY][newX] == 0) {
                    passages.add(new int[]{newX, newY});
                }
            }

            if (!passages.isEmpty()) {
                // Connect the frontier cell to a random adjacent passage
                int[] neighbor = passages.get(rand.nextInt(passages.size()));
                int connectX = (cell[0] + neighbor[0]) / 2;
                int connectY = (cell[1] + neighbor[1]) / 2;
                matrix[cell[1]][cell[0]] = 0; // Mark frontier cell as passage
                matrix[connectY][connectX] = 0; // Mark connecting cell as passage

                // Add new frontier cells from the newly added passage to the list
                addFrontier(cell[0], cell[1], frontier);
            }
        }

        //choose a random entrance and exit
        int entranceY = rand.nextInt(height-2) + 1;
        int exitY = rand.nextInt(height-2) + 1;
        // Ensure entrance and exit are on odd rows for better maze structure
        if(entranceY%2 == 0) entranceY--;
        if(exitY%2 == 0) exitY--;
        matrix[entranceY][0] = matrix[entranceY][1] = matrix[exitY][width-2] = matrix[exitY][width-1] = 0; // Entrance and Exit
    }

    private void addFrontier(int startX, int startY, java.util.List<int[]> frontier) {
        for (int[] dir : directions) {
            int newX = startX + dir[0];
            int newY = startY + dir[1];
            // Ensure the new cell is within maze frame and is a wall
            if (newX > 0 && newX < width - 1 && newY > 0 && newY < height - 1 && matrix[newY][newX] == 1) {
                frontier.add(new int[]{newX, newY});
                matrix[newY][newX] = 2; // Mark as frontier
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int cell : row) {
                sb.append(cell == 1 ? WALL : (cell == 0 ? PASSAGE : PATH));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
