package maze;

import java.util.PriorityQueue;
import java.util.Queue;

public class MazeSolver {
    private Queue<Vertex> verticles = new PriorityQueue<>(new VertexComparator());
    private Maze maze;
    private int[][] matrix;
    private Vertex[][] vertexMatrix;
    private Vertex entrance;
    private Vertex exit;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        matrix = maze.getMatrix();
        vertexMatrix = new Vertex[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 0) { // Only consider passages as vertices
                    Vertex v = new Vertex(i, j);
                    vertexMatrix[i][j] = v;
                    if(i == 0 || j == 0) {
                        v.setEntrance(true);
                        v.setCost(0);
                        entrance = v;
                    } else if(i == matrix.length - 1 || j == matrix[i].length - 1) {
                        v.setExit(true);
                        exit = v;
                    }
                    verticles.add(v);
                }
            }
        }

    }

    public void solveMaze() {
        // Implement Dijkstra's algorithm to find the shortest path from entrance to exit
        while(!verticles.isEmpty()) {
            Vertex current = verticles.poll();
            if (current.isVisited()) continue;
            current.setVisited(true);
            updateNeighbors(current);
            if(current.isExit()) {
                break; // Exit found, stop the algorithm
            }
        }

        int[][] solutionPath = new int[matrix.length][matrix[0].length];
        for(int i = 0; i < solutionPath.length; i++) {
            // Copy original maze structure
            System.arraycopy(matrix[i], 0, solutionPath[i], 0, solutionPath[i].length);
        }

        showSolution(solutionPath);
    }

    private void showSolution(int[][] solutionPath) {
        Vertex v = exit;
        solutionPath[v.getX()][v.getY()] = 2;

        while(v != null) {
            v = v.getParent();
            if(v != null) {
                solutionPath[v.getX()][v.getY()] = 2; // Mark the path with a different value (e.g., 2)
            }
        }

        maze.setMatrix(solutionPath);
        System.out.println(maze);
        maze.setMatrix(matrix);
    }

    private void updateNeighbors(Vertex current) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int newX = current.getX() + dir[0];
            int newY = current.getY() + dir[1];
            if (newX >= 0 && newX < matrix.length && newY >= 0 && newY < matrix[newX].length) {
                Vertex neighbor = vertexMatrix[newX][newY];
                if (neighbor != null && !neighbor.isVisited()) { // Only consider unvisited passages
                    int newCost = current.getCost() + 1;
                    if (newCost < neighbor.getCost()) {
                        neighbor.setCost(newCost);
                        neighbor.setParent(current);
                        // Reorder the priority queue by removing and re-adding the neighbor
                        verticles.remove(neighbor); // Remove the neighbor to update its position in the priority queue
                        verticles.add(neighbor); // Re-add the neighbor to update its position in the priority queue
                    }
                }
            }
        }
    }
}
