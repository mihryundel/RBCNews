package com.mihryundel.rbcnews.mvp.models;

import org.jsoup.nodes.Element;

public class AutoNews extends News {

    public AutoNews(Element newsElement) {
        this.url = newsElement.getElementsByAttribute("href").get(0).attributes().get("href");

        if (newsElement.getElementsByClass("item-big__title").hasText()) {
            this.title = newsElement.getElementsByClass("item-big__title").text();
        }
        else {
            this.title = newsElement.getElementsByClass("item-medium__title").text();
        }

        if (newsElement.getElementsByClass("item-big__date").hasText()) {
            this.time = newsElement.getElementsByClass("item-big__date").text();
        } else {
            this.time = newsElement.getElementsByClass("item-medium__date").text();
        }

        if (newsElement.getElementsByClass("item-big__category").hasText()) {
            this.subcategory = newsElement.getElementsByClass("item-big__category").text();
        }
        else {
            this.subcategory = newsElement.getElementsByClass("item-medium__category").text();
        }

        this.imageUrl = newsElement.getElementsByAttribute("src").get(0).attributes().get("src");
    }
}
