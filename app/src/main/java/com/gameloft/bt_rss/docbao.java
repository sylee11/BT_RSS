package com.gameloft.bt_rss;

/**
 * Created by Admin on 11/4/2017.
 */

public class docbao {
    public String tittle;
    public  String link;
    public  String image;
    public String pubDate;


    public docbao(String tittle, String link, String image, String pubDate) {
        this.tittle = tittle;
        this.link = link;
        this.image = image;
        this.pubDate = pubDate;

    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getPubday() {
        return pubDate;
    }

    public void setPubday(String pubDate) {
        this.pubDate = pubDate;
    }

}
