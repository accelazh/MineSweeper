package org.accela.minesweeper.controller.formodelupdate;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.model.MineFieldModel;
import org.accela.minesweeper.model.MineGridModel;
import org.accela.minesweeper.model.SmileModel;
import org.accela.minesweeper.util.ObjectActionEvent;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.MineFieldPanel;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.MineGridPanel.IconState;
import org.accela.minesweeper.view.SmilePanel;

public class ModelUpdateController implements Controller
{
	public static final int PRIORITY = 4;

	private UpdateListener listener = new UpdateListener();

	private GameFrame frame = null;

	private GameModel model = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		model.addActionListener(listener);
		this.frame = view;
		this.model = model;

		this.frame.getGamePanel().getStatus().getLeftNumber().setDigitCount(3);
		this.frame.getGamePanel().getStatus().getRightNumber().setDigitCount(3);
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		model.removeActionListener(listener);
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

	private void updateMineField(MineFieldModel f, Object arg)
	{
		boolean structureChanged = false;
		MineFieldPanel fp = frame.getGamePanel().getMineField();
		Dimension modelGridSize = f.getFieldSize();
		if (!f.getFieldSize().equals(fp.getFieldSize()))
		{
			fp.setFieldSize(modelGridSize.width, modelGridSize.height);
			structureChanged = true;
		}

		if (arg instanceof MineFieldModel)
		{
			for (MineGridModel g : f)
			{
				Point pos = f.getGridPos(g);
				MineGridPanel gp = fp.getGrid(pos.x, pos.y);
				updateMineGrid(g, gp);
			}
		}
		else
		{
			MineGridModel[] gs = (MineGridModel[]) arg;
			for (MineGridModel g : gs)
			{
				Point pos = f.getGridPos(g);
				MineGridPanel gp = fp.getGrid(pos.x, pos.y);
				updateMineGrid(g, gp);
			}
		}

		if (structureChanged)
		{
			frame.pack();
			frame.revalidate();
			frame.setLocationRelativeTo(null);
		}
		else
		{
			if (arg instanceof MineGridModel[])
			{
				Dimension fieldSize = frame.getGamePanel().getMineField()
						.getFieldSize();
				int gridCount = fieldSize.width * fieldSize.height;
				MineGridModel[] gs = (MineGridModel[]) arg;

				if (gs.length < gridCount / 2)
				{
					for (MineGridModel g : gs)
					{
						Point pos = f.getGridPos(g);
						MineGridPanel gp = fp.getGrid(pos.x, pos.y);
						gp.repaint();
					}
				}
				else
				{
					frame.getGamePanel().getMineField().repaint();
				}
			}
			else
			{
				frame.repaint();
			}
		}
	}

