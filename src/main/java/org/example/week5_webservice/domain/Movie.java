package org.example.week5_webservice.domain;

public class Movie {
    private Long id;
    private String title;
    private String director;
    private int rating;
    private int pubyear;
    private String  category;

    public Movie(Long id, String title, String director, int rating, int pubyear, String category) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.rating = rating;
        this.pubyear = pubyear;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getdirector() {
        return director;
    }

    public void setdirector(String director) {
        this.director = director;
    }

    public int getrating() {
        return rating;
    }

    public void setrating(int rating) {
        this.rating = rating;
    }

    public int getPubyear() {
        return pubyear;
    }

    public void setPubyear(int pubyear) {
        this.pubyear = pubyear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
