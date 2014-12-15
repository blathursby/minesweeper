package minesweeper;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

public class Options extends JFrame{
	
	private ApplicationFrame frame;
	JRadioButton beginner, intermediate, advanced, custom;
	
	private JComboBox yList, xList, minesList;
	
	public Options(int style, ApplicationFrame _frame){
		 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        this.setSize(320, 230);
	        this.setResizable(false);
	        this.setLayout( new FlowLayout() );
	        this.setVisible(true);
	        
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
	        frame  = _frame;
	        
	        Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
	        
	        JLabel instruction = new JLabel("Pick the style of game:");
	        instruction.setBorder(paddingBorder);
	        this.add(instruction);
	        
	        beginner = new JRadioButton("Beginner : 10 x 10 with 5 mines");
	        beginner.addActionListener( selectOption() );
	        this.add(beginner);
	        
	        intermediate = new JRadioButton("Intermediate : 10 x 20 with 12 mines");
	        intermediate.addActionListener( selectOption() );
	        this.add(intermediate);
	        
	        advanced = new JRadioButton("Advanced : 20 x 20 with 20 mines");
	        advanced.addActionListener( selectOption() );
	        this.add(advanced);
	        
	        custom = new JRadioButton("Custom");
	        custom.addActionListener( selectOption() );
	        this.add(custom);
	        
	        ArrayList<String> sizes = new ArrayList<String>();
	        for(int i=5; i<20; i++)
	        	sizes.add( Integer.toString(i) );
	        
	        xList = new JComboBox( sizes.toArray() );
	        xList.setSelectedIndex(0);
	        this.add(xList);
	        
	        JLabel xLabel = new JLabel("x");
	        this.add(xLabel);
	        
	        yList = new JComboBox( sizes.toArray() );
	        yList.setSelectedIndex(0);
	        this.add(yList);
	        
	        JLabel minesLabel = new JLabel("mines :");
	        this.add(minesLabel);
	        
	        minesList = new JComboBox( sizes.toArray() );
	        minesList.setSelectedIndex(0);
	        this.add(minesList);
	        
	        JButton cancelButton = new JButton("cancel");
	        cancelButton.setBorder(paddingBorder);
	        cancelButton.addActionListener( close() );
	        this.add(cancelButton);
	        
	        JButton saveButton = new JButton("save");
	        saveButton.setBorder(paddingBorder);
	        saveButton.addActionListener( save() );
	        this.add(saveButton);
	        
	        JRadioButton[] radioButtonArray = {beginner, intermediate, advanced, custom};
	        radioButtonArray[style].setSelected(true);
	}
	
	private void closeWindow(){
    	this.dispose();
    }
	
	 private ActionListener save(){
	    	ActionListener exitGameAction = new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	int cols = 10, rows = 10, mines = 5, style = 0;
	            	if(beginner.isSelected()){
	            		cols = rows = 10;
	            		mines = 5;
	            		style = 0;
	            	}
	            	if(intermediate.isSelected()){
	            		cols = 20;
	            		rows = 10;
	            		mines = 12;
	            		style = 0;
	            	}
	            	if(advanced.isSelected()){
	            		cols = 20;
	            		rows = 20;
	            		mines = 20;
	            		style = 0;
	            	}
	            	if(custom.isSelected()){
	            		cols = Integer.parseInt( (String) xList.getSelectedItem() );
	            		rows = Integer.parseInt( (String) yList.getSelectedItem() );
	            		mines = Integer.parseInt( (String) minesList.getSelectedItem() );
	            	}
	            	frame.newCols = cols;
	            	frame.newRows = rows;
	            	frame.newMines = mines;
	            	frame.style = style;
	            			
	            	closeWindow();
	            }
	        };
	        
	        return exitGameAction;
	 }
	 
	 private ActionListener close(){
	    	ActionListener exitGameAction = new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	
	            	closeWindow();
	            }
	        };
	        
	        return exitGameAction;
	 }
	
	private ActionListener selectOption(){
    	ActionListener exitGameAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	JRadioButton[] radioButtonArray = {beginner, intermediate, advanced, custom};
            	
            	for( JRadioButton radiobutton : radioButtonArray ){
            		if( radiobutton != event.getSource() ){
            			radiobutton.setSelected(false);
            		}else{
            			radiobutton.setSelected(true);
            		}
            	}
            }
        };
        
        return exitGameAction;
    }
	
}
