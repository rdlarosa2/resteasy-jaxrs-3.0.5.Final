package com.restfully.shop.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CartTwo
{
   public int idCart;
   public Item[] items = null ;
   public int numItems = 0 ;
   

   public CartTwo(int anIdCart, int size) {	   
      this.idCart = anIdCart;	
      items = new Item[size] ;
   }

   public int getIdCart()
   {
      return idCart;
   }

   public void setIdCart(int anIdCart)
   {
      this.idCart = anIdCart;
   }
   
   public void addItem(String strIdArticle, Integer aQuantity) {   
	   if ( numItems<items.length ) {
	      Item item = new Item(strIdArticle, aQuantity.intValue() ); 
	      items[numItems] = item ; 
	      numItems++ ;		   
	   }	   
   }
   
}
