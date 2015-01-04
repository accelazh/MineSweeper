package org.accela.minesweeper.view;

import java.awt.BorderLayout;

public class GamePanel extends BasicPanel
{
	private static final long serialVersionUID = 1L;

	private StatusPanel status = new StatusPanel();

	private MineFieldPanel mineField = new MineFieldPanel();

	private int gap = 0;

	private BorderLayout layout = new BorderLayout(0, gap);

	public GamePanel()
	{
		this.setLayout(this.layout);

		this.add(status, BorderLayout.NORTH);
		this.add(mineField, BorderLayout.CENTER);
	}

	public int getGap()
	{
		return gap;
	}

	public void setGap(int gap)
	{
		this.gap = gap;
		this.syncCompWithGap();
	}

	private void syncCompWithGap()
	{
		this.layout.setVgap(this.gap);
	}

	public StatusPanel getStatus()
	{
		return status;
	}

	public MineFieldPanel getMineField()
	{
		return mineField;
	}

}
