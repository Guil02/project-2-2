package group.seven.gui.tasks;

import group.seven.model.Scenario;
import group.seven.model.environment.Cell;
import javafx.concurrent.Task;

import java.util.Arrays;
import java.util.Objects;

public class CalculateCoverage extends Task<Long> {
    double finished = 99.0;

    public CalculateCoverage(){
    }

    @Override
    protected Long call() throws Exception {
        int explored = Arrays.stream(Scenario.map)
                .flatMap(cells -> Arrays.stream(cells).parallel().filter(cell -> cell.explored)
                        .map(c -> c.explored ? 1 : 0))
                .sequential()
                .reduce(0, Integer::sum);

        updateMessage(explored + "%");
        updateProgress(explored / 10, 10);
        updateValue((long) explored);

        return explored / ((long) Scenario.width * Scenario.height);
    }

    public static void main(String[] args) {
        Scenario.map = new Cell[10][10];

        long start = System.currentTimeMillis();
        Arrays.stream(Scenario.map)
                .flatMap(Arrays::stream)
                .map(cell -> new Cell(3,5))
                .forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

