
import java.util.*;
import java.lang.Thread;

public class Wordle {
	
	private static int row = 0;
	private static boolean inGame = true;
	private static boolean newGame = true;
	private static boolean turnTime = false;
	private static String word = new String();
	private static String answer = new String();
	private static ArrayList<Boolean> letters = new ArrayList<Boolean>();
	
	public static void setWord(String wd){
		word = wd;
	}
	
	public static void gameTime() {
		turnTime = true;
	}
	
	public static boolean checkCorrectLetter(int num) {
		return answer.substring(num, num + 1).equals(word.substring(num, num + 1));
	}
	
	public static int letterInWord(int num) {
		for (int j = 0 ; j < 5 ; j++) {
			if(letters.get(j)==true)
				continue;
			if (word.substring(num, num + 1).equals(answer.substring(j, j + 1))) {
				return j;
			}
		}
		return -1;
	}
	
	public static void startGame() {
		newGame = true;
		letters.clear();
	}
	
	public static void game(WordleWin wrdl, Dictionary d) throws InterruptedException {
		row = 0;
		inGame = true;
		turnTime = false;
		wrdl.clearBoard();
		wrdl.setCurrentRow(row);
		wrdl.setResultText("Enter a word!");
		
		answer = d.getRandomWord();

		System.out.println("Answer: " + answer);
		
		
		while (inGame) {
			if (turnTime) {
				letters.clear();

				for (int i = 0 ; i < 5 ; i++) {
					letters.add(false);
				}
				if (!(d.wordExists(word))) {
					wrdl.setResultText("Illegal word! Try again");
					turnTime = false;
					continue;
				}
				row++;
				wrdl.setCurrentRow(row);
				if (row == 6) {
					wrdl.setResultText("The answer was: " + answer);
					inGame = false;
					wrdl.gameTimeChange();
					break;
				}
				if (word.equals(answer)) {
					wrdl.setResultText("You won!");
					for (int i = 0 ; i < 5 ; i++) {
						wrdl.markFieldIncorrect(row, i); 
					}
					wrdl.gameTimeChange();
					inGame = false;
				}
				else {
					wrdl.setResultText("Incorrect, try again...");
				}
				int j = -1;
				for (int i = 0 ; i < 5 ; i++) {
					if (checkCorrectLetter(i)) {
						letters.set(i, true);
						wrdl.markFieldCorrectLetterCorrectPosition(row - 1, i);
					}
					else {
						j = letterInWord(i);
						if (j !=-1) {
							wrdl.markFieldCorrectLetterNotCorrectPosition(row - 1, i);
							letters.set(j, true);
						}
						else {
							wrdl.markFieldIncorrect(row - 1, i);
						}
					}
				}
				turnTime = false;
			}
			Thread.sleep(100);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		WordleWin wrdl = new WordleWin();
		Dictionary d = new Dictionary();
		while(true) {
			if(newGame) {
				newGame=false;
				game(wrdl, d);
			}
			Thread.sleep(100);
		}
	}
}
