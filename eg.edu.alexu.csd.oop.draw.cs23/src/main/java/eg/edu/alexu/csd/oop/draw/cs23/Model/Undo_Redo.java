package eg.edu.alexu.csd.oop.draw.cs23.Model;

public class Undo_Redo {
    private Shape prevShape;
    private Shape CurrentShape;

    public Shape getCurrentShape() {
        return CurrentShape;
    }

    public void setCurrentShape(Shape currentShape) {
        CurrentShape = currentShape;
    }

    public Shape getPrevShape() {
        return prevShape;
    }

    public void setPrevShape(Shape prevShape) {
        this.prevShape = prevShape;
    }

}
