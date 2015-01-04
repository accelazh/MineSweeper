package org.accela.minesweeper.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * ��Composite�ķ�ʽ�����Iterator���������Iterator��Ƕ�������Ҫ������Ԫ����������ͬ�����Ч���൱�ĵ͡�
 * ʹ��ʱҪС�ġ�
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
