package org.accela.minesweeper.view;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.util.Arrays;

import org.accela.minesweeper.util.Util;

public class NumberPanel extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	public static final String NEW_DIGITS_CHANGED_PROPERTY = "newDigits";

	private DigitPanel[] oldDigits = null;
	// ╣мн╩сеох
	private DigitPanel[] digits = null;

	public NumberPanel()
	{
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		this.setDigitCount(1);
	}

	public int getDigitCount()
	{
		return this.digits.length;
	}

	public void setDigitCount(int count)
	{
		if (count < 1)
		{
			throw new IllegalArgumentException(
					"count should not be less than 1");
		}

		this.oldDigits = this.digits;
		this.digits = new DigitPanel[count];
		for (int i = 0; i < this.digits.length; i++)
		{
			this.digits[i] = new DigitPanel();
		}

		this.syncCompWithDigits();
	}

	private void syncCompWithDigits()
	{
		this.removeAll();
		this.setLayout(new GridLayout(1, this.getDigitCount()));
		for (int i = 0; i < this.digits.length; i++)
		{
			this.add(this.digits[i]);
		}

		this.firePropertyChange(
				NEW_DIGITS_CHANGED_PROPERTY,
				(this.oldDigits != null) ? Arrays.copyOf(
						this.oldDigits,
						this.oldDigits.length) : null,
				Arrays.copyOf(this.digits, this.digits.length));
	}

	public DigitPanel getDigit(int idx)
	{
		return this.digits[idx];
	}

	public int getNumber()
	{
		if (!digits[digits.length - 1].isNegativeSign())
		{
			return Util.digitsToNumber(digitsToArray());
		}
		else
		{
			return -Util.digitsToNumber(digitsToArray(digits.length - 1));
		}
	}

	private int[] digitsToArray()
	{
		return this.digitsToArray(digits.length);
	}

	private int[] digitsToArray(int len)
	{
		int[] array = new int[len];
		for (int i = 0; i < len; i++)
		{
			array[i] = digits[i].getDigit();
		}

		return array;
	}

	private void arrayToDigits(int[] array)
	{
		for (int i = 0; i < digits.length; i++)
		{
			if (i < array.length)
			{
				digits[i].setDigit(array[i]);
			}
			else
			{
				digits[i].setDigit(0);
			}
		}
	}

	public void setNumber(int num)
	{
		boolean negSign = num < 0;
		num = Math.abs(num);

		arrayToDigits(Util.numberToDigits(num));
		if (digits.length > 0)
		{
			if (negSign)
			{
				digits[digits.length - 1].setNegativeSign(true);
			}
			else
			{
				digits[digits.length - 1].setNegativeSign(false);
			}
		}

	}
}
