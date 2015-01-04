package org.accela.minesweeper.controller.formenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.GameMenuBar;

public class HelpMenuController implements Controller
{
	public static final int PRIORITY = 10;

	private GameFrame frame = null;

	private JMenuItem catalog = null;
	private JMenuItem findHelp = null;
	private JMenuItem userManual = null;
	private JMenuItem about = null;

	private ActionListener listener = new HelpListener();

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.frame = view;

		GameMenuBar bar = view.getGameMenuBar();
		catalog = bar.getCatalog();
		findHelp = bar.getFindHelp();
		userManual = bar.getUserManual();
		about = bar.getAbout();

		catalog.addActionListener(listener);
		findHelp.addActionListener(listener);
		userManual.addActionListener(listener);
		about.addActionListener(listener);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		catalog.removeActionListener(listener);
		findHelp.removeActionListener(listener);
		userManual.removeActionListener(listener);
		about.removeActionListener(listener);
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

	private class HelpListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == about)
			{
				JOptionPane.showMessageDialog(
						frame,
						"---- Mine Sweeper V1.0 ----\n" + "\n"
								+ "---- Developed by: \n" + "    Accela\n"
								+ "---- Originated from: \n"
								+ "    Mine Sweeper of\n"
								+ "    MS Windows XP\n" + "---- Date: \n"
								+ "    2012-3-23\n" + "    ",
						"About",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				if (Util.isWindows())
				{
					try
					{
						String cmd = "hh.exe";
						String path=Util.createExternalFile("help.chm").getPath();
						while ((path.startsWith("/") || path.startsWith("\\")))
						{
							path = path.substring(1);
						}

						Runtime.getRuntime().exec(cmd + " " + path);
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(
							frame,
							"---- Mine Sweeper V1.0 ----\n" + "\n"
									+ "---- Developed by: \n" + "    Accela\n"
									+ "---- Originated from: \n"
									+ "    Mine Sweeper of\n"
									+ "    MS Windows XP\n" + "---- Date: \n"
									+ "    2012-3-23\n" + "    ",
							"About",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

}
