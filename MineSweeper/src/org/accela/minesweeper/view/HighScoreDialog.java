package org.accela.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.accela.minesweeper.util.Common;


public class HighScoreDialog extends BasicDialog
{
	private static final long serialVersionUID = 1L;

	private Panel panel = new Panel();

	public HighScoreDialog()
	{
		initHighScoreDialog();
	}

	public HighScoreDialog(Frame owner)
	{
		super(owner);
		initHighScoreDialog();
	}

	private void initHighScoreDialog()
	{
		panel.setOpaque(true);
		this.setContentPane(panel);
		this.pack();

		this.addExitDialogActionListenerFor(panel.getOkButton());
	}

	public Panel getPanel()
	{
		return panel;
	}

	@Override
	protected void onEscStroke()
	{
		this.panel.getOkButton().doClick();
	}

	@Override
	protected void onEnterStroke()
	{
		this.panel.getOkButton().doClick();
	}

	public static class PanelValue
	{
		public int lowScore;
		public int middleScore;
		public int highScore;

		public PanelValue()
		{
			// do nothing
		}

		public PanelValue(int lowScore, int middleScore, int highScore)
		{
			this.lowScore = lowScore;
			this.middleScore = middleScore;
			this.highScore = highScore;
		}

	}

	public static class Panel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		private JLabel lowLevelLabel = new JLabel();
		private JLabel middleLevelLabel = new JLabel();
		private JLabel highLevelLabel = new JLabel();

		private JLabel lowScoreLabel = new JLabel();
		private JLabel middleScoreLabel = new JLabel();
		private JLabel highScoreLabel = new JLabel();

		private JLabel lowScoreUnitLabel = new JLabel();
		private JLabel middleScoreUnitLabel = new JLabel();
		private JLabel highScoreUnitLabel = new JLabel();

		private JLabel lowScoreNameLabel = new JLabel();
		private JLabel middleScoreNameLabel = new JLabel();
		private JLabel highScoreNameLabel = new JLabel();

		private JButton resetButton = new JButton();
		private JButton okButton = new JButton();

		private final JLabel[][] labels = new JLabel[][] {
				{
						lowLevelLabel,
						lowScoreLabel,
						lowScoreUnitLabel,
						lowScoreNameLabel },
				{
						middleLevelLabel,
						middleScoreLabel,
						middleScoreUnitLabel,
						middleScoreNameLabel },
				{
						highLevelLabel,
						highScoreLabel,
						highScoreUnitLabel,
						highScoreNameLabel }, };

		public Panel()
		{
			this.setLayout(new BorderLayout(0, Common.GUI_STANDARD_INTERVAL * 2));

			this.add(createTopPanel(), BorderLayout.CENTER);
			this.add(createBottomPanel(), BorderLayout.SOUTH);
		}

		private JPanel createTopPanel()
		{
			// JPanel p = new JPanel(new GridLayout(3, 1, 0,
			// Common.GUI_STANDARD_INTERVAL));
			// for (int i = 0; i < labels.length; i++)
			// {
			// JLabel[] group = labels[i];
			//
			// JLabel level = group[0];
			// JLabel score = group[1];
			// JLabel unit = group[2];
			// JLabel name = group[3];
			//
			// JPanel outer = new JPanel(new BorderLayout(
			// Common.GUI_STANDARD_INTERVAL, 0));
			// outer.add(level, BorderLayout.WEST);
			//
			// JPanel inner = new JPanel(new BorderLayout(
			// Common.GUI_STANDARD_INTERVAL, 0));
			// inner.add(score, BorderLayout.CENTER);
			// inner.add(unit, BorderLayout.EAST);
			// outer.add(inner, BorderLayout.CENTER);
			//
			// outer.add(name, BorderLayout.EAST);
			//
			// p.add(outer);
			// }

			JPanel p = new JPanel(new GridBagLayout());
			for (int i = 0; i < labels.length; i++)
			{
				JLabel[] group = labels[i];

				JLabel level = group[0];
				JLabel score = group[1];
				JLabel unit = group[2];
				JLabel name = group[3];

				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = i;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.insets = new Insets(Common.GUI_STANDARD_INTERVAL / 2,
						Common.GUI_STANDARD_INTERVAL / 2,
						Common.GUI_STANDARD_INTERVAL / 2,
						Common.GUI_STANDARD_INTERVAL / 2);
				p.add(level, c);

				c.gridx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.8;
				p.add(score, c);

				c.gridx = 2;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				p.add(unit, c);
				
				c.gridx = 3;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.4;
				p.add(name, c);

			}

			return p;
		}

		public PanelValue getValue()
		{
			PanelValue v = new PanelValue();
			try
			{
				v.lowScore = Integer.parseInt(this.lowScoreLabel.getText());
				v.middleScore = Integer.parseInt(this.middleScoreLabel
						.getText());
				v.highScore = Integer.parseInt(this.highScoreLabel.getText());
			}
			catch (NumberFormatException ex)
			{
				v.lowScore = 0;
				v.middleScore = 0;
				v.highScore = 0;
			}

			return v;
		}

		public void setValue(PanelValue v)
		{
			this.lowScoreLabel.setText(v.lowScore + "");
			this.middleScoreLabel.setText(v.middleScore + "");
			this.highScoreLabel.setText(v.highScore + "");
		}

		private JPanel createBottomPanel()
		{
			JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER,
					Common.GUI_STANDARD_INTERVAL * 4, 0));
			p.add(resetButton);
			p.add(okButton);

			return p;
		}

		public JLabel getLowLevelLabel()
		{
			return lowLevelLabel;
		}

		public JLabel getMiddleLevelLabel()
		{
			return middleLevelLabel;
		}

		public JLabel getHighLevelLabel()
		{
			return highLevelLabel;
		}

		public JLabel getLowScoreLabel()
		{
			return lowScoreLabel;
		}

		public JLabel getMiddleScoreLabel()
		{
			return middleScoreLabel;
		}

		public JLabel getHighScoreLabel()
		{
			return highScoreLabel;
		}

		public JLabel getLowScoreUnitLabel()
		{
			return lowScoreUnitLabel;
		}

		public JLabel getMiddleScoreUnitLabel()
		{
			return middleScoreUnitLabel;
		}

		public JLabel getHighScoreUnitLabel()
		{
			return highScoreUnitLabel;
		}

		public JLabel getLowScoreNameLabel()
		{
			return lowScoreNameLabel;
		}

		public JLabel getMiddleScoreNameLabel()
		{
			return middleScoreNameLabel;
		}

		public JLabel getHighScoreNameLabel()
		{
			return highScoreNameLabel;
		}

		public JButton getResetButton()
		{
			return resetButton;
		}

		public JButton getOkButton()
		{
			return okButton;
		}

		public JLabel[][] getLabels()
		{
			return labels;
		}
	}
}
