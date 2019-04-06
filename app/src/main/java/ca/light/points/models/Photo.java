package ca.light.points.models;

import java.util.ArrayList;

public class Photo {
    public int id;
    public String name;
    public String description;
    public int times_viewed;
    public Double rating;
    public String created_at;
    public int category;
    public boolean privacy;
    public int width;
    public int height;
    public int votes_count;
    public int comments_count;
    public boolean nsfw;
    public ArrayList<PhotoUrl> images;
    public User user;

    public String preferredSize;
    public String preferredUrl;

    public class PhotoUrl {
        public int size;
        public String url;
    }
}
