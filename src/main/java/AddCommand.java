package duke.command;

import duke.component.*;
import duke.exception.*;

public abstract class AddCommand extends Command {

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    public boolean isExit() {
        return false;
    }

}