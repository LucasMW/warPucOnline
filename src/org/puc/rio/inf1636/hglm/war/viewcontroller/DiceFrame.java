package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.WarLogic;

//This class controls battle. Attacker and Defender Dice etc.

public class DiceFrame extends JFrame {
	private JPanel attackerPanel;
	private JPanel defenderPanel;
	private JPanel resultPanel;

	private List<JLabel> attackerDice = new LinkedList<JLabel>();
	private List<JLabel> defenderDice = new LinkedList<JLabel>();
	private JLabel resultLabel;

	private JButton attackButton;
	private JButton defendButton;
	private JButton confirmButton;

	private List<Integer> attackResults = new LinkedList<Integer>();
	private List<Integer> defenseResults = new LinkedList<Integer>();

	public DiceFrame() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(String.format("%s is attacking %s", "Territory1", WarGame
				.getInstance().getWarState().getSelectedTerritory().getName()));
		this.setSize(new Dimension(300, 400));
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);

		// attacker panel
		this.attackerPanel = new JPanel();
		this.attackButton = new JButton("attack");
		this.attackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.attackerPanel.setLayout(new BoxLayout(attackerPanel,
				BoxLayout.Y_AXIS));
		ActionListener actLisA = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				rollDice(true);
				checkEnd();
			}
		};
		this.attackButton.addActionListener(actLisA);
		this.attackerPanel.add(this.attackButton);

		// defender panel
		this.defenderPanel = new JPanel();
		this.defenderPanel.setLayout(new BoxLayout(defenderPanel,
				BoxLayout.Y_AXIS));
		this.defendButton = new JButton("defend");
		this.defendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ActionListener actLisB = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				rollDice(false);
				checkEnd();
			}
		};
		this.defendButton.addActionListener(actLisB);
		this.defenderPanel.add(this.defendButton);

		this.generateDice();

		// result panel
		this.resultPanel = new JPanel();
		this.resultLabel = new JLabel();
		this.confirmButton = new JButton("confirm");
		this.resultPanel.add(this.resultLabel);
		this.resultPanel.add(this.confirmButton);
		ActionListener actLisC = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		};
		this.confirmButton.addActionListener(actLisC);
		this.resultPanel.setVisible(false);
		this.getContentPane().add(this.attackerPanel);
		this.getContentPane().add(this.defenderPanel);
		this.getContentPane().add(this.resultPanel);
	}

	private void generateDice() {
		for (int i = 0; i < WarLogic.MAX_DICE; i++) {
			ImageIcon iconA = new ImageIcon("resources/dice/dado_ataque_1.png");
			ImageIcon iconB = new ImageIcon("resources/dice/dado_defesa_1.png");
			JLabel diceA = new JLabel(iconA);
			JLabel diceB = new JLabel(iconB);
			diceA.setVisible(false);
			diceB.setVisible(false);
			diceA.setAlignmentX(Component.CENTER_ALIGNMENT);
			diceB.setAlignmentX(Component.CENTER_ALIGNMENT);

			this.attackerDice.add(diceA);
			this.attackerPanel.add(diceA);
			this.defenderDice.add(diceB);
			this.defenderPanel.add(diceB);
		}
	}

	private void rollDice(boolean attack) {
		// if dice are rolled already
		if ((attack && this.attackResults.size() == 3)
				|| (!attack && this.defenseResults.size() == 3)) {
			return;
		}

		Random rand = new Random();
		for (int i = 0; i < WarLogic.MAX_DICE; i++) {
			int result = rand.nextInt(6) + 1;
			if (attack) {
				this.attackResults.add(result);
			} else {
				this.defenseResults.add(result);
			}
		}
		// sort dice in descending order
		Collections.sort(this.attackResults, Collections.reverseOrder());
		Collections.sort(this.defenseResults, Collections.reverseOrder());

		int i = 0;
		for (int result : attack ? this.attackResults : this.defenseResults) {
			ImageIcon imgX;
			imgX = new ImageIcon(String.format("resources/dice/dado_%s_%d.png",
					attack ? "ataque" : "defesa", result));
			JLabel dice = attack ? attackerDice.get(i) : defenderDice.get(i);
			dice.setIcon(imgX);
			dice.setVisible(true);
			i++;
		}
	}

	private int[] calculateLosses() {
		int attackLosses = 0;
		int defenseLosses = 0;
		for (int i = 0; i < WarLogic.MAX_DICE; i++) {
			if (this.attackResults.get(i) <= this.defenseResults.get(i)) {
				attackLosses++;
			} else {
				defenseLosses++;
			}
		}
		int[] result = { attackLosses, defenseLosses };
		return result;
	}

	private void checkEnd() {
		if (this.attackResults.size() != 3 || this.defenseResults.size() != 3) {
			return;
		}
		int[] losses = calculateLosses();
		this.resultLabel.setText(String.format(
				"Attacker loses %d units and Defender loses %d units",
				losses[0], losses[1]));
		this.resultPanel.setVisible(true);
	}
}
