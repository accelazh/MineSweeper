package org.accela.minesweeper.controller.forviewevent;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.model.operation.Operation;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.view.DigitPanel;
import org.accela.minesweeper.view.GameFrame;
import org.accela.minesweeper.view.MineFieldPanel;
import org.accela.minesweeper.view.MineGridPanel;
import org.accela.minesweeper.view.NumberPanel;

public class ViewEventController implements Controller
{
	public static final int PRIORITY = 3;

	private AllMouseListener mouseListener = new AllMouseListener();

	private AWTEventListener keyEventListener = new KeyEventListener();

	private PropertyChangeListener newGridsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getPropertyName().equals(
					MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY))
			{
				Object[] newVals = (Object[]) evt.getNewValue();
				for (Object obj : newVals)
				{
					MineGridPanel g = (MineGridPanel) obj;

					g.addMouseListener(mouseListener);
					g.addMouseMotionListener(mouseListener);
					g.addMouseWheelListener(mouseListener);
				}
			}
		}
	};

	private PropertyChangeListener newDigitsListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getPropertyName().equals(
					NumberPanel.NEW_DIGITS_CHANGED_PROPERTY))
			{
				Object[] newVals = (Object[]) evt.getNewValue();
				for (Object obj : newVals)
				{
					DigitPanel g = (DigitPanel) obj;

					g.addMouseListener(mouseListener);
					g.addMouseMotionListener(mouseListener);
					g.addMouseWheelListener(mouseListener);
				}
			}
		}
	};

	private GameModel model = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		Toolkit.getDefaultToolkit().addAWTEventListener(keyEventListener,
				AWTEvent.KEY_EVENT_MASK);

		for (Component c : view)
		{
			if (c instanceof JMenuItem)
			{
				continue;
			}

			c.addMouseListener(mouseListener);
			c.addMouseMotionListener(mouseListener);
			c.addMouseWheelListener(mouseListener);
		}

		view.getGamePanel()
				.getMineField()
				.addPropertyChangeListener(
						MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY,
						newGridsListener);
		view.getGamePanel()
				.getStatus()
				.getLeftNumber()
				.addPropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
		view.getGamePanel()
				.getStatus()
				.getRightNumber()
				.addPropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);

		this.model = model;
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		Toolkit.getDefaultToolkit().removeAWTEventListener(keyEventListener);

		for (Component c : view)
		{
			c.removeMouseListener(mouseListener);
			c.removeMouseMotionListener(mouseListener);
			c.removeMouseWheelListener(mouseListener);
		}

		view.getGamePanel()
				.getMineField()
				.removePropertyChangeListener(
						MineFieldPanel.NEW_GRIDS_CHANGED_PROPERTY,
						newGridsListener);
		view.getGamePanel()
				.getStatus()
				.getLeftNumber()
				.removePropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
		view.getGamePanel()
				.getStatus()
				.getRightNumber()
				.removePropertyChangeListener(
						NumberPanel.NEW_DIGITS_CHANGED_PROPERTY,
						newDigitsListener);
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

	private void sendOperation(Operation.Type type, MouseEvent me, KeyEvent ke)
	{
		boolean questionMarkEnabled = Profile.getInstance()
				.isQuestionMarkEnabled();
		Operation op = new Operation(type, me, ke, questionMarkEnabled);
		model.onOperation(op);
	}

	private class AllMouseListener implements MouseListener,
			MouseMotionListener, MouseWheelListener
	{
		private MouseEvent lastMouseEvent = null;

		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			sendOperation(Operation.Type.MOUSE_WHEEL_MOVED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_DRAGGED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_MOVED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_CLICKED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_PRESSED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_RELEASED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			/*
			 * 从菜单中退出时，MineGridPanel会接受到一个button==1的MouseEntered事件，这很诡异，
			 * 会扰乱我的程序逻辑，而且还没法修正。不知道swing干嘛这么做。 这里我要把这个事件拦截，并修正掉。
			 */
			if (lastMouseEvent != null
					&& lastMouseEvent.getID() == MouseEvent.MOUSE_EXITED
					&& ((lastMouseEvent.getSource() instanceof JPopupMenu.Separator)
							|| (lastMouseEvent.getSource() instanceof JMenuBar) || (lastMouseEvent
								.getSource() instanceof JPopupMenu)))
			{
				e = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), 0,
						e.getX(), e.getY(), e.getClickCount(),
						e.isPopupTrigger(), MouseEvent.NOBUTTON);
			}

			sendOperation(Operation.Type.MOUSE_ENTERED, e, null);
			lastMouseEvent = e;
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			sendOperation(Operation.Type.MOUSE_EXITED, e, null);
			lastMouseEvent = e;
		}
	}

	private class KeyEventListener implements AWTEventListener
	{
		@Override
		public void eventDispatched(AWTEvent event)
		{
			KeyEvent e = (KeyEvent) event;
			Operation.Type type = null;
			switch (e.getID())
			{
			case KeyEvent.KEY_PRESSED:
				type = Operation.Type.KEY_PRESSED;
				break;
			case KeyEvent.KEY_RELEASED:
				type = Operation.Type.KEY_RELEASED;
				break;
			case KeyEvent.KEY_TYPED:
				type = Operation.Type.KEY_TYPED;
				break;
			default:
				// do nothing
				break;
			}

			sendOperation(type, null, e);
		}
	}
}
