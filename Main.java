import java.io.File;
import java.util.concurrent.TimeUnit;

import BoardFiles.*;
import WireworldGUI.Window;

public class Main {

    public static void main(String args[]) {

        int n;

        Map map = null;
        Window window;

        try{
            n = args.length > 0 ? Integer.parseInt( "+"+args[0] ) : 100;
        }catch( NumberFormatException e ){
            System.err.println("Podano bledne dane wejsciowe: " + args[0] + ". Oczekiwano dodatniej liczby calkowitej");
            n = 100;
        }

        if( args.length > 1 )
            map = MapLoader.LoadMap(new File(args[1]));

        if( map == null )
            map = new Map(35, 25);

        if( args.length > 2 )
            window = new Window( map, args[2], n );
        else
            window = new Window( map, null, n );


        while( window.getN() > 0 ) {
            if (!map.isRun()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            map.nextRound();
            window.repaintPanel();

            try {
                TimeUnit.MILLISECONDS.sleep( window.getDelay() );
            } catch (InterruptedException e) {}
            if( !window.isInfinity() )
                window.decrementN();
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