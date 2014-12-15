package minesweeper;

import java.awt.event.ActionListener;

public class Main {
	
	ActionListener[] listeners;
	
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		createApplicationFrame();
	}
	
	private ApplicationFrame createApplicationFrame() {
		return new ApplicationFrame(10, 10, 5, 0);
	}
}
