package WireworldGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
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
    private JSpinner spinner;
    private JSlider slider;

    private String finalPath;
    private int n;

    public Window( Map map, String finalPath, int n ) {
        super("WIREWORLD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new Panel(map, this);
        this.map = map;
        this.finalPath = finalPath;
        this.n = n;

        createPageStartPanel();

        setLayout( new BorderLayout( 0, 0 ) );

        add(panel, BorderLayout.CENTER );
        add(pageStartPanel, BorderLayout.PAGE_START );

        pack();

        setResizable(false);
        setVisible(true);
    }

    private void createPageStartPanel(){

        pageStartPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
        pageStartPanel.setBackground( new Color( 80, 80, 140 ) );

        button = new JButton("START");
            button.addActionListener(this);
            button.setPreferredSize( new Dimension( 100, 40 ) );

        textField = new JTextField("Gate Orienation");
            textField.setPreferredSize( new Dimension( 100, 40 ) );

        gateButton = new JToggleButton("Draw gate");
            gateButton.setPreferredSize( new Dimension( 100, 40 ) );

        spinner = new JSpinner( new SpinnerNumberModel(n, 0, 1e6, 1) );
            spinner.setPreferredSize( new Dimension( 100, 40 ) );

        slider = new JSlider( JSlider.HORIZONTAL, 0, 250, 100 );
            slider.setMajorTickSpacing( 50 );
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

        pageStartPanel.add(slider);
        pageStartPanel.add(spinner);
        pageStartPanel.add(textField);
        pageStartPanel.add(gateButton);
        pageStartPanel.add(button);
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

    public int getN(){
        String tmp = spinner.getValue() + "";
        double x = Double.parseDouble(tmp);
        n = (int)x;
        return n;
    }

    public int getDelay(){
        return slider.getValue();
    }

    public void decrementN(){
        n--;
        spinner.setValue(n);
    }
}