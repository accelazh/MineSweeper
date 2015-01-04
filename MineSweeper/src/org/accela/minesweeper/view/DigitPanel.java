package org.accela.minesweeper.view;

import javax.swing.Icon;

import org.accela.minesweeper.util.Common;


public class DigitPanel extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	private int digit = 0;

	private Icon[] digitIcons = new Icon[Common.MAX_DIGIT - Common.MIN_DIGIT
			+ 1];

	private Icon negativeSignIcon=null;
	
	private boolean negativeSign=false;
	
	public int getDigit()
	{
		return digit;
	}

	public void setDigit(int digit)
	{
		if (digit < Common.MIN_DIGIT || digit > Common.MAX_DIGIT)
		{
			throw new IllegalArgumentException("digit out of bounds");
		}

		this.digit = digit;
		this.syncCompWithDigit();
	}

	public boolean isNegativeSign()
	{
		return negativeSign;
	}

	public void setNegativeSign(boolean negativeSign)
	{
		this.negativeSign = negativeSign;
		syncCompWithDigit();
	}

	private void syncCompWithDigit()
	{
		if(!this.isNegativeSign())
		{
			this.setIcon(this.getDigitIcon(this.getDigit()));
		}
		else
		{
			this.setIcon(this.getNegativeSignIcon());
		}
	}

	public Icon getDigitIcon(int digit)
	{
		if (digit < Common.MIN_DIGIT || digit > Common.MAX_DIGIT)
		{
			throw new IllegalArgumentException("digit out of bounds");
		}

		return digitIcons[digit - Common.MIN_DIGIT];
	}

	public void setDigitIcon(int digit, Icon icon)
	{
		if (digit < Common.MIN_DIGIT || digit > Common.MAX_DIGIT)
		{
			throw new IllegalArgumentException("digit out of bounds");
		}
	
		this.digitIcons[digit - Common.MIN_DIGIT] = icon;
		if (this.getDigit() == digit)
		{
			this.syncCompWithDigit();
		}
	}

	public Icon getNegativeSignIcon()
	{
		return negativeSignIcon;
	}

	public void setNegativeSignIcon(Icon negativeSignIcon)
	{
		this.negativeSignIcon = negativeSignIcon;
		if(this.isNegativeSign())
		{
			this.syncCompWithDigit();
		}
	}

}
