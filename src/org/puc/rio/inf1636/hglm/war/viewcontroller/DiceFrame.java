package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.puc.rio.inf1636.hglm.war.model.Player;

//This class controls battle. Attacker and Defender Dices etc.

public class DiceFrame extends JFrame 
{
	private JPanel attackerPanel;
	private JPanel defenderPanel;
	private List<JLabel> attackerDices = new ArrayList<JLabel>();
	private List<JLabel> defenderDices = new ArrayList<JLabel>();
	private int numberOfAttackDices=3; //the rules I remember
	private JButton attackButton;
	private JButton defendButton;
	
	Dimension frameSize;
	public DiceFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War Dice Window Feel Lucky?");
		attackerPanel = new JPanel();
		defenderPanel = new JPanel();
		attackerPanel.setLayout(new BoxLayout(attackerPanel, BoxLayout.Y_AXIS));
		defenderPanel.setLayout(new BoxLayout(defenderPanel, BoxLayout.Y_AXIS));
		frameSize = WarFrame.getGameSize();
		frameSize.height = (int)(frameSize.height);
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
		
		
		attackerPanel.add(attackButton);
		
		defenderPanel.add(defendButton);
		this.generateDices();
		
	}
	private void generateDices()
	{
		 for(int i=0; i<numberOfAttackDices; i++)
		 {

			 JLabel diceA = new JLabel("dice");
			 diceA.setBackground(Color.red);
			 JLabel diceB = new JLabel("dice");
			 diceB.setBackground(Color.yellow);
			 attackerDices.add(diceA);
			 attackerPanel.add(diceA);
			 defenderDices.add(diceB);
			 defenderPanel.add(diceB);
			 try // I don`t understand why it`s not working
			 {
				 Image imgA=  new ImageIcon("resources/Dados/dado_ataque_1.png").getImage(); //this generates an image file
				 Image imgB=  new ImageIcon("resources/Dados/dado_defesa_1.png").getImage();
				 ImageIcon iconA = new ImageIcon(imgA);
				 ImageIcon iconB=  new ImageIcon(imgB);
				 diceA.setIcon(iconA);
				 diceB.setIcon(iconB);
			 }
			 catch( Exception e)
			 {
				 System.out.println(e.getMessage());
			 }
			
			 
			
			 
		 }
		
	}

}
