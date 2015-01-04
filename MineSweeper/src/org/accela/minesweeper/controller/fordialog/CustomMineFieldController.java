package org.accela.minesweeper.controller.fordialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.util.Common;
import org.accela.minesweeper.view.CustomMineFieldDialog;
import org.accela.minesweeper.view.GameFrame;

public class CustomMineFieldController implements Controller
{
	public static final int PRIORITY = 10;

	private CustomMineFieldDialog dialog = null;

	private ComponentListener showListener = new ShowListener();

	private ActionListener okListener = new OkListener();

	private GameModel model = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		this.dialog = view.getCustomMineFieldDialog();
		this.dialog.addComponentListener(showListener);
		this.dialog.getPanel().getOkButton().addActionListener(okListener);
		this.model = model;
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		this.dialog.removeComponentListener(showListener);
		this.dialog.getPanel().getOkButton().removeActionListener(okListener);
	}

	@Override
	public String getName()
	{
		return this.getClass().getName();
	}

	@Override
	public double getPriorityOnInstallation()
	{
		return PRIORITY;
	}

	private class ShowListener extends ComponentAdapter
	{
		@Override
		public void componentShown(ComponentEvent e)
		{
			Profile profile = Profile.getInstance();
			CustomMineFieldDialog.Panel panel = dialog.getPanel();
			panel.getWidthField().setText("" + profile.getFieldSize().width);
			panel.getHeightField().setText("" + profile.getFieldSize().height);
			panel.getMineField().setText("" + profile.getMineCount());
		}
	}

	private class OkListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Profile profile = Profile.getInstance();
			CustomMineFieldDialog.Panel panel = dialog.getPanel();

			Dimension fieldSize = new Dimension(-1, -1);
			int mineCount = -1;
			try
			{
				fieldSize.width = Integer.parseInt(panel.getWidthField()
						.getText());
				fieldSize.height = Integer.parseInt(panel.getHeightField()
						.getText());
				mineCount = Integer.parseInt(panel.getMineField().getText());
			}
			catch (NumberFormatException ex)
			{
				// do nothing
			}

			// set field size
			fieldSize.width = Math.max(
					fieldSize.width,
					Common.MIN_MINE_FIELD_SIZE.width);
			fieldSize.height = Math.max(
					fieldSize.height,
					Common.MIN_MINE_FIELD_SIZE.height);

			fieldSize.width = Math.min(
					fieldSize.width,
					Common.MAX_MINE_FIELD_SIZE.width);
			fieldSize.height = Math.min(
					fieldSize.height,
					Common.MAX_MINE_FIELD_SIZE.height);

			profile.setFieldSize(fieldSize);
			
			// set mine count
			mineCount=Math.max(mineCount, Common.getMinMineCount());
			mineCount=Math.min(mineCount, Common.getMaxMineCount(fieldSize));
			profile.setMineCount(mineCount);
			
			model.setFieldSizeOnStart(profile.getFieldSize());
			model.setMineCountOnStart(profile.getMineCount());
			model.reset();
		}
	}
}
