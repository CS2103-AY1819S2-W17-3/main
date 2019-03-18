package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_DINING;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.finance.testutil.TypicalRecords.ALICE;
import static seedu.finance.testutil.TypicalRecords.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.testutil.RecordBuilder;

public class RecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Record record = new RecordBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        record.getCategories().remove(0);
    }

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(ALICE.isSameRecord(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameRecord(null));

        // different amount and date -> returns false
        Record editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.isSameRecord(editedAlice));

        // different name -> returns false
        editedAlice = new RecordBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameRecord(editedAlice));

        // same name, same amount, different attributes -> returns true
        editedAlice = new RecordBuilder(ALICE).withDate(VALID_DATE_BOB).withCategories(VALID_CATEGORY_DINING).build();
        assertTrue(ALICE.isSameRecord(editedAlice));

        // same name, same date, different attributes -> returns true
        editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB).withCategories(VALID_CATEGORY_DINING)
                .build();
        assertTrue(ALICE.isSameRecord(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Record aliceCopy = new RecordBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different record -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Record editedAlice = new RecordBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different amount -> returns false
        editedAlice = new RecordBuilder(ALICE).withAmount(VALID_AMOUNT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new RecordBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different categories -> returns false
        editedAlice = new RecordBuilder(ALICE).withCategories(VALID_CATEGORY_DINING).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
