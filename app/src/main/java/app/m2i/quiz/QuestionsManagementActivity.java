package app.m2i.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.m2i.quiz.model.DatabaseHelper;
import app.m2i.quiz.model.Question;

public class QuestionsManagementActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final int QUESTION_FORM_ACTIVITY = 3;
    private DatabaseHelper dbHelper;
    private List<String> listData;
    private List<Question> questionList;

    private ListView listView;

    private Long questionId;
    private Intent intention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_management);

        // on instancie le helper
        dbHelper = new DatabaseHelper(this);

        loadQuestions();

        listView.setOnItemClickListener(this);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        questionId = questionList.get(position).getId();

        Toast.makeText(this, "L'id de la question est : " + String.valueOf(questionId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questions_actions_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addQuestion:

                addQuestion();

                break;
            case R.id.modifyQuestion:

                modifyQuestion();

                break;
            case R.id.deleteQuestion:

                questionDelete();
                break;
            default:
        }
        return true;
    }

    private void modifyQuestion() {

        // Instanciation de la nouvelle intention
        intention = new Intent(this, QuestionFormActivity.class);
        intention.putExtra("questionAnswerId", String.valueOf(questionId.longValue()));
        startActivityForResult(intention, QUESTION_FORM_ACTIVITY);
    }

    private void addQuestion() {
        // Instanciation de la nouvelle intention
        intention = new Intent(this, QuestionFormActivity.class);

        startActivityForResult(intention, QUESTION_FORM_ACTIVITY);


    }

    private void loadQuestions() {

        // récupération de la liste des questions depuis la base de données

        questionList = dbHelper.findAllQuestions();
        // Conversion en list de String
        listData = new ArrayList<>();
        for (Question item : questionList) {
            listData.add(item.getText());
        }

        // Création de l'arayAdapter
        ArrayAdapter<String> totoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

        // Récupération de l liste view de l'activité

        listView = findViewById(R.id.questionListView);

        // Liaison entte la liste et la source de Data
        listView.setAdapter(totoAdapter);

    }

    private void questionDelete() {
        if (questionId != null) {
            dbHelper.delete(questionId);
            // Raffaichissement de la liste
            loadQuestions();
        }


    }
}
