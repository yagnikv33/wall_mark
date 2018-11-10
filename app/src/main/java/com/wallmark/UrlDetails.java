package com.wallmark;

public class  UrlDetails {
    private String url,id,name;

    public UrlDetails() {
    }

    UrlDetails(String url, String id, String name) {
        this.url = url;
        this.id = id;
        this.name = name;
    }

    String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}