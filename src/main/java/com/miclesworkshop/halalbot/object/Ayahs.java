package com.miclesworkshop.halalbot.object;

public class Ayahs {

    private String textFR, textAR, textEN;

    public Ayahs(String textFR, String textAR, String textEN) {
        this.textFR = textFR;
        this.textAR = textAR;
        this.textEN = textEN;
    }

    public String getTextFR() {
        return textFR;
    }

    public String getTextAR() {
        return textAR;
    }

    public String getTextEN() {
        return textEN;
    }

    public void setTextAR(String textAR) {
        this.textAR = textAR;
    }

    public void setTextEN(String textEN) {
        this.textEN = textEN;
    }

    public void setTextFR(String textFR) {
        this.textFR = textFR;
    }
}
