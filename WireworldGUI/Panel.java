package WireworldGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.regex.PatternSyntaxException;
import java.awt.*;

import javax.swing.JPanel;

import BoardFiles.Map;
import BoardFiles.MapLoader;

public class Panel extends JPanel implements MouseMotionListener, MouseListener{
    private static final long serialVersionUID = 1L;

    private final int M_LEFT = 1;
    private final int M_MID = 2;
    private final int M_RIGHT = 3;

    private int lastButton;
    private Window window;

    private Map map;
    private static int fieldSize = 20; // < 5; 50 >
    private static final int d = 5; //space
    
    public Panel( Map map, Window window ){
        super();
        setPreferredSize( new Dimension( map.width*fieldSize+2*d, map.height*fieldSize+2*d ) );
        addMouseListener(this);
        addMouseMotionListener(this);
        
        this.window = window;
        this.map = map;
    }

    @Override
 	public void mouseDragged(MouseEvent e) {
        int x = (e.getX()-d)/fieldSize;
        int y = (e.getY()-d)/fieldSize;

        if( lastButton == M_LEFT && !window.isGateButtonClicked() )
            map.setState(x, y, Map.CONDUCTOR);
        else if( lastButton == M_RIGHT )
            map.setState(x, y, Map.EMPTY);

        repaint();
    }
     
    @Override
 	public void mousePressed(MouseEvent e) {
        lastButton = e.getButton();
        int x = (e.getX()-d)/fieldSize;
        int y = (e.getY()-d)/fieldSize;

        if( lastButton == M_LEFT ){
            if( window.isGateButtonClicked() ){
                try{
                    String tmp[] = window.getTextFieldString().split(" ");
                    MapLoader.loadGate(map, tmp[0], x, y, tmp[1]);
                } catch( PatternSyntaxException t ){

                } catch( ArrayIndexOutOfBoundsException t ){
                    System.err.println("Zly format bramki w polu tekstowym");
                }
            }
            else{
                map.setState(x, y, Map.CONDUCTOR);
            }
        }
        else if( lastButton == M_RIGHT )
            map.setState(x, y, Map.EMPTY);
        else if( lastButton == M_MID )
            map.swapHeadTail( x, y );

        repaint();
 	}
 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawRect(d-1, d-1, map.width*fieldSize+1, map.height*fieldSize+1 );

        for( int i = 0; i < map.height; i++ )
            for( int j = 0; j < map.width; j++ ){
                if( map.getField(j, i) == Map.CONDUCTOR )
                    drawConductor(j*fieldSize+d, i*fieldSize+d, g2d);
                else if( map.getField(j, i) == Map.HEAD )
                    drawHead(j*fieldSize+d, i*fieldSize+d, g2d);
                else if( map.getField(j, i) == Map.TAIL )
                    drawTail(j*fieldSize+d, i*fieldSize+d, g2d);
            }
    }

    private void drawConductor( int x, int y, Graphics2D g2d ){
        g2d.setColor( Color.BLACK );
        g2d.fillRect(x, y, fieldSize, fieldSize );
    }

    private void drawHead( int x, int y, Graphics2D g2d ){
        g2d.setColor( Color.YELLOW );
        g2d.fillRect(x, y, fieldSize, fieldSize );
    }

    private void drawTail( int x, int y, Graphics2D g2d ){
        g2d.setColor( Color.RED );
        g2d.fillRect(x, y, fieldSize, fieldSize );
    }

    @Override
 	public void mouseMoved(MouseEvent e) {}
 
 	@Override
 	public void mouseClicked(MouseEvent e) {}
 
 	@Override
 	public void mouseEntered(MouseEvent e) {}
 
 	@Override
 	public void mouseExited(MouseEvent e) {}
 
 	@Override
    public void mouseReleased(MouseEvent e) {}
     
    public static Dimension calculateBestFieldSize( int x, int y ){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int minSize = 3;
        int maxSize = 50;

        int screenWidth = (int)screenSize.getWidth()-4*d;
        int screenHeight = (int)screenSize.getHeight()-180-4*d;

        int w = screenWidth/x;
        int h = screenHeight/y;
        
        if( w < minSize ){
            x = screenWidth/minSize;
            w = minSize;
        }
        else if( x > maxSize ){
            w = maxSize;
        }

        if( h < minSize ){
            y = screenHeight/minSize;
            h = minSize;
        }
        else if( h > maxSize ){
            h = maxSize;
        }
        
        fieldSize = w > h ? h: w;
        
        return new Dimension(x, y);
    }
}