import java.io.File;
import java.util.concurrent.TimeUnit;

import BoardFiles.*;
import WireworldGUI.Window;

public class Main {

    public static void main(String args[]) {

        int n;
        int delay = 500;

        MapLoader loader = new MapLoader();
        Map map = null;
        Window window;

        try{
            n = args.length > 0 ? Integer.parseInt(args[0]) : 25;
        }catch( NumberFormatException e ){
            System.err.println("Podano bledne dane wejsciowe: " + args[0] + ". Podaj liczbe calkowita");
            n = 25;
        }

        if( args.length > 1 )
            map = loader.LoadMap(new File(args[1]));

        if( map == null )
            map = new Map(20, 20);

        if( args.length > 2 )
            window = new Window( map, args[2] );
        else
            window = new Window( map, null );


        for (int i = 0; i < n;) {
            if (!map.isRun()) {
                try {
                    Thread.sleep(10);
                    continue;
                } catch (InterruptedException e) {
                }
                continue;
            }

            map.nextRound();
            window.repaintPanel();

            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {}

            i++;
        }
        map.setIsFinished( true );

        if( args.length > 2 )
            window.setButtonText("SAVE");
        else{
            window.setButtonText("START");
            window.disableButton();
        }
    }
}