package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

	private List<JLabel> attackerDices = new ArrayList<JLabel>();
	private List<JLabel> defenderDices = new ArrayList<JLabel>();
	
	private JButton attackButton;
	private JButton defendButton;
	private JButton confirmButton;
	private int attackTotal;
	private int defendTotal;
	private boolean flagA = false, flagB = false;
	private Dimension frameSize;

	public DiceFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War Dice Window Feel Lucky?");
		attackerPanel = new JPanel();
		defenderPanel = new JPanel();
		attackerPanel.setLayout(new BoxLayout(attackerPanel, BoxLayout.Y_AXIS));
		defenderPanel.setLayout(new BoxLayout(defenderPanel, BoxLayout.Y_AXIS));
		frameSize = WarGame.getGameSize();
		frameSize.height = (int) (frameSize.height);
		frameSize.width = frameSize.height;

		System.out.print(frameSize);
		this.setSize(frameSize);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		this.getContentPane().add(attackerPanel);
		this.getContentPane().add(defenderPanel);
		this.attackButton = new JButton("attack");
		this.defendButton = new JButton("defend");
		ActionListener actLisA = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("attack!");
				attackButton();
				checkVictory();
			}
		};
		ActionListener actLisB = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("defend!");
				defendButton();
				checkVictory();
			}
		};
		attackButton.addActionListener(actLisA);
		defendButton.addActionListener(actLisB);
		attackerPanel.add(attackButton);
		defenderPanel.add(defendButton);
		this.generateDices();

		confirmPanel = new JPanel();
		this.getContentPane().add(confirmPanel);
		this.confirmButton = new JButton("confirm");
		confirmButton.setVisible(false);
		confirmPanel.add(confirmButton);
		ActionListener actLisC = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Confirm");
				nextTurn();
			}
		};
		confirmButton.addActionListener(actLisC);

	}

	private void nextTurn() {
		WarGame.getInstance().nextTurn();
		this.dispose();
	}

	private void generateDices() {
		for (int i = 0; i < WarGame.MAX_DICES; i++) {

			Image imgA = new ImageIcon("resources/dices/dado_ataque_1.png")
					.getImage(); // this generates an image file
			Image imgB = new ImageIcon("resources/dices/dado_defesa_1.png")
					.getImage();
			ImageIcon iconA = new ImageIcon(imgA);
			ImageIcon iconB = new ImageIcon(imgB);
			JLabel diceA = new JLabel(iconA);
			// diceA.setBackground(Color.red);
			JLabel diceB = new JLabel(iconB);
			// diceB.setBackground(Color.yellow);
			diceA.setVisible(false);
			diceB.setVisible(false);

			attackerDices.add(diceA);
			attackerPanel.add(diceA);
			defenderDices.add(diceB);
			defenderPanel.add(diceB);

		}

	}

	private int attackButton() {
		int sum = 0;
		Random rand = new Random();
		for (int i = 0; i < WarGame.MAX_DICES; i++) {
			JLabel dice = attackerDices.get(i);
			ImageIcon imgX;
			int x = rand.nextInt((6 - 1) + 1) + 1;
			imgX = new ImageIcon(String.format(
					"resources/dices/dado_ataque_%d.png", x));
			dice.setIcon(imgX);
			dice.setVisible(true);
			sum += x;
		}
		flagA = true;
		return sum;
	}

	private int defendButton() {
		Random rand = new Random();
		int sum = 0;
		for (JLabel dice : defenderDices) {
			ImageIcon imgX;
			int x = rand.nextInt((6 - 1) + 1) + 1;
			imgX = new ImageIcon(String.format(
					"resources/dices/dado_defesa_%d.png", x));
			dice.setIcon(imgX);
			dice.setVisible(true);
			sum += x;

		}
		flagB = true;
		return sum;
	}

	private void checkVictory() {
		int answer;
		if (flagA && flagB) {
			answer = attackTotal - defendTotal;
			if (answer > 0)// attacker wins
			{
				System.out.println("Attacker wins");
			} else // defenders have priority
			{
				System.out.println("Defender wins");
			}
			confirmButton.setVisible(true);

		}
	}

}
