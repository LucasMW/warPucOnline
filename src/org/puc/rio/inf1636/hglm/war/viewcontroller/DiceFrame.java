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

//This class controls battle. Attacker and Defender Dices etc.

public class DiceFrame extends JFrame {
	private JPanel attackerPanel;
	private JPanel defenderPanel;
	private JPanel confirmPanel;

	private List<JLabel> attackerDices = new LinkedList<JLabel>();
	private List<JLabel> defenderDices = new LinkedList<JLabel>();

	private JButton attackButton;
	private JButton defendButton;
	private JButton confirmButton;

	
	private List<Integer> attackResults = new LinkedList<Integer>();
	private List<Integer> defenseResults = new LinkedList<Integer>();
 //	private Dimension frameSize;

	public DiceFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War Dice Window Feel Lucky?");

		this.attackerPanel = new JPanel();
		this.defenderPanel = new JPanel();
		this.attackerPanel.setLayout(new BoxLayout(attackerPanel,
				BoxLayout.Y_AXIS));
		this.defenderPanel.setLayout(new BoxLayout(defenderPanel,
				BoxLayout.Y_AXIS));

		// this.frameSize = WarGame.getGameSize();
		// this.frameSize.height = (int) (frameSize.height);
		// this.frameSize.width = frameSize.height;
		// System.out.print(frameSize);
		// this.setSize(frameSize);

		this.setSize(new Dimension(300, 400));
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);

		this.attackButton = new JButton("attack");
		this.attackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.defendButton = new JButton("defend");
		this.defendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ActionListener actLisA = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("attack!");
				rollDices(true);
				checkEnd();
			}
		};
		ActionListener actLisB = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("defend!");
				rollDices(false);
				checkEnd();
			}
		};
		attackButton.addActionListener(actLisA);
		defendButton.addActionListener(actLisB);
		attackerPanel.add(attackButton);
		defenderPanel.add(defendButton);
		this.generateDices();

		confirmPanel = new JPanel();
		this.confirmButton = new JButton("confirm");
		confirmButton.setVisible(false);
		confirmPanel.add(confirmButton);
		ActionListener actLisC = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				nextTurn();
			}
		};
		confirmButton.addActionListener(actLisC);

		this.getContentPane().add(attackerPanel);
		this.getContentPane().add(defenderPanel);
		this.getContentPane().add(confirmPanel);
	}

	private void nextTurn() {
		WarGame.getInstance().nextTurn();
		this.dispose();
	}

	private void generateDices() {
		for (int i = 0; i < WarGame.MAX_DICES; i++) {
			ImageIcon iconA = new ImageIcon("resources/dices/dado_ataque_1.png");
			ImageIcon iconB = new ImageIcon("resources/dices/dado_defesa_1.png");
			JLabel diceA = new JLabel(iconA);
			JLabel diceB = new JLabel(iconB);
			diceA.setVisible(false);
			diceB.setVisible(false);
			diceA.setAlignmentX(Component.CENTER_ALIGNMENT);
			diceB.setAlignmentX(Component.CENTER_ALIGNMENT);

			attackerDices.add(diceA);
			attackerPanel.add(diceA);
			defenderDices.add(diceB);
			defenderPanel.add(diceB);
		}
	}

	private void rollDices(boolean attack) {
		if (attack && this.attackResults.size() == 3) {
			return;
		}
		if (!attack && this.defenseResults.size() == 3) {
			return;
		}
		
		Random rand = new Random();
		for (int i = 0; i < WarGame.MAX_DICES; i++) {
			int result = rand.nextInt(6) + 1;
			if (attack) {
				this.attackResults.add(result);
			} else {
				this.defenseResults.add(result);
			}
		}
		Collections.sort(this.attackResults, Collections.reverseOrder());
		Collections.sort(this.defenseResults, Collections.reverseOrder());
		
		int i = 0;
		for (int result : attack ? this.attackResults : this.defenseResults) {
			ImageIcon imgX;
			imgX = new ImageIcon(String.format(
					"resources/dices/dado_%s_%d.png", attack ? "ataque"
							: "defesa", result));
			JLabel dice = attack ? attackerDices.get(i) : defenderDices.get(i);
			dice.setIcon(imgX);
			dice.setVisible(true);
			i++;
		}		
	}

	private int[] calculateLosses() {
		int attackLosses = 0;
		int defenseLosses = 0;
		for (int i = 0; i < WarGame.MAX_DICES; i++) {
			if (this.attackResults.get(i) <= this.defenseResults.get(i)) {
				attackLosses++;
			} else {
				defenseLosses++;
			}
		}
		int[] result = {attackLosses, defenseLosses};
		return result;
	}
	
	private void checkEnd() {
		if (this.attackResults.size() != 3 || this.defenseResults.size() != 3) {
			return;
		}
		int[] losses = calculateLosses();
		
		System.out.println(String.format("Attacker loses %d units and Defender loses %d units", losses[0], losses[1]));
		confirmButton.setVisible(true);
	}

}
