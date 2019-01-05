package com.application.hinduarchive.dao;

import java.util.ArrayList;
import java.util.Map;



public interface DataScrapDaoInterface {
	public Map<Boolean, ArrayList<String>> searchAuthor(String data,String search);
	public ArrayList<String> searchArticleByAuthor(String date,String author);
}
