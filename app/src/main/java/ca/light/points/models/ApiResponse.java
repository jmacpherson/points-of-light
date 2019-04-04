package ca.light.points.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ApiResponse {
    public String feature;
//    HashMap<String, Boolean> filters;
    int current_page;
    int total_pages;
    int total_items;
    ArrayList<Photo> photos;
}
