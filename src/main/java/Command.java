package duke.command;

import duke.exception.*;
import duke.component.*;

public abstract class Command {

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    public abstract boolean isExit();
}