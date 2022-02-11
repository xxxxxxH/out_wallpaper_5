package net.basicmodel.model;

import java.io.Serializable;

public class DataEntity implements Serializable {
    String height;
    String img_category;
    String img_like;
    String img_tag;
    String img_thumb_url;
    String img_title;
    String img_url;
    String thumb_height;
    String thumb_width;
    String width;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImg_category() {
        return img_category;
    }

    public void setImg_category(String img_category) {
        this.img_category = img_category;
    }

    public String getImg_like() {
        return img_like;
    }

    public void setImg_like(String img_like) {
        this.img_like = img_like;
    }

    public String getImg_tag() {
        return img_tag;
    }

    public void setImg_tag(String img_tag) {
        this.img_tag = img_tag;
    }

    public String getImg_thumb_url() {
        return img_thumb_url;
    }

    public void setImg_thumb_url(String img_thumb_url) {
        this.img_thumb_url = img_thumb_url;
    }

    public String getImg_title() {
        return img_title;
    }

    public void setImg_title(String img_title) {
        this.img_title = img_title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(String thumb_height) {
        this.thumb_height = thumb_height;
    }

    public String getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(String thumb_width) {
        this.thumb_width = thumb_width;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
