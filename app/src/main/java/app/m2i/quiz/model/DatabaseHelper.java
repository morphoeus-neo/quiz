package app.m2i.quiz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzz5md on 06/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quiz";
    private static final int DB_VERSION = 3;

    private SQLiteDatabase writableDb;
    private SQLiteDatabase readableDb;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.readableDb = getReadableDatabase();
        this.writableDb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE questions (id INTEGER PRIMARY KEY AUTOINCREMENT, question_text TEXT NOT NULL , goodAnswer INTEGER NOT NULL )";

        db.execSQL(sql);
        // Chargement des données
        loadFixtures(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = " DROP TABLE IF EXISTS questions";
        db.execSQL(sql);

        onCreate(db);

    }

    private void loadFixtures(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("question_text", "un litre d'eau fait a peu près 100 CL");
        values.put("goodAnswer", true);

        //SQLiteDatabase db = writableDb;

        db.insert("questions", null, values);

        // autre méthode
        String sql = "INSERT INTO questions ( question_text,goodAnswer) VALUES " +
                "('Tim cook est le patron de Microsoif ? ',0)," +
                "('La lune est plus grosse que la terre ? ',0)," +
                "('Donald Trump est le président des Etats Unis ? ',1)," +
                "('C++ est un language de programmation? ',0)," +
                " ('Manger c est mal ? ',1)," +
                "('Android a été inventé par Bill Gates? ',0)";

        db.execSQL(sql);

    }

    public List<Question> findAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = readableDb;
        // Commande SQL
        String sql = "SELECT id, question_text, goodAnswer FROM questions";
        // Execution de la commande
        Cursor cursor = db.rawQuery(sql, null);
        // Boucle sur le curseur pour remplir questionList
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getLong(0));
            question.setText(cursor.getString(1));
            question.setGoodAnswer(cursor.getInt(2) == 1);
            questionList.add(question);
        }
        cursor.close();
        return questionList;
    }

    public void findOneById(String questionId) {
        String questionText = "";
        SQLiteDatabase db = readableDb;
        String sql = "SELECT id, question-text, goodAnswer FROM questions WHERE id=?";
        db.execSQL(sql, new String[]{questionId});


    }


    public void delete(Long questionId) {
        String[] params = {String.valueOf(questionId)};
        writableDb.delete("questions", "id=?", params);
        findAllQuestions();


    }

    public void modify(String questionId, String questionText, Boolean goodAnswer) {

        ContentValues values = new ContentValues();
        values.put("question_text", questionText);
        values.put("goodAnswer", goodAnswer);
        values.put("id", questionId);
        writableDb.update("questions", values, "id=?", new String[]{questionId});

    }

    public void insert(String questionText, Boolean goodAnswer) {
        ContentValues values = new ContentValues();
        values.put("question_text", questionText);
        values.put("goodAnswer", goodAnswer);
        writableDb.insert("questions", null, values);
    }
}
