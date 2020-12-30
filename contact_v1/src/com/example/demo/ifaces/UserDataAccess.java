package com.example.demo.ifaces;

public interface UserDataAccess<T>{
	public boolean addUser(T user);
	public boolean validateUser(T user);
}
