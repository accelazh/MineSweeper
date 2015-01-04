package org.accela.minesweeper.ui.lang.chs;

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
		bar.getGame().setText("游戏(G)");
		bar.getGame().setMnemonic('g');
		
		bar.getStart().setText("开局(N)");
		bar.getStart().setMnemonic('n');
		bar.getStart().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		
		bar.getLevelLow().setText("初级(B)");
		bar.getLevelLow().setMnemonic('b');
		bar.getLevelMiddle().setText("中级(I)");
		bar.getLevelMiddle().setMnemonic('i');
		bar.getLevelHigh().setText("高级(E)");
		bar.getLevelHigh().setMnemonic('e');
		bar.getLevelCustom().setText("自定义(C)");
		bar.getLevelCustom().setMnemonic('c');
		
		bar.getMark().setText("标记(?)(M)");
		bar.getMark().setMnemonic('m');
		bar.getSkin().setText("皮肤(K)");
		bar.getSkin().setMnemonic('k');
		bar.getLang().setText("语言(L)");
		bar.getLang().setMnemonic('l');
		bar.getSound().setText("声音(S)");
		bar.getSound().setMnemonic('s');
		
		bar.getHighScore().setText("扫雷英雄榜(T)");
		bar.getHighScore().setMnemonic('t');
		
		bar.getExit().setText("退出(X)");
		bar.getExit().setMnemonic('x');
		
		//=======Help Menu=======
		bar.getHelp().setText("帮助(H)");
		bar.getHelp().setMnemonic('h');
		
		bar.getCatalog().setText("目录(C)");
		bar.getCatalog().setMnemonic('c');
		bar.getCatalog().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		bar.getFindHelp().setText("查找帮助主题(S)");
		bar.getFindHelp().setMnemonic('s');
		bar.getUserManual().setText("使用帮助(H)");
		bar.getUserManual().setMnemonic('h');
		bar.getAbout().setText("关于扫雷(A)");
		bar.getAbout().setMnemonic('a');
		
		return true;
		
	}

}
