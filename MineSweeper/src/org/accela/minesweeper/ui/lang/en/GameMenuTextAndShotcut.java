package org.accela.minesweeper.ui.lang.en;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.view.GameMenuBar;


public class GameMenuTextAndShotcut extends CompositeProcessor
{
	public GameMenuTextAndShotcut()
	{
		super(GameMenuBar.class, null);
	}
	
	@Override
	protected boolean doProcess(Object obj)
	{
		GameMenuBar bar=(GameMenuBar)obj;
		
		//======Game Menu=====
		bar.getGame().setText("Game(G)");
		bar.getGame().setMnemonic('g');
		
		bar.getStart().setText("New Round(N)");
		bar.getStart().setMnemonic('n');
		bar.getStart().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		
		bar.getLevelLow().setText("Beginner(B)");
		bar.getLevelLow().setMnemonic('b');
		bar.getLevelMiddle().setText("Intermediate(I)");
		bar.getLevelMiddle().setMnemonic('i');
		bar.getLevelHigh().setText("Expert(E)");
		bar.getLevelHigh().setMnemonic('e');
		bar.getLevelCustom().setText("Custom(C)");
		bar.getLevelCustom().setMnemonic('c');
		
		bar.getMark().setText("Mark(?)(M)");
		bar.getMark().setMnemonic('m');
		bar.getSkin().setText("Skin(K)");
		bar.getSkin().setMnemonic('k');
		bar.getLang().setText("Lang(L)");
		bar.getLang().setMnemonic('l');
		bar.getSound().setText("Sound(S)");
		bar.getSound().setMnemonic('s');
		
		bar.getHighScore().setText("High Score(T)");
		bar.getHighScore().setMnemonic('t');
		
		bar.getExit().setText("Exit(X)");
		bar.getExit().setMnemonic('x');
		
		//=======Help Menu=======
		bar.getHelp().setText("Help(H)");
		bar.getHelp().setMnemonic('h');
		
		bar.getCatalog().setText("Catalog(C)");
		bar.getCatalog().setMnemonic('c');
		bar.getCatalog().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		bar.getFindHelp().setText("Search Help(S)");
		bar.getFindHelp().setMnemonic('s');
		bar.getUserManual().setText("Manual(H)");
		bar.getUserManual().setMnemonic('h');
		bar.getAbout().setText("About(A)");
		bar.getAbout().setMnemonic('a');
		
		return true;
		
	}

}
