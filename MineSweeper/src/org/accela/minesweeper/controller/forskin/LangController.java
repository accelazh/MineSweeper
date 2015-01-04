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

//NOTE: LangController必须放在SkinController前头
public class LangController implements Controller
{
	public static final int PRIORITY = 1;

	private JMenu langMenu = null;
	private JCheckBoxMenuItem[] menus = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.langMenu = view.getGameMenuBar().getLang();
		menus = createLangMenuByUI(
				GameUIManager.getInstance().getLangNames(),
				view);
		for (JCheckBoxMenuItem mi : menus)
		{
			this.langMenu.add(mi);
			if (mi.getText().equals(Profile.getInstance().getLangName()))
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
			this.langMenu.remove(mi);
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

	public static JCheckBoxMenuItem createLangMenuByUI(String name,
			GameFrame frame)
	{
		JCheckBoxMenuItem m = new JCheckBoxMenuItem(name);
		m.addActionListener(new SetLangOnActionListener(name, frame));

		return m;
	}

	public static JCheckBoxMenuItem[] createLangMenuByUI(String[] names,
			GameFrame frame)
	{
		ButtonGroup group = new ButtonGroup();
		JCheckBoxMenuItem[] ms = new JCheckBoxMenuItem[names.length];
		for (int i = 0; i < ms.length; i++)
		{
			ms[i] = createLangMenuByUI(names[i], frame);
			group.add(ms[i]);
		}

		return ms;
	}

	private static class SetLangOnActionListener implements ActionListener
	{
		private String name;
		private GameFrame frame;

		public SetLangOnActionListener(String name, GameFrame frame)
		{
			this.name = name;
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			GameUIManager.getInstance().replaceLangByName(name, frame);
			Profile.getInstance().setLangName(name);
			
			frame.pack();
			frame.revalidate();
		}
	}
}
