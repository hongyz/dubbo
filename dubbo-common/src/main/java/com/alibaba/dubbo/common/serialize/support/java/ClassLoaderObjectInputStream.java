package com.alibaba.dubbo.common.serialize.support.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import com.alibaba.dubbo.common.utils.ClassHelper;

/**
 * classloader java object input stream.
 * 
 * @author hongyz
 */

public class ClassLoaderObjectInputStream extends ObjectInputStream
{
	private ClassLoader mClassLoader;

	public ClassLoaderObjectInputStream(InputStream in) throws IOException
	{
		this(in, Thread.currentThread().getContextClassLoader());
	}

	public ClassLoaderObjectInputStream(InputStream in, ClassLoader cl) throws IOException
	{
		super(in);
		mClassLoader = cl == null ? ClassHelper.getClassLoader() : cl;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
			ClassNotFoundException {
		 String name = desc.getName();
		  try {
		   return Class.forName(name, false, mClassLoader);
		  } catch (ClassNotFoundException ex) {
		   return super.resolveClass(desc);
		  }
	}
	
}