package org.accela.minesweeper.controller.forsound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.controller.fordialog.HighScoreController;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.sound.SoundManager;
import org.accela.minesweeper.util.ObjectActionEvent;
import org.accela.minesweeper.view.GameFrame;

public class SoundController implements Controller
{
	public static final int PRIORITY = HighScoreController.PRIORITY-1;

	private GameModel model;

	private SoundListener listener = new SoundListener();

	private int lastTimeCount = 0;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.model = model;
		this.model.addActionListener(listener);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		model.removeActionListener(listener);
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

	private class SoundListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (!Profile.getInstance().isSoundEnabled())
			{
				return;
			}

			if (e.getActionCommand().equals(
					GameModel.TIME_COUNTER_UPDATED_ACTION_COMMAND))
			{
				ObjectActionEvent oe = (ObjectActionEvent) e;
				int curTimeCount = (Integer) oe.getObject();
				if (curTimeCount > lastTimeCount)
				{
					SoundManager.getInstance().playByName(SoundManager.TICK);
				}

				lastTimeCount = curTimeCount;
			}
			else if (e.getActionCommand().equals(
					GameModel.GAME_WIN_ACTION_COMMAND))
			{
				SoundManager.getInstance().playByName(SoundManager.WIN);
			}
			else if (e.getActionCommand().equals(
					GameModel.GAME_DEAD_ACTION_COMMAND))
			{
				SoundManager.getInstance().playByName(SoundManager.DEAD);
			}
			else
			{
				// do nothing
			}
		}
	}
}
