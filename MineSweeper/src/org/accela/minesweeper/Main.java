package org.accela.minesweeper;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.accela.minesweeper.controller.ControllerManager;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.GameFrame;

public class Main
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		Util.setSwingFont(new Font("MS Sans Serif", Font.PLAIN, 12));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		GameModel model=new GameModel();
		GameFrame view=new GameFrame();
		
		ControllerManager.getInstance().install(model, view);
		
		view.pack();
		view.revalidate();

		view.setVisible(true);
	}
}
