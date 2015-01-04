package org.accela.minesweeper.ui.backpaint;

import java.awt.Component;
import java.awt.Graphics;

public interface Backpaint
{
	void paintBackground(Component c, Graphics g, int x, int y, int width, int height);
	
	boolean isBackgroundOpaque();
}
