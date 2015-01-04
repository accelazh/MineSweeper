package org.accela.minesweeper.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * 用Composite的方式构造的Iterator，可能造成Iterator的嵌套深度与要遍历的元素数量级相同，因而效率相当的低。
 * 使用时要小心。
 */
public class CompositeIterator<T> implements Iterator<T>, Serializable
{
	private static final long serialVersionUID = 1L;

	private Iterator<T> itrFirst;

	private Iterator<T> itrSecond;

	public Iterator<T> getItrFirst()
	{
		return itrFirst;
	}

	public Iterator<T> getItrSecond()
	{
		return itrSecond;
	}

	public CompositeIterator(Iterator<T> itrFirst, Iterator<T> itrSecond)
	{
		this.itrFirst = itrFirst;
		this.itrSecond = itrSecond;
	}

	@Override
	public boolean hasNext()
	{
		return itrHasNext(itrFirst) || itrHasNext(itrSecond);
	}

	private boolean itrHasNext(Iterator<T> itr)
	{
		return (itr != null) ? itr.hasNext() : false;
	}

	@Override
	public T next()
	{
		if (itrHasNext(itrFirst))
		{
			return itrFirst.next();
		}
		else if (itrSecond != null)
		{
			return itrSecond.next();
		}
		else
		{
			throw new NoSuchElementException();
		}
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
