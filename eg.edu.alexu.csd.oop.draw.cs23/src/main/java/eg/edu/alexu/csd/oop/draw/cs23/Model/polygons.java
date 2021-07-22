package eg.edu.alexu.csd.oop.draw.cs23.Model;

import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;

public class polygons extends Shape {
    private int NumberOfSides;
    private Point[] points = new Point[2];

    public polygons() {}

    public polygons(int ID, String type, String borderColor, String fillColor, int borderWeight) {
        super(ID, type, borderColor, fillColor, borderWeight);
    }

    public int getNumberOfSides() {
        return NumberOfSides;
    }

    public void setNumberOfSides(int numberOfSides) {
        this.NumberOfSides = numberOfSides;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }
}