	private void updateMineGrid(MineGridModel m, MineGridPanel p)
	{
		switch (m.getMarkState())
		{
		case NONE:
			p.setIconState(MineGridPanel.IconState.CLEAR);
			break;
		case FLAG:
			p.setIconState(MineGridPanel.IconState.FLAG);
			break;
		case QUESTION:
			if (model.isQuestionMarkEnabled())
			{
				p.setIconState(MineGridPanel.IconState.QUESTION);
			}
			else
			{
				p.setIconState(MineGridPanel.IconState.CLEAR);
			}
			break;
		default:
			assert (false);
			break;
		}

		switch (m.getState())
		{
		case UP_NORMAL:
			p.setBtnState(MineGridPanel.BtnState.UP);
			p.setBtnUpState(MineGridPanel.BtnUpState.NORMAL);
			break;
		case UP_GLOW:
			p.setBtnState(MineGridPanel.BtnState.UP);
			p.setBtnUpState(MineGridPanel.BtnUpState.GLOW);
			break;
		case DOWN_TRYING:
			p.setBtnState(MineGridPanel.BtnState.DOWN);
			p.setBtnDownState(MineGridPanel.BtnDownState.ALERT);
			p.setIconState(MineGridPanel.IconState.CLEAR);
			break;
		case DOWN:
			p.setBtnState(MineGridPanel.BtnState.DOWN);
			if (m.isMine())
			{
				if (m.getBombState().equals(MineGridModel.BombState.NORMAL))
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.ALERT);
					p.setIconState(MineGridPanel.IconState.BOMB);
				}
				else if (m.getBombState().equals(MineGridModel.BombState.WRONG))
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.SAFE);
					p.setIconState(MineGridPanel.IconState.WRONG_BOMB);
				}
				else if (m.getBombState().equals(MineGridModel.BombState.DEAD))
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.DEAD);
					p.setIconState(MineGridPanel.IconState.DEAD_BOMB);
				}
				else
				{
					assert (false);
				}
			}
			else
			{
				int mineAround = m.getParent().countMineAround(m);
				if (mineAround > 0)
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.ALERT);
					p.setIconState(MineGridPanel.numToIconState(mineAround));
				}
				else
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.SAFE);
					p.setIconState(IconState.CLEAR);
				}
				
				if(m.getBombState().equals(MineGridModel.BombState.WRONG))
				{
					p.setBtnDownState(MineGridPanel.BtnDownState.ALERT);
					p.setIconState(IconState.WRONG_BOMB);
				}
			}
			break;
		default:
			assert (false);
			break;
		}
	}

	private void updateSmile(SmileModel s)
	{
		SmilePanel sp = frame.getGamePanel().getStatus().getSmile();
		if (s.isGlow())
		{
			sp.setBtnUpState(SmilePanel.BtnUpState.GLOW);
		}
		else
		{
			sp.setBtnUpState(SmilePanel.BtnUpState.NORMAL);
		}
		sp.setBtnDownState(SmilePanel.BtnDownState.NORMAL);

		switch (s.getState())
		{
		case UP_SMILE:
			sp.setIconState(SmilePanel.IconState.SMILE);
			sp.setBtnState(SmilePanel.BtnState.UP);
			break;
		case UP_NERVOUS:
			sp.setIconState(SmilePanel.IconState.NERVOUS);
			sp.setBtnState(SmilePanel.BtnState.UP);
			break;
		case UP_WIN:
			sp.setIconState(SmilePanel.IconState.WIN);
			sp.setBtnState(SmilePanel.BtnState.UP);
			break;
		case UP_DEAD:
			sp.setIconState(SmilePanel.IconState.DEAD);
			sp.setBtnState(SmilePanel.BtnState.UP);
			break;
		case DOWN_SMILE:
			sp.setIconState(SmilePanel.IconState.SMILE);
			sp.setBtnState(SmilePanel.BtnState.DOWN);
			break;
		default:
			assert (false);
			break;
		}

		frame.repaint();
	}

	private class UpdateListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals(
					GameModel.FLAG_COUNTER_UPDATED_ACTION_COMMAND))
			{
				ObjectActionEvent oe = (ObjectActionEvent) e;
				frame.getGamePanel().getStatus().getLeftNumber()
						.setNumber((Integer) oe.getObject());

				frame.getGamePanel().getStatus().getLeftNumber().repaint();
			}
			else if (e.getActionCommand().equals(
					GameModel.GAME_DEAD_ACTION_COMMAND))
			{
				// do nothing
			}
			else if (e.getActionCommand().equals(
					GameModel.GAME_START_ACTION_COMMAND))
			{
				// do nothing
			}
			else if (e.getActionCommand().equals(
					GameModel.GAME_WIN_ACTION_COMMAND))
			{
				// do nothing
			}
			else if (e.getActionCommand().equals(
					GameModel.MINE_FIELD_UPDATED_ACTION_COMMAND))
			{
				ObjectActionEvent oe = (ObjectActionEvent) e;
				updateMineField(model.getField(), oe.getObject());
			}
			else if (e.getActionCommand().equals(
					GameModel.SMILE_UPDATED_ACTION_COMMAND))
			{
				ObjectActionEvent oe = (ObjectActionEvent) e;
				updateSmile((SmileModel) oe.getObject());
			}
			else if (e.getActionCommand().equals(
					GameModel.TIME_COUNTER_UPDATED_ACTION_COMMAND))
			{
				ObjectActionEvent oe = (ObjectActionEvent) e;
				frame.getGamePanel().getStatus().getRightNumber()
						.setNumber((Integer) oe.getObject());

				frame.getGamePanel().getStatus().getRightNumber().repaint();
			}
			else
			{
				assert (false);
			}
		}
	}

}
