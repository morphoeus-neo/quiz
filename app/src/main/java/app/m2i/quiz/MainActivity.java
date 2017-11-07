package app.m2i.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.m2i.quiz.model.DatabaseHelper;
import app.m2i.quiz.model.Question;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Définition de Constante // parcequ'elle est finale de classe // parcequ'elle est static

    private static final String TAG = "MainActivity";
    private static final int SOLUTION_ACTIVITY = 1;
    private static final int QUESTION_MANAGEMENT_ACTIVITY = 2;


    // Définition des variables globales boutons
    private Button prevButton;
    private Button nextButton;
    private Button trueButton;
    private Button falseButton;
    private Button solutionButton;


    // Définition des variables globales Text
    private TextView questionTextView;
    private TextView navTextView;
    private TextView resultMessage;

    // Définition des variables globales List
    private List<Question> questionList;

    // Définition des variables globales int
    private int currentQuestionIndex = 0;

    // Définition des variables globales Question
    private Question currentQuestion;

    // Déclaration de la variable globale TextToSpeech
    TextToSpeech textToSpeech;

    // Définition du spinner
    private Spinner questionSpinner;

    // Déclaration du chargement des solutions
    private Intent intention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Récupération des text
        questionTextView = findViewById(R.id.questionLabel);
        resultMessage = findViewById(R.id.resultLabel);
        navTextView = findViewById(R.id.questionRatio);

        // Récupération des références aux bouttons
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        prevButton = findViewById((R.id.previousButton));
        nextButton = findViewById(R.id.forwardButton);
        solutionButton = findViewById(R.id.solutionButton);

        //Référence au widget Spinner
        questionSpinner = (Spinner) findViewById(R.id.spinner);

        //Gestion de l'évennement sur le Spinner
        questionSpinner.setOnItemSelectedListener(this);

        // Gestion des evenements sur les boutons
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        solutionButton.setOnClickListener(this);

        // Remplissage de la liste de questions
        questionList = new ArrayList<>();
        questionList.add(new Question("Est-ce que l'application a demarré ???", true));

        questionList.add(new Question("Est-ce que Guillaume mange 5 fruits et légumes par jour ???", false));
        questionList.add(new Question("Est-ce que Guillaume aime la 1ère ???  ", false));
        questionList.add(new Question("Est-ce que Guillaume passes prendre un café a la fin de sa garde  ???", true));
        questionList.add(new Question("Est-ce que Guillaume va manger des fillets de harangs ce midi ???", true));
        questionList.add(new Question("Est-ce que cette application c'est de la merde ???", true));


        // Instanciation de la classe TextToSpeetch
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            /**
             * Déclaration d'une classe interne
             */
            public void onInit(int status) {
                // on teste si le status ( argument onInit) est egal a "Succes"
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.FRENCH);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "La synthèse vocale n'est pas disponible",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Restauration de l'état de l'application
        // CQI = currentQuestionIndex
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("CQI");
        }

        // On appelle la question pour son affichage
        showQuestion();


    }

    private void showQuestion() {

        // Définition du Spinner
        // Création de la liste des questions du spinner
        String[] spinnerData = new String[questionList.size()];
        for (int i = 0; i < questionList.size(); i++) {
            // récupération de la question en cours dans la boucle
            Question question = questionList.get(i);
            String spinnerText = "Question" + (i + 1) + " ";
            if (question.isAnswered()) {
                spinnerText += "(répondu)";
            }
            spinnerData[i] = spinnerText;
        }

        // Création de l'arrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerData);
        //Définition de la vue lorsque le spinner est ouvert
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // On Attache l'adapter au spinner
        questionSpinner.setAdapter(adapter);
        // permet de changer de question car sas cette ligne on est bloqué à la question 1
        questionSpinner.setSelection(currentQuestionIndex);

        // On définit la question en cours grace a l'index de la question du tableau de question
        currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getText());

        // Affichage du Ratio de navigation dans les questions

        String navigationText = (currentQuestionIndex + 1) + " / " + questionList.size();
        navTextView.setText(navigationText);


        // Affichage du résultat de la réponse
        showQuestionResult();

        // gestion de l'affichage des boutons
        trueButton.setEnabled(!currentQuestion.isAnswered());
        falseButton.setEnabled(!currentQuestion.isAnswered());
        prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questionList.size() - 1);

        // Lecture de la question avec la synthèse vocale
        textToSpeech.speak(currentQuestion.getText(), TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    /**
     * Gestion de l'action des boutons
     */
    public void onClick(View v) {


        if (v == falseButton) {
            checkAnswer(false);
        } else if (v == trueButton) {
            checkAnswer(true);
        } else if (v == prevButton) {
            goToPreviousQuestion();
        } else if (v == nextButton) {
            goToNextQuestion();
        } else if (v == solutionButton) {
            // Instanciation de la nouvelle intention
            intention = new Intent(this.getBaseContext(), SolutionActivity.class);

            //Transfert d'informations vers l'activité Solution

            intention.putExtra("questionIndex", currentQuestionIndex);
            intention.putExtra("questionAnswer", currentQuestion.getGoodAnswer());
            intention.putExtra("questionAnswerTextValue", currentQuestion.getText());

            startActivityForResult(intention, SOLUTION_ACTIVITY);
        }
    }

    private void goToPreviousQuestion() {

        if (currentQuestionIndex > 0) {
            prevButton.setEnabled(false);
            currentQuestionIndex--;
            questionSpinner.setSelection(currentQuestionIndex, true);
            showQuestion();
        }

    }

    private void goToNextQuestion() {

        if (currentQuestionIndex < questionList.size()) {
            nextButton.setEnabled(false);
            currentQuestionIndex++;
            questionSpinner.setSelection(currentQuestionIndex, true);
            showQuestion();
        }
    }


    private void checkAnswer(boolean userAnswer) {


        currentQuestion.setUserAnswer(userAnswer);
        showQuestionResult();
    }

    private void showQuestionResult() {

        String message;
        Boolean userQuestionResult;
        userQuestionResult = currentQuestion.getUserAnswer();

        // Condition d'affichage du message
        if (userQuestionResult == null) {
            message = "";
        } else if (userQuestionResult == currentQuestion.getGoodAnswer()) {
            message = " Bonne réponse";
        } else {
            message = " Mauvaise réponse";
        }
        // Affichage du message
        resultMessage.setText(message);


    }


    public void onStart() {
        // on appel le parent de onStart
        super.onStart();
        Log.d(TAG, "onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");
    }

    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    // Sauvegarde de l'état de l'application
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Sauvegarde de l'index en cours
        // CQI = currentQuestionIndex
        savedInstanceState.putInt("CQI", currentQuestionIndex);
        super.onSaveInstanceState(savedInstanceState);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Correspondance de la question en cours avec l'index du spinner sur la ligne sélectionnée

        currentQuestionIndex = position;
        // Affichage dans la console de la ligne sélectionnée
        Log.d(TAG, parent.getItemAtPosition(position).toString());
        // Puis on affiche la question dans le spinner
        showQuestion();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode = SOLUTION_ACTIVITY) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option_load_SQL:

                Toast.makeText(getApplicationContext(),
                        "J'ai sélectionné le bouton load Json",
                        Toast.LENGTH_SHORT).show();

                DatabaseHelper dbHelper = new DatabaseHelper(this);

                questionList = dbHelper.findAllQuestions();

                currentQuestionIndex = 0;

                showQuestion();

                break;

            case R.id.option_loadJson:

                Toast.makeText(getApplicationContext(),
                        "J'ai sélectionné le bouton load SQL",
                        Toast.LENGTH_SHORT).show();

                break;
            case R.id.option_AnswerActivity:
                intention = new Intent(this.getBaseContext(), QuestionsManagementActivity.class);


                intention.putExtra("questionAnswerTextValue", currentQuestion.getText());

                startActivityForResult(intention, QUESTION_MANAGEMENT_ACTIVITY);

                break;
        }

        return true;
    }
}
