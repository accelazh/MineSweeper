package org.accela.minesweeper.view;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.JPanel;

import org.accela.minesweeper.ui.backpaint.Backpaint;

public class BasicPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Icon icon = null;

	// 使用浮点像素数，以做到更精确的定位
	private Point2D iconOffset = null;

	private Backpaint backpaint = null;

	public Icon getIcon()
	{
		return icon;
	}

	public void setIcon(Icon icon)
	{
		this.icon = icon;
	}

	public Backpaint getBackpaint()
	{
		return backpaint;
	}

	public void setBackpaint(Backpaint backpaint)
	{
		this.backpaint = backpaint;
	}

	public void setIconOffset(Point2D iconOffset)
	{
		this.iconOffset = iconOffset;
	}

	public Point2D getIconOffset()
	{
		return iconOffset;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Rectangle paintingArea = new Rectangle(0, 0, this.getWidth(),
				this.getHeight());
		Point2D curIconOffset = (iconOffset != null) ? iconOffset : new Point(
				0, 0);

		if (this.backpaint != null)
		{
			this.backpaint.paintBackground(
					this,
					g,
					paintingArea.x,
					paintingArea.y,
					paintingArea.width,
					paintingArea.height);
		}

		if (this.icon != null)
		{
			Point2D iconPos = new Point2D.Double(
					(double) paintingArea.x
							+ ((double) paintingArea.width - (double) this.icon.getIconWidth())
							/ 2 + curIconOffset.getX(),
					(double) paintingArea.y
							+ ((double) paintingArea.height - (double) this.icon
									.getIconHeight()) / 2
							+ curIconOffset.getY());
			
			this.icon.paintIcon(this, g, (int)iconPos.getX(), (int)iconPos.getY());
		}
	}

}
