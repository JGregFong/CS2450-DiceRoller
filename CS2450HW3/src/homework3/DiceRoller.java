package homework3;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class DiceRoller extends Application{
	
	//Jonathan Fong
	//CS 2450 Homework3
	VBox vbox;
	HBox diceRow;
	
	Button rollDice;
	
	Scene scene;
	
	Random randomizer;
	
	private Label overallScore;
	private Label roundScore;
	private Label remainingRolls;
	
	int rollsRemaining;
	int wholeScore;
	int turnScore;
	boolean finished = false;
	boolean startRound = true;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	ImageView imageView5;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		randomizer = new Random();
		
		Dice die1 = new Dice(1);
		Dice die2 = new Dice(1);
		Dice die3 = new Dice(1);
		Dice die4 = new Dice(1);
		Dice die5 = new Dice(1);
		
		Dice[] hand = {die1, die2, die3, die4, die5};
		//ImageView [] handPics = {imageView1, imageView2, imageView3, imageView4, imageView5};
		
		rollsRemaining = 3;		
		wholeScore = 0;
		turnScore = 0;
		
		overallScore = new Label("Overall Score: " + wholeScore);
		roundScore = new Label("Your Score: " + turnScore);
		remainingRolls = new Label("Rolls Remaining: "+rollsRemaining);
		
		imageView1 = new ImageView(die1.getImage());
		imageView2 = new ImageView(die2.getImage());
		imageView3 = new ImageView(die3.getImage());
		imageView4 = new ImageView(die4.getImage());
		imageView5 = new ImageView(die5.getImage());

		
		rollDice = new Button("Roll Dice");
		startRound= true;
		rollDice.setOnAction(event->{
			
			if(finished) {
				rollsRemaining = 3;
				turnScore = 0;
				startRound = true;
				finished = false;
				rollDice.setText("Roll Dice.");
				for (int i = 0; i<hand.length; i++) {
					hand[i].reset();
				}
				roundScore.setText("Your Score: " + turnScore);
				remainingRolls.setText("Rolls Remaining: " + rollsRemaining);
			}
			else {

				--rollsRemaining;
				remainingRolls.setText("Rolls Remaining: " + rollsRemaining);				
				if(rollsRemaining >=0 ) {
					startRound = false;

					if(!die1.isHeld()) {
						die1.reRoll(randomizer.nextInt(6)+1);
						imageView1.setImage(die1.getImage());
					}
					if(!die2.isHeld()) {
						die2.reRoll(randomizer.nextInt(6)+1);
						imageView2.setImage(die2.getImage());	

					}
					if(!die3.isHeld()) {
						die3.reRoll(randomizer.nextInt(6)+1);
						imageView3.setImage(die3.getImage());	

					}
					if(!die4.isHeld()) {
						die4.reRoll(randomizer.nextInt(6)+1);
						imageView4.setImage(die4.getImage());	

					}
					if(!die5.isHeld()) {
						die5.reRoll(randomizer.nextInt(6)+1);
						imageView5.setImage(die5.getImage());	

					}
				}
			
			
				if(rollsRemaining ==0) {
					
					rollDice.setText("Play Again");
					finished = true;
					
					turnScore = calculateScore(hand);
					
					wholeScore+=turnScore;
					roundScore.setText("Your Score: " + turnScore);
					overallScore.setText("Overall Score: " + wholeScore);
					
			}
			}

			
		});
		
		imageView1.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			if(!startRound && !finished) {
				die1.click();
				imageView1.setImage(die1.getImage());	
			}
		});
		imageView2.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			if(!startRound && !finished) {
				die2.click();
				imageView2.setImage(die2.getImage());
			}
		});
		imageView3.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			if(!startRound && !finished) {
				die3.click();
				imageView3.setImage(die3.getImage());
			}
		});
		imageView4.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			if(!startRound && !finished) {
				die4.click();
				imageView4.setImage(die4.getImage());
			}	
		});
		imageView5.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			if(!startRound && !finished) {
				die5.click();
				imageView5.setImage(die5.getImage());
			}
		});
		
		

		diceRow = new HBox(10, imageView1, imageView2, imageView3, imageView4, imageView5);
		
		vbox = new VBox(10, overallScore, diceRow, rollDice, roundScore, remainingRolls);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		scene = new Scene(vbox);
		scene.getStylesheets().add("file:./src/homework3/mystyles.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Dice Game");
		primaryStage.show();
		
	}
	
	public int calculateScore(Dice[] hand) {
		Dice[] sortedHand = hand;
		Arrays.sort(sortedHand);

			int [] counter = new int [6];
			for(int i = 0; i < sortedHand.length; i++) {
				if(sortedHand[i].showRoll() == 1) {
					++counter[0];
				}
				else if (sortedHand[i].showRoll() == 2) {
					++counter[1];
				}
				else if (sortedHand[i].showRoll() == 3) {
					++counter[2];
				}
				else if (sortedHand[i].showRoll() == 4) {
					++counter[3];
				}
				else if (sortedHand[i].showRoll() == 5) {
					++counter[4];
				}
				else if (sortedHand[i].showRoll() == 6) {
					++counter[5];
				}
			}
			return handEval(counter);
		
	}
	
	public int handEval(int [] count) {
		//Five of a Kind.
		if(count[0] ==5 || count[1]==5 || count[2]==5 || count[3]==5 || count[4]==5 || count[5]==5) {
			remainingRolls.setText("Five of a Kind!");
			return 10;
		}
		else if ((count[0]==1 && count[1]==1 && count[2]==1 && count[3]==1 && count[4]==1)||
				(count[1]==1 && count[2]==1 && count[3]==1 && count[4]==1 && count[5]==1)) {
			remainingRolls.setText("Straight!");
			return 8;
		}
		//Four of a Kind.
		else if(count[0] ==4 || count[1]==4 || count[2]==4 || count[3]==4 || count[4]==4  || count[5]==4) {
			remainingRolls.setText("Four of a Kind!");
			return 7;
		}
		else if (count[0]==3) {
			//Full House
			if(count[1]==2 || count[2]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		else if(count[1]==3) {
			//Full House
			if(count[0]==2 || count[2]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		else if(count[2] ==3 ) {
			//Full House
			if(count[0]==2 || count[1]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		else if(count[3]==3) {
			//Full House
			if(count[0]==2 || count[1]==2 || count[2]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}		
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		else if(count[4]==3) {
			//Full House
			if(count[0]==2 || count[1]==2 || count[2]==2 || count[3]==2 || count[5]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		
		else if(count[5]==3) {
			//Full House
			if(count[0]==2 || count[1]==2 || count[2]==2 || count[3]==2 || count[4]==2) {
				remainingRolls.setText("Full House!");
				return 6;
			}
			//Three of a Kind.
			else {
				remainingRolls.setText("Three of a Kind!");
				return 5;
			}
		}
		
		else if (count[0]==2) {
			if(count[1]==2|| count[2]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else if (count[1]==2) {
			if(count[0]==2|| count[2]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else if (count[2]==2) {
			if(count[0]==2|| count[1]==2 || count[3]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else if (count[3]==2) {
			if(count[0]==2|| count[1]==2 || count[2]==2 || count[4]==2 || count[5]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else if (count[4]==2) {
			if(count[0]==2|| count[1]==2 || count[2]==2 || count[3]==2 || count[5]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else if (count[5]==2) {
			if(count[0]==2|| count[1]==2 || count[2]==2 || count[3]==2 || count[4]==2) {
				remainingRolls.setText("Two Pairs!");
				return 4;
			}
			else {
				remainingRolls.setText("One Pair!");
				return 1;
			}
		}
		else {
			remainingRolls.setText("Sorry, Nothing!");
			return 0;
		}
		

	}

}
