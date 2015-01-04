package org.accela.minesweeper.ui.backpaint;

import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

public class EmptyBackpaint implements Backpaint, Serializable
{
	private static final long serialVersionUID = 1L;

	@Override
	public void paintBackground(Component c, Graphics g, int x, int y,
			int width, int height)
	{
		//do nothing
	}

	@Override
	public boolean isBackgroundOpaque()
	{
		return false;
	}

}
