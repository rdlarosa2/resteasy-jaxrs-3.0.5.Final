package com.restfully.shop.domain;

public class Item
{
   private String idArticle;
   private int quantity;   

   public Item(String anIdArticle, int aQuantity) {
      this.idArticle = anIdArticle;	  
	  this.quantity = aQuantity;
   }
   
   public String getIdArticle()
   {
      return this.idArticle;
   }

   public void setIdArticle(String anIdArticle)
   {
      this.idArticle = anIdArticle;
   }   
   
   public int getQuantity()
   {
      return this.quantity;
   }

   public void setQuantity(int aQuantity)
   {
      this.quantity = aQuantity;
   }   
   
}
