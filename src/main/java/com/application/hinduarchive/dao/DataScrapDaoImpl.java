package com.application.hinduarchive.dao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;


import com.application.hinduarchive.QueryReductionCheck;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * 
 * DataScrapDaoImpl used webclient class to send the request and gets the HTML code in return which has to be parsed using methods defined in the class and also by XPath,
 * 
 *
 */


@Repository
public class DataScrapDaoImpl extends QueryReductionCheck implements DataScrapDaoInterface {
	public Map<Boolean,ArrayList<String>> searchAuthor(String data,String search) {
		Map<Boolean,ArrayList<String>> returnMap=new HashMap<Boolean,ArrayList<String>>();
		LocalDate localDate=LocalDate.parse(data);												//Parsing the date to java.time.LocalDate
		System.out.println(localDate);
		ArrayList<String> authorNames=new ArrayList<>();
		WebClient client = new WebClient();  													//Required to send the request and to retrieve the result using XPath
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		try {
			if(QueryReductionCheck.map.containsKey(localDate))   								//In order to avoid too many requests to the archive site we are storing the some results at the server-side so,the same result can be sent to the next user for the same request instead of getting the result from archive-site.
			{
				System.out.println("Not queried");													//printing to the console to check that the request is not sent 
			ArrayList<String> authorsAvailable=QueryReductionCheck.map.get(localDate);	//getting the ArrayList value of available authors from the protected static Map variable in QueryReductionCheck 
			System.out.println(authorsAvailable.contains(search));						//searching for the author value in the Arraylist
			returnMap.put(authorsAvailable.contains(search), authorsAvailable);			//using contains method of ArrayList for the author name existence.
			}
			else																	//If the requested data is not stored on the server-side querying the site to get the result	
			{
				System.out.println("queried");										//printing to the console to check that the request is sent. 
			String searchUrl = "https://www.thehindu.com/archive/web/ " +localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth() ;
			HtmlPage page = client.getPage(searchUrl);								//Returns the HTML page code which can be queried using XPath
			List<HtmlAnchor> list1=(List<HtmlAnchor>)page.getByXPath("//a[@class='section-list-heading']"); //selecting all the title names using the class attribute
			String totalArticles[]=new String[list1.size()];
			for(int cnt=0;cnt<list1.size();cnt++)
			{
			  totalArticles[cnt]=list1.get(cnt).asText();							//Storing the title names in the String Array totalArticles
			}
			for(int cnt=0;cnt<totalArticles.length;cnt++) 
			{
			String url=page.getElementById(totalArticles[cnt].toLowerCase()).getFirstChild().getAttributes().getNamedItem("href").getNodeValue();  //Getting each hyper reference of the Article using the methods of webclient class
			HtmlPage getAuthorName=client.getPage(url);						//Request sent to the retrieved href of the article.
			List<HtmlAnchor> list=(List<HtmlAnchor>)getAuthorName.getByXPath("//a[@class='person-name lnk']");		//Selecting all the authors available for that particular article.
			for(int listSize=0;listSize<list.size();listSize++)
			{
			authorNames.add(list.get(listSize).asText());							//Iterating the list and adding author names to the ArrayList. 
			}
			for(HtmlAnchor anchor : list)
			{
			 System.out.println(anchor.asText());									//Printing to the console to check
			}
				
		  }
			QueryReductionCheck.map.put(localDate, authorNames);						//Adding to the static map at the server-side 
			//returnMap.put(arg0, arg1)
		  
			}
		}catch(Exception e){
		  e.printStackTrace(); 
		}finally {
			client.close();
		}
		returnMap.put(authorNames.contains(search),authorNames);					//adding the key and values in the map	
		return returnMap;						
	}


	public ArrayList<String> searchArticleByAuthor(String date,String author)		//This method is to get the author written articles by querying all the authors for all the articles in the webpage and matches with author name entered by the user and gets the articles written
	{
		Map<Boolean,ArrayList<String>> returnMap=new HashMap<Boolean,ArrayList<String>>();
		LocalDate localDate=LocalDate.parse(date);
		System.out.println(localDate);
		
		ArrayList<String> writtenArticles=new ArrayList<>();
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false); 
		try
		{
		String searchUrl = "https://www.thehindu.com/archive/web/ " +localDate.getYear()+"/"+localDate.getMonthValue()+"/"+localDate.getDayOfMonth() ;
		  
		HtmlPage page = client.getPage(searchUrl);
		  List<HtmlAnchor> list1=(List<HtmlAnchor>)page.getByXPath("//a[@class='section-list-heading']");
		  String totalArticles[]=new String[list1.size()];
		  for(int cnt=0;cnt<list1.size();cnt++)
		  {
			  totalArticles[cnt]=list1.get(cnt).asText();
			  System.out.println(totalArticles[cnt]);
		  }
		  for(int cnt=0;cnt<totalArticles.length;cnt++) 
		  {
			 String url=page.getElementById(totalArticles[cnt].toLowerCase()).getFirstChild().getAttributes().getNamedItem("href").getNodeValue();
			 HtmlPage getAuthorName=client.getPage(url);
			 List<HtmlAnchor> list=(List<HtmlAnchor>)getAuthorName.getByXPath("//a[@class='person-name lnk']");
		
			 for(int listSize=0;listSize<list.size();listSize++) 
			 {
				System.out.println(list.get(listSize).asText()+" but searching for "+author);
			 	if(author.equalsIgnoreCase(list.get(listSize).asText())) 
			 	{
			 		writtenArticles.add(totalArticles[cnt]);					//Adding article written by the author
			 		System.out.println("adding article");
			 	}
			 }
		 
				
		  }
		  
		  for(String s : writtenArticles)
		System.out.println("article is "+s);
		  
		}
		catch(Exception e)
		{
			
			return null;
		}
		finally {
			client.close();
		}
	   return writtenArticles;
	
	}
}
 