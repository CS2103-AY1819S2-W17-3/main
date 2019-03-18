package seedu.finance.testutil;

import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_SHOPPING;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_DINING;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record ALICE = new RecordBuilder().withName("Alice Pauline")
            .withAmount("120").withDate("12/02/2017").withCategories("friends").build();
    public static final Record BENSON = new RecordBuilder().withName("Benson Meier")
            .withAmount("119").withDate("12/02/2015").withCategories("owesMoney", "friends").build();
    public static final Record CARL = new RecordBuilder().withName("Carl Kurz")
            .withAmount("130").withDate("12/05/2017").build();
    public static final Record DANIEL = new RecordBuilder().withName("Daniel Meier")
            .withAmount("129").withDate("12/02/2007").withCategories("friends").build();
    public static final Record ELLE = new RecordBuilder().withName("Elle Meyer")
            .withAmount("150").withDate("12/12/2017").build();
    public static final Record FIONA = new RecordBuilder().withName("Fiona Kunz")
            .withAmount("520").withDate("02/02/2017").build();
    public static final Record GEORGE = new RecordBuilder().withName("George Best")
            .withAmount("128").withDate("12/02/2027").build();

    // Manually added
    public static final Record HOON = new RecordBuilder().withName("Hoon Meier")
            .withAmount("720").withDate("12/07/2017").build();
    public static final Record IDA = new RecordBuilder().withName("Ida Mueller")
            .withAmount("129").withDate("12/01/2017").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withAmount(VALID_AMOUNT_AMY)
            .withDate(VALID_DATE_AMY).withCategories(VALID_CATEGORY_SHOPPING).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withAmount(VALID_AMOUNT_BOB)
            .withDate(VALID_DATE_BOB).withCategories(VALID_CATEGORY_DINING, VALID_CATEGORY_SHOPPING)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecords() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical records.
     */
    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker ft = new FinanceTracker();
        for (Record record : getTypicalRecords()) {
            ft.addRecord(record);
        }

        return ft;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
