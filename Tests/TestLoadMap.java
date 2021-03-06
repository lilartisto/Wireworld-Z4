package Tests;

import java.io.File;

import BoardFiles.*;
import WireworldGUI.Window;

public class TestLoadMap {
    
    public static void main(String args[]){

        FileManager loader = new FileManager();

        Map map = loader.LoadMap( new File("Tests/testLoadMap.txt") );
        
        try{
            new Window( map, null, 10 );
        }catch( NullPointerException e ){}
    }
}