package com.example.nawar.quizes.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Result implements Parcelable {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private List<String> answers;

    public Result(Parcel in) {
        category = in.readString();
        type = in.readString();
        difficulty = in.readString();
        question = in.readString();
        correctAnswer = in.readString();
        if (in.readByte() == 0x01) {
            answers = new ArrayList<String>();
            in.readList(answers, String.class.getClassLoader());
        } else {
            answers = null;
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeString(question);
        dest.writeString(correctAnswer);
        if (answers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(answers);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}