package org.accela.minesweeper.view;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.JFrame;

import org.accela.minesweeper.util.CompositeIterator;
import org.accela.minesweeper.util.Util;

public class GameFrame extends JFrame implements Iterable<Component>
{
	private static final long serialVersionUID = 1L;

	private GameMenuBar gameMenuBar = new GameMenuBar();

	private GamePanel gamePanel = new GamePanel();

	private CustomMineFieldDialog customMineFieldDialog = new CustomMineFieldDialog(
			this);

	private HighScoreDialog highScoreDialog = new HighScoreDialog(this);

	private InputYourNameDialogUIRes inputYourNameDialogUIRes = new InputYourNameDialogUIRes();

	public GameFrame()
	{
		this.setJMenuBar(gameMenuBar);
		gamePanel.setOpaque(true);
		this.setContentPane(gamePanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();

		this.customMineFieldDialog
				.setModalityType(ModalityType.APPLICATION_MODAL);
		this.highScoreDialog.setModalityType(ModalityType.APPLICATION_MODAL);

		weaveEvents();
	}

	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			this.pack();
			this.setLocationRelativeTo(null);
		}
		super.setVisible(visible);
	}

	private void weaveEvents()
	{
		this.gameMenuBar.getLevelCustom().addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// 校正customMineFieldDialog中的值的操作放在Contoller中
						customMineFieldDialog.setVisible(true);
					}
				});

		this.gameMenuBar.getHighScore().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// 校正highScoreDialog中的值的操作放在Contoller中
				highScoreDialog.setVisible(true);
			}
		});
		this.customMineFieldDialog.getPanel().getCancelButton()
				.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						boolean otherBtnSelected = false;
						otherBtnSelected = otherBtnSelected
								|| gameMenuBar.getLevelLow().isSelected();
						otherBtnSelected = otherBtnSelected
								|| gameMenuBar.getLevelMiddle().isSelected();
						otherBtnSelected = otherBtnSelected
								|| gameMenuBar.getLevelHigh().isSelected();
						if (otherBtnSelected)
						{
							gameMenuBar.getLevelCustom().setSelected(false);
						}
						else
						{
							gameMenuBar.getLevelCustom().setSelected(true);
						}
					}
				});
		this.customMineFieldDialog.getPanel().getOkButton()
				.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						gameMenuBar.getLevelLow().setSelected(false);
						gameMenuBar.getLevelMiddle().setSelected(false);
						gameMenuBar.getLevelHigh().setSelected(false);

						gameMenuBar.getLevelCustom().setSelected(true);
					}
				});
		gameMenuBar.getLevelLow().addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				gameMenuBar.getLevelLow().setSelected(true);
				gameMenuBar.getLevelMiddle().setSelected(false);
				gameMenuBar.getLevelHigh().setSelected(false);

				gameMenuBar.getLevelCustom().setSelected(false);
			}

		});
		gameMenuBar.getLevelMiddle().addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				gameMenuBar.getLevelLow().setSelected(false);
				gameMenuBar.getLevelMiddle().setSelected(true);
				gameMenuBar.getLevelHigh().setSelected(false);

				gameMenuBar.getLevelCustom().setSelected(false);
			}

		});
		gameMenuBar.getLevelHigh().addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				gameMenuBar.getLevelLow().setSelected(false);
				gameMenuBar.getLevelMiddle().setSelected(false);
				gameMenuBar.getLevelHigh().setSelected(true);

				gameMenuBar.getLevelCustom().setSelected(false);
			}

		});
	}

	public GameMenuBar getGameMenuBar()
	{
		return gameMenuBar;
	}

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public HighScoreDialog getHighScoreDialog()
	{
		return highScoreDialog;
	}

	public CustomMineFieldDialog getCustomMineFieldDialog()
	{
		return customMineFieldDialog;
	}

	public InputYourNameDialogUIRes getInputYourNameDialogUIRes()
	{
		return inputYourNameDialogUIRes;
	}

	@Override
	public Iterator<Component> iterator()
	{
		Iterator<Component> itr = Util.createCompItr(this);
		itr = new CompositeIterator<Component>(itr,
				Util.createCompItr(customMineFieldDialog));
		itr = new CompositeIterator<Component>(itr,
				Util.createCompItr(highScoreDialog));

		return itr;
	}

	@Override
	public void pack()
	{
		super.pack();
		this.customMineFieldDialog.pack();
		this.highScoreDialog.pack();
	}

	@Override
	public void revalidate()
	{
		super.revalidate();
		this.customMineFieldDialog.revalidate();
		this.highScoreDialog.revalidate();
	}

	public void close()
	{
		this.processWindowEvent(new WindowEvent(this,
				WindowEvent.WINDOW_CLOSING));
	}

	public static class InputYourNameDialogUIRes
	{
		private String title = "";
		private String message = "";

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getMessage()
		{
			return message;
		}

		public void setMessage(String message)
		{
			this.message = message;
		}
	}

}
