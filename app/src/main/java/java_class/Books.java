package java_class;

import android.graphics.drawable.Drawable;

/**
 * Created by shafiz on 5/30/2017.
 */

public class Books {
    private String title;
    private String published_date;
    private double rating;
    private String description;
    private String publisher;
    private String imagePath;

    private Drawable tempImage;

    public Books(String title, String published_date, double rating, String description, String publisher, String imagePath) {
        this.title = title;
        this.published_date = published_date;
        this.rating = rating;
        this.description = description;
        this.publisher = publisher;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Drawable getTempImage() {
        return tempImage;
    }

    public void setTempImage(Drawable tempImage) {
        this.tempImage = tempImage;
    }
}
