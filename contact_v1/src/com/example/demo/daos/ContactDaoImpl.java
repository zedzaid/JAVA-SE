package com.example.demo.daos;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

import com.example.demo.ifaces.DataAccess;
import com.example.demo.models.Contact;
import com.example.demo.utils.DbConnectionUtils;


public class ContactDaoImpl implements DataAccess<Contact> {
	
	private Connection con;
	
	//Call this constructor to use Mysql Connection
	public ContactDaoImpl() {
		con=DbConnectionUtils.getMySqlConnection();
	}
	
	//Call this constructor if you want to have your own connection
	public ContactDaoImpl(Connection con) {
		super();
		this.con = con;
	}

	

//========================CRUD DAOS=================================
	@Override
	public int add(Contact t) {
		String sql = "insert into contact(name,address,mobileNumber,imageReference,dateOfBirth,email,groupId) values(?,?,?,?,?,?,?)";
		int rowsAdded=0;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,t.getName());
			pstmt.setString(2, t.getAddress());
			StringBuffer mobileNumber = new StringBuffer();
			String[] numbers = t.getMobileNumber();
			for(String eachNumber : numbers) {
				mobileNumber.append(eachNumber+",");
			}
			pstmt.setString(3, mobileNumber.toString());
			pstmt.setString(4, t.getImageReference());
			LocalDate date = t.getDateOfBirth();
			Date birthDate = Date.valueOf(date); 
			pstmt.setDate(5, birthDate);
			StringBuffer email = new StringBuffer();
			String[] emailList = t.getEmail();
			for(String eachEmail : emailList) {
				email.append(eachEmail+",");
			}
			pstmt.setString(6, email.toString());
			pstmt.setInt(7, t.getGroupId());
			rowsAdded=pstmt.executeUpdate();
		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rowsAdded;
	}
	
	
	
	//REMOVE CONTACT FROM THE TABLE
	@Override
	public int remove(String name) {
		String query = "delete from contact where name=?";
		int rowsEffected=0;
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setString(1, name);
			rowsEffected=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowsEffected;
	}

	
	
	//UPDATE CONTACT FROM THE TABLE
	@Override
	public int update(Contact existing, Contact updated) {
		String query = "update contact "
				+ "set address=?,mobileNumber=?,imageReference=?,"
				+ "dateOfBirth=?,"
				+ "email=?,groupId=? "
				+ "where name=?";
int rowsAffected=0;
try(PreparedStatement pstmt=con.prepareStatement(query)){
	
	pstmt.setString(1, updated.getAddress());
	
	StringBuffer mobileNumber = new StringBuffer();
	String[] numbers = updated.getMobileNumber();
	for(String eachNumber : numbers) {
		mobileNumber.append(eachNumber+",");
	}
	pstmt.setString(2,mobileNumber.toString());
	pstmt.setString(3, updated.getImageReference());
	
	LocalDate date = updated.getDateOfBirth();
	Date birthDate = Date.valueOf(date); 
	pstmt.setDate(4, birthDate);
	
	StringBuffer email = new StringBuffer();
	String[] emailList = updated.getEmail();
	for(String eachEmail : emailList) {
		email.append(eachEmail+",");
	}
	pstmt.setString(5, email.toString());
	pstmt.setInt(6, updated.getGroupId());
	pstmt.setString(7, existing.getName());
	rowsAffected=pstmt.executeUpdate();
	
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return rowsAffected;
	}
	
	
	
	
//=======================QUERY DAOS===============================

	@Override
	public List<Contact> getAll() {
		String query= "select * from contact";
		List<Contact> contactList = new ArrayList<Contact>();
		
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			
			ResultSet results = pstmt.executeQuery();
			
			contactList=getContacts(results);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	
	
	
	
    //GET CONTACTS WITH GROUP NAME (INNER JOIN CONTACT AND GROUP TABLE)
	@Override
	public Map<String,List<Contact>> getContactsWithGroupName() {
		Map<String,List<Contact>> contactMap = new HashMap<String, List<Contact>>();
		Contact contact=null;
		String sql="select name,address,mobileNumber,"
				+ "imageReference,dateOfBirth,email,"
				+ "contact.groupId,groupName from contact join"
				+ " contact_group "
				+ "where contact.groupId = contact_group.groupId;";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				String name=resultSet.getString("name");
				String address = resultSet.getString("address");
				String []mobileNumber = resultSet.getString("mobileNumber").split(",");
				String imageReference = resultSet.getString("imageReference");
				LocalDate dateOfBirth = resultSet.getDate("dateOfBirth").toLocalDate();
				String email[] = resultSet.getString("email").split(",");
				int groupId = resultSet.getInt("groupId");
				String groupName = resultSet.getString("groupName");
				contact = new Contact(name, address, mobileNumber, imageReference, dateOfBirth, email, groupId);
				if(contactMap.containsKey(groupName)) {
					List<Contact> contactList = contactMap.get(groupName);
					contactList.add(contact);
					contactMap.put(groupName, contactList);
				}
				else {
					List<Contact> contactList = new ArrayList<Contact>(Arrays.asList(contact));
					contactMap.put(groupName, contactList);
				}
				
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return contactMap;
	}

	

	
	//GET A SINGLE CONTACT WITH ITS NAME
	@Override
	public Contact getContactByName(String contactName) {
		String sql = "select * from contact where name=?";
		Contact contact=null;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, contactName);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				String name=result.getString("name");
				String address = result.getString("address");
				String []mobileNumber = result.getString("mobileNumber").split(",");
				String imageReference = result.getString("imageReference");
				LocalDate dateOfBirth = result.getDate("dateOfBirth").toLocalDate();
				String email[] = result.getString("email").split(",");
				int groupId = result.getInt("groupId");
				contact = new Contact(name, address, mobileNumber, imageReference, dateOfBirth, email, groupId);
				
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return contact;
	}
	
	
	
//====================PRIVATE STATIC METHODS========================
	
	//A STATIC METHOD USED TO ITERATE THROUGH RESULT SET , CREATE LIST OF CONTACTS FROM IT AND RETURN
	private static List<Contact> getContacts(ResultSet result) {
		Contact contact=null;
		List<Contact> contactList = new ArrayList<Contact>();
		try {
		while(result.next()) {
			String name=result.getString("name");
			String address = result.getString("address");
			String []mobileNumber = result.getString("mobileNumber").split(",");
			String imageReference = result.getString("imageReference");
			LocalDate dateOfBirth = result.getDate("dateOfBirth").toLocalDate();
			String email[] = result.getString("email").split(",");
			int groupId = result.getInt("groupId");
			contact = new Contact(name, address, mobileNumber, imageReference, dateOfBirth, email, groupId);
			contactList.add(contact);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}
	

//=================CLOSING CONNECTION================
	public void closeConnection() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//USED FOR TESTING PURPOSE ONLY / WILL BE DEPRECATED BEFORE PRODUCTION	
	public Connection getConnection() {
		return this.con;
	}

}
