package org.accela.minesweeper.util;

import java.io.Serializable;

public abstract class CompositeProcessor
{
	private Class<?> targetClass = null;

	private String targetName = null;

	public CompositeProcessor()
	{
		// do nothing
	}

	public CompositeProcessor(Class<?> targetClass, String targetName)
	{
		this.targetClass = targetClass;
		this.targetName = targetName;
	}

	public String getTargetName()
	{
		return targetName;
	}

	public void setTargetName(String targetName)
	{
		this.targetName = targetName;
	}

	public Class<?> getTargetClass()
	{
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass)
	{
		this.targetClass = targetClass;
	}

	public boolean process(Object obj)
	{
		if (isTargetClassMatched(obj) && isTargetNameMatched(obj))
		{
			return doProcess(obj);
		}
		else
		{
			return false;
		}
	}

	public boolean process(Iterable<?> itr)
	{
		boolean succ = false;
		for (Object obj : itr)
		{
			succ = process(obj) || succ;
		}

		return succ;
	}

	private boolean isTargetClassMatched(Object obj)
	{
		if (null == this.targetClass)
		{
			return true;
		}
		else if (null == obj)
		{
			return true;
		}
		else
		{
			return this.targetClass.isInstance(obj);
		}
	}

	private boolean isTargetNameMatched(Object obj)
	{
		if (null == this.targetName)
		{
			return true;
		}
		else if (null == obj)
		{
			return false;
		}
		else
		{
			String objName = ReflexUtil.getNameOf(obj);
			return this.targetName.equals(objName);
		}
	}

	protected abstract boolean doProcess(Object obj);

	public CompositeProcessor compose(CompositeProcessor proc)
	{
		return new ProcessBoth(this, proc);
	}

	private static class ProcessBoth extends CompositeProcessor implements
			Serializable
	{
		private static final long serialVersionUID = 1L;

		private CompositeProcessor procFirst;
		private CompositeProcessor procSecond;

		public ProcessBoth(CompositeProcessor procFirst,
				CompositeProcessor procSecond)
		{
			this.procFirst = procFirst;
			this.procSecond = procSecond;
		}

		@Override
		protected boolean doProcess(Object obj)
		{
			boolean[] succ = new boolean[2];
			succ[0] = (procFirst != null) ? (procFirst.process(obj)) : false;
			succ[1] = (procSecond != null) ? (procSecond.process(obj)) : false;
			return succ[0] || succ[1];
		}

	}
}
