package org.accela.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.accela.minesweeper.util.Common;


public class CustomMineFieldDialog extends BasicDialog
{
	private static final long serialVersionUID = 1L;

	private Panel panel = new Panel();

	public CustomMineFieldDialog()
	{
		initCustomMineFieldDialog();
	}

	public CustomMineFieldDialog(Frame owner)
	{
		super(owner);
		initCustomMineFieldDialog();
	}

	private void initCustomMineFieldDialog()
	{
		panel.setOpaque(true);
		this.setContentPane(panel);
		this.pack();

		this.addExitDialogActionListenerFor(panel.getOkButton());
		this.addExitDialogActionListenerFor(panel.getCancelButton());
	}

	public Panel getPanel()
	{
		return panel;
	}

	@Override
	protected void onEscStroke()
	{
		this.panel.getCancelButton().doClick();
	}

	@Override
	protected void onEnterStroke()
	{
		this.panel.getOkButton().doClick();
	}

	public static class MineFieldValue
	{
		public int height;
		public int width;
		public int mine;

		public MineFieldValue()
		{
			// do nothing
		}

		public MineFieldValue(int height, int width, int mine)
		{
			this.height = height;
			this.width = width;
			this.mine = mine;
		}

	}

	public static class Panel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		private JLabel heightLabel = new JLabel();
		private JLabel widthLabel = new JLabel();
		private JLabel mineLabel = new JLabel();

		private JTextField heightField = new JTextField();
		private JTextField widthField = new JTextField();
		private JTextField mineField = new JTextField();

		private JButton okButton = new JButton();
		private JButton cancelButton = new JButton();

		public Panel()
		{
			this.setLayout(new BorderLayout(2 * Common.GUI_STANDARD_INTERVAL, 0));
			this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			this.add(createLeftPanel(), BorderLayout.CENTER);
			this.add(createRightPanel(), BorderLayout.EAST);
		}

		private JPanel createLeftPanel()
		{
			JPanel p = new JPanel(new GridLayout(3, 1, 0,
					Common.GUI_STANDARD_INTERVAL));
			JPanel p1 = new JPanel(new BorderLayout(
					Common.GUI_STANDARD_INTERVAL, 0));
			JPanel p2 = new JPanel(new BorderLayout(
					Common.GUI_STANDARD_INTERVAL, 0));
			JPanel p3 = new JPanel(new BorderLayout(
					Common.GUI_STANDARD_INTERVAL, 0));

			p.add(p1);
			p.add(p2);
			p.add(p3);

			p1.add(heightLabel, BorderLayout.WEST);
			p1.add(heightField, BorderLayout.CENTER);

			p2.add(widthLabel, BorderLayout.WEST);
			p2.add(widthField, BorderLayout.CENTER);

			p3.add(mineLabel, BorderLayout.WEST);
			p3.add(mineField, BorderLayout.CENTER);

			return p;
		}

		private JPanel createRightPanel()
		{
			JPanel p = new JPanel(new BorderLayout());

			p.add(okButton, BorderLayout.NORTH);
			p.add(new JPanel(), BorderLayout.CENTER);
			p.add(cancelButton, BorderLayout.SOUTH);

			return p;

		}

		public MineFieldValue getValue()
		{
			MineFieldValue v = new MineFieldValue();
			try
			{
				v.height = Integer.parseInt(heightField.getText());
				v.width = Integer.parseInt(widthField.getText());
				v.mine = Integer.parseInt(mineField.getText());
			} catch (NumberFormatException ex)
			{
				v.height = 0;
				v.width = 0;
				v.mine = 0;
			}

			return v;
		}

		public void setValue(MineFieldValue value)
		{
			heightField.setText(value.height + "");
			widthField.setText(value.width + "");
			mineField.setText(value.mine + "");
		}

		public JLabel getHeightLabel()
		{
			return heightLabel;
		}

		public JLabel getWidthLabel()
		{
			return widthLabel;
		}

		public JLabel getMineLabel()
		{
			return mineLabel;
		}

		public JTextField getHeightField()
		{
			return heightField;
		}

		public JTextField getWidthField()
		{
			return widthField;
		}

		public JTextField getMineField()
		{
			return mineField;
		}

		public JButton getOkButton()
		{
			return okButton;
		}

		public JButton getCancelButton()
		{
			return cancelButton;
		}

	}

}
