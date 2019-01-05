package com.application.hinduarchive;
import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
public class QueryReductionCheck {	//class to store the author details at the server-side to avoid too many requests to the archive-site 
protected static Map<LocalDate,ArrayList<String>> map=new HashMap<LocalDate,ArrayList<String>>();	

}
