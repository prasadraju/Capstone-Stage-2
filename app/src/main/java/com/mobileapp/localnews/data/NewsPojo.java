package com.mobileapp.localnews.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Venkatesh on 2/25/2017.
 */

public class NewsPojo implements  Serializable{


    List<Inner> articles;

    public List<Inner> getArticles() {
        return articles;
    }

    public void setArticles(List<Inner> articles) {
        this.articles = articles;
    }

    public static class Inner implements Serializable {

        String author;
        String title;
        String description;
        String url;
        String urlToImage;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        String publishedAt;

    }
}