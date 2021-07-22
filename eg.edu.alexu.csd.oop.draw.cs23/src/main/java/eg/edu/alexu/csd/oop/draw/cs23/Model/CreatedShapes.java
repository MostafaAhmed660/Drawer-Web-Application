package eg.edu.alexu.csd.oop.draw.cs23.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class CreatedShapes {
    private int idGenterator ;
    private ArrayList<Shape> shapes = new ArrayList<Shape>(0);
    //action now not used but will used in future based on frontEnd
    private Action actions=new Action();

    public CreatedShapes() {
    }

    public CreatedShapes(int idGenterator, ArrayList<Shape> shapes, Action actions) {
        this.idGenterator = idGenterator;
        this.shapes = shapes;
        this.actions = actions;
    }

    public int getIdGenterator() {
        return idGenterator;
    }

    public void setIdGenterator(int idGenterator) {
        this.idGenterator = idGenterator;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public Action getActions() {
        return actions;
    }

    public void setActions(Action actions) {
        this.actions = actions;
    }

    public void AddShape(Shape s){
        if(s.getID()!=0){return;}
        s.setID(++idGenterator);
        //if shape s id == 0 then it mean it newly created so we add to action undos and clear redos
        Undo_Redo object = new Undo_Redo();
        object.setCurrentShape(s);
        actions.addUndo(object);
        shapes.add(s);
        actions.clearRedos();
    }

    public void editShape(Shape s){
        //first if shape s send as edit to exists shape
        for(int i=0;i<shapes.size();i++){
            Shape temp = shapes.get(i);
            if(temp.getID()==s.getID()){
                //if shape s id !=0 then it mean it has been already created before so it's mean
                //shape s is edit of old shape
                Undo_Redo object = new Undo_Redo();
                object.setCurrentShape(s);
                object.setPrevShape(temp);
                shapes.set(i,s);
                actions.addUndo(object);
                return;
            }
        }
    }

    public void RemoveShape(int id){
        for(int i=0;i<shapes.size();i++){
            if(shapes.get(i).getID()==id){
                shapes.remove(i);
                return;
            }
        }
    }

    public void Undo(){
        //first if undo stack is empty
        if(actions.getUndosSize() == 0)
            return ;

        Undo_Redo s = actions.Undo();
        if(s.getPrevShape() == null){
            this.RemoveShape(s.getCurrentShape().getID());
            return;
        }

        //now edit this shape in shapes array
        for(int i=0;i<shapes.size();i++){
            Shape temp = shapes.get(i);
            if(temp.getID()==s.getPrevShape().getID()){
                shapes.set(i,s.getPrevShape());
                return;
            }
        }
        //if we remove by undo and want to redo "redraw" shape
        //so it's id wouldn't be in shapes or it's id defiantly !=0 or any id in shapes
        shapes.add(s.getPrevShape());
    }

    public void Redo(){
        //first if redo stack is empty
        if(actions.getRedosSize() == 0)
            return ;

        Undo_Redo s = actions.Redo();
        if(s.getPrevShape() == null){
            this.RemoveShape(s.getCurrentShape().getID());
            return;
        }
        //now edit this shape in shapes array
        for(int i=0;i<shapes.size();i++){
            Shape temp = shapes.get(i);
            if(temp.getID()==s.getPrevShape().getID()){
                shapes.set(i,s.getPrevShape());
                return;
            }
        }
        //if we remove by undo and want to redo "redraw" shape
        //so it's id wouldn't be in shapes or it's id defiantly !=0 or any id in shapes
        shapes.add(s.getPrevShape());
    }

    public void delete(Shape s){
        for(int i=0;i<shapes.size();i++){
            if(shapes.get(i).getID()==s.getID()){
                Undo_Redo object = new Undo_Redo();
                object.setCurrentShape(null);
                object.setPrevShape(s);
                actions.addUndo(object);
                shapes.remove(i);
                return;
            }
        }
    }


    public ArrayList<Shape>show(){
        return shapes;
    }

    public Stack<Undo_Redo> showUndo(){
        return actions.showUndo();
    }

    public Stack<Undo_Redo> showRedo(){
        return actions.showRedo();
    }

    public void clear(){
        shapes = new ArrayList<Shape>(0);
        actions.clearRedos();
        actions.clearUndos();
        this.idGenterator = 0 ;
    }
}
