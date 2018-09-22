package com.example.pal.newsfeedapp;

public class News {
    String category;
    String newsTitle;
    String newsUrl;
    String authorName;
    String date;

    public News(String category, String newsTitle, String newsUrl, String authorName, String date) {
        this.category = category;
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.authorName = authorName;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
