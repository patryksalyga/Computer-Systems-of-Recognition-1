package org.example;

public class Text {
    private String places;
    private String title;
    private String dateline;
    private String body;

    public Text(String places, String title, String dateline, String body) {
        this.places = places;
        this.title = title;
        this.dateline = dateline;
        this.body = body;
    }

    public String getPlaces() {
        return places;
    }

    public String getTitle() {
        return title;
    }

    public String getDateline() {
        return dateline;
    }

    public String getBody() {
        return body;
    }
}