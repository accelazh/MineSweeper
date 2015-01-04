package org.accela.minesweeper.controller.formenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.GameMenuBar;

public class LevelSelectionController implements Controller
{
	public static final int PRIORITY = 10;

	public static final Dimension[] FIELD_SIZE_OF_DIFFICULTY = new Dimension[Profile.Difficulty
			.values().length - 1];
	public static final int[] MINE_COUNT_OF_DIFFICULTY = new int[Profile.Difficulty
			.values().length - 1];

	static
	{
		FIELD_SIZE_OF_DIFFICULTY[Profile.Difficulty.BEGINNER.ordinal()] = new Dimension(
				9, 9);
		FIELD_SIZE_OF_DIFFICULTY[Profile.Difficulty.INTERMEDIATE.ordinal()] = new Dimension(
				16, 16);
		FIELD_SIZE_OF_DIFFICULTY[Profile.Difficulty.EXPERT.ordinal()] = new Dimension(
				30, 16);

		MINE_COUNT_OF_DIFFICULTY[Profile.Difficulty.BEGINNER.ordinal()] = Common.getMinMineCount();
		MINE_COUNT_OF_DIFFICULTY[Profile.Difficulty.INTERMEDIATE.ordinal()] = 40;
		MINE_COUNT_OF_DIFFICULTY[Profile.Difficulty.EXPERT.ordinal()] = 99;
	}

	private JCheckBoxMenuItem[] levelBtns = new JCheckBoxMenuItem[Profile.Difficulty
			.values().length];

	private ActionListener[] listeners = new ActionListener[Profile.Difficulty
			.values().length];

	private GameModel model = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.model = model;
		GameMenuBar bar = view.getGameMenuBar();
		levelBtns[Profile.Difficulty.BEGINNER.ordinal()] = bar.getLevelLow();
		levelBtns[Profile.Difficulty.INTERMEDIATE.ordinal()] = bar
				.getLevelMiddle();
		levelBtns[Profile.Difficulty.EXPERT.ordinal()] = bar.getLevelHigh();
		levelBtns[Profile.Difficulty.CUSTOM.ordinal()] = bar.getLevelCustom();

		for (int i = 0; i < levelBtns.length; i++)
		{
			Profile.Difficulty dif = Profile.Difficulty.values()[i];
			listeners[i] = new LevelListener(dif);

			JCheckBoxMenuItem b = levelBtns[i];
			b.addActionListener(listeners[i]);
		}

		Profile profile = Profile.getInstance();
		if (!profile.getDifficulty().equals(Profile.Difficulty.CUSTOM))
		{
			levelBtns[profile.getDifficulty().ordinal()].doClick();
		}
		else
		{
			levelBtns[profile.getDifficulty().ordinal()].setSelected(true);

			model.setFieldSizeOnStart(profile.getFieldSize());
			model.setMineCountOnStart(profile.getMineCount());
			model.reset();
		}
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		for (int i = 0; i < levelBtns.length; i++)
		{
			JCheckBoxMenuItem b = levelBtns[i];
			b.removeActionListener(listeners[i]);
		}
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

	private class LevelListener implements ActionListener
	{
		private Profile.Difficulty dif;

		public LevelListener(Profile.Difficulty dif)
		{
			this.dif = dif;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Profile.getInstance().setDifficulty(dif);

			if (dif.equals(Profile.Difficulty.CUSTOM))
			{
				// do nothing
				return;
			}

			Dimension fieldSize = FIELD_SIZE_OF_DIFFICULTY[dif.ordinal()];
			int mineCount = MINE_COUNT_OF_DIFFICULTY[dif.ordinal()];

			Profile.getInstance().setFieldSize(fieldSize);
			Profile.getInstance().setMineCount(mineCount);

			model.setFieldSizeOnStart(fieldSize);
			model.setMineCountOnStart(mineCount);
			model.reset();

		}
	}
}
