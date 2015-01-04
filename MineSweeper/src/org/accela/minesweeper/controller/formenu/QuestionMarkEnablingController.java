package org.accela.minesweeper.controller.formenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import org.accela.minesweeper.controller.Controller;
import org.accela.minesweeper.model.GameModel;
import org.accela.minesweeper.profile.Profile;
import org.accela.minesweeper.view.GameFrame;

//QuestionMark��ȫ��ģ�Ͳ�͸��¿�����ʵ�֣�ģ�Ͳ�ֻ�ܼ�¼MarkEnable״̬������Mark�����¿������������ѡ����ʵ���ͼ����
public class QuestionMarkEnablingController implements Controller
{
	public static final int PRIORITY=10;
	
	private ActionListener listener = new EnablingListener();

	private GameModel model = null;

	@Override
	public void install(GameModel model, GameFrame view)
	{
		view.getGameMenuBar().getMark().addActionListener(listener);
		this.model = model;
		
		model.setQuestionMarkEnabled(Profile.getInstance().isQuestionMarkEnabled());
		view.getGameMenuBar().getMark().setSelected(Profile.getInstance().isQuestionMarkEnabled());
	}

	@Override
	public void uninstall(GameModel model, GameFrame view)
	{
		view.getGameMenuBar().getMark().removeActionListener(listener);
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

	private class EnablingListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem m = (JCheckBoxMenuItem) e.getSource();
			Profile.getInstance().setQuestionMarkEnabled(m.isSelected());

			model.setQuestionMarkEnabled(Profile.getInstance()
					.isQuestionMarkEnabled());
		}
	}

}
