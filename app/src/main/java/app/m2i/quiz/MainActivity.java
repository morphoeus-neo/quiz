package app.m2i.quiz;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.m2i.quiz.model.Question;


public class MainActivity extends Activity implements View.OnClickListener {

    // Définition de Constante // parcequ'elle est finale de classe // parcequ'elle est static

    private static final String TAG = "MainActivity";

    // Définition des variables globales boutons
    private Button prevButton;
    private Button nextButton;
    private Button trueButton;
    private Button falseButton;
    private String buttonValue;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        // Gestion des evenements sur les boutons
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        // Remplissage de la liste de questions
        questionList = new ArrayList<>();
        questionList.add(new Question("Peux-t'on manger du plastique", false));
        questionList.add(new Question("Est-ce que Jean Marc a réussi a faire parler Android  ", false));
        questionList.add(new Question("Est-ce que  ???", true));
        questionList.add(new Question("Est-ce que  ???", true));
        questionList.add(new Question("Est-ce que  ???", true));


        // Restauration de l'état de l'application
        // CQI = currentQuestionIndex
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("CQI");
        }

        // On appelle la question pour son affichage
        showQuestion();
    }

    private void showQuestion() {

        // On définit la question en cours grace a l'index de la question du tableau de question
        currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getText());

        // Affichage du Ratio de navigation dans les questions

        String navigationText = (currentQuestionIndex + 1) + " / " + questionList.size();
        navTextView.setText(navigationText);

        // Lecture de la question avec la synthèse vocale
        textToSpeech.speak(currentQuestion.getText(), TextToSpeech.QUEUE_FLUSH, null);

        // Affichage du résultat de la réponse
        showQuestionResult();

        // gestion de l'affichage des boutons
        trueButton.setEnabled(!currentQuestion.isAnswered());
        falseButton.setEnabled(!currentQuestion.isAnswered());
        prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questionList.size() - 1);

    }


    @Override
    /**
     * Gestion de l'action des boutons
     */
    public void onClick(View v) {


        if (v == falseButton) {
            checkAnswer(false);
            buttonValue = "Le bouton Faux";

        } else if (v == trueButton) {
            checkAnswer(true);
            buttonValue = "Le bouton Vrai";

        } else if (v == prevButton) {

            goToPreviousQuestion();
        } else if (v == nextButton) {

            goToNextQuestion();
        }


    }

    private void goToPreviousQuestion() {

        if (currentQuestionIndex > 0) {
            prevButton.setEnabled(false);
            currentQuestionIndex--;
            showQuestion();
        }

    }

    private void goToNextQuestion() {

        if (currentQuestionIndex < questionList.size()) {
            nextButton.setEnabled(false);
            currentQuestionIndex++;
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
            message = buttonValue + " Es la Bonne réponse";

            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            message = buttonValue + " Es la Mauvaise réponse";

            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
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


}
