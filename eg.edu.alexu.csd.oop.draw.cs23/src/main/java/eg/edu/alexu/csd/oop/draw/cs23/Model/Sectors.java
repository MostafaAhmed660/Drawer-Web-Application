package eg.edu.alexu.csd.oop.draw.cs23.Model;

public class Sectors extends Shape{
    private int Angel;
    private int RadiusLength;

    public Sectors(int ID, String type, String borderColor, String fillColor, int borderWeight) {
        super(ID, type, borderColor, fillColor, borderWeight);
    }

    public int getRadiusLength() {
        return RadiusLength;
    }

    public void setRadiusLength(int radiusLength) {
        RadiusLength = radiusLength;
    }

    public int getAngel() {
        return Angel;
    }

    public void setAngel(int angel) {
        Angel = angel;
    }

}
