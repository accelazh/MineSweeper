package org.accela.minesweeper.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class StatusPanel extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	private NumberPanel leftNumber = new NumberPanel();

	private NumberPanel rightNumber = new NumberPanel();

	private SmilePanel smile = new SmilePanel();

	public StatusPanel()
	{
		this.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.add(leftNumber);
		panel.setOpaque(false);
		this.add(panel);

		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(smile);
		panel.setOpaque(false);
		this.add(panel);

		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panel.add(rightNumber);
		panel.setOpaque(false);
		this.add(panel);
	}

	public NumberPanel getLeftNumber()
	{
		return leftNumber;
	}

	public NumberPanel getRightNumber()
	{
		return rightNumber;
	}

	public SmilePanel getSmile()
	{
		return smile;
	}

}
