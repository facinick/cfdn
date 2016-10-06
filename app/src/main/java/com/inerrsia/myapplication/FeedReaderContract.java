package com.inerrsia.myapplication;
import android.database.sqlite.*;
import android.provider.BaseColumns;


public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_WORD = "WORD";
        public static final String COLUMN_MEANING = "MEANING";
        public static final String COLUMN_SYNONYM = "SYNONYM";
        public static final String COLUMN_PRIORITY = "PRIORITY";

    }

}
