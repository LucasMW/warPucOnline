package org.puc.rio.inf1636.hglm.war.viewcontroller;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.puc.rio.inf1636.hglm.war.Util;
import org.puc.rio.inf1636.hglm.war.model.Territory;

@SuppressWarnings("serial")
public class WarFrame extends JFrame {

	private UIPanel uiPanel;
	private MapPanel mapPanel;
	private AttackFrame attackFrame;
	private ChooseNumberFrame chooseNumberFrame;

	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		this.setSize(Util.getGameSize());
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

	public void update(boolean first) {
		this.uiPanel.update(first);
		this.mapPanel.update(first);
	}
	
	public void focusPopup() {
		if (this.hasAttackFrameActive()) {
			this.attackFrame.toFront();
		} else if (this.hasChooseNumberFrameActive()) {
			this.chooseNumberFrame.toFront();
		}
	}
	
	public boolean hasAttackFrameActive() {
		return this.attackFrame != null && this.attackFrame.isVisible();
	}
	
	public boolean hasChooseNumberFrameActive() {
		return this.chooseNumberFrame != null && this.chooseNumberFrame.isVisible();
	}
	
	public boolean hasPopupActive() {
		return this.hasAttackFrameActive() || this.hasChooseNumberFrameActive();
	}

	public void spawnAttackFrame(Territory from, Territory to, int number) {
		/* only one at once */
		if (!this.hasPopupActive()) {
			this.attackFrame = new AttackFrame(from, to, number);
			this.attackFrame.setVisible(true);
		}
	}

	public void spawnChooseNumberFrame(int number, String message) {
		/* only one at once */
		if (!this.hasPopupActive()) {
			this.chooseNumberFrame = new ChooseNumberFrame(number, message);
			this.chooseNumberFrame.setVisible(true);
		}
	}
}
