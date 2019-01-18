package homework3;

import java.util.Random;

import javafx.scene.image.Image;
public class Dice implements Comparable<Dice>{
	
	int result;
	boolean clicked;
	
	
	Image die01 = new Image("file:./res/DiceImages/Dice1.png");
	Image die02 = new Image("file:./res/DiceImages/Dice2.png");
	Image die03 = new Image("file:./res/DiceImages/Dice3.png");
	Image die04 = new Image("file:./res/DiceImages/Dice4.png");
	Image die05 = new Image("file:./res/DiceImages/Dice5.png");
	Image die06 = new Image("file:./res/DiceImages/Dice6.png");
	
	Image die01B = new Image("file:./res/DiceImages/Dice1Held.png");
	Image die02B = new Image("file:./res/DiceImages/Dice2Held.png");
	Image die03B = new Image("file:./res/DiceImages/Dice3Held.png");
	Image die04B = new Image("file:./res/DiceImages/Dice4Held.png");
	Image die05B = new Image("file:./res/DiceImages/Dice5Held.png");
	Image die06B = new Image("file:./res/DiceImages/Dice6Held.png");
	
	Dice(){
		Random randomizer = new Random();
		result = randomizer.nextInt(6)+1;
		clicked = false;
	}
	
	Dice(int roll){
		result = roll;
		clicked = false;
	}
	
	public void reRoll(int roll) {
		result = roll;
	}
	
	public int showRoll() {
		return result;
	}
	
	public void click() {
		if(clicked) {
			clicked = false;
		}
		else {
			clicked = true;			
		}
	}
	
	public void reset() {
		clicked = false;
		
	}
	
	public boolean isHeld() {
		return clicked;
	}
	
	public Image getImage() {
		if(result ==1) {
			if(clicked) {
				return die01B;
			}
			else {
				return die01;			
			}
		}
		else if(result==2) {
			if(clicked) {
				return die02B;
			}
			else {
				return die02;
			}
		}
		else if(result==3) {
			if(clicked) {
				return die03B;
			}
			else {
				return die03;
			}
		}
		else if(result==4) {
			if(clicked) {
				return die04B;
			}
			else {
				return die04;
			}
		}
		else if(result ==5) {
			if(clicked) {
				return die05B;
			}
			else {
				return die05;
			}
		}
		else {
			if(clicked) {
				return die06B;
			}
			else {
				return die06;
			}
		}
	}

	@Override
	public int compareTo(Dice other) {
		// TODO Auto-generated method stub
		if(showRoll()>other.showRoll()) {
			return 1;
		}
		else if(showRoll()<other.showRoll()) {
			return -1;
		}
		else {
			return 0;
		}

	}
	
	public String toString() {
		return Integer.toString(showRoll());
	}
	
}
