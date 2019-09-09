package duke.component;

//imports
import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.exception.EmptyDescException;
import duke.exception.InvalidArgumentException;

/**
 * Represents a Parser object that parses user inputs.
 * The 'Parser' class supports operators (i) parse out user inputs
 * and (ii) determine the input type, which helps parse out user inputs.
 */
public class Parser {


    /**
     * Parses out user inputs.
     *
     * @param input User input.
     * @return Command Type of command that user input in.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    public Command parse(String input) throws DukeException {
        switch (input) {

        case "bye":
            return new ExitCommand();


        case "list":
            return new ListCommand();


        default:
            return determineInputType(input);

        }
    }

    /**
     * Returns Command object based on user input.
     * Helper method to parse(String input) method.
     *
     * @param input User input.
     * @return Command Type o comand summoned by user.
     * @throws DukeException If there is IOException when reading or writing from text file.
     */
    private Command determineInputType(String input) throws DukeException {
        if (input.contains("done")) {

            try {

                int taskNum = Integer.parseInt(input.substring(5));
                DoneCommand resultCommand = new DoneCommand(taskNum);

                //Assert that result is an instance of DoneCommand
                assert resultCommand instanceof DoneCommand : "Result is not an instance of DoneCommand";

                return resultCommand;

            } catch (StringIndexOutOfBoundsException e1) {
                throw new EmptyDescException("done");
            }


        } else if (input.contains("todo")) {

            try {
                AddCommand resultCommand =  new AddCommand("T", false, input.substring(5), null);

                //Assert that result is an instance of DoneCommand
                assert resultCommand instanceof AddCommand : "Result is not an instance of AddCommand";

                return resultCommand;

            } catch (StringIndexOutOfBoundsException e) {
                throw new EmptyDescException("todo");
            }


        } else if (input.contains("deadline")) {

            try {

                String[] parts = input.split("/by");

                AddCommand resultCommand = new AddCommand("D", false, parts[0].substring(9), parts[1]);

                //Assert that result is an instance of DoneCommand
                assert resultCommand instanceof AddCommand : "Result is not an instance of AddCommand";

                return resultCommand;
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                throw new EmptyDescException("deadline");

            }

        } else if (input.contains("event")) {
            try {

                String[] parts = input.split("/at");

                AddCommand resultCommand = new AddCommand("E", false, parts[0].substring(5), parts[1]);
                //Assert that result is an instance of DoneCommand
                assert resultCommand instanceof AddCommand : "Result is not an instance of AddCommand";

                return resultCommand;
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                throw new EmptyDescException("event");
            }
        } else if (input.contains("delete")) {

            int taskNum;
            try {
                taskNum = Integer.parseInt(input.substring(7));
                DeleteCommand resultCommand =  new DeleteCommand(taskNum);

                //Assert that result is an instance of DoneCommand
                assert resultCommand instanceof DeleteCommand : "Result is not an instance of AddCommand";

                return resultCommand;

            } catch (StringIndexOutOfBoundsException e) {
                throw new EmptyDescException("delete");
            }


        } else if (input.contains("find")) {
            String keyword;
            try {
                keyword = input.substring(5);
                return new FindCommand(keyword);
            } catch (StringIndexOutOfBoundsException e) {
                throw new EmptyDescException("find");
            }
        } else {
            throw new InvalidArgumentException();
        }
    }


}






