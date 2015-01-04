package org.accela.minesweeper.controller.formenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

public class NewRoundController implements Controller
{
	public static final int PRIORITY = 10;

	private GameModel model = null;

	private GameFrame frame = null;

	private NewRoundListener listener = new NewRoundListener();

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.model = model;
		this.frame = view;
		this.frame.getGameMenuBar().getStart().addActionListener(this.listener);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.frame.getGameMenuBar().getStart()
				.removeActionListener(this.listener);
	}

	@Override
	public String getName()
	{
		return this.getClass().getName();
	}

	@Override
	public double getPriorityOnInstallation()
	{
		return PRIORITY;
	}

	private class NewRoundListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			model.reset();
		}
	}
}
