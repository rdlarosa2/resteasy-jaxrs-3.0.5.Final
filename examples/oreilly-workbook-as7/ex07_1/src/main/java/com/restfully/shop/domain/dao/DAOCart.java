package com.restfully.shop.domain.dao;

import java.util.Map ;
import java.util.Set ; 
import java.util.Iterator ;
import java.util.concurrent.ConcurrentHashMap;

import com.restfully.shop.domain.Article;
import com.restfully.shop.domain.Cart;
import com.restfully.shop.domain.CartTwo;
import com.restfully.shop.domain.exception.ArticleNotFoundException ;

public class DAOCart
{
	public DAOCart() {
	}
	
	public static CartTwo loadCartTwo(Cart aCart)
	{
		Map<String, Integer> items = aCart.getItems();
		
		CartTwo cartTwo = new CartTwo( aCart.getIdCart(), items.size() ) ;
		
		Set<String> keys = items.keySet(); 
		Iterator<String> it = keys.iterator();
		String idArticle = null ; 
		Integer quantity = null ;
		
		while ( it.hasNext() ) {
			idArticle = it.next() ;
			quantity = items.get(idArticle);
			cartTwo.addItem(idArticle, quantity);
		}
		
	    return cartTwo;		
	}	
	
	public static Double getTotalAmount(Cart aCart, Map<String, Article> articles) throws ArticleNotFoundException
	{
		Article article = null ;
		double priceArticle = 0.0 ;
		double dblTotalAmount = 0.0 ;
		
		Map<String, Integer> items = aCart.getItems();
		
		Set<String> keys = items.keySet(); 
		Iterator<String> it = keys.iterator();
		String idArticle = null ; 
		Integer quantity = null ;
		
		while ( it.hasNext() ) {
			idArticle = it.next() ;
			quantity = items.get(idArticle);
			System.out.println("DAOCart:getTotalAmount: Before calculating price to article " + idArticle );

			try {			
			
				if ( DAOArticle.verifyExistingArticle(idArticle) ) {
					
					System.out.println("DAOCart:getTotalAmount: In true branch");
					
					article = DAOArticle.loadArticle(idArticle);

					System.out.println("DAOCart:getTotalAmount: After DAOArticle.loadArticle - article!=null: " + (article!=null) );					
					
					priceArticle = Double.parseDouble( article.getPrice() ) ; 
					
					System.out.println("DAOCart:getTotalAmount: After Double.parseDouble - priceArticle: " + priceArticle );
					
					dblTotalAmount += ( priceArticle * (double)quantity.intValue() ) ;
					
					System.out.println("DAOCart:getTotalAmount: After calculating dblTotalAmount: " + dblTotalAmount );
				} 
				else {
					System.out.println("DAOCart:getTotalAmount: Before ArticleNotFoundException()");					
					throw new ArticleNotFoundException() ;
				}
			}
			catch (Exception exc) {
			   exc.printStackTrace();
			   return null ;	
			}

		}
		return new Double(dblTotalAmount) ; 
	}
	
	
	
	
}	