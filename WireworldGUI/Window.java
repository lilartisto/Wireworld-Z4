package WireworldGUI;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BoardFiles.Map;

public class Window extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Panel panel;
    private JButton button;
    private Map map;

    private boolean canContinue;

    public Window( Map map ){
        super("WIREWORLD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new Panel( map );
        button = new JButton( "NEXT ROUND" );
        this.map = map;

        setLayout( new FlowLayout() );

        button.addActionListener(this);
        add(panel);
        add(button);

        pack();

        canContinue = false;
        setResizable(false);
        setVisible(true);
    }

    public boolean canContinue(){
        return canContinue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == button ){
            map.nextRound();
        }
        panel.repaint();
    }
}