package eg.edu.alexu.csd.oop.draw.cs23.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Stack;
@JsonIgnoreProperties
public class Action {
    private Stack<Undo_Redo>undos=new Stack<Undo_Redo>();
    private Stack<Undo_Redo>redos=new Stack<Undo_Redo>();
    public Action(){}

    public Action(Stack<Undo_Redo> undos, Stack<Undo_Redo> redos) {
        this.undos = undos;
        this.redos = redos;
    }
    public int getUndosSize(){
        return this.undos.size();
    }
    public int getRedosSize(){
        return this.redos.size();
    }
    //add step
    public void addUndo(Undo_Redo undo){
        this.undos.push(undo);
    }

    //do undo
    public Undo_Redo Undo(){
        Undo_Redo redo=new Undo_Redo();
        Undo_Redo temp=undos.peek();
        redo.setCurrentShape(temp.getPrevShape());
        redo.setPrevShape(temp.getCurrentShape());
        redos.push(redo);
        return this.undos.pop();
    }

    //do redo
    public Undo_Redo Redo(){
        Undo_Redo undo=new Undo_Redo();
        Undo_Redo temp=redos.peek();
        undo.setCurrentShape(temp.getPrevShape());
        undo.setPrevShape(temp.getCurrentShape());
        undos.push(undo);
        return this.redos.pop();
    }

    public void clearRedos(){
        redos.clear();
    }
    public void clearUndos(){
        undos.clear();
    }

    public Stack<Undo_Redo> getUndos() {
        return undos;
    }

    public void setUndos(Stack<Undo_Redo> undos) {
        this.undos = undos;
    }

    public Stack<Undo_Redo> getRedos() {
        return redos;
    }

    public void setRedos(Stack<Undo_Redo> redos) {
        this.redos = redos;
    }

    public Stack<Undo_Redo> showUndo(){
        return undos;
    }

    public Stack<Undo_Redo> showRedo(){
        return redos;
    }


}
