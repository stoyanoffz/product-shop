package org.roza.domain.enums;

public enum UserSecretQuestion {

    CITY_BORN("In what city were you born?"),
    FIRST_PET("What was your first pet?"),
    CHILDHOOD_NICKNAME("What was your childhood nickname?"),
    FIRST_CAR("What was the model of your first car?"),
    MOTHERS_MAIDEN_NAME("What is your mother's maiden name?"),
    FATHERS_MIDDLE_NAME("What was your father's middle name?");

    private String question;

    public String getQuestion() {
        return this.question;
    }

    UserSecretQuestion(String action) {
        this.question = action;
    }
}

