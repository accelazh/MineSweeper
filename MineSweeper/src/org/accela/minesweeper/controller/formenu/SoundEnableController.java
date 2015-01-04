package org.accela.minesweeper.controller.formenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.view.GameFrame;

public class SoundEnableController implements Controller
{
	public static final int PRIORITY = 10;

	private GameFrame frame = null;

	private EnablingListener listener = new EnablingListener();

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.frame = view;
		this.frame.getGameMenuBar().getSound().addActionListener(this.listener);
		
		Profile profile=Profile.getInstance();
		frame.getGameMenuBar().getSound().setSelected(profile.isSoundEnabled());
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.frame.getGameMenuBar().getSound()
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

	private class EnablingListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem m = (JCheckBoxMenuItem) e.getSource();
			Profile.getInstance().setSoundEnabled(m.isSelected());
		}
	}
}
