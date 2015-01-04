package org.accela.minesweeper.controller;

import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

public interface Controller
{
	public void install(GameModel model, GameFrame view);

	public void uninstall(GameModel model, GameFrame view);

	public String getName();
	
	// Controller安装和卸载是有先后顺序要求的，如LangController安装必须咋在SkinController前头
	public double getPriorityOnInstallation();
}
