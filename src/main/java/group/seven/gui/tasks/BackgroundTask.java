package group.seven.gui.tasks;

import javafx.concurrent.Task;

public class BackgroundTask extends Task<Boolean> {

    private boolean finished;

    public BackgroundTask() {
        finished = false;
    }

    @Override
    protected Boolean call() throws Exception {
        finished = true;
        updateValue(finished);
        return finished;
    }
}
