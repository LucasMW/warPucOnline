package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.puc.rio.inf1636.hglm.war.WarGame;

@SuppressWarnings("serial")
public class ChooseNumberFrame extends JFrame implements ActionListener {

	public ChooseNumberFrame(int maxNumber, String message) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(message);
		this.setSize(new Dimension(300, 100));
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);

		JLabel instructionLabel = new JLabel(message);

		String[] options = new String[maxNumber];
		for (int i = 0; i < maxNumber; i++) {
			options[i] = ((Integer) (i + 1)).toString();
		}
		JComboBox<String> numberOptions = new JComboBox<String>(options);
		numberOptions.addActionListener(this);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});

		this.add(instructionLabel, BorderLayout.PAGE_START);
		this.add(numberOptions, BorderLayout.CENTER);
		this.add(cancelButton, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		String value = (String) cb.getSelectedItem();
		WarGame.getInstance().selectNumber(Integer.parseInt(value));
		this.dispose();
	}
}
