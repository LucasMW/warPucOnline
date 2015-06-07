package org.puc.rio.inf1636.hglm.war.viewcontroller;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.puc.rio.inf1636.hglm.war.WarGame;

public class WarFrame extends JFrame {


	private UIPanel uiPanel;
	private MapPanel mapPanel;

	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		System.out.print(WarGame.getGameSize());
		this.setSize(WarGame.getGameSize());
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		this.addPanels();
		this.setVisible(true);
	}

	private void addPanels() {
		this.mapPanel = new MapPanel();
		this.uiPanel = new UIPanel();
		this.getContentPane().add(mapPanel);
		this.getContentPane().add(uiPanel);
	}
	
	public MapPanel getMapPanel() {
		return this.mapPanel;
	}
	
	public UIPanel getUIPanel() {
		return this.uiPanel;
	}

	public void selectedTerritory() {
		uiPanel.updateSelectedLabel();
	}

	public void battleEnded() {
		uiPanel.switchPlayer();
	}

}
