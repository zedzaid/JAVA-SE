package com.example.demo.ifaces;

import java.util.*;

public interface DataAccess<T> {
	public int add(T t);
	public int remove(String t);
	public int update(T existing,T updated);
	public List<T> getAll();
	public T getContactByName(String name);
	public Map<String,List<T>> getContactsWithGroupName();

	
	
}
