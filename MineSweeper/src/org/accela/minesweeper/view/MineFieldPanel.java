package org.accela.minesweeper.view;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import org.accela.minesweeper.util.Matrix;


public class MineFieldPanel extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	public static final String NEW_GRIDS_CHANGED_PROPERTY = "newGrids";

	private Matrix<MineGridPanel> grids = new Matrix<MineGridPanel>(new MineGridPanel[0]);

	private MineGridPanel[] oldGrids = null;

	public MineFieldPanel()
	{
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		this.setFieldSize(1, 1);
	}

	public Dimension getFieldSize()
	{
		return grids.getSize();
	}

	public void setFieldSize(int width, int height)
	{
		if (width < 1 || height < 1)
		{
			throw new IllegalArgumentException(
					"width and height should not be less than 1");
		}

		this.oldGrids = this.grids.getAll();
		this.grids .setSize(width, height);
		for (int i = 0; i < grids.getWidth(); i++)
		{
			for (int j = 0; j < grids.getHeight(); j++)
			{
				grids.set(i, j, new MineGridPanel());
				grids.get(i, j).setParentField(this);
			}
		}

		this.syncCompWithGrids();
	}

	private void syncCompWithGrids()
	{
		this.removeAll();
		Dimension gridSize = this.getFieldSize();

		this.setLayout(new GridLayout(gridSize.height, gridSize.width));
		for(MineGridPanel g: grids)
		{
			this.add(g);
		}
		this.firePropertyChange(
				NEW_GRIDS_CHANGED_PROPERTY,
				this.oldGrids,
				this.grids.getAll());
	}

	public MineGridPanel getGrid(int x, int y)
	{
		return grids.get(x,y);
	}

	public Point getGridPos(MineGridPanel g)
	{
		return grids.getPos(g);
	}

}
