# Checklist of test cases

Creating a triangle:
1. Create a triangle   V
2. Create 10 triangles
3. Create a triangle with negative side values    V
4. Create a triangle with 1 side > the sum of the other two   V  
5. Create a triangle without separator value.    V
6. Create a triangle by changing the key value for the sides of the triangle  Vb
7. Create triangle with zero before side  V???
8. Use an invalid http method to create a triangle    V
9. Pass a non-json object in the http Protocol     Vb
10. Create a triangle with 4 sides  V
11. Create a triangle with empty sides values  -V
12. Create a triangle with an invalid separator    Vb
13. Create a triangle with an invalid separator key   V??
14. To create a triangle with the empty values of the sides   -V???
15. Get an answer to the inability to create an 11 triangle
16. Create a triangle with float values for the sides     V
17. Create a triangle with a large numeric value    -V????
18. Create triangle with zero side  Vb

Delete:
1. Deleting a triangle  V
2. Deleting a nonexistent triangle.  V
3. Use the wrong http method to delete the triangle   V
4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  V
5. Delete a triangle from the ID of an already deleted triangle Vb
6. Try various SQl: select*  Vb ??
7. Try various SQl: DROP TABLE triangle  Vb ??
8. Try various XSS: <script>alert("XSS1")</script>    V
9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form   V
10. Deleting a triangle with empty ID V
 
Get all triangles:
1. Get all the triangles(10) V
2. Get the answer if there are no triangles   V 
3. Use the wrong http method to get the triangle   V
 
Get a triangle:
1. Get a triangle V
2. Get a response when requesting a nonexistent triangle  V
3. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  V
4. Get a triangle with a previously deleted ID   V
5. Try various SQl: select*  V
6. Try various SQl: DROP TABLE triangle   V
7. Try various XSS: <script>alert("XSS1")</script>  V
8. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form> V

Get the triangle perimeter value:
1. Get the perimeter of the triangle
2. Get the answer when calculating the perimeter of a nonexistent triangle
3. Use an invalid http method to calculate the perimeter of the triangle
4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  
5. Get the perimeter of a previously deleted triangle
6. Try various SQl: select*
7. Try various SQl: DROP TABLE triangle
8. Try various XSS: <script>alert("XSS1")</script>
9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form>

Get the lower area of a triangle:
1. Get the area of the triangle
2. Get the answer when calculating the area of a nonexistent triangle
3. Use an invalid http method to calculate the area of the triangle
4. Try various invalid characters ‘~!@#$%^&*()?>,./\<][ /*<!—«»♣☺♂  
5. Get the area of a previously deleted triangle
6. Try various SQl: select*
7. Try various SQl: DROP TABLE triangle
8. Try various XSS: <script>alert("XSS1")</script>
9. Try various HTML: <form%20action=»http://live.hh.ru»><input%20type=»submit»></form>