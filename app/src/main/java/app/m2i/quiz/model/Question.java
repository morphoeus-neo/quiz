package app.m2i.quiz.model;

import java.util.UUID;

/**
 * Created by tzz5md on 31/10/2017.
 */

public class Question {

    String text;
    UUID id;
    Boolean goodAnswer;
    Boolean userAnswer;

    public Question() {
    }

    /**
     *
     * @param text
     * @param id
     * @param goodAnswer
     * @param userAnswer
     */
    public Question(String text, UUID id, Boolean goodAnswer, Boolean userAnswer) {
        this.text = text;
        this.id = id;
        this.goodAnswer = goodAnswer;
        this.userAnswer = userAnswer;
    }

    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * @param goodAnswer
     */
    public Question(String text, Boolean goodAnswer) {
        this.text = text;
        this.goodAnswer = goodAnswer;
        // Lorsque je fait appel a ce constructeur je dois retourner un UUID
        this.id = UUID.randomUUID();
    }

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
}