package eg.edu.alexu.csd.oop.draw.cs23.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eg.edu.alexu.csd.oop.draw.cs23.Model.*;
import eg.edu.alexu.csd.oop.draw.cs23.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class Controller {
    @Autowired
    private Services services;

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    @PostMapping(value = {"/initialization"})
    public void initialization(){
        services.initialization();
    }

    @PostMapping(value = {"/addElipticalShapes"})
    public ArrayList<Shape> addEliptical(@RequestBody ElipticalShapes s){
        return services.add(s);
    }

    @PostMapping(value = {"/addPolygonShapes"})
    public ArrayList<Shape> addPolygon(@RequestBody polygons s){
        return services.add(s);
    }

    @PostMapping(value = {"/EditEliptical"})
    public ArrayList<Shape> editEliptical(@RequestBody ElipticalShapes s){
        return services.editShape(s);
    }

    @PostMapping(value = {"/EditPolygon"})
    public ArrayList<Shape> editPolygon(@RequestBody polygons s){
        return services.editShape(s);
    }

    @PostMapping(value = {"/Undo"})
    public ArrayList<Shape> Undo(){
        return services.Undo();
    }

    @PostMapping(value = {"/Redo"})
    public ArrayList<Shape> Redo(){
        return services.Redo();
    }

    @PostMapping(value = {"/DeletePolygons"})
    public ArrayList<Shape> deletePolygons(@RequestBody polygons s){
        return services.delete(s);
    }

    @PostMapping(value = {"/DeleteEliptical"})
    public ArrayList<Shape> deleteEliptical(@RequestBody ElipticalShapes s){
        return services.delete(s);
    }

    //this for as to show undo stack
    @PostMapping(value = {"/showUndo"})
    public Stack<Undo_Redo> showUndo(){
        return services.showUndo();
    }

    //this for as to show Redo stack
    @PostMapping(value = {"/showRedo"})
    public Stack<Undo_Redo> showRedo(){
        return services.showRedo();
    }

    @PostMapping(value = {"/Show"})
    public ArrayList<Shape> show(){
        return services.showShapes();
    }

    @PostMapping(value ={"/SaveXML"} )
    public void SaveXML(){
        try {
            FileOutputStream fos=new FileOutputStream(new File("./shapes.xml"));
            XMLEncoder encoder=new XMLEncoder(fos);
            encoder.writeObject(this.services);
            encoder.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value ={"/LoadXML"} )
    public ArrayList<Shape> LoadXML(){
        try{
            FileInputStream fis=new FileInputStream(new File("./shapes.xml"));
            XMLDecoder decoder=new XMLDecoder(fis);
            this.services=(Services)decoder.readObject();
            decoder.close();
            fis.close();
            return services.showShapes();
        }
        catch (IOException ex){
            ex.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    @PostMapping(value ={"/SaveJSON"} )
    public void SaveJSON(){
        ObjectMapper mapper=new ObjectMapper();
        File file=new File("./shapes.json");
        try {
            mapper.writeValue(file,this.services);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value ={"/LoadJSON"} )
    public ArrayList<Shape> LoadJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File fo = new File("./shapes.json");
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            this.services = mapper.readValue(fo, Services.class);
            return services.showShapes();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }

    }

    }
