
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.*;

public class WordleWin{
	
	final private String TITLE = "Wordle";
	final private String SUBMIT_TEXT = "Enter";
	final private int WIN_WIDTH = 300;
	final private int WIN_HEIGHT = 300;
	final private int WORD_LEN = 5;
	final private int NUM_OF_TRIES = 6;
	final private int BUTTON_X = 0; 
	final private int BUTTON_Y = 8;
	final private int BUTTON_WIDTH = 5;
	final private int LABEL_X = 0;
	final private int LABEL_Y = 7;
	final private int LABEL_WIDTH = 5;
	final private int WORD_FONT_SIZE = 20;
	final private String WORD_FONT_TYPE= "Verdana";
	private int curr_row;
	private JFrame frame;
	private JTextField [][]letters;
	private JButton submit;
	private JLabel result;
	
	public void game() {	
		//Current row for guessing
		curr_row = 0;
		//Create the Frame
		frame = new JFrame(TITLE);
		frame.setSize(WIN_WIDTH,WIN_HEIGHT);
		letters = new JTextField[NUM_OF_TRIES][WORD_LEN];
		//Create a grid layout
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints grid_cons = new GridBagConstraints();
		frame.setLayout(grid);
		
		//insert the text fields for each letter
		for(int i=0; i<NUM_OF_TRIES; i++){
			for(int j=0; j<WORD_LEN; j++){
				letters[i][j] = new JTextField("",1);
				Font f = new Font(WORD_FONT_TYPE,Font.BOLD,WORD_FONT_SIZE);
				letters[i][j].setBackground(Color.gray);
				letters[i][j].setFont(f);
				letters[i][j].setEditable(false);
				letters[i][j].setHorizontalAlignment(JTextField.CENTER);
				JTextField tmp  = letters[i][j];
				//An event to confirm only one letter will be typed in the text field
				letters[i][j].addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						if(tmp.getText().length()>=1){
							evt.consume();
						}
					}
				});
				//Organize the text field in the grid
				grid_cons.fill = GridBagConstraints.HORIZONTAL;
				grid_cons.gridx = j;
				grid_cons.gridy = i;
				frame.add(letters[i][j], grid_cons);
			}
		}
		
		grid_cons.fill = GridBagConstraints.HORIZONTAL;
		grid_cons.gridx = LABEL_X;
		grid_cons.gridy = LABEL_Y;
		grid_cons.gridwidth = LABEL_WIDTH;
		result = new JLabel();
		result.setHorizontalAlignment(JLabel.CENTER);
		frame.add(result, grid_cons);
		
		//Create a submit button to send the word for a check
		submit = new JButton(SUBMIT_TEXT);
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (submit.getText().equals("Enter")) {
					String word = getWord(curr_row);
					Wordle.setWord(word);
					Wordle.gameTime();
				} else {
					Wordle.startGame();
					gameTimeChange();
				}
			}  
	});
	
	//Organize the button in the grid
	grid_cons.fill = GridBagConstraints.HORIZONTAL;
	grid_cons.gridx = BUTTON_X;
	grid_cons.gridy = BUTTON_Y;
	grid_cons.gridwidth = BUTTON_WIDTH;
	frame.add(submit, grid_cons);
	//make the frame visible
	frame.setVisible(true);
}
	
	
	public WordleWin(){
		game();
	}
	
	//Send the word to the Wordle class to check the word
	public String getWord(int row){
		String word="";
		if(row>=0 && row<=NUM_OF_TRIES-1){
			for(int i=0; i<WORD_LEN; i++){
				word += letters[row][i].getText();
			}
		}
		return word;
	}
	public void clearBoard(){
		for(int i=0; i<NUM_OF_TRIES; i++) {
			for(int j=0; j<WORD_LEN; j++){
				letters[i][j].setBackground(Color.gray);
				letters[i][j].setText("");
				letters[i][j].setEditable(false);
			}
		}
	}
	//Enable to row for the user to guess
	public void setCurrentRow(int row){
		if(row>=0 && row<=NUM_OF_TRIES-1){
			curr_row = row;
			for(int i=0; i<WORD_LEN; i++){
				letters[row][i].setBackground(Color.white);
				letters[row][i].setEditable(true);
			}
		}
	}
	//mark if it is a correct letter in the correct position
	public void markFieldCorrectLetterCorrectPosition(int row, int col){
		if(row>=0 && row<=NUM_OF_TRIES-1 && col>=0 && col<=WORD_LEN-1){
			letters[row][col].setBackground(Color.green);
			letters[row][col].setEditable(false);
		}
	}
	//mark if it is a correct letter but not in the correct position
	public void markFieldCorrectLetterNotCorrectPosition(int row, int col){
		if(row>=0 && row<=NUM_OF_TRIES-1 && col>=0 && col<=WORD_LEN-1){
			letters[row][col].setBackground(Color.yellow);
			letters[row][col].setEditable(false);
		}
	}	
	public void markFieldIncorrect(int row, int col){
		if(row>=0 && row<=NUM_OF_TRIES-1 && col>=0 && col<=WORD_LEN-1){
			letters[row][col].setBackground(Color.gray);
			letters[row][col].setEditable(false);
		}
	}
	
	public void setResultText(String text) {
		result.setText(text);
	}
	
	public void gameTimeChange() {
		if (submit.getText().equals("Enter")) {
			submit.setText("Play Again");
		} else {
			submit.setText("Enter");
		}
	}
}
