package org.accela.minesweeper.controller;

import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.view.GameFrame;

public interface Controller
{
	public void install(GameModel model, GameFrame view);

	public void uninstall(GameModel model, GameFrame view);

	public String getName();
	
	// Controller��װ��ж�������Ⱥ�˳��Ҫ��ģ���LangController��װ����զ��SkinControllerǰͷ
	public double getPriorityOnInstallation();
}
