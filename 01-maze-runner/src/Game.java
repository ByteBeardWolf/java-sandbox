package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    private Maze maze;
    int size;

    public void start() {
        //initialize game state
        GameState currentState = new GameState(false);
        boolean gameOver = false;
        File file;

        while (!gameOver) {
            displayMenu(currentState);

            Scanner sc = new Scanner(System.in);
            currentState.setSelectedOption(sc.nextInt());
            if(currentState.isValidSelection()) {
                switch(currentState.getSelectedOption()) {
                    case 0: //exit
                        System.out.println("Bye!");
                        gameOver = true;
                        break;
                    case 1: //generate maze
                        System.out.println("Enter the size of a new maze");
                        size = sc.nextInt();
                        maze = new Maze(size, size);
                        maze.generateMaze();
                        System.out.println(maze);
                        currentState.setMazeReady(true);
                        break;
                    case 2: //load maze
                        System.out.println("Enter file name:");
                        file = new File(sc.next());
                        int i = 0;
                        size = 0;
                        String line;
                        int[][] matrix;

                        try (Scanner scannerLoad = new Scanner(file)) {
                            while (scannerLoad.hasNext()) {
                                line = scannerLoad.nextLine();
                                if (i == 0) {
                                    size = line.length() / 2;
                                    maze = new Maze(size, size);
                                }
                                for (int j = 0; j < size; j++) {
                                    if (line.charAt(j * 2) == '\u2588') {
                                        matrix = maze.getMatrix();
                                        matrix[i][j] = 1;
                                        maze.setMatrix(matrix);
                                    } else {
                                        maze.getMatrix()[i][j] = 0;
                                    }
                                }
                                i++;
                            }
                            System.out.println("Maze loaded:");
                            currentState.setMazeReady(true);
                        } catch (FileNotFoundException e) {
                            System.out.println("The file " + file + " does not exist");
                        }
                        break;
                    case 3: //save maze
                        if(currentState.isMazeReady()) {
                            System.out.println("Enter file name:");
                            String saveFileName = sc.next();
                            file = new File(saveFileName);

                            try (FileWriter writer = new FileWriter(file)) {
                                writer.write(maze.toString());
                                currentState.setMazeReady(true);
                            } catch (IOException e) {
                                System.out.printf("Cannot save a maze. Exception occurred - %s", e.getMessage());
                            }
                            break;
                        }
                    case 4: //display maze
                        if(currentState.isMazeReady()) {
                            System.out.println(maze);
                            break;
                        }

                }
            } else {
                System.out.println("Incorrect option. Please try again");
            }
        }
    }

    private static void displayMenu(GameState currentState) {
        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append("=== Menu ===\n");
        menuBuilder.append("1. Generate a new maze\n");
        menuBuilder.append("2. Load a maze\n");
        if(currentState.isMazeReady()) menuBuilder.append("3. Save the maze\n");
        if(currentState.isMazeReady()) menuBuilder.append("4. Display the maze\n");
        menuBuilder.append("0. Exit\n");

        System.out.println(menuBuilder);
    }
}
