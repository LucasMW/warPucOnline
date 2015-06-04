package org.puc.rio.inf1636.hglm.war;

import java.awt.*;
import javax.swing.*;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

public class WarGame {

	public WarGame()
	{
		WarFrame gameFrame = new WarFrame();
		gameFrame.setTitle("War - by Hugo Grochau and Lucas Menezes");
		gameFrame.setVisible(true);
		Rectangle bounds = new Rectangle();
		bounds.height=500;
		bounds.width=1000;
		gameFrame.setBounds(bounds );
		gameFrame.setDefaultCloseOperation(0);
		
		
	}
}
