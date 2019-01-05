package com.application.hinduarchive.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.application.hinduarchive.dao.DataScrapDaoImpl;

@Service
public class DataScrapServiceImpl implements DataScrapServiceInterface{

	@Autowired
	DataScrapDaoImpl dataScrapDaoImpl;
	
	
	public Map<Boolean,ArrayList<String>> searchAuthor(String date,String search) {
		return dataScrapDaoImpl.searchAuthor(date,search);									//calling method in dao and passing the search query parameters
	}
	
	public  ArrayList<String> searchArticleByAuthor(String date,String author) {
		return dataScrapDaoImpl.searchArticleByAuthor(date,author);							////calling method in dao and passing the search query parameters
	}
}
