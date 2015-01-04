package org.accela.minesweeper.ui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;

import javax.swing.border.Border;

public class EnhancedLineBorder implements Border, Serializable
{
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
		if(null==this.color)
		{
			this.color=DEFAULT_COLOR;
		}
	}

	private static final long serialVersionUID = 1L;

	public static final Color DEFAULT_COLOR = Color.BLACK;
	public static final Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

	private Color color = DEFAULT_COLOR;
	private Insets insets=DEFAULT_INSETS;
	
	public EnhancedLineBorder()
	{
		//do nothing
	}
	
	public EnhancedLineBorder(Insets insets, Color color)
	{
		this.setColor(color);
		this.setBorderInsets(insets);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		g.setColor(this.color);
		for(int i=0;i<this.insets.top;i++)
		{
			g.drawLine(x, y+i, x+width-1, y+i);
		}
		for(int i=0;i<this.insets.bottom;i++)
		{
			g.drawLine(x, y+height-1-i, x+width-1, y+height-1-i);
		}
		for(int i=0;i<this.insets.left;i++)
		{
			g.drawLine(x+i, y, x+i, y+height-1);
		}
		for(int i=0;i<this.insets.right;i++)
		{
			g.drawLine(x+width-1-i, y, x+width-1-i, y+height-1);
		}
	}

	@Override
	public Insets getBorderInsets(Component c)
	{
		return this.insets;
	}
	
	public void setBorderInsets(Insets insets)
	{
		this.insets = insets;
		if (null == this.insets)
		{
			this.insets = DEFAULT_INSETS;
		}
	}

	@Override
	public boolean isBorderOpaque()
	{
		return false;
	}

}
