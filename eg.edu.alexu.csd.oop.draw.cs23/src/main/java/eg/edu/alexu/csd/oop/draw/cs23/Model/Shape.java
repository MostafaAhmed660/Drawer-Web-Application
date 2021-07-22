package eg.edu.alexu.csd.oop.draw.cs23.Model;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Random;
import java.awt.*;
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "json")
@JsonSubTypes({
       @JsonSubTypes.Type(value = polygons.class, name = "polygon"),
        @JsonSubTypes.Type(value = Sectors.class, name = "sectors"),
        @JsonSubTypes.Type(value = ElipticalShapes.class, name = "ElipticalShapes")
})
public abstract class Shape implements Cloneable {
    private int ID;
    private String type ;
    private String BorderColor;
    private String FillColor;
    private int BorderWeight;
    // private Point position;


    public Shape(int ID, String type, String borderColor, String fillColor, int borderWeight) {
        this.ID = ID;
        this.type = type;
        BorderColor = borderColor;
        FillColor = fillColor;
        BorderWeight = borderWeight;
    }
    public Shape(){
        super();
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBorderColor() {
        return BorderColor;
    }

    public void setBorderColor(String borderColor) {
        BorderColor = borderColor;
    }

    public String getFillColor() {
        return FillColor;
    }

    public void setFillColor(String fillColor) {
        FillColor = fillColor;
    }

    public int getBorderWeight() {
        return BorderWeight;
    }

    public void setBorderWeight(int borderWeight) {
        BorderWeight = borderWeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //public abstract void SetPosition(int Point);
}
