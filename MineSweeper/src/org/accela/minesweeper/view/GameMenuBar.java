package org.accela.minesweeper.view;

import java.lang.reflect.Field;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	// ======== game menu ========
	private JMenu game = new JMenu();

	private JMenuItem start = new JMenuItem();

	private JCheckBoxMenuItem levelLow = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem levelMiddle = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem levelHigh = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem levelCustom = new JCheckBoxMenuItem();
	
	private JCheckBoxMenuItem mark = new JCheckBoxMenuItem();
	private JMenu skin = new JMenu();
	private JMenu lang = new JMenu();
	private JCheckBoxMenuItem sound = new JCheckBoxMenuItem();

	private JMenuItem highScore = new JMenuItem();

	private JMenuItem exit = new JMenuItem();

	// ======== help menu =========
	private JMenu help = new JMenu();

	private JMenuItem catalog = new JMenuItem();
	private JMenuItem findHelp = new JMenuItem();
	private JMenuItem userManual = new JMenuItem();

	private JMenuItem about = new JMenuItem();

	public GameMenuBar()
	{
		initMenuNamesAndActionCommands();
		initGameMenu();
		initHelpMenu();
		
		this.add(this.game);
		this.add(this.help);
	}

	private void initMenuNamesAndActionCommands()
	{
		try
		{
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field f : fields)
			{
				Object obj = f.get(this);
				if (JMenuItem.class.isInstance(obj))
				{
					JMenuItem mi = (JMenuItem) obj;
					mi.setName(f.getName());
					mi.setActionCommand(f.getName());
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			assert (false);
		}
	}
	
	private void initGameMenu()
	{
		game.add(start);
		game.addSeparator();

		game.add(levelLow);
		game.add(levelMiddle);
		game.add(levelHigh);
		game.add(levelCustom);
		
		game.addSeparator();

		game.add(mark);
		game.add(skin);
		game.add(lang);
		game.add(sound);
		game.addSeparator();

		game.add(highScore);
		game.addSeparator();

		game.add(exit);
	}
	
	private void initHelpMenu()
	{
		help.add(catalog);
		help.add(findHelp);
		help.add(userManual);
		help.addSeparator();

		help.add(about);
	}

	public JMenu getGame()
	{
		return game;
	}

	public JMenuItem getStart()
	{
		return start;
	}

	public JCheckBoxMenuItem getLevelLow()
	{
		return levelLow;
	}

	public JCheckBoxMenuItem getLevelMiddle()
	{
		return levelMiddle;
	}

	public JCheckBoxMenuItem getLevelHigh()
	{
		return levelHigh;
	}

	public JCheckBoxMenuItem getLevelCustom()
	{
		return levelCustom;
	}

	public JCheckBoxMenuItem getMark()
	{
		return mark;
	}

	
	public JMenu getSkin()
	{
		return skin;
	}

	public JMenu getLang()
	{
		return lang;
	}

	public JCheckBoxMenuItem getSound()
	{
		return sound;
	}

	public JMenuItem getHighScore()
	{
		return highScore;
	}

	public JMenuItem getExit()
	{
		return exit;
	}

	public JMenu getHelp()
	{
		return help;
	}

	public JMenuItem getCatalog()
	{
		return catalog;
	}

	public JMenuItem getFindHelp()
	{
		return findHelp;
	}

	public JMenuItem getUserManual()
	{
		return userManual;
	}

	public JMenuItem getAbout()
	{
		return about;
	}
}
