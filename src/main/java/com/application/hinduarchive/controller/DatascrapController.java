package com.application.hinduarchive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.application.hinduarchive.service.DataScrapServiceImpl;

@RestController
public class DatascrapController {

	@Autowired
	DataScrapServiceImpl dataScrapServiceImpl;
	/*
	 * Method searchAuthor accepts two String parameters which are parsed later in the dao and returns the list of authors and author existence.
	 */
	@RequestMapping(value="/getScrap",method=RequestMethod.GET)
	public @ResponseBody Map<String,ArrayList<String>> searchAuthor(@RequestParam("date") String date,@RequestParam("search") String search){
		Map<String,ArrayList<String>> returnMap=new HashMap<String,ArrayList<String>>();
		Map<Boolean,ArrayList<String>> map=dataScrapServiceImpl.searchAuthor(date,search);
		if(map.containsKey(true))
		{
			returnMap.put("Author found and the list is ", map.get(true));	//Getting the value of Authors ArrayList and setting in the map
		}
		else
		{
			returnMap.put("Author not found in the list ", null );			//Getting the value of Authors ArrayList and setting in the map
		}
	
		return returnMap;
	}
	
	/*
	 * Method searchArticleByAuthor accepts two String parameters which are parsed later in the dao and returns the written articles by the author
	 */
	@RequestMapping(value="/getArticles",method=RequestMethod.GET)
	public @ResponseBody ArrayList<String> searchArticleByAuthor(@RequestParam("date") String date,@RequestParam("author") String author)
	{
		return dataScrapServiceImpl.searchArticleByAuthor(date,author);
	}
	
	
}
 