package com.example.demo.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ifaces.*;
import com.example.demo.models.Group;
import com.example.demo.utils.DbConnectionUtils;

public class GroupDaoImp implements GroupDataAccess<Group> {
	
	private Connection con=null;
	
	

	public GroupDaoImp() {
		con = DbConnectionUtils.getMySqlConnection();
	}



	@Override
	public List<Group> getAll() {
		String sql = "select * from contact_group";
		List<Group> groupList = new ArrayList<Group>();
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				int groupId = result.getInt("groupId");
				String groupName = result.getString("groupName");
				Group group = new Group(groupId, groupName);
				groupList.add(group);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return groupList;
	}



	@Override
	public int add(Group t) {
		String sql = "insert into contact_group(groupName) values(?)";
		int rowsAdded=0;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, t.getGroupName());
			rowsAdded = pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rowsAdded;
	}

}
