package maze;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameState{
    private boolean mazeReady;
    private int selectedOption;
    private Set<Integer> availableOptions;

    public GameState(boolean mazeReady) {
        this.mazeReady = mazeReady;
        this.availableOptions = new HashSet<>(Arrays.asList(1, 2, 0));
    }

    public boolean isValidSelection() {
        if(mazeReady) {
            availableOptions.add(3);
            availableOptions.add(4);
            availableOptions.add(5);
        } else {
            availableOptions.remove(3);
            availableOptions.remove(4);
            availableOptions.remove(5);
        }

        return availableOptions.contains(selectedOption);
    }

    public boolean isMazeReady() {
        return mazeReady;
    }

    public void setMazeReady(boolean mazeReady) {
        this.mazeReady = mazeReady;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }
}