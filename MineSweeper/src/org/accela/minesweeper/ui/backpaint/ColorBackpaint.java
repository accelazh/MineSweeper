package org.accela.minesweeper.ui.backpaint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

public class ColorBackpaint implements Backpaint, Serializable
{
	private static final long serialVersionUID = 1L;

	public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;

	private Color color = DEFAULT_COLOR;

	public ColorBackpaint()
	{
		//do nothing
	}
	
	public ColorBackpaint(Color color)
	{
		this.setColor(color);
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
		if (null == this.color)
		{
			this.color = DEFAULT_COLOR;
		}
	}

	@Override
	public void paintBackground(Component c, Graphics g, int x, int y,
			int width, int height)
	{
		g.setColor(this.color);
		g.fillRect(x, y, width, height);
	}

	@Override
	public boolean isBackgroundOpaque()
	{
		return true;
	}

}
