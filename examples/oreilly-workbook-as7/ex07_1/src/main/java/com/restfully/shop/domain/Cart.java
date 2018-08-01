package com.restfully.shop.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart
{
   private int idCart;
   private Map<String, Integer> items = new ConcurrentHashMap<String, Integer>(); 
   

   public Cart(int anIdCart) {	   
      this.idCart = anIdCart;	   
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
	  Integer currentQuantity =null; 
	  if ( items.containsKey(strIdArticle) ) {
		  currentQuantity = items.get(strIdArticle);
		  currentQuantity = new Integer( currentQuantity.intValue() +  aQuantity.intValue() ) ;
		  items.put(strIdArticle, currentQuantity);
	  }  
	  else {
		 items.put(strIdArticle, aQuantity); 
	  }
   }
   
   public Map<String, Integer> getItems() {
	  return items ; 
   }
}
