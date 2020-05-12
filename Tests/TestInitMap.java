package Tests;

import BoardFiles.*;
import WireworldGUI.*;

public class TestInitMap {

    public static void main( String args[] ){
        Map map = new Map( 20, 20 );

        map.setState(0, 0, Map.CONDUCTOR);
        map.setState(3, 5, Map.HEAD);
        map.setState(9, 4, Map.TAIL);
        map.setState(15, 13, Map.EMPTY);
        map.setState(0, 20, Map.CONDUCTOR);

        map.swapHeadTail(3, 5);
        map.swapHeadTail(9, 4);

        new Window( map, null );

        System.out.println( map.getField(0, 0) == Map.CONDUCTOR ? "CONDUCTOR" : "FAIL" );
        System.out.println( map.getField(1, 1) == Map.CONDUCTOR ? "CONDUCTOR" : "FAIL" );
    }
}