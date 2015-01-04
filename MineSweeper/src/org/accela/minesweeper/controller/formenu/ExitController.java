package org.accela.minesweeper.controller.formenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

public class ExitController implements Controller
{
	public static final int PRIORITY=10;

	private GameFrame frame = null;

	private ExitListener listener = new ExitListener();

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.frame = view;
		this.frame.getGameMenuBar().getExit().addActionListener(this.listener);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.frame.getGameMenuBar().getExit()
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

	private class ExitListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			frame.close();
		}
	}
}
