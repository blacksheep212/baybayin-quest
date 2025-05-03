package com.example.baybayinquest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaybayinDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "baybayin.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "baybayin_characters";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CHARACTER = "character";
    private static final String COLUMN_SYLLABLE = "syllable";
    private static final String COLUMN_DESCRIPTION = "description";

    public BaybayinDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CHARACTER + " TEXT NOT NULL, " +
                COLUMN_SYLLABLE + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);

        // Insert 10 sample Baybayin characters
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        String[] characters = {"ᜀ", "ᜁ", "ᜂ", "ᜃ", "ᜄ", "ᜅ", "ᜆ", "ᜇ", "ᜈ", "ᜉ"};
        String[] syllables = {"A", "I", "U", "Ka", "Ga", "Nga", "Ta", "Da", "Na", "Pa"};
        String[] descriptions = {
                "Vowel A",
                "Vowel I",
                "Vowel U",
                "Consonant Ka",
                "Consonant Ga",
                "Consonant Nga",
                "Consonant Ta",
                "Consonant Da",
                "Consonant Na",
                "Consonant Pa"
        };

        for (int i = 0; i < characters.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CHARACTER, characters[i]);
            values.put(COLUMN_SYLLABLE, syllables[i]);
            values.put(COLUMN_DESCRIPTION, descriptions[i]);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public List<BaybayinCharacter> getAllCharacters() {
        List<BaybayinCharacter> characters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String character = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHARACTER));
                String syllable = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYLLABLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                characters.add(new BaybayinCharacter(id, character, syllable, description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return characters;
    }

    public BaybayinCharacter getRandomCharacter() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
        BaybayinCharacter character = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String charText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHARACTER));
            String syllable = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYLLABLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            character = new BaybayinCharacter(id, charText, syllable, description);
        }

        cursor.close();
        db.close();
        return character;
    }

    // Inner class to represent a Baybayin character
    public static class BaybayinCharacter {
        private int id;
        private String character;
        private String syllable;
        private String description;

        public BaybayinCharacter(int id, String character, String syllable, String description) {
            this.id = id;
            this.character = character;
            this.syllable = syllable;
            this.description = description;
        }

        public int getId() { return id; }
        public String getCharacter() { return character; }
        public String getSyllable() { return syllable; }
        public String getDescription() { return description; }
    }
}