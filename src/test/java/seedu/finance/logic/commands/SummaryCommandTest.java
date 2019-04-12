package seedu.finance.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;
import seedu.finance.testutil.Assert;

//import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;

/**
 * Contains integration tests (interaction with Model) and unit test for SummaryCommand
 */
public class SummaryCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    public SummaryCommandTest() throws ParseException { }

    /*@Test
    public void execute() {
        assertCommandFailure(new SummaryCommand(), model, new CommandHistory(), MESSAGE_NOT_IMPLEMENTED_YET);
    }*/

    @Test
    public void constructorValidParameters() {
        SummaryCommand summaryCommand = new SummaryCommand(6, "d");
        assertTrue(summaryCommand.equals(new SummaryCommand(6, "d")));

        summaryCommand = new SummaryCommand();
        assertTrue(summaryCommand.equals(new SummaryCommand(7, "d")));

        summaryCommand = new SummaryCommand(8, "m");
        assertTrue(summaryCommand.equals(new SummaryCommand(8, "m")));
    }

    @Test
    public void constructorInvalidParameters() {
        Assert.assertThrows(NullPointerException.class, () -> new SummaryCommand(1, null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new SummaryCommand(1, "asd"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new SummaryCommand(0, "d"));
    }

}
