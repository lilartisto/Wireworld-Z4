package WireworldGUI;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BoardFiles.Map;
import BoardFiles.FileManager;

public class Window extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Panel panel;
    private JButton button;
    private Map map;

    private String finalPath;

    public Window( Map map, String finalPath ) {
        super("WIREWORLD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new Panel(map);
        button = new JButton("START");

        this.map = map;
        this.finalPath = finalPath;

        setLayout(new FlowLayout());

        button.addActionListener(this);
        button.setPreferredSize( new Dimension( 150, 100 ) );

        add(panel);
        add(button);

        pack();

        setResizable(false);
        setVisible(true);
    }

    public void repaintPanel(){
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button ) {
            if( map.isFinished() ){
                FileManager.writeMapToFile(map, finalPath);
                disableButton();
            }
            else {
                swapButtonText();
                map.changeIsRun();
            }
        }
    }

    private void swapButtonText(){
        if( button.getText().equals( "STOP" ) )
            button.setText("START");
        else
            button.setText("STOP");
    }

    public void setButtonText( String text ){
        button.setText( text );
    }

    public void disableButton(){
        button.setEnabled(false);
    }
}