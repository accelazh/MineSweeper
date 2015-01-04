package org.accela.minesweeper.ui.backpaint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Shape;
import java.io.Serializable;

public class DashBackpaint implements Backpaint, Serializable
{
	private static final long serialVersionUID = 1L;

	public static final Color DEFAULT_COLOR = Color.BLACK;

	private Color color = DEFAULT_COLOR;

	public DashBackpaint()
	{
		//do nothing
	}
	
	public DashBackpaint(Color color)
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
		Shape oldClip=g.getClip();
		g.clipRect(x,y,width, height);

		int lineStartX = x - height;
		if (lineStartX % 2 != 0)
		{
			lineStartX--;
		}
		for (; lineStartX < x + width; lineStartX += 2)
		{
			g.drawLine(lineStartX, y, lineStartX + height, y + height);
		}

		g.setClip(oldClip);
	}

	@Override
	public boolean isBackgroundOpaque()
	{
		return false;
	}

}
