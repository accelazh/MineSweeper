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
		bar.getGame().setText("��Ϸ(G)");
		bar.getGame().setMnemonic('g');
		
		bar.getStart().setText("����(N)");
		bar.getStart().setMnemonic('n');
		bar.getStart().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		
		bar.getLevelLow().setText("����(B)");
		bar.getLevelLow().setMnemonic('b');
		bar.getLevelMiddle().setText("�м�(I)");
		bar.getLevelMiddle().setMnemonic('i');
		bar.getLevelHigh().setText("�߼�(E)");
		bar.getLevelHigh().setMnemonic('e');
		bar.getLevelCustom().setText("�Զ���(C)");
		bar.getLevelCustom().setMnemonic('c');
		
		bar.getMark().setText("���(?)(M)");
		bar.getMark().setMnemonic('m');
		bar.getSkin().setText("Ƥ��(K)");
		bar.getSkin().setMnemonic('k');
		bar.getLang().setText("����(L)");
		bar.getLang().setMnemonic('l');
		bar.getSound().setText("����(S)");
		bar.getSound().setMnemonic('s');
		
		bar.getHighScore().setText("ɨ��Ӣ�۰�(T)");
		bar.getHighScore().setMnemonic('t');
		
		bar.getExit().setText("�˳�(X)");
		bar.getExit().setMnemonic('x');
		
		//=======Help Menu=======
		bar.getHelp().setText("����(H)");
		bar.getHelp().setMnemonic('h');
		
		bar.getCatalog().setText("Ŀ¼(C)");
		bar.getCatalog().setMnemonic('c');
		bar.getCatalog().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		bar.getFindHelp().setText("���Ұ�������(S)");
		bar.getFindHelp().setMnemonic('s');
		bar.getUserManual().setText("ʹ�ð���(H)");
		bar.getUserManual().setMnemonic('h');
		bar.getAbout().setText("����ɨ��(A)");
		bar.getAbout().setMnemonic('a');
		
		return true;
		
	}

}
