package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.PinnedSourcesCoordinationCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.Source;

/**
 * Deletes a source identified using it's displayed index from the source manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the source identified by the index number used in the displayed source list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SOURCE_SUCCESS = "Deleted Source:\n---------------------"
            + "--------------\n%1$s";
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.switchToSources(); // sets source manager data to list
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        boolean isSourcePinned = PinnedSourcesCoordinationCenter.isPinnedSource(model, targetIndex.getZeroBased());
        if (isSourcePinned == true) {
            PinnedSourcesCoordinationCenter.decrementPinnedSources(model);
            PinnedSourcesCoordinationCenter.saveCurrentPinnedSources(model);
        }

        Source sourceToDelete = lastShownList.get(targetIndex.getZeroBased());

        // permanently delete from source manager list if the exact same source exists in deleted source list
        if (model.hasDeletedSource(sourceToDelete)) {
            model.deleteSource(sourceToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete));
        }

        model.addDeletedSource(sourceToDelete);
        model.deleteSource(sourceToDelete);
        model.commitDeletedSources();
        model.commitSourceManager();
        return new CommandResult(String.format(MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public int hashCode() {
        return targetIndex.getOneBased();
    }
}
