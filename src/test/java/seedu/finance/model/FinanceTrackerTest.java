package seedu.finance.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_DINING;
import static seedu.finance.testutil.TypicalRecords.ALICE;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.exceptions.DuplicateRecordException;
import seedu.finance.testutil.RecordBuilder;

public class FinanceTrackerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTracker financeTracker = new FinanceTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financeTracker.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFinanceTracker_replacesData() {
        FinanceTracker newData = getTypicalFinanceTracker();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same identity fields
        Record editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB)
                .withCategories(VALID_CATEGORY_DINING)
                .build();
        List<Record> newRecords = Arrays.asList(ALICE, editedAlice);
        FinanceTrackerStub newData = new FinanceTrackerStub(newRecords);

        thrown.expect(DuplicateRecordException.class);
        financeTracker.resetData(newData);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasRecord(ALICE));
    }

    @Test
    public void hasRecord_recordInFinanceTracker_returnsTrue() {
        financeTracker.addRecord(ALICE);
        assertTrue(financeTracker.hasRecord(ALICE));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addRecord(ALICE);
        Record editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB)
                .withCategories(VALID_CATEGORY_DINING).build();
        assertTrue(financeTracker.hasRecord(editedAlice));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getRecordList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.addRecord(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.removeListener(listener);
        financeTracker.addRecord(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFinanceTracker whose records list can violate interface constraints.
     */
    private static class FinanceTrackerStub implements ReadOnlyFinanceTracker {
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        FinanceTrackerStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
