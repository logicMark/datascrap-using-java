package com.application.hinduarchive.service;

import java.util.ArrayList;
import java.util.Map;


public interface DataScrapServiceInterface {
public Map<Boolean, ArrayList<String>> searchAuthor(String data,String search);
public ArrayList<String> searchArticleByAuthor(String date,String author);
}
