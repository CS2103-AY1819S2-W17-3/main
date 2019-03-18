package seedu.finance.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.EditRecordDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_AMOUNT_AMY = "312";
    public static final String VALID_AMOUNT_BOB = "123";
    public static final String VALID_DATE_AMY = "12/01/2005";
    public static final String VALID_DATE_BOB = "23/04/2014";
    public static final String VALID_CATEGORY_HUSBAND = "husband";
    public static final String VALID_CATEGORY_FRIEND = "friend";
    public static final String VALID_DESCRIPTION_AMY = "Birthday present for Amy";
    public static final String VALID_DESCRIPTION_BOB = "Birthday present for Bob";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String AMOUNT_DESC_AMY = " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY;
    public static final String AMOUNT_DESC_BOB = " " + PREFIX_AMOUNT + VALID_AMOUNT_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String CATEGORY_DESC_FRIEND = " " + PREFIX_CATEGORY + VALID_CATEGORY_FRIEND;
    public static final String CATEGORY_DESC_HUSBAND = " " + PREFIX_CATEGORY + VALID_CATEGORY_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "*2"; // "*" not allowed in amounts
    public static final String INVALID_DATE_DESC =
            " " + PREFIX_DATE + "29/29/2019"; // invalid date not allowed in dates
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY
            + "hubby*"; // '*' not allowed in categories

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditRecordDescriptor DESC_AMY;
    public static final EditCommand.EditRecordDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditRecordDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAmount(VALID_AMOUNT_AMY).withDate(VALID_DATE_AMY)
                .withCategories(VALID_CATEGORY_FRIEND).build();
        DESC_BOB = new EditRecordDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB)
                .withCategories(VALID_CATEGORY_HUSBAND, VALID_CATEGORY_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the finance tracker, filtered record list and selected record in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinanceTracker expectedFinanceTracker = new FinanceTracker(actualModel.getFinanceTracker());
        List<Record> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecordList());
        Record expectedSelectedRecord = actualModel.getSelectedRecord();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedFinanceTracker, actualModel.getFinanceTracker());
            assertEquals(expectedFilteredList, actualModel.getFilteredRecordList());
            assertEquals(expectedSelectedRecord, actualModel.getSelectedRecord());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the record at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showRecordAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecordList().size());

        Record record = model.getFilteredRecordList().get(targetIndex.getZeroBased());
        final String[] splitName = record.getName().fullName.split("\\s+");
        model.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecordList().size());
    }

    /**
     * Deletes the first record in {@code model}'s filtered list from {@code model}'s finance tracker.
     */
    public static void deleteFirstRecord(Model model) {
        Record firstRecord = model.getFilteredRecordList().get(0);
        model.deleteRecord(firstRecord);
        model.commitFinanceTracker();
    }

}
