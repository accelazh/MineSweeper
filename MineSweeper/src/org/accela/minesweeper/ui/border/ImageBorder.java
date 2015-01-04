package org.accela.minesweeper.ui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.Serializable;

import javax.swing.border.Border;

public class ImageBorder implements Border, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final Insets DEFAULT_INSETS=new Insets(1,1,1,1);
	
	private Insets insets=DEFAULT_INSETS;
	
	//从top-left开始逆时针
	private Image[] corners=new Image[4];
	//从top开始逆时针
	private Image[] edges=new Image[4];

	public ImageBorder()
	{
		//do nothing
	}
	
	public ImageBorder(Insets insets, Image[] corners, Image[] edges)
	{
		this.setBorderInsets(insets);
		this.setCorners(corners);
		this.setEdges(edges);
	}
	
	public Image[] getCorners()
	{
		return this.corners;
	}
	
	public void setCorners(Image[] corners)
	{
		for(int i=0;i<this.corners.length;i++)
		{
			this.corners[i]=corners[i];
		}
	}
	
	public Image[] getEdges()
	{
		return this.edges;
	}
	
	public void setEdges(Image[] edges)
	{
		for(int i=0;i<this.edges.length;i++)
		{
			this.edges[i]=edges[i];
		}
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		if(corners[0]!=null)
		{
			g.drawImage(corners[0], x, y, insets.left, insets.top, c);
		}
		if(corners[1]!=null)
		{
			g.drawImage(corners[1], x, y+height-insets.bottom, insets.left, insets.bottom, c);
		}
		if(corners[2]!=null)
		{
			g.drawImage(corners[2], x+width-insets.right, y+height-insets.bottom, insets.right, insets.bottom, c);
		}
		if(corners[3]!=null)
		{
			g.drawImage(corners[3], x+width-insets.right, y, insets.right, insets.top, c);
		}
		
		if(edges[0]!=null)
		{
			g.drawImage(edges[0], x+insets.right, y, width-insets.left-insets.right, insets.top, c);
		}
		if(edges[1]!=null)
		{
			g.drawImage(edges[1], x, y+insets.top, insets.left, height-insets.top-insets.bottom, c);
		}
		if(edges[2]!=null)
		{
			g.drawImage(edges[2], x+insets.left, y+height-insets.bottom, width-insets.left-insets.right, insets.bottom, c);
		}
		if(edges[3]!=null)
		{
			g.drawImage(edges[3], x+width-insets.right, y+insets.top, insets.right, height-insets.top-insets.bottom, c);
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
