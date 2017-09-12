package com.development.mesquita.agendaapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by augusto on 25/08/17.
 */

public class JCourse implements Serializable {
    private String name;
    private String date;
    private List<String> topics;

    public JCourse(String name, String date, List<String> topics) {
        this.name = name;
        this.date = date;
        this.topics = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return name;
    }
}
