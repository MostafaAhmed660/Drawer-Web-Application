# Drawer-Web-Application
canvas drawing web-based application

*The steps required to run your code*

  1.Install Vuetify
  2.Install Axios
  3.Run Back-end first so it runs on port 8080
  4.Then Run Front-end so it runs on port 8081
  
  
#*Class Diagram*
  
![image](https://user-images.githubusercontent.com/59110598/126701316-f5cf90cc-bd9c-45b8-ae84-a2d5b87a4714.png)

*how we have applied the required design pattern*

 1. We used Shape Factory design pattern: Service Class is the shape factory for shapes to generate shapes and to make any operation on shapes(delete, copy…).
 2. We used singleton design pattern: to do only one object of service class .
 3.We wanted to use prototype design pattern on shapes to make copy of them.
But that causes a problem in frontend; it makes (flicker problem).
So, we made a copy of our shapes in the frontend to be more efficient for the user.
