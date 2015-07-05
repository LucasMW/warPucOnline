package org.puc.rio.inf1636.hglm.war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.puc.rio.inf1636.hglm.war.model.Card;
import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Deck;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;
import org.puc.rio.inf1636.hglm.war.model.TerritoryCard;
import org.puc.rio.inf1636.hglm.war.model.WarState;
import org.puc.rio.inf1636.hglm.war.model.WarState.TurnState;
import org.puc.rio.inf1636.hglm.war.objective.ConquerContinentsObjective;
import org.puc.rio.inf1636.hglm.war.objective.ConquerTerritoriesObjective;
import org.puc.rio.inf1636.hglm.war.objective.DestroyPlayerObjective;
import org.puc.rio.inf1636.hglm.war.objective.WarObjective;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

public class WarGame {

	private static WarGame instance;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private Deck deck;

	private WarFrame warFrame;
	private WarState warState = null;

	private WarGame() {
		this.warFrame = new WarFrame();
		this.map = new Map();
		this.deck = new Deck();
	}

	public static WarGame getInstance() {
		if (WarGame.instance == null) {
			WarGame.instance = new WarGame();
		}
		return WarGame.instance;
	}

	public void startGame() {
		Collections.shuffle(players); // randomize player order
		Util.loadTerritories(this.map, this.deck);
		this.deck.addJoker(10);
		this.deck.shuffle();
		this.warState = new WarState(players.get(0));
		this.giveAwayTerritories();
		this.getMap().calculateNeighbors();
		this.giveObjectiveToPlayers();
		players.get(0).giveArmies(
				WarLogic.calculateArmiesToGain(this.getMap(), players.get(0)));
		for (int i = 0; i < 5; i++) {
			this.giveCardToPlayer(players.get(0));
		}
		this.getWarFrame().update(true);
	}

	public void nextTurn() {
		/* Do nothing when a pop up is active */
		if (warFrame.hasPopupActive()) {
			warFrame.focusPopup();
			return;
		}
		if (this.warState.getConquestsThisTurn() > 0) {
			this.giveCardToPlayer(this.getCurrentPlayer());
		}
		this.getMap().resetMovableArmiesCount();
		this.warState.nextTurn();
		this.warState.getCurrentPlayer().giveArmies(
				WarLogic.calculateArmiesToGain(this.getMap(),
						warState.getCurrentPlayer()));
		for (int i = 0; i < 5; i++) {
			this.giveCardToPlayer(this.getCurrentPlayer());
		}
		if (this.getCurrentPlayer().getCards().size() >= 5) {
			this.warFrame
					.spawnCardSelectionFrame(this.getCurrentPlayer(), true);
		}
		this.warFrame.update(false);
	}

