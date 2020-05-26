package WireworldGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BoardFiles.Map;
import BoardFiles.FileManager;

public class Window extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Panel panel;
    private Map map;

    private JPanel pageStartPanel;
    private JButton button;
    private JToggleButton gateButton;
    private JTextField textField;

    private String finalPath;

    public Window( Map map, String finalPath ) {
        super("WIREWORLD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new Panel(map, this);
        this.map = map;
        this.finalPath = finalPath;

        pageStartPanel = new JPanel( new FlowLayout() );
        button = new JButton("START");
        textField = new JTextField("Type gate's name");
        gateButton = new JToggleButton("Draw gate");

        button.addActionListener(this);
        pageStartPanel.add(textField);
        pageStartPanel.add(gateButton);
        pageStartPanel.add(button);

        setLayout( new BorderLayout( 5, 5 ) );

        add(panel, BorderLayout.CENTER );
        add(pageStartPanel, BorderLayout.PAGE_START );

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

    public boolean isGateButtonClicked(){
        return gateButton.isSelected();
    }

    public String getTextFieldString(){
        return textField.getText();
    }
}