README CODE CHALLENGE
=====================
Author: Ricardo De la Rosa

1. Install JDK 1.7
Set the environment variable named JAVA_HOME with the directory of jdk 1.7.
An example of this value can be "C:\Program Files\Java\jdk1.7.0_80".
Add the path %JAVA_HOME%\bin to the environment variable named Path. 

2. Install maven 3.5.2 or a greater version
Set the environment variable named MAVEN_HOME with the path of the directory apache-maven-3.5.2  
An example of this value can be "C:\java\apache-maven-3.5.2".
Add the path %MAVEN_HOME%\bin to the environment variable named Path.

3. Download the application server named jboss-as-7.1.1.Final. This application server can be downloaded from  http://jbossas.jboss.org/downloads . 
The element is named [JBoss AS 7.1.1.Final] and is the first in the list.

4. Set the environment variable JBOSS_HOME with the apropiate path (for example C:\jboss-as-7.1.1.Final). 

5. Go to the folder %JBOSS_HOME%\bin and execute the command: standalone.bat
This command will execute JBoss Application Server on the port 8080.
You must be sure that port 8080 is available before installing jboss-as-7.1.1.Final. 

4. Create the folder C:\java si es para Windows o /java si es para linux 

5. Open a MS-DOS window with the "cmd" command and go to the C:\java  

6. Execute the command: git clone https://github.com/rdlarosa2/resteasy-jaxrs-3.0.5.Final.git

7. Navigate to the folder C:\java\resteasy-jaxrs-3.0.5.Final\examples\oreilly-workbook-as7\ex07_1

8. Execute the command: mvn clean install
This command will deploy ex07_1.war on the application server. Besides, this command will execute two test on the functionality.

The first test does the following tasks:
   [1A] Create a cart with the identificator equals to 1.
   [1B] Add to this cart 53 units of the article with identificator equals to 10.
   [1C] Call the service Get Cart Information.
   [1D] Verify that the item added to the cart is present.   

The second test does the following tasks:
   [2A] Create a cart with the identificator equals to 2.
   [2B] Add to this cart 48 units of the article with identificator equals to 11.
   [2C] Call the service Get Cart Information.
   [2D] Verify that the to the total amount to be paid corresponds to the sum of the quantities of the 2 items.      
   
9. To execute the operations of the API using HTML pages, try:

   [9A] To execute the service to create a cart, you can go the the URL: http://localhost:8080/ex07_1/create_cart.html
   [9B] To execute the service to add an item to a cart, you can go the the URL: http://localhost:8080/ex07_1/add_item.html
   [9C] To execute the service to get information of a cart (with id=1), you can go the the URL: http://localhost:8080/ex07_1/services/customers/getCartInformation/1
   [9D] To execute the service to get the total amount to be paid by the items of a cart (with id=1), you can go the the URL:
        http://localhost:8080/ex07_1/services/customers/getTotalAmount/1   
  