package org.accela.minesweeper.ui.backpaint;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

public class ImageBackpaint implements Backpaint, Serializable
{
	private static final long serialVersionUID = 1L;

	private Image image=null;
	
	public ImageBackpaint()
	{
		this(null);
	}
	
	public ImageBackpaint(Image image)
	{
		this.image=image;
	}
	
	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	@Override
	public void paintBackground(Component c, Graphics g, int x, int y,
			int width, int height)
	{
		if(image!=null)
		{
			g.drawImage(image, x, y, width, height, c);
		}
	}

	@Override
	public boolean isBackgroundOpaque()
	{
		return true;
	}

}
