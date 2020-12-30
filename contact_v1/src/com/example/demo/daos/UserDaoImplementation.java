package com.example.demo.daos;

import java.sql.Connection;

import com.example.demo.ifaces.UserDataAccess;
import com.example.demo.models.User;

public class UserDaoImplementation implements UserDataAccess<User> {
	private static final String tableName="users";
	private Connection connection=null;

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
