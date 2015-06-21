package org.puc.rio.inf1636.hglm.war.model;

import java.awt.Color;

import org.puc.rio.inf1636.hglm.war.WarGame;

public class WarLogic 
{
	WarGame game; //this will be accessed too many times
	public enum TurnState
	{
		PLACING_NEW_ARMIES,
		ATTACKING,
		MOVING_ARMIES,
		RECEIVING_LETTER
		
	}
//	public static TurnState[] states = {  //all Turn States
//		TurnState.PLACING_NEW_ARMIES, 
//		TurnState.ATTACKING, 
//		TurnState.MOVING_ARMIES, 
//		TurnState.RECEIVING_LETTER
//		};
	private int currentStateIndex;
	
	public TurnState getCurrentState()
	{
		return TurnState.values()[this.currentStateIndex];
	}
	public WarLogic()
	{
		this.game = WarGame.getInstance();
	}
	public int getCurrentTurnStateIndex()
	{
		return this.currentStateIndex;
	}
	public boolean giveReinforcements()
	{
		if(this.getCurrentState() != TurnState.PLACING_NEW_ARMIES ){
			System.out.printf("Error In Call: State %d differs from state %d \n",1,this.currentStateIndex);
			return false; //this cannot be called
			
		}
		Player currentPlayer = game.getCurrentPlayer();
		currentPlayer.setUnsetArmiesNumber(currentPlayer.getNumberOfTerritories()/2); //integer division as specified in manual
		return true;
	}
	private void  advanceState()
	{
		this.currentStateIndex++;
		if (this.currentStateIndex >= TurnState.values().length) { //means it once repeated
			this.currentStateIndex = 0;
		}
	}
	public boolean reinforcementsPlaced()
	{
		if(game.getCurrentPlayer().getUnsetArmiesNumber()!=0)
		{
			System.out.println("Should Place All reinforcements first");
			return false;
		}
		this.advanceState(); // Time to attack
		return true;
		
	}
	
	
	
	

	

}
