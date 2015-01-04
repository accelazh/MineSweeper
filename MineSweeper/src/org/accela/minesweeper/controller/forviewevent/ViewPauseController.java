package org.accela.minesweeper.controller.forviewevent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

public class ViewPauseController implements Controller
{
	public static final int PRIORITY = 10;

	private GameModel model = null;

	private GameFrame view = null;

	private WindowListener listener = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.model = model;
		this.view = view;

		this.listener = new WindowAdapter()
		{
			public void windowDeactivated(WindowEvent e)
			{
				ViewPauseController.this.model.pause(true);
			}

			public void windowActivated(WindowEvent e)
			{
				ViewPauseController.this.model.pause(false);
			}
		};
		this.view.addWindowListener(listener);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.view.removeWindowListener(listener);
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
}
