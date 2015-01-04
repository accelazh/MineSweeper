package org.accela.minesweeper.controller.fordialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JOptionPane;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.HighScore;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.profile.Profile.Difficulty;
import org.accela.minesweeper.util.ObjectActionEvent;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.HighScoreDialog;

public class HighScoreController implements Controller
{
	public static final int PRIORITY=10;
	
	private HighScoreDialog dialog = null;

	private GameModel model = null;

	private GameFrame frame = null;

	private ActionListener newHighScoreListener = new NewHighScoreListener();

	private ComponentListener showListener = new ShowListener();

	private ActionListener resetListener = new ResetListener();

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.dialog = view.getHighScoreDialog();
		this.frame = view;

		model.addActionListener(newHighScoreListener);
		this.dialog.addComponentListener(showListener);
		this.dialog.getPanel().getResetButton()
				.addActionListener(resetListener);

	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.model.removeActionListener(newHighScoreListener);
		this.dialog.removeComponentListener(showListener);
		this.dialog.getPanel().getResetButton()
				.removeActionListener(resetListener);
	}

	private void loadDataToDialog()
	{
		Profile profile = Profile.getInstance();
		HighScoreDialog.Panel panel = dialog.getPanel();
		panel.getLowScoreLabel().setText(
				profile.getHighScore(Difficulty.BEGINNER).score + "");
		panel.getLowScoreNameLabel().setText(
				profile.getHighScore(Difficulty.BEGINNER).name);
		panel.getMiddleScoreLabel().setText(
				profile.getHighScore(Difficulty.INTERMEDIATE).score + "");
		panel.getMiddleScoreNameLabel().setText(
				profile.getHighScore(Difficulty.INTERMEDIATE).name);
		panel.getHighScoreLabel().setText(
				profile.getHighScore(Difficulty.EXPERT).score + "");
		panel.getHighScoreNameLabel().setText(
				profile.getHighScore(Difficulty.EXPERT).name);
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

	private class NewHighScoreListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (!e.getActionCommand().equals(GameModel.GAME_WIN_ACTION_COMMAND))
			{
				return;
			}

			ObjectActionEvent oe = (ObjectActionEvent) e;
			int newHighScore = (Integer) oe.getObject();
			if (!Profile.getInstance().isHighScore(
					Profile.getInstance().getDifficulty(),
					newHighScore))
			{
				return;
			}

			String playerName = JOptionPane.showInputDialog(
					frame,
					frame.getInputYourNameDialogUIRes().getMessage(),
					frame.getInputYourNameDialogUIRes().getTitle(),
					JOptionPane.INFORMATION_MESSAGE);
			playerName.trim();
			if (playerName.length() <= 0)
			{
				playerName = "unnamed";
			}

			Profile.getInstance().setHighScore(
					Profile.getInstance().getDifficulty(),
					new HighScore(newHighScore, playerName));
		}
	}

	private class ShowListener extends ComponentAdapter
	{
		@Override
		public void componentShown(ComponentEvent e)
		{
			loadDataToDialog();
		}
	}

	private class ResetListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Profile.getInstance().resetHighScore();
			loadDataToDialog();
		}
	}
}
