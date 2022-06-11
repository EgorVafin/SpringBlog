package com.example.demo.dao;

public interface PostProjection {

    Integer getId();

    Long getPostTimestamp();

    String getTitle();

    String getText();

    Integer getUserId();

    String getUserName();

    Integer getCommentsCount();

    Integer getViewCount();

    Integer getLikesCount();

    Integer getDislikesCount();

}
