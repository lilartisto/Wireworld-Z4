package Tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import BoardFiles.*;

public class TestLoadMap {
    
    public static void main(String args[]){

        MapLoader loader = new MapLoader();

        try{
            loader.LoadMap( new File("Tests/testLoadMap.txt") );
        } catch( FileNotFoundException | NoSuchElementException e ){
            e.printStackTrace();
        }
    }
}