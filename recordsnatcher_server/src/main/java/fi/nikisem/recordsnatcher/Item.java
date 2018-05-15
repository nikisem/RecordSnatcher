package fi.nikisem.recordsnatcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {

    private String title;
    private String image;
    private String link;
    private String itemID;
    private String price;
    private String source;
    private LocalDateTime timeStamp;
    private String timeStampString;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm / dd.MM.yyyy");

    public Item(String title, String image, String link, String itemID, String price, String source, LocalDateTime timeStamp) {

        this.title = title;
        this.image = image;
        this.link = link;
        this.itemID = itemID;
        this.price = price;
        this.source = source;
        this.timeStamp = timeStamp;
        this.timeStampString = timeStamp.format(formatter);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

    public void setTimeStampString(String timeStampString) {
        this.timeStampString = timeStampString;
    }
}