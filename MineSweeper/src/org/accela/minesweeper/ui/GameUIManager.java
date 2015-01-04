package org.accela.minesweeper.ui;

import org.accela.minesweeper.ui.lang.chs.ChsUI;
import org.accela.minesweeper.ui.lang.en.EnUI;
import org.accela.minesweeper.ui.skin.blackwhite.BlackWhiteUI;
import org.accela.minesweeper.ui.skin.blue.BlueUI;
import org.accela.minesweeper.ui.skin.classic.ClassicUI;
import org.accela.minesweeper.view.GameFrame;

public class GameUIManager
{
	// 单件模式
	private static GameUIManager instance = null;

	public static GameUIManager getInstance()
	{
		if (null == instance)
		{
			instance = new GameUIManager();
		}

		return instance;
	}

	// ====================================================================

	private GameUI[] langs = new GameUI[] { new ChsUI(), new EnUI() };
	private GameUI[] skins = new GameUI[] {
			new ClassicUI(),
			new BlueUI(),
			new BlackWhiteUI() };

	private GameUI lastLang = null;
	private GameUI lastSkin = null;

	protected GameUIManager()
	{
		//do nothing
	}
	
	public void replaceSkinByName(String name, GameFrame frame)
	{
		if (lastSkin != null)
		{
			lastSkin.uninstall(frame);
		}

		lastSkin = this.getSkinByName(name);
		lastSkin.install(frame);
	}

	public void uninstallSkinByName(GameFrame frame)
	{
		lastSkin.uninstall(frame);
		lastSkin = null;
	}

	public void replaceLangByName(String name, GameFrame frame)
	{
		if (lastLang != null)
		{
			lastLang.uninstall(frame);
		}

		lastLang = this.getLangByName(name);
		lastLang.install(frame);
	}

	public void uninstallLangByName(GameFrame frame)
	{
		lastLang.uninstall(frame);
		lastLang = null;
	}

	public String[] getLangNames()
	{
		return getUINames(langs);
	}

	public String[] getSkinNames()
	{
		return getUINames(skins);
	}

	private String[] getUINames(GameUI[] uis)
	{
		String[] strs = new String[uis.length];
		for (int i = 0; i < uis.length; i++)
		{
			strs[i] = uis[i].getName();
		}

		return strs;
	}

	public GameUI getLangByName(String name)
	{
		return getUIByName(name, langs);
	}

	public GameUI getSkinByName(String name)
	{
		return getUIByName(name, skins);
	}

	private GameUI getUIByName(String name, GameUI[] uis)
	{
		for (GameUI u : uis)
		{
			if (u.getName().equals(name))
			{
				return u;
			}
		}

		return null;
	}
}