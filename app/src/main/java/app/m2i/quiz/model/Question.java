package app.m2i.quiz.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by tzz5md on 31/10/2017.
 */

public class Question {

    private String text;
    private UUID id;
    private Boolean goodAnswer;
    private Boolean userAnswer;


    // Constructeur Vide
    public Question() {
    }


    // constructeur complet
    public Question(String text, UUID id, Boolean goodAnswer, Boolean userAnswer) {
        this.text = text;
        this.id = id;
        this.goodAnswer = goodAnswer;
        this.userAnswer = userAnswer;
    }


    public String getText() {
        return text;
    }


    public Question(String text, Boolean goodAnswer) {
        this.text = text;
        this.goodAnswer = goodAnswer;
        // Lorsque je fait appel a ce constructeur je dois retourner un UUID
        this.id = UUID.randomUUID();
    }

    // Définition des guetteurs et Setteurs

    public void setText(String text) {
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(Boolean goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public Boolean getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Boolean userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isAnswered(){return userAnswer != null; }
}
