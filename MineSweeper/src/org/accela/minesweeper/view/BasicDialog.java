package org.accela.minesweeper.view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class BasicDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private ExitDialogActionListener listener = new ExitDialogActionListener();

	public BasicDialog()
	{
		this.initBasicDialog();
	}

	public BasicDialog(Frame owner)
	{
		super(owner);
		this.initBasicDialog();
	}

	private void initBasicDialog()
	{
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

		this.getRootPane()
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
						"BasicDialogEscStroke");
		this.getRootPane().getActionMap()
				.put("BasicDialogEscStroke", new AbstractAction()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e)
					{
						onEscStroke();
					}
				});

		this.getRootPane()
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
						"BasicDialogEnterStroke");
		this.getRootPane().getActionMap()
				.put("BasicDialogEnterStroke", new AbstractAction()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e)
					{
						onEnterStroke();
					}
				});
	}

	protected void onEscStroke()
	{
		// do nothing
	}

	protected void onEnterStroke()
	{
		// do nothing
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			this.pack();
			this.setLocationRelativeTo(this.getOwner());
		}
		super.setVisible(visible);
	}

	public void addExitDialogActionListenerFor(AbstractButton btn)
	{
		btn.addActionListener(listener);
	}

	private class ExitDialogActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			BasicDialog.this.setVisible(false);
		}
	}
}
