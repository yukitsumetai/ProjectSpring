package com.telekom.exampleTopic;


import javax.inject.Named;

@Named
public class ExampleBean {

    private String text="My example";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
