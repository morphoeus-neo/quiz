package app.m2i.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.m2i.quiz.model.Question;


public class MainActivity extends Activity implements View.OnClickListener{

    // Constante ( parcequ'elle est finale de classe ( parcequ'elle est static))
    //
    private static final String TAG = "MainActivity";

    private Question currentQuestion;
    private TextView questionTextView;
    private int currentQuestionIndex = 0;
    private Button trueButton;
    private Button falseButton;
    private List<Question> questionList;

    private Button prevButton;
    private Button nextButton;
    private TextView navTextView;
    private TextView resultMessage ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Instanciation de la question
        //currentQuestion = new Question("Java c'est prise de tête ?", true);
        // Affichage de la question
        questionTextView = findViewById(R.id.questionLabel);
        resultMessage = findViewById(R.id.resultLabel);

        //questionTextView.setText(currentQuestion.getText());

        // Récupération des références aux bouttons
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);

        // Récupération des références des boutons suivant et precedents ainsi que le texte pour le Ratio
        prevButton = findViewById((R.id.previousButton));
        nextButton = findViewById(R.id.forwardButton);
        navTextView= findViewById(R.id.questionRatio);


        // Gestion des evenements sur les boutons
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        questionList =new ArrayList<>();
        questionList.add(new Question("Peux-t'on manger du plastique",false));
        questionList.add(new Question("Est-ce que Maxime est toujours à l'heure",false));
        questionList.add(new Question("Est-ce Maxime aimes les hommes ???",true));
        questionList.add(new Question("Est-ce Maxime aimes les femmes ???",true));
        questionList.add(new Question("Est-ce Maxime mange les hommes ???",true));



        // Restauration de l'état de l'application
        // CQI = currentQuestionIndex
        if (savedInstanceState != null){
            currentQuestionIndex = savedInstanceState.getInt("CQI");
        }



        // On appelle la question pour son affichage
        showQuestion();


    }

    private void showQuestion(){
        // On définit la question en cours grace a l'index de la question du tableau de question
        currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getText());

        // Affichage du Ration de navigation dans les questions

        String navigationText = (currentQuestionIndex +1) + " / " + questionList.size();
        navTextView.setText(navigationText);

    }




    @Override
    public void onClick(View v) {



        if (v == falseButton){
            checkAnswer(false);
        }else if (v == trueButton){
            checkAnswer(true);
        } else if (v == prevButton){
            resultMessage.setText("");
            goToPreviousQuestion();
        } else if (v == nextButton){
            resultMessage.setText("");
            goToNextQuestion();
        }


    }

    private void goToPreviousQuestion() {

        if (currentQuestionIndex > 0){
            currentQuestionIndex --;
            showQuestion();
        }

    }

    private void goToNextQuestion() {

        if (currentQuestionIndex < questionList.size()){
            currentQuestionIndex ++;
            showQuestion();
        }

    }


    private void checkAnswer(boolean b) {
        String message;

        if (b == currentQuestion.getGoodAnswer()){
            message = "Bonne réponse";
        }else {
            message = "Mauvaise réponse";
        }
        // Affichage du message


        resultMessage.setText(message);
    }


    public void onStart(){
        // on appel le parent de onStart
        super.onStart();
        Log.d(TAG, "onStart");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    public void onBackPressed(){
        super.onBackPressed();
        Log.d(TAG, "onBackPressed");
    }
    public void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }


    // Sauvegarde de l'état de l'application
    public void onSaveInstanceState(Bundle savedInstanceState){
        // Sauvegarde de l'index en cours
        // CQI = currentQuestionIndex
        savedInstanceState.putInt("CQI", currentQuestionIndex);

        super.onSaveInstanceState(savedInstanceState);

    }




}
