package eg.edu.alexu.csd.oop.draw.cs23.services;

import eg.edu.alexu.csd.oop.draw.cs23.Model.CreatedShapes;
import eg.edu.alexu.csd.oop.draw.cs23.Model.Shape;
import eg.edu.alexu.csd.oop.draw.cs23.Model.Undo_Redo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class Services {
    private CreatedShapes createdShapes = new CreatedShapes();

    public CreatedShapes getCreatedShapes() {
        return createdShapes;
    }

    public void setCreatedShapes(CreatedShapes createdShapes) {
        this.createdShapes = createdShapes;
    }

    public ArrayList<Shape> add(Shape s){
        createdShapes.AddShape(s);
        return createdShapes.show();
    }

    public ArrayList<Shape>showShapes(){
        return createdShapes.show();
    }

    public ArrayList<Shape> Undo(){
        createdShapes.Undo();
        return createdShapes.show();
    }

    public Stack<Undo_Redo> showUndo(){
        return createdShapes.showUndo();
    }

    public ArrayList<Shape> Redo(){
        createdShapes.Redo();
        return createdShapes.show();
    }

    public Stack<Undo_Redo> showRedo(){
        return createdShapes.showRedo();
    }

    public ArrayList<Shape> editShape(Shape s){
        createdShapes.editShape(s);
        return createdShapes.show();
    }

    public ArrayList<Shape> delete(Shape s){
        createdShapes.delete(s);
        return createdShapes.show();
    }

    public void initialization(){
        createdShapes.clear();
    }
}
