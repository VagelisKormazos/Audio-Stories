package com.unipi.kormazos.unipi_audio_stories;

public class Item {
    String bookName;
    String information;
    String mainStory;
    int image;
    int clickCount;

    public Item() {}

    public Item(String bookName, String information, String mainStory, int image, int clickCount) {
        this.bookName = bookName;
        this.information = information;
        this.mainStory = mainStory;
        this.image = image;
        this.clickCount = clickCount;
    }

    public Item(String bookName, String information, int image, int clickCount) {
        this.bookName = bookName;
        this.information = information;
        this.image = image;
        this.clickCount = clickCount;
    }

    public String getMainStory() {
        return mainStory;
    }

    public void setMainStory(String mainStory) {
        this.mainStory = mainStory;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getClickCount() {  return clickCount;  }

    public void incrementClickCount() {  clickCount++;}
}