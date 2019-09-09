package duke.component;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents Storage object that stores tasks user need to do.
 * Tasks are stored in a test file.
 * The 'Storage' class supports operators (i) loading TaskList object from text file,
 * (ii) converting a line of text to Task object,
 * (iii) adding text to text file,
 * (iv) changig text in text file and
 * (v) amending text in text file.
 */
public class Storage {

    /**
     * File path.
     */
    private String filePath;

    /**
     * Text file.
     */
    private File textFile;

    private static final int COMMAND_IND = 0;
    private static final int DONE_STATUS_IND = 1;
    private static final int DESCRIPTION_IND = 2;
    private static final int TIME_IND = 1;

    private static final int STATUS_DONE = 1;
    private static final int STATUS_NOT_DONE = 1;

    /**
     * Initialises a new Storage object.
     *
     * @param filePath File path where text file is stored in.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.textFile = new File(filePath);

    }

    /**
     * Returns ArrayList of Tasks converted from tasks in text file.
     *
     * @return ArrayList of Tasks.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    public ArrayList<Task> load() throws DukeException {

        try {
            Path path = this.textFile.toPath();

            List<String> lines = Files.readAllLines(path);
            ArrayList<Task> tasks = new ArrayList<>();

            initialiseTasks(tasks, lines);


            return tasks;
        } catch (IOException e) {
            throw new DukeException();
        }
    }

    private void initialiseTasks(ArrayList<Task> tasks, List<String> lines) {
        for (String line : lines) {
            tasks.add(this.lineToTask(line));


        }
    }


    /**
     * Returns task converted from String representation of text in text file.
     *
     * @param line String representation of a task, from text file.
     * @return Task.
     */
    public Task lineToTask(String line) {
        String[] lineArray = line.split("\\|");

        switch (lineArray[COMMAND_IND].trim()) {
        case "T":
            //return a new toDo task with "whether it is done" and description
            return new Todo(lineArray[DONE_STATUS_IND], lineArray[DESCRIPTION_IND].trim());

        case "E":
            //return a new toDo task with "whether it is done" and description and event time
            return new Event(lineArray[DONE_STATUS_IND], lineArray[DESCRIPTION_IND], lineArray[TIME_IND]);

        case "D":
            //return a new deadline task with "whether it is done" and description and deadline
            return new Deadline(lineArray[DONE_STATUS_IND], lineArray[DESCRIPTION_IND], lineArray[TIME_IND]);

        default:
            assert false : "text line from text file is not a valid task";
            return null;

        }
    }


    /**
     * Adds text to text file.
     *
     * @param textToAdd Text to be added into text file.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    public void writeToFile(String textToAdd) throws DukeException {
        try { //Create a file writer object to represent the hard disk
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(textToAdd);
            fw.close();
        } catch (IOException e) {
            throw new DukeException();
        }
    }


    /**
     * Appends task into text file.
     *
     * @param type Type of task.
     * @param date Date the task is due on.
     * @param desc Description of task.
     * @throws DukeException If IOException is thrown when reading or writing from file.
     */
    public void appendToFile(String type, Date date, String desc) throws DukeException {

        if (date == null) {
            writeToFile(type + " | " + STATUS_NOT_DONE + " | " + desc + "\n");
        } else {
            writeToFile(type + " | " + STATUS_DONE + " | " + desc + " | " + date + "\n");
        }
    }


    /**
     * Updates text that is already in text file.
     *
     * @param taskNum Task Number of the task to be updated.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    public void updateText(int taskNum) throws DukeException {
        try {
            int lineNumber = taskNum - 1;
            Path path = Paths.get(filePath);

            //read all the line in the files
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String oldText = lines.get(lineNumber);

            lines.set(lineNumber, oldText.substring(0, 3) + " " + STATUS_DONE + " " + oldText.substring(6, oldText.length()));

            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException();
        }
    }

    /**
     * Deletes text that is already in text file.
     *
     * @param taskNum Task Number of the task to delete.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    public void deleteText(int taskNum) throws DukeException {
        try {
            int lineNumber = taskNum - 1;
            Path path = Paths.get(filePath);

            //read all the line in the files
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            lines.remove(lineNumber);
            Files.write(path, lines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new DukeException();
        }
    }

}