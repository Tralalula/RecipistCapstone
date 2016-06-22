package localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tobias on 22-06-2016.
 */
public class RecipistDbHelper extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "recipist.db";

    public RecipistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " +
                RecipistContract.RecipeEntry.TABLE_NAME + " (" +
                RecipistContract.RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipistContract.RecipeEntry.COLUMN_FIREBASE_ID + " TEXT NOT NULL, " +
                RecipistContract.RecipeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                RecipistContract.RecipeEntry.COLUMN_IMAGE_PATH + " TEXT NOT NULL, " +
                RecipistContract.RecipeEntry.COLUMN_PROGRESS + " TEXT NOT NULL, " +
                RecipistContract.RecipeEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                RecipistContract.RecipeEntry.COLUMN_SERVINGS + " TEXT NOT NULL, " +
                " UNIQUE (" + RecipistContract.RecipeEntry.COLUMN_FIREBASE_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE " +
                RecipistContract.IngredientEntry.TABLE_NAME + " (" +
                RecipistContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipistContract.IngredientEntry.COLUMN_FIREBASE_ID + " TEXT NOT NULL, " +
                RecipistContract.IngredientEntry.COLUMN_RECIPE_ID + " TEXT NOT NULL, " +
                RecipistContract.IngredientEntry.COLUMN_ORDER_NUMBER + " TEXT NOT NULL, " +
                RecipistContract.IngredientEntry.COLUMN_INGREDIENT + " TEXT NOT NULL, " +
                " UNIQUE (" + RecipistContract.IngredientEntry.COLUMN_FIREBASE_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_STEP_TABLE = "CREATE TABLE " +
                RecipistContract.StepEntry.TABLE_NAME + " (" +
                RecipistContract.StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipistContract.StepEntry.COLUMN_FIREBASE_ID + " TEXT NOT NULL, " +
                RecipistContract.StepEntry.COLUMN_RECIPE_ID + " TEXT NOT NULL, " +
                RecipistContract.StepEntry.COLUMN_ORDER_NUMBER + " TEXT NOT NULL, " +
                RecipistContract.StepEntry.COLUMN_METHOD + " TEXT NOT NULL, " +
                " UNIQUE (" + RecipistContract.StepEntry.COLUMN_FIREBASE_ID + ") ON CONFLICT REPLACE);";


        db.execSQL(SQL_CREATE_RECIPE_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_STEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipistContract.RecipeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecipistContract.IngredientEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecipistContract.StepEntry.TABLE_NAME);

        onCreate(db);
    }
}
