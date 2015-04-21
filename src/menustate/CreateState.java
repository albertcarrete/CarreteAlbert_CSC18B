package menustate;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import utilities.SpringUtilities;

public class CreateState extends JPanel{
	public CreateState(){
		
		String[] labels = {"Name: ", "Description: ", "Type: ", "NumPlayers: "};
	    int numPairs = labels.length;
		setLayout(new SpringLayout());
		
		for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            add(textField);
        }
	      //Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
		
	}
}
