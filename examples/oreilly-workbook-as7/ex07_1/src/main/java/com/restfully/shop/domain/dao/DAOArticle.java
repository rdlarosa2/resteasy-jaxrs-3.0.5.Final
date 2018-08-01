package com.restfully.shop.domain.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileNotFoundException;

import com.restfully.shop.domain.Article;

public class DAOArticle
{
	public DAOArticle() {
	}
	
	public static Article loadArticle(String idArticle) 
	{		   
	   try {
		   URL getUrl = new URL("http://challenge.getsandbox.com/articles/" + idArticle );
		   BufferedReader reader = null ;
		   HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		   connection.setRequestMethod("GET");
		   System.out.println("Content-Type: " + connection.getContentType());
	
		   reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		   String line = reader.readLine();
		   StringBuffer buffer = new StringBuffer(line);
		   
		   while (line != null) {
		         System.out.println(line);
		         line = reader.readLine();
		         buffer.append(line);
		   }
		   Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
		   connection.disconnect();
		   
		   JSONObject obj = new JSONObject( buffer.toString() );
		   
		   String id = obj.getString("id");
		   String title = obj.getString("title");
		   String price = obj.getString("price");

		   return new Article( id, title, price);		   
	   }
	   catch (FileNotFoundException fnfExc) {
		  System.err.println("The exception FileNotFoundException was catched ***"); 
		  System.out.println("The exception FileNotFoundException was catched ***");
	   }
	   catch (Exception exc) {
		  System.err.println("The exception Exception was catched ***"); 
		  System.out.println("The exception Exception was catched ***");
	   }
   
	   return null ;	
	}	
	
	public static boolean verifyExistingArticle(String idArticle)
	{
		Article article = loadArticle(idArticle);
		
		System.out.println("verifyExistingArticle @@@ >" + ( article!=null ) );
		
		return ( article!=null );
	}
	
	public static Map<String,Article> loadArticles()
	{
		/*
		JSONObject obj = new JSONObject(str);
		JSONArray arr = obj.getJSONArray("number");
		for (int i = 0; i < arr.length(); i++) {
		    System.out.println(arr.getInt(i));
	    }
		*/
		Map<String, Article> articlesDB = new ConcurrentHashMap<String,Article>();
		
		Article article = new Article("1", "Bannana", "2.50") ;	
		articlesDB.put( article.getId() , article ); 
		
		article = new Article("2", "Apple", "3.20") ; 
		articlesDB.put( article.getId() , article );		
		
		article = new Article("3","Cookies", "10.40") ; 
		articlesDB.put( article.getId() , article );		
		
		
		article = new Article("4", "Noodles", "23.50") ; 
		articlesDB.put( article.getId() , article );		
		
		article = new Article("5", "Olive Oil", "13.00") ; 
		articlesDB.put( article.getId() , article );		
		
		article = new Article("6", "Water", "0.50") ; 
		articlesDB.put( article.getId() , article );		
		
		article = new Article("7", "Beer", "1.50") ; 
		articlesDB.put( article.getId() , article );		
		
		article = new Article("8", "Vodka", "10.50") ; 
		articlesDB.put( article.getId() , article );		

		article = new Article("9", "Bread", "0.20") ; 
		articlesDB.put( article.getId() , article );		

		article = new Article("10", "Grapes", "0.50") ; 
		articlesDB.put( article.getId() , article );		

		article = new Article("11", "Rice", "3.50") ;
		articlesDB.put( article.getId() , article );		

		article = new Article("12", "Pizza", "13.10") ; 
		articlesDB.put( article.getId() , article );		
		
	    return articlesDB;		
	}			
}	