package WireworldGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;
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
    private JPanel pageEndPanel;

    private JButton button;
    private JToggleButton gateButton;
    private JToggleButton infiButton;
    private JTextField textField;
    private JSpinner spinner;
    private JSlider slider;
    private ButtonGroup radioButtons;

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
        createPageEndPanel();

        setLayout( new BorderLayout( 0, 0 ) );

        add(panel, BorderLayout.CENTER );
        add(pageStartPanel, BorderLayout.PAGE_START );
        add(pageEndPanel, BorderLayout.PAGE_END );

        pack();

        setResizable(false);
        setVisible(true);
    }

    private void createPageStartPanel(){
        pageStartPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );

        Dimension d = new Dimension( 100, 30 );

        button = new JButton("START");
            button.addActionListener(this);
            button.setPreferredSize( d );

        spinner = new JSpinner( new SpinnerNumberModel(n, 0, 1e6, 1) );
            spinner.setPreferredSize( d );

        infiButton = new JToggleButton("Infinity");
            infiButton.setPreferredSize( d );

        textField = new JTextField("Gate Direction");
            textField.setPreferredSize( d );

        gateButton = new JToggleButton("Gate");
            gateButton.setPreferredSize( d );

        pageStartPanel.add(spinner);
        pageStartPanel.add(infiButton);
        pageStartPanel.add(textField);
        pageStartPanel.add(gateButton);
        pageStartPanel.add(button);
    }

    private void createPageEndPanel(){
        pageEndPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );

        slider = new JSlider( JSlider.HORIZONTAL, 0, 250, 100 );
            slider.setMajorTickSpacing( 50 );
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

        createRadioButtons();
        pageEndPanel.add(slider);
    }

    private void createRadioButtons(){
        radioButtons = new ButtonGroup();
        JPanel radioPanel = new JPanel( new GridLayout(2, 1) );

        JRadioButton radioButton = new JRadioButton("Wireworld");
            radioButton.setSelected(true);
            radioButton.addActionListener(this);
            radioButtons.add( radioButton );
            radioPanel.add( radioButton );

        radioButton = new JRadioButton("Game Of Life");
            radioButton.addActionListener(this);
            radioButtons.add( radioButton );
            radioPanel.add( radioButton );

        pageEndPanel.add( radioPanel );
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
        else{
            map.setAutomatonName( e.getActionCommand() );
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

    public boolean isInfinity(){
        return infiButton.isSelected();
    }

    public int getDelay(){
        return slider.getValue();
    }

    public void decrementN(){
        n--;
        spinner.setValue(n);
    }
}