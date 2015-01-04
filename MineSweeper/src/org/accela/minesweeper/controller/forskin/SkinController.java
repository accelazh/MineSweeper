package org.accela.minesweeper.controller.forskin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.ui.GameUIManager;
import org.accela.minesweeper.view.GameFrame;

public class SkinController implements Controller
{
	public static final int PRIORITY = 2;

	private JMenu skinMenu = null;
	private JCheckBoxMenuItem[] menus = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.skinMenu = view.getGameMenuBar().getSkin();
		menus = createSkinMenuByUI(
				GameUIManager.getInstance().getSkinNames(),
				view);
		for (JCheckBoxMenuItem mi : menus)
		{
			this.skinMenu.add(mi);
			if (mi.getText().equals(Profile.getInstance().getSkinName()))
			{
				mi.doClick();
			}
		}

	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		for (JCheckBoxMenuItem mi : menus)
		{
			this.skinMenu.remove(mi);
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

	public static JCheckBoxMenuItem createSkinMenuByUI(String name,
			GameFrame frame)
	{
		JCheckBoxMenuItem m = new JCheckBoxMenuItem(name);
		m.addActionListener(new SetSkinOnActionListener(name, frame));

		return m;
	}

	public static JCheckBoxMenuItem[] createSkinMenuByUI(String[] names,
			GameFrame frame)
	{
		ButtonGroup group = new ButtonGroup();
		JCheckBoxMenuItem[] ms = new JCheckBoxMenuItem[names.length];
		for (int i = 0; i < ms.length; i++)
		{
			ms[i] = createSkinMenuByUI(names[i], frame);
			group.add(ms[i]);
		}

		return ms;
	}

	private static class SetSkinOnActionListener implements ActionListener
	{
		private String name;
		private GameFrame frame;

		public SetSkinOnActionListener(String name, GameFrame frame)
		{
			this.name = name;
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			GameUIManager.getInstance().replaceSkinByName(name, frame);
			Profile.getInstance().setSkinName(name);
			
			frame.pack();
			frame.revalidate();
		}
	}

}
