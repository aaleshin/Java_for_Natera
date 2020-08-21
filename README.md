# Checklist of test cases  https://qa-quiz.natera.com/

1) Creating a triangle:
1.1. Create a triangle   
1.2. Create 10 triangles 
1.3. Create a triangle with negative side values    
1.4. Create a triangle with 1 side > the sum of the other two  
1.5. Create a triangle without separator value    
1.6. Create a triangle by changing the key value for the sides of the triangle  
1.7. Create triangle with zero before side  
1.8. Use an invalid http method to create a triangle    
1.9. Pass a non-json object in the http Protocol     
1.10. Create a triangle with 4 sides  
1.11. Create a triangle with empty sides values  
1.12. Create a triangle with an invalid separator    
1.13. Create a triangle with an invalid separator key   
1.14. To create a triangle with the empty values of the sides   
1.15. Get an answer to the inability to create an 11 triangle
1.16. Create a triangle with float values for the sides     
1.17. Create a triangle with a large numeric value    
1.18. Create triangle with zero side  
1.19. Create 11 triangles
1.20. Create triangle with text in side

2) Delete:
2.1. Deleting a triangle  
2.2. Deleting a nonexistent triangle
2.3. Use the wrong http method to delete the triangle   
2.4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  
2.5. Delete a triangle from the ID of an already deleted triangle 
2.6. Try various SQl: select*  
2.7. Try various SQl: DROP TABLE triangle  
2.8. Try various XSS: <script>alert("XSS1")</script>    
2.9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form   
2.10. Deleting a triangle with empty ID 
 
3) Get all triangles:
3.1. Get all the triangles(10) 
3.2. Get the answer if there are no triangles   
3.3. Use the wrong http method to get the triangle   
 
4) Get a triangle:
4.1. Get a triangle 
4.2. Get a response when requesting a nonexistent triangle  
4.3. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  
4.4. Get a triangle with a previously deleted ID   
4.5. Try various SQl: select*  
4.6. Try various SQl: DROP TABLE triangle   
4.7. Try various XSS: <script>alert("XSS1")</script>  
4.8. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form> 

5) Get the triangle perimeter value:
5.1. Get the perimeter of the triangle   
5.2. Get the answer when calculating the perimeter of a nonexistent triangle    
5.3. Use an invalid http method to calculate the perimeter of the triangle     
5.4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂    
5.5. Get the perimeter of a previously deleted triangle    
5.6. Try various SQl: select*
5.7. Try various SQl: DROP TABLE triangle
5.8. Try various XSS: <script>alert("XSS1")</script>
5.9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form>

6) Get the lower area of a triangle:
6.1. Get the area of the triangle    
6.2. Get the answer when calculating the area of a nonexistent triangle    
6.3. Use an invalid http method to calculate the area of the triangle      
6.4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂        
6.5. Get the area of a previously deleted triangle     
6.6. Try various SQl: select*     
6.7. Try various SQl: DROP TABLE triangle     
6.8. Try various XSS: <script>alert("XSS1")</script>    
6.9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form>     

# Errors found:

 - Internal Server Error if use some string instead of "input" (1.6)
 - Internal Server Error if use non JSON object  (1.9)
 - Method DELETE has exploit to SQL injections (2.6, 2.7)
 
 - Method AREA, PERIMETER, GET - has exploit to SQL injections(4.5, 4.6, 5.6, 5.7, 6.6, 6.7)
 - System create square if you add 4 side (1.10) 
 - System create wrong triangle with empty separator value (1.5)
 - System create triangle with wrong separator value (1.12)
 - System create triangle with 0 side value (1.18)
 - System can create 11 triangle (1.19)
 - System response 200 in DELETE method if use deleted before or empty ID(2.2, 2.3) 