	public Map getMap() {
		return this.map;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getCurrentPlayer() {
		return this.warState.getCurrentPlayer();
	}

	public int getCurrentPlayerIndex() {
		return this.getPlayers().indexOf(warState.getCurrentPlayer());
	}

	public TurnState getTurnState() {
		return this.warState.getCurrentState();
	}

	public WarState getState() {
		return this.warState;
	}

	public WarFrame getWarFrame() {
		return this.warFrame;
	}

	private void giveAwayTerritories() {
		List<Integer> indexes = new ArrayList<Integer>();
		Iterator<Player> pi = this.getPlayers().iterator();
		for (int i = 0; i < this.getMap().getTerritories().size(); i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		for (Integer i : indexes) {
			if (!pi.hasNext()) {
				pi = this.getPlayers().iterator(); // loop back
			}
			Player p = pi.next();
			Territory t = this.getMap().getTerritories().get(i);
			t.setOwner(p);

		}
	}

	private void giveObjectiveToPlayers() {
		List<WarObjective> objectives = new ArrayList<WarObjective>();
		objectives.add(new ConquerTerritoriesObjective(18, 2));
		objectives.add(new ConquerTerritoriesObjective(24, 1));
		objectives.add(new ConquerContinentsObjective(Continent.EUROPE,
				Continent.OCEANIA, false));
		objectives.add(new ConquerContinentsObjective(Continent.ASIA,
				Continent.SOUTH_AMERICA, false));
		objectives.add(new ConquerContinentsObjective(Continent.EUROPE,
				Continent.SOUTH_AMERICA, true));
		objectives.add(new ConquerContinentsObjective(Continent.ASIA,
				Continent.AFRICA, false));
		objectives.add(new ConquerContinentsObjective(Continent.NORTH_AMERICA,
				Continent.AFRICA, false));
		objectives.add(new ConquerContinentsObjective(Continent.NORTH_AMERICA,
				Continent.OCEANIA, false));

		for (Player p : this.players) {
			objectives.add(new DestroyPlayerObjective(p));
		}

		Random r = new Random();
		for (Player p : this.players) {
			int index = 0;
			WarObjective objective;
			do {
				index = r.nextInt(objectives.size());
				objective = objectives.get(index);
				/* Disallow player having to destroy himself */
			} while (objective instanceof DestroyPlayerObjective
					&& ((DestroyPlayerObjective) objective).getTargetPlayer()
							.equals(p));
			p.setObjective(objective);
			/* Disallow duplicate objectives */
			objectives.remove(index);
		}
	}

	/* Event handlers */
	public void actionPerformed() {
		/* don't do anything when a pop up is active */
		if (warFrame.hasPopupActive()) {
			warFrame.focusPopup();
			return;
		}
		switch (this.getTurnState()) {
		case ATTACKING:
			this.warState.startMovingArmies();
			this.warFrame.update(false);
			break;
		case MOVING_ARMIES:
			this.getState().clearSelections();
			break;
		case PLACING_NEW_ARMIES:
			if (this.getSelectedTerritory() != null) {
				this.getWarFrame().spawnChooseNumberFrame(
						this.getCurrentPlayer().getUnplacedArmies(),
						String.format("How many do you want to place in %s?",
								this.getSelectedTerritory().getName()));
			}
			break;
		case RECEIVING_LETTER:

			break;
		default:
			break;
		}
		this.warFrame.update(false);
	}

	public void selectTerritory(Territory t) {
		/* don't do anything when a pop up is active */
		if (warFrame.hasPopupActive()) {
			warFrame.focusPopup();
			return;
		}
		switch (this.getTurnState()) {
		case ATTACKING:
			if (this.getSelectedTerritory() == null
					&& t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
			} else if (t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
			} else if (this.getSelectedTerritory().canAttack(t)) {
				this.warState.targetTerritory(t);
				this.getWarFrame().spawnChooseNumberFrame(
						this.getSelectedTerritory().getAtackableArmyCount(),
						String.format("How many to attack from %s to %s with?",
								this.getSelectedTerritory().getName(), this
										.getTargetedTerritory().getName()));
			}
			break;
		case MOVING_ARMIES:
			/* select territory */
			if (this.getSelectedTerritory() == null
					&& t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
				/* re-select territory */
			} else if (t.getOwner().equals(this.getCurrentPlayer())
					&& !this.getSelectedTerritory().canMoveTo(t)) {
				this.warState.selectTerritory(t);
				/* target territory */
			} else {
				this.warState.targetTerritory(t);
				this.getWarFrame().spawnChooseNumberFrame(
						this.getSelectedTerritory().getMoveableArmyCount(),
						String.format("How many to move from %s to %s?", this
								.getSelectedTerritory().getName(), this
								.getTargetedTerritory().getName()));
			}
			break;
		case PLACING_NEW_ARMIES:
			if (t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
			}
			break;
		case RECEIVING_LETTER:

			break;
		default:
			break;
		}
		this.warFrame.update(false);
	}

	public void selectNumber(int number) {
		switch (this.getTurnState()) {
		case ATTACKING:
			/* conquered */
			if (this.getSelectedTerritory().getOwner()
					.equals(this.getTargetedTerritory().getOwner())) {
				this.getMap().moveArmies(this.getSelectedTerritory(),
						this.getTargetedTerritory(), number - 1, true);
			} else {
				this.getWarFrame().spawnAttackFrame(
						this.getSelectedTerritory(),
						this.getTargetedTerritory(), number);
			}
			Player winner = this.checkWinner();
			if (winner != null) {
				this.endGameSequence(winner);
			}
			break;
		case MOVING_ARMIES:
			this.getMap().moveArmies(this.getSelectedTerritory(),
					this.getTargetedTerritory(), number, false);
			this.getState().clearSelections();
			break;
		case PLACING_NEW_ARMIES:
			this.getCurrentPlayer().removeArmies(number);
			this.warState.getSelectedTerritory().addArmies(number);
			if (this.getCurrentPlayer().getUnplacedArmies() <= 0) {
				this.warState.startAttacking();
			}
			break;
		case RECEIVING_LETTER:
			break;
		default:
			break;

		}
		this.warFrame.update(false);
	}

	public void attackResult(int[] losses) {
		if (this.getState().isAttacking()) {
			this.getSelectedTerritory().removeArmies(losses[0]);
			this.getTargetedTerritory().removeArmies(losses[1]);

			/* attacker conquered */
			if (this.getTargetedTerritory().getArmyCount() == 0) {
				this.getState().addConquestThisTurn();
				int maxToMove = this.getMap().conquerTerritory(
						this.getSelectedTerritory(),
						this.getTargetedTerritory());
				this.warFrame.spawnChooseNumberFrame(maxToMove, String.format(
						"How many armies to move from %s to %s?", this
								.getSelectedTerritory().getName(), this
								.getTargetedTerritory().getName()));
			}
		}
		this.warFrame.update(false);
	}

	/* End event handlers */

	public Territory getTargetedTerritory() {
		return this.warState.getTargetedTerritory();
	}

	public Territory getSelectedTerritory() {
		return this.warState.getSelectedTerritory();
	}

	public Deck getDeck() {
		return deck;
	}

	public void giveCardToPlayer(Player p) {
		Card c = this.deck.takeCard();
		p.addCard(c);
	}

	public void receiveCardFromPlayer(Player p, TerritoryCard c) {
		p.removeCard(c);
		this.deck.returnCard(c);
	}

	public Player checkWinner() {
		for (Player p : this.players) {
			if (p.checkVictory()) {
				return p;
			}
		}
		return null;
	}

	private void endGameSequence(Player p) {
		System.out.println("GAME FINISHED");
		System.out.println("Winner is " + p.getName() + " "
				+ p.getObjective().getDescription());
	}

	public void showObjective() {
		this.getWarFrame().spawnTextFrame(
				this.getCurrentPlayer().getObjective().getDescription());
	}

	public void showCards() {
		this.getWarFrame().spawnCardSelectionFrame(this.getCurrentPlayer(),
				false);
	}

	public void exchangeCards(List<Card> selectedCards) {
		if (this.getState().isPlacing()) {
			for (Card tc : selectedCards) {
				this.getCurrentPlayer().removeCard(tc);
			}
			this.getCurrentPlayer().giveArmies(
					this.getState().getCardExchangeArmyCount());
			this.getState().incrementCardExchangeArmyCount();
			this.warFrame.update(false);
		}
	}
}
