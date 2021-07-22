# Drawer-Web-Application
canvas drawing web-based application

*The steps required to run your code

  -1.Install Vuetify<br/>
  -2.Install Axios<br/>
  -3.Run Back-end first so it runs on port 8080<br/>
  -4.Then Run Front-end so it runs on port 8081<br/>
  
  
##Class Diagram
  
![image](https://user-images.githubusercontent.com/59110598/126701316-f5cf90cc-bd9c-45b8-ae84-a2d5b87a4714.png)

###how we have applied the required design pattern

 1. We used Shape Factory design pattern: Service Class is the shape factory for shapes to generate shapes and to make any operation on shapes(delete, copy…).<br/>
 2. We used singleton design pattern: to do only one object of service class .<br/>
 3.We wanted to use prototype design pattern on shapes to make copy of them.<br/>
But that causes a problem in frontend; it makes (flicker problem).<br/>
So, we made a copy of our shapes in the frontend to be more efficient for the user.<br/>

<br/><br/>

##snapshots of UI
![image](https://user-images.githubusercontent.com/59110598/126701717-b0f55192-564e-4ca4-a87f-6b4a7e66303d.png)


##some guides
  1.you cannot draw unless you choose shape first<br/>
  2.you can do move , recolor, or copy or…<br/>
not only one time on click like if u click delete you can delete shapes as much as you want but you cannot do anything -like drawing- except delete<br/>
  3.Recolor Program will take new colors from fill color and stroke color in left of canvas<br/>
  4.copy When you make copy, u have two options:<br/>
     *First, one-click on shape then new copy will be created in front of the old one<br/>
     *Second, user can click and drag which will make user move the new copy anywhere he want<br/>
*Like shown below one-click on circle , click and drag on square<br/>

![image](https://user-images.githubusercontent.com/59110598/126701848-54cb0097-3cdc-41a1-87d9-4a6d318ac2bd.png)


  5.Move and Resize<br/>
    Just click on shape and drag and it will happen<br/>
  6.Save & load<br/>
    There are two buttons to save one for XML and other for JSON And same for load<br/>
  
  We save xml as file name “shapes.xml” and Json as file name “shapes.json”<br/>
  These two files are created in backend folder.<br/>
  Cause of time we cannot make user choose where to save or where to load from, but program still save and load correctly as XML and as JSON<br/>
  U can test it by save drawing as json and another one as xml.<br/>
