package com.restfully.shop.domain;

public class Article
{
   private String id;
   private String title;
   private String price;
   

   public Article(String anId, String aTitle, String aPrice) {
	   
      this.id = anId;	   
      this.title = aTitle;	  
      this.price = aPrice;
   }

   public String getId()
   {
      return id;
   }

   public void setId(String anId)
   {
      this.id = anId;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String aTitle)
   {
      this.title = aTitle;
   }   
   
   public String getPrice()
   {
      return price;
   }

   public void setPrice(String aPrice)
   {
      this.price = aPrice;
   }   
}
