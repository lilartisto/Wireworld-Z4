package BoardFiles;

import WireworldGUI.Panel;

import java.awt.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class FileManager {
    public static void writeMapToFile(Map map, String fileName)
    {
        try (
                var fileWriter = new FileWriter(fileName);
                var writer = new BufferedWriter(fileWriter);
        ) {
            writer.write(map.width + " " + map.height);
            writer.newLine();

            for (int i = 0; i < map.height; i++)
                for (int j = 0; j < map.width; j++)
                {
                    int state = map.getField(j, i);
                    String stateString = getStateAsString(state);

                    if (stateString == null)
                        continue;

                    writer.write(stateString + " " + j + " " + i);
                    writer.newLine();
                }
        } catch (IOException e)
        {
            System.err.println("Nie udało się zapisać do pliku");
        }
    }

    private static String getStateAsString(int state)
    {
        switch (state)
        {
            case Map.HEAD:
                return "HEAD";
            case Map.TAIL:
                return "TAIL";
            case Map.CONDUCTOR:
                return "CONDUCTOR";
            default:
                return null;
        }
    }

    public static Map LoadMap(File file){
        Scanner fileScanner;
        try{
            fileScanner = new Scanner( file );
        } catch( FileNotFoundException e ){
            System.err.println("Nie znaleziono pliku wejsciowego " + file.getPath() );
            return null;
        }

        int x, y;
        Map map;

        try{
            x = fileScanner.nextInt();
            y = fileScanner.nextInt();
        } catch( NoSuchElementException e ){
            System.err.println("Zly format pliku wejsciowego " + file.getPath() );
            fileScanner.close();
            return null;
        }

        Dimension mapSize = Panel.calculateBestFieldSize( x, y );
        map = new Map( (int)mapSize.getWidth(), (int)mapSize.getHeight() );

        try{
            fileScanner.nextLine(); // zeby pozbyc sie \n
        } catch( NoSuchElementException e ){}

        while( fileScanner.hasNextLine() ){
            String line[] = fileScanner.nextLine().split(" ");
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

    public static void loadGate( Map map, String type, int x, int y, String direction ){
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

        Point startPoint = calculateStartPoint(gate);

        if( direction.equals("UP") )
            loadGateUp(map, x-startPoint.y, y-startPoint.x, gate);
        else if( direction.equals("DOWN") )
            loadGateDown(map, x-startPoint.y, y-startPoint.x, gate);
        else if( direction.equals("LEFT") )
            loadGateLeft(map, x-startPoint.x, y-startPoint.y, gate);
        else if( direction.equals("RIGHT") )
            loadGateRight(map, x-startPoint.x, y-startPoint.y, gate);
        else
            System.err.println("Bledny kierunek bramki");
    }

    private static Point calculateStartPoint( int gate[][] ){
        for( int i = 0; i < gate[0].length; i++ )
            for( int j = 0; j < gate.length; j++ )
                if( gate[j][i] == 1 )
                    return new Point( i, j );
        return new Point( 0, 0 );
    }

    private static void loadGateUp( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = 0; i < width; i++ ){
            for( int j = 0; j < height; j++ )
                if( gate[j][i] == 1 )
                    map.setState(x+(height-1-j), y+(width-1-i), Map.CONDUCTOR);
        }
    }

    private static void loadGateDown( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = 0; i < width; i++ ){
            for( int j = 0; j < height; j++ )
                if( gate[j][i] == 1 )
                    map.setState(x+j, y+i, Map.CONDUCTOR);
        }

    }

    private static void loadGateLeft( Map map, int x, int y, int gate[][] ){
        int width = gate[0].length;
        int height = gate.length;

        for( int i = height-1; i >= 0; i-- )
            for( int j = width-1; j >= 0 ; j-- )
                if( gate[i][j] == 1 )
                    map.setState(x+(width-1-j), y+(height-1-i), Map.CONDUCTOR);
    }

    private static void loadGateRight( Map map, int x, int y, int gate[][] ){
        for( int i = 0; i < gate.length; i++ )
            for( int j = 0; j < gate[0].length; j++ )
                if( gate[i][j] == 1 )
                    map.setState(x+j, y+i, Map.CONDUCTOR);
    }

    private static void loadCell( Map map, String type, int x, int y ){
        if( type.equals("CONDUCTOR") )
            map.setState(x, y, Map.CONDUCTOR );
        else if( type.equals("HEAD") )
            map.setState(x, y, Map.HEAD );
        else if( type.equals("TAIL") )
            map.setState(x, y, Map.TAIL );
    }
}

