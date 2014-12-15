package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class ApplicationFrame extends JFrame {
    
    private Integer runTime = 0, rows, cols, mines, minesAll, released = 0;
    public Integer newRows, newCols, newMines, style; // used for new Game
    private boolean canPlay = true;
    private Board board;
    private JPanel gamePanel;
    private JButton[][] buttonBoard;
    private JLabel minesLabel, timeLabel, gameLabel;

    public ApplicationFrame(int _rows, int _cols, Integer _mines, int _style) throws HeadlessException {
        super("Minesweeper");
        rows = _rows;
        cols = _cols;
        mines = _mines;
        minesAll = mines;	// won't be decreased
        
        // for options
        style = _style;
        newRows = rows;
        newCols = cols;
        newMines = mines;
        
        board = new Board(rows, cols, mines);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(150 + cols*35, 100 + rows * 35);
        this.setLayout( new BorderLayout() );
        this.createComponents();
        this.setVisible(true);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    } 

    private void createComponents() {
    	 JMenuBar menubar = new JMenuBar();

         JMenu file = new JMenu("File");

         file.add( addMenuItem("New game", newGame()) );
         file.add( addMenuItem("Options", optionsGame()) );
         file.add( addMenuItem("Exit", exitGame()) );

         menubar.add(file);

         setJMenuBar(menubar);
         
         JPanel statusPanel = new JPanel();
         statusPanel.setLayout( new BorderLayout() );
         statusPanel.setPreferredSize(new Dimension(this.getWidth() - 20, 30));
         
         Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
         
         String minesString = "mines left: " + Integer.toString(mines);
         minesLabel = new JLabel( minesString );
         minesLabel.setBorder(paddingBorder);
         statusPanel.add(minesLabel, BorderLayout.WEST);
         
         timeLabel = new JLabel("time: 0");
         timeLabel.setBorder(paddingBorder);
         statusPanel.add(timeLabel, BorderLayout.EAST);
         
         gameLabel = new JLabel("ready to play!");
         gameLabel.setBorder(  BorderFactory.createEmptyBorder(10, 70, 10,10) );
         statusPanel.add( gameLabel, BorderLayout.CENTER );
         
         this.add(statusPanel, BorderLayout.NORTH);
         
         buttonBoard = new JButton[rows][cols];
         
         gamePanel = new JPanel();
         gamePanel.setLayout( new GridLayout( rows, cols ) );
         this.add(gamePanel, BorderLayout.CENTER);

         for(int i=0; i < (rows * cols); i++){
        	 JButton btn = createButton( i );
        	 int[] position = idToPosition( i );
        	 int y = position[0];
        	 int x = position[1];
        	 
        	 buttonBoard[x][y] = btn;	
        	 gamePanel.add(btn);
         }
    }
    
    private JButton createButton(int id) {
    	JButton button = new JButton( );
    	
        button.setName( Integer.toString(id) );
        button.setBackground( Color.WHITE );
        button.addMouseListener( fieldClickAction() );
        return button;
    }
    
    private void closeWindow(){
    	this.dispose();
    }
    
    private JMenuItem addMenuItem( String str, ActionListener action ){
    	JMenuItem eMenuItem = new JMenuItem( str );
        eMenuItem.addActionListener(action);
        
        return eMenuItem;
    }
    
    private ApplicationFrame getSelf(){
    	return this;
    }
    
    private ActionListener optionsGame(){
    	ActionListener exitGameAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	new Options( style, getSelf() );
            }
        };
        
        return exitGameAction;
    }
    
    private ActionListener exitGame(){
    	ActionListener exitGameAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	closeWindow();
            }
        };
        
        return exitGameAction;
    }
    
    private ActionListener newGame(){
    	ActionListener newGameAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	new ApplicationFrame(newRows, newCols, newMines, style);
            	closeWindow();
            }
        };
        
        return newGameAction;
    }
    
    private MouseListener fieldClickAction(){
    	MouseListener fieldClickAction = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent event) {
				// TODO Auto-generated method stub
				Object source = event.getSource();
            	JButton button = (JButton)source;
            
            	if(SwingUtilities.isLeftMouseButton( event ))
            		clickField( button );
            	if(SwingUtilities.isRightMouseButton(event))
            		flagField( button );          		
			}

			@Override
			public void mouseEntered(MouseEvent event) { }
			@Override
			public void mouseExited(MouseEvent event) { }
			@Override
			public void mousePressed(MouseEvent event) { }
			@Override
			public void mouseReleased(MouseEvent event) { }
        };
        
        return fieldClickAction;
    }
    
    private void startGameSetUp(){
    	if( gameLabel.getText().equals("ready to play!") ){
	    	gameLabel.setText("playing...");
			
			class runTimeExec extends TimerTask {
	            public void run() {
	            	if( canPlay ){
	            		runTime++;
	            		timeLabel.setText( "time: " + runTime );
	            	}
	            }
	         }
	
	         Timer timer = new Timer();
	         timer.schedule(new runTimeExec(), 0, 1000);
    	}
    }
    
    private void flagField( JButton button ){
    	startGameSetUp();
    	
    	if( button.getText().equals("") ){
    		if(mines > 0){
    			mines--;
    			button.setText("?");	
    		}
    	}else if( button.getText().equals("?") ){
    		button.setText("");
    		mines++;
    	}
    	minesLabel.setText("mines left: " + mines);
    }
    
    private void clickField( JButton button ){
    	if( (button.getText().equals("") || button.getText().equals("?")) && canPlay ){
    		released++;
    		startGameSetUp();
    		
	    	int[] position = idToPosition( Integer.parseInt( button.getName() ) );
	    	int y = position[0];
	    	int x = position[1];
	    	int state = board.getPosition(x, y);
	    	
	    	if( button.getText().equals("?") ){
	    		mines++;
	    		minesLabel.setText("mines left: " + mines);
	    	}
	    	
	    	if( state == -1){
	    		canPlay = false;
	    		gameLabel.setText("game over!");
	    		
	    		button.setText( "x" );
				button.setBackground(Color.GRAY);
				
				for(int _x = 0; _x < rows; _x++)
					for(int _y = 0; _y < cols; _y++){
						if( board.getPosition(_x, _y) == -1 ){
							buttonBoard[_x][_y].setText("x");
							buttonBoard[_x][_y].setBackground(Color.GRAY);
						}
					}
				
	    	}else if( state == 0){
	    		button.setBackground(Color.LIGHT_GRAY);
	    		button.setText( " " );
	    		
	    		for(int _x = (x-1); _x < (x+2); _x ++)
	    			for(int _y = (y-1); _y < (y+2); _y++){
	    				
	    				if( _x > -1 && _y > -1 && _x < rows && _y < cols ){
	    					if( board.getPosition(_x, _y) != -1 )
	    						clickField( buttonBoard[_x][_y] );
	    				}
	    				
	    			}
	    	}else{
	    		button.setText( Integer.toString(state) );
				button.setBackground(Color.LIGHT_GRAY);
	    	}
	    	
	    	if( released >= (rows * cols) - minesAll && canPlay ){
	    		canPlay = false;
	    		gameLabel.setText("game won!");
	    	}
    	}
    }
    
    
    private int[] idToPosition( int id ){
    	int[] pos = new int[2];
    	pos[0] = id % cols;	// row 
    	pos[1] = (int) Math.floor(id / cols);	// col
    	return pos;
    }

    

}
