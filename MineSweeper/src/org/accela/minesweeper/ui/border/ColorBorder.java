package org.accela.minesweeper.ui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;

import javax.swing.border.Border;

public class ColorBorder implements Border, Serializable
{
	private static final long serialVersionUID = 1L;

	private AsymmetricLineBorder border = null;

	public static final Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

	public static final Color DEFAULT_COLOR = Color.BLACK;

	public ColorBorder()
	{
		this(DEFAULT_INSETS, DEFAULT_COLOR);
	}

	public ColorBorder(Insets insets, Color color)
	{
		border = new AsymmetricLineBorder(insets, new Color[] {
				color,
				color,
				color,
				color });
	}

	public Color getColor()
	{
		return border.getTopColor();
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		border.paintBorder(c, g, x, y, width, height);
	}

	@Override
	public Insets getBorderInsets(Component c)
	{
		return border.getBorderInsets(c);
	}

	@Override
	public boolean isBorderOpaque()
	{
		return border.isBorderOpaque();
	}

}
