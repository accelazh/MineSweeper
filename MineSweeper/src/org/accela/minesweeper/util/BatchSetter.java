package org.accela.minesweeper.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BatchSetter
{
	// /*
	// * whatToSet is a map:
	// * <className>|* / <ObjectName>|* / property => <Object>
	// * <className>|* / <ObjectName>|* / property => <Object>
	// * ...
	// *
	// * the former part tells which property in which object is to be set
	// * the latter part tells what object to set
	// */
	/**
	 * 
	 *  @deprecated 功能能太弱：只能通过(className/objectName/property => Object)方式设定
	 *  属性，不支持更多级的路径，也不支持如setIcons(int idx, Icon icon)式的多参数setter。理想的现
	 *  实应该是能够进行(className/objectName/property1/property2/property3[?]/property4[?], IdxObject1, IdxObject2 => Object)
	 *  形式的属性设定。但是这个实现起来太复杂，现在已经改用其它方法。
	 */
	 @Deprecated
	public void batchSet(Map<String, Object> whatToSet,
			Iterator<Object> whereToSet)
	{
		Map<ObjectPath, List<PropertyAndValue>> map = null;
		try
		{
			map = parseWhatToSet(whatToSet);
		}
		catch (MalformedPathException ex)
		{
			throw new IllegalArgumentException(ex.getMessage());
		}

		while (whereToSet.hasNext())
		{
			Object obj = whereToSet.next();
			findMatchedAndSet(map, obj);
		}

	}

	private void findMatchedAndSet(Map<ObjectPath, List<PropertyAndValue>> map,
			Object obj)
	{
		List<ObjectPath> targetObjPath = getPossibleObjPath(obj);
		for (ObjectPath path : targetObjPath)
		{
			List<PropertyAndValue> matchedList = map.get(path);
			if (null == matchedList)
			{
				continue;
			}

			String errorStr = "Failed to set property on object (" + obj
					+ ") of path (" + path.toString() + ")";
			try
			{
				batchSetProperty(matchedList, obj);
			}
			catch (NoSuchMethodException ex)
			{
				throw new IllegalArgumentException(errorStr, ex);
			}
			catch (InvocationTargetException ex)
			{
				throw new IllegalArgumentException(errorStr, ex);
			}
		}
	}

	private void batchSetProperty(List<PropertyAndValue> list, Object obj)
			throws NoSuchMethodException, InvocationTargetException
	{
		for (PropertyAndValue propAndVal : list)
		{
			String errorStr = "Failed to set property (" + propAndVal.prop
					+ ") to value (" + propAndVal.value + ")";
			try
			{
				ReflexUtil.setProperty(obj, propAndVal.prop, propAndVal.value);
			}
			catch (NoSuchMethodException ex)
			{
				throw new NoSuchMethodException(errorStr + ": No proper setter found!");
			}
			catch (InvocationTargetException ex)
			{
				InvocationTargetException newEx = new InvocationTargetException(
						ex.getTargetException(), errorStr
								+ ": Exception on setter!");
				throw newEx;
			}
		}
	}

	private static class ObjectPath
	{
		public String className;
		public String name;

		public ObjectPath()
		{
			// do nothing
		}

		public ObjectPath(String className, String name)
		{
			this.className = className;
			this.name = name;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((className == null) ? 0 : className.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ObjectPath other = (ObjectPath) obj;
			if (className == null)
			{
				if (other.className != null)
					return false;
			}
			else if (!className.equals(other.className))
				return false;
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return className + "/" + name;
		}

	}

	private static class PropertyAndValue
	{
		public String prop;
		public Object value;
	}

	private Map<ObjectPath, List<PropertyAndValue>> parseWhatToSet(
			Map<String, Object> whatToSet) throws MalformedPathException
	{
		Map<ObjectPath, List<PropertyAndValue>> map = new HashMap<ObjectPath, List<PropertyAndValue>>();

		for (String path : whatToSet.keySet())
		{
			ObjectPath objPath = new ObjectPath();
			PropertyAndValue propAndVal = new PropertyAndValue();

			parsePath(objPath, propAndVal, path);
			propAndVal.value = whatToSet.get(path);

			addToMap(map, objPath, propAndVal);
		}

		return map;
	}

	private void addToMap(Map<ObjectPath, List<PropertyAndValue>> map,
			ObjectPath objPath, PropertyAndValue propAndVal)
	{
		List<PropertyAndValue> list = map.get(objPath);
		if (null == list)
		{
			list = new LinkedList<PropertyAndValue>();
			map.put(objPath, list);
		}
		list.add(propAndVal);
	}

	private static class MalformedPathException extends Exception
	{
		private static final long serialVersionUID = 1L;

		public MalformedPathException(String message)
		{
			super(message);
		}
	}

	private void parsePath(ObjectPath destObjPath,
			PropertyAndValue destPropAndVal, String path)
			throws MalformedPathException
	{
		String[] pathTokens = path.split("\\/");
		if (pathTokens.length != 3)
		{
			throw new MalformedPathException("illegal path: " + path);
		}
		for (int i = 0; i < pathTokens.length; i++)
		{
			pathTokens[i] = pathTokens[i].trim();
		}
		if (pathTokens[0].length() <= 0 || pathTokens[2].length() <= 0)
		{
			throw new MalformedPathException("illegal path: " + path);
		}

		destObjPath.className = pathTokens[0];
		if (pathTokens[1].equals("null"))
		{
			destObjPath.name = null;
		}
		else
		{
			destObjPath.name = pathTokens[1];
		}

		destPropAndVal.prop = pathTokens[2];
	}

	private List<ObjectPath> getPossibleObjPath(Object obj)
	{
		List<ObjectPath> list = new LinkedList<ObjectPath>();

		String name = ReflexUtil.getNameOf(obj);
		String className = obj.getClass().getName();
		String simpleClassName = obj.getClass().getSimpleName();

		// 排列顺序会影响路径匹配的优先级
		list.add(new ObjectPath("*", "*"));
		list.add(new ObjectPath("*", name));
		list.add(new ObjectPath(simpleClassName, "*"));
		list.add(new ObjectPath(className, "*"));
		list.add(new ObjectPath(simpleClassName, name));
		list.add(new ObjectPath(className, name));

		return list;
	}

}
