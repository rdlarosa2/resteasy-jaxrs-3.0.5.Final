package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.domain.Cart;
import com.restfully.shop.domain.CartTwo;
import com.restfully.shop.domain.Article;
import com.restfully.shop.domain.dao.DAOArticle ;
import com.restfully.shop.domain.dao.DAOCart;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.Exception;
import com.restfully.shop.domain.exception.ArticleNotFoundException ;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Path("/customers")
public class SMarketResource
{
   private Map<Integer, Cart> cartDB = new ConcurrentHashMap<Integer, Cart>();   
   private Map<String, Article> articlesDB = null ;   
   private AtomicInteger idCounter = new AtomicInteger();
   
   public SMarketResource()
   {
	   this.articlesDB = DAOArticle.loadArticles();
   }

   @POST
   @Produces("application/json")
   @Path("/createCart")   
   public Response createCart()
   {
	  int idCart = idCounter.incrementAndGet() ;
	  Cart cart = new Cart(idCart);
	  	  
      cartDB.put( cart.getIdCart() , cart );	  

	  System.out.println("Created cart " + cart.getIdCart() );
	  
      String lastVisit = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(new Date());
      URI location = URI.create("/customers/createCart" );	  
      String output = (new Integer(cart.getIdCart())).toString() ;
      return Response.created(location)
              .entity(output)
              .build();
   }   
      
   @POST
   @Produces("application/json")
   @Path("/addItem")   
   public Response addItem(@FormParam("idArticle") String strIdArticle, 
		                   @FormParam("quantity") String strQuantity, 
		                   @FormParam("idCart") String strIdCart)
   {
	  boolean articleExists = false ;	   
	  Boolean result = new Boolean(false) ; 
	  System.out.println("addItem success idArticle " + strIdArticle + " quantity " + strQuantity + " idCart " + strIdCart );
	  int intIdCart = Integer.parseInt(strIdCart); 
      Integer idCart = new Integer(intIdCart);
	  
	  int intQuantity = Integer.parseInt(strQuantity);
	  Integer quantity = new Integer(intQuantity);
	  
	  Cart cart = this.cartDB.get(idCart);
	  
	  if ( cart==null ) {
	 	  System.out.println("CustomerResource:addItem: Could not find cart with id " + idCart );		  
	 	  System.out.println("CustomerResource:addItem: Before throwing the exception NotFoundException");
		  throw new NotFoundException("Could not find cart with id " + idCart );		  
	  }
		    
      if ( DAOArticle.verifyExistingArticle(strIdArticle) ) {	  
		     cart.addItem(strIdArticle, quantity );
			 result = new Boolean(true) ;
	  }
	  else {
			 System.out.println("CustomerResource:addItem: Could not find article with id " + strIdArticle );		  
			 System.out.println("CustomerResource:addItem: Before throwing the exception NotFoundException");
		     throw new NotFoundException("Could not find article with id " + strIdArticle);
	  }

	  
      URI location = URI.create("/customers/addItem/" + strIdArticle );	  

      return Response.created(location).entity(result.toString()).build();	  
   }
      
   @GET
   @Produces("application/json")
   @Path("/getCartInformation/{idCart}")
   public Response getCartInformation( @PathParam("idCart") String idCart)
   {
	  Integer intIdCart = new Integer( Integer.parseInt(idCart) );
	  Cart cart = cartDB.get(intIdCart) ;
	  CartTwo cartTwo = null ;
	  if ( cart!=null ) {
		 cartTwo = DAOCart.loadCartTwo(cart);
	  }
	  else {
	 	  System.out.println("CustomerResource:getCartInformation: Could not find cart with id " + idCart );		  
	 	  System.out.println("CustomerResource:getCartInformation: Before throwing the exception NotFoundException");
		  throw new NotFoundException("Could not find cart with id " + idCart ); 
	  }
	  	    
	  return Response.ok().entity( cartTwo ).build();  //.created(location)   
   }  

   @GET
   @Produces("application/json")
   @Path("/getTotalAmount/{idCart}")
   public Response getTotalAmount( @PathParam("idCart") String idCart)
   {
	  Integer intIdCart = new Integer( Integer.parseInt(idCart) );
	  Cart cart = cartDB.get(intIdCart) ;
	  String totalAmount = new String("") ;
	  
	  if ( cart!=null ) {
		  Double dblTotalAmount = null ;
		  
		  try {
		     dblTotalAmount = DAOCart.getTotalAmount(cart,articlesDB) ; 
		  }
		  catch (ArticleNotFoundException antExc) {
			  
			 System.out.println("CustomerResource:getTotalAmount: Could not find an article in the articles inventory");
			 
			 System.out.println("CustomerResource:getTotalAmount: Before throwing the exception NotFoundException");
			 throw new NotFoundException( "Could not find an article in the articles inventory" );			  
		  }
		  
		  if ( dblTotalAmount != null ) {
		     totalAmount = new String( String.format("%.2f", dblTotalAmount.doubleValue() ) ) ;
		  }
		  else {
			  
		  }
	  }
	  else {
	 	  System.out.println("CustomerResource:getTotalAmount: Could not find cart with id " + idCart );		  
	 	  System.out.println("CustomerResource:getTotalAmount: Before throwing the exception NotFoundException");
		  throw new NotFoundException("Could not find cart with id " + idCart );		  
	  }
	  	    
	  return Response.ok().entity( totalAmount ).build();	    
   }

  
   @POST
   @Consumes("application/xml")
   @Produces("application/json")
   @Path("/addItemIS") 
   public Response addItemIS(InputStream is) {
	  String[] array = readItem(is);
	  
	  return addItem( array[0] , array[1] , array[2]);
	  
   }   
   
   protected String[] readItem(InputStream is) {
	      try {
	         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	         Document doc = builder.parse(is);
	         Element root = doc.getDocumentElement();
             String[] array = new String[3]; 

	         NodeList nodes = root.getChildNodes();
	         for (int i = 0; i < nodes.getLength(); i++) {
	            Element element = (Element) nodes.item(i);
	            if (element.getTagName().equals("idArticle")) { 	
	               array[i] = new String( element.getTextContent() ) ;
	            }
	            else if (element.getTagName().equals("quantity")) {
		           array[i] = new String( element.getTextContent() ) ;	            	
	            }
	            else if (element.getTagName().equals("idCart")) {
		           array[i] = new String( element.getTextContent() ) ;
	            }
	         }
	         return array;
	      }
	      catch (Exception e) {
	         throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
	      }
	   }   
}
