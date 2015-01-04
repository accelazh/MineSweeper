package org.accela.minesweeper.ui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.border.Border;

import org.accela.minesweeper.util.Util;


public class AsymmetricLineBorder implements Border, Serializable
{
	private static final long serialVersionUID = 1L;

	public static final Color DEFAULT_COLOR = Color.BLACK;

	public static final Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

	private Color topColor = DEFAULT_COLOR;

	private Color leftColor = DEFAULT_COLOR;

	private Color bottomColor = DEFAULT_COLOR;

	private Color rightColor = DEFAULT_COLOR;

	private Insets insets = DEFAULT_INSETS;

	private boolean mergeCornerColor = true;

	public AsymmetricLineBorder()
	{
		// do nothing
	}

	public AsymmetricLineBorder(Insets insets, Color[] colors)
	{
		this.setBorderInsets(insets);

		this.setTopColor(colors[0]);
		this.setLeftColor(colors[1]);
		this.setBottomColor(colors[2]);
		this.setRightColor(colors[3]);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		Point start = new Point();
		Point end = new Point();

		// draw top border
		g.setColor(topColor);
		start.move(x + 1, y);
		end.move(x + width - 1 - 1, y);
		for (int i = 0; i < insets.top; i++)
		{
			g.drawLine(start.x, start.y, end.x, end.y);

			if (i + 1 < insets.left)
			{
				start.x++;
			}
			if (i + 1 < insets.right)
			{
				end.x--;
			}
			start.y++;
			end.y++;
		}

		// draw left border
		g.setColor(leftColor);
		start.move(x, y + 1);
		end.move(x, y + height - 1 - 1);
		for (int i = 0; i < insets.left; i++)
		{
			g.drawLine(start.x, start.y, end.x, end.y);

			if (i + 1 < insets.top)
			{
				start.y++;
			}
			if (i + 1 < insets.bottom)
			{
				end.y--;
			}
			start.x++;
			end.x++;
		}

		// draw bottom border
		g.setColor(bottomColor);
		start.move(x + 1, y + height - 1);
		end.move(x + width - 1 - 1, y + height - 1);
		for (int i = 0; i < insets.bottom; i++)
		{
			g.drawLine(start.x, start.y, end.x, end.y);
			if (i + 1 < insets.left)
			{
				start.x++;
			}
			if (i + 1 < insets.right)
			{
				end.x--;
			}
			start.y--;
			end.y--;
		}

		// draw right border
		g.setColor(rightColor);
		start.move(x + width - 1, y + 1);
		end.move(x + width - 1, y + height - 1 - 1);
		for (int i = 0; i < insets.right; i++)
		{
			g.drawLine(start.x, start.y, end.x, end.y);
			if (i + 1 < insets.top)
			{
				start.y++;
			}
			if (i + 1 < insets.bottom)
			{
				end.y--;
			}
			start.x--;
			end.x--;
		}

		// draw left-top corner
		g.setColor(mergeCornerColor ? Util.mergeColor(leftColor, topColor)
				: topColor);
		int extend = Math.min(insets.left, insets.top);
		if (extend > 0)
		{
			g.drawLine(x, y, x + extend - 1, y + extend - 1);
		}

		// draw left-bottom corner
		g.setColor(mergeCornerColor ? Util.mergeColor(leftColor, bottomColor)
				: leftColor);
		extend = Math.min(insets.left, insets.bottom);
		if (extend > 0)
		{
			g.drawLine(x, y + height - 1, x + extend - 1, y + height - 1
					- (extend - 1));
		}

		// draw right-bottom corner
		g.setColor(mergeCornerColor ? Util.mergeColor(rightColor, bottomColor)
				: bottomColor);
		extend = Math.min(insets.right, insets.bottom);
		if (extend > 0)
		{
			g.drawLine(x + width - 1, y + height - 1, x + width - 1
					- (extend - 1), y + height - 1 - (extend - 1));
		}

		// draw right-top corner
		g.setColor(mergeCornerColor ? Util.mergeColor(rightColor, topColor)
				: rightColor);
		extend = Math.min(insets.right, insets.top);
		if (extend > 0)
		{
			g.drawLine(x + width - 1, y, x + width - 1 - (extend - 1), y
					+ extend - 1);
		}

	}

	@Override
	public Insets getBorderInsets(Component c)
	{
		return insets;
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

	public Color getTopColor()
	{
		return topColor;
	}

	public void setTopColor(Color topColor)
	{
		this.topColor = topColor;
		if (null == this.topColor)
		{
			this.topColor = DEFAULT_COLOR;
		}
	}

	public Color getLeftColor()
	{
		return leftColor;
	}

	public void setLeftColor(Color leftColor)
	{
		this.leftColor = leftColor;
		if (null == this.leftColor)
		{
			this.leftColor = DEFAULT_COLOR;
		}
	}

	public Color getBottomColor()
	{
		return bottomColor;
	}

	public void setBottomColor(Color bottomColor)
	{
		this.bottomColor = bottomColor;
		if (null == this.bottomColor)
		{
			this.bottomColor = DEFAULT_COLOR;
		}
	}

	public Color getRightColor()
	{
		return rightColor;
	}

	public void setRightColor(Color rightColor)
	{
		this.rightColor = rightColor;
		if (null == this.rightColor)
		{
			this.rightColor = DEFAULT_COLOR;
		}
	}

	public boolean isMergeCornerColor()
	{
		return mergeCornerColor;
	}

	public void setMergeCornerColor(boolean mergeCornerColor)
	{
		this.mergeCornerColor = mergeCornerColor;
	}

}
