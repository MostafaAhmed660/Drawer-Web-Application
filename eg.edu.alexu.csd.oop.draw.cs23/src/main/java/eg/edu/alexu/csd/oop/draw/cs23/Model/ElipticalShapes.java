package eg.edu.alexu.csd.oop.draw.cs23.Model;

import java.awt.*;
import java.util.ArrayList;

public class ElipticalShapes extends Shape{
    private Point[] points = new Point[2];

    public ElipticalShapes(int ID, String type, String borderColor, String fillColor, int borderWeight) {
        super(ID, type, borderColor, fillColor, borderWeight);
    }

    public ElipticalShapes() {}

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}