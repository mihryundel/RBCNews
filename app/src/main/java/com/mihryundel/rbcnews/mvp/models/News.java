package com.mihryundel.rbcnews.mvp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Entity
public class News {

    @PrimaryKey
    @ColumnInfo(name = "url")
    String url;

    String title;

    String time;

    String imageUrl;

    String subcategory;

    public News() {

    }

    @Ignore
    public News(Element newsElement) {
        this.url = newsElement.getElementsByAttribute("href").get(0).attributes().get("href");

        if (newsElement.getElementsByClass("item__title yes-injects").hasText()) {
            this.title = newsElement.getElementsByClass("item__title yes-injects").text();
        }
        else {
            this.title = newsElement.getElementsByClass("item__title").text();
        }

        this.time = newsElement.getElementsByClass("item__info").text();

        try {
            String imageUrl = newsElement.getElementsByClass("item__image").get(0).attributes().get("src");
            if (imageUrl != null) {
                this.imageUrl = imageUrl;
            }
        }
        catch (IndexOutOfBoundsException e) {

        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
