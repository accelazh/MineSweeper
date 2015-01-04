package org.accela.minesweeper.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.accela.minesweeper.controller.fordialog.CustomMineFieldController;
import org.accela.minesweeper.controller.fordialog.HighScoreController;
import org.accela.minesweeper.controller.formenu.ExitController;
import org.accela.minesweeper.controller.formenu.HelpMenuController;
import org.accela.minesweeper.controller.formenu.LevelSelectionController;
import org.accela.minesweeper.controller.formenu.NewRoundController;
import org.accela.minesweeper.controller.formenu.QuestionMarkEnablingController;
import org.accela.minesweeper.controller.formenu.SoundEnableController;
import org.accela.minesweeper.controller.formodelupdate.ModelUpdateController;
import org.accela.minesweeper.controller.forskin.LangController;
import org.accela.minesweeper.controller.forskin.SkinController;
import org.accela.minesweeper.controller.forsound.SoundController;
import org.accela.minesweeper.controller.forviewevent.ViewEventController;
import org.accela.minesweeper.controller.forviewevent.ViewPauseController;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

import java.util.Comparator;

public class ControllerManager
{
	private static ControllerManager instance = null;

	public static ControllerManager getInstance()
	{
		if (null == instance)
		{
			instance = new ControllerManager();
		}

		return instance;
	}

	// ===================================================================

	private List<Controller> controllers = new LinkedList<Controller>();

	protected ControllerManager()
	{
		controllers.add(new CustomMineFieldController());
		controllers.add(new HighScoreController());

		controllers.add(new ExitController());
		controllers.add(new HelpMenuController());
		controllers.add(new LevelSelectionController());
		controllers.add(new NewRoundController());
		controllers.add(new QuestionMarkEnablingController());
		controllers.add(new SoundEnableController());

		controllers.add(new ModelUpdateController());

		controllers.add(new LangController());
		controllers.add(new SkinController());

		controllers.add(new SoundController());

		controllers.add(new ViewEventController());
		controllers.add(new ViewPauseController());

		Collections.sort(controllers, new Comparator<Controller>()
		{
			@Override
			public int compare(Controller o1, Controller o2)
			{
				double p1 = o1.getPriorityOnInstallation();
				double p2 = o2.getPriorityOnInstallation();

				if (p1 > p2)
				{
					return 1;
				}
				else if (p1 < p2)
				{
					return -1;

				}
				else
				{
					return 0;
				}
			}
		});
	}

	public void install(GameModel model, GameFrame view)
	{
		for (Controller c : controllers)
		{
			c.install(model, view);
		}
	}

	public void uninstall(GameModel model, GameFrame view)
	{
		for (int i = controllers.size() - 1; i >= 0; i--)
		{
			Controller c = controllers.get(i);
			c.uninstall(model, view);
		}
	}
}
