package org.accela.minesweeper.ui.skin.blue;

import java.awt.Dimension;

import javax.swing.Icon;

import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.util.CompositeProcessor;
import org.accela.minesweeper.util.Util;
import org.accela.minesweeper.view.DigitPanel;


public class BlueDigitPanelUI extends CompositeProcessor
{
	private Icon[] digitIcons = new Icon[Common.MAX_DIGIT - Common.MIN_DIGIT
	                         			+ 1];
	private Icon negSign=null;
	
	public BlueDigitPanelUI()
	{
		super(DigitPanel.class, null);
	}

	@Override
	protected boolean doProcess(Object obj)
	{
		DigitPanel panel=(DigitPanel)obj;
		
		panel.setOpaque(false);
		for(int i=Common.MIN_DIGIT;i<=Common.MAX_DIGIT;i++)
		{
			if(digitIcons[i-Common.MIN_DIGIT]==null)
			{
				digitIcons[i-Common.MIN_DIGIT]=Util.createImageIcon("blue/digit/"+i+".png");
			}
			
			panel.setDigitIcon(i, digitIcons[i-Common.MIN_DIGIT]);
		}
		
		negSign=Util.createImageIcon("blue/digit/negSign.png");
		panel.setNegativeSignIcon(negSign);
		
		panel.setPreferredSize(new Dimension(13, 23));
		
		return true;
	}

}
