package BoardFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MapLoader {

    public Map LoadMap(File file) throws FileNotFoundException, NoSuchElementException{
        Scanner fileScanner = new Scanner( file );
        int x, y;
        Map map;

        x = fileScanner.nextInt();
        y = fileScanner.nextInt();

        map = new Map( x, y );

        fileScanner.nextLine(); // zeby pozbyc sie \n

        while( fileScanner.hasNextLine() ){
            String line[] = fileScanner.nextLine().split("\s");
            try{
                x = Integer.parseInt( line[1] );
                y = Integer.parseInt( line[2] );
            } catch ( NumberFormatException e ){
                continue;
            }

            if( line.length == 4 ){
                loadGate( map, line[0], x, y, line[3] );
            }
            else if( line.length == 3 )
                loadCell(map, line[0], x, y);
        }
        fileScanner.close();

        return map;
    }

    private void loadGate( Map map, String type, int x, int y, String direction ){
        int gate[][];
        try {
            Scanner scanner = new Scanner(new File("BoardFiles/Gates/" + type + ".txt"));

            int width = scanner.nextInt();
            int height = scanner.nextInt();

            gate = new int[height][width];

            for( int i = 0; i < height; i++ )
                for( int j = 0; j < width; j++ )
                    gate[i][j] = scanner.nextInt();

        } catch(FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku z bramka " + type );
            return;
        } catch( NumberFormatException e ){
            System.err.println("Bledny format pliku z bramka " + type );
            return;
        }

        if( direction.equals("UP") )
            loadGateUp(map, x, y, gate);
        else if( direction.equals("DOWN") )
            loadGateDown(map, x, y, gate);
        else if( direction.equals("LEFT") )
            loadGateLeft(map, x, y, gate);
        else if( direction.equals("RIGHT") )
            loadGateRight(map, x, y, gate);
    }

    private void loadGateUp( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = 0; i < width; i++ ){
            for( int j = 0; j < height; j++ )
                if( gate[j][i] == 1 )
                    map.setState(x+(height-1-j), y+(width-1-i), Map.CONDUCTOR);
        }
    }

    private void loadGateDown( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = 0; i < width; i++ ){
            for( int j = 0; j < height; j++ )
                if( gate[j][i] == 1 )
                    map.setState(x+j, y+i, Map.CONDUCTOR);
        }

    }

    private void loadGateLeft( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = height-1; i >= 0; i-- )
            for( int j = width-1; j >= 0 ; j-- )
                if( gate[i][j] == 1 )
                    map.setState(x+(width-1-j), y+(height-1-i), Map.CONDUCTOR);
    }

    private void loadGateRight( Map map, int x, int y, int gate[][] ){
        for( int i = 0; i < gate.length; i++ )
            for( int j = 0; j < gate[0].length; j++ )
                if( gate[i][j] == 1 )
                    map.setState(x+j, y+i, Map.CONDUCTOR);
    }

    private void loadCell( Map map, String type, int x, int y ){
        if( type.equals("CONDUCTOR") )
            map.setState(x, y, Map.CONDUCTOR );
        else if( type.equals("HEAD") )
            map.setState(x, y, Map.HEAD );
        else if( type.equals("TAIL") )
            map.setState(x, y, Map.TAIL );
    }
}