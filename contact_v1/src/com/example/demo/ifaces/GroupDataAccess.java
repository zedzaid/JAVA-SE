package com.example.demo.ifaces;

import java.util.List;

public interface GroupDataAccess<T> {

	public int add(T t);
	public List<T> getAll();
	
}
