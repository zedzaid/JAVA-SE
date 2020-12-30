package com.example.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.demo.daos.ContactDaoImpl;
import com.example.demo.daos.GroupDaoImp;
import com.example.demo.models.Contact;
import com.example.demo.models.Group;

public class Service {
		private ContactDaoImpl contactDaoObject ;
		private GroupDaoImp	groupDaoObject;
		private List<Group> groupList;
		private List<Contact> contactList;
		public Service() {
			contactDaoObject = new ContactDaoImpl();
			groupDaoObject = new GroupDaoImp();
			groupList=new ArrayList<Group>();
			contactList = new ArrayList<Contact>();
		}
		
	
//==================CONTACT CRUD SERVICES================================
		
		/*AFTER EVERY ADD DELETE AND UPDATE STATEMENT GETS EXECUTED
		 * THE SERVICE UPDATES THE CONTACT LIST BY FETCHING THE VALUES AGAIN
		 * FROM THE SQL.
		 * ================================
		 * IF NOT ADDED/REMOVED/UPDATED IT RETURNS THE ALREADY EXISTING LIST
		 * THEREBY REDUCING THE NUMBER OF CALLS TO THE CONNECTION
		 */
		
		public int addContact(Contact contact) {
			int rowsAdded = contactDaoObject.add(contact);
			this.updateContactList();
			return rowsAdded;
		}
		
		public int removeContact(String name) {
			int rowsEffected = contactDaoObject.remove(name);
			this.updateContactList();
			return rowsEffected;
		}
		
		public int updateExistingContact(Contact existing,Contact updated) {
			int rowsEffected = contactDaoObject.update(existing, updated);
			this.updateContactList();
			return rowsEffected;
		}
		
	
		
		
		
		
		
		
		
//==========================================================	
		
		
//updating and fetching the details from table	
		
		public void updateContactList() {
			this.contactList=contactDaoObject.getAll();
		}
		
		//get all contacts
		public List<Contact> getContactList(){
			if(this.contactList.isEmpty()) {
				this.updateContactList();
			}
			return this.contactList;
		}
		
		//get only specified contact
		public Contact getContact(String name) {
			Contact contact = contactDaoObject.getContactByName(name);
			return contact;
		}
		

		
//==================GROUP CRUD SERVICES===================
		
		
		/*AFTER EVERY ADD DELETE AND UPDATE STATEMENT GETS EXECUTED
		 * THE SERVICE UPDATES THE GROUP LIST BY FETCHING THE VALUES AGAIN
		 * FROM THE SQL.
		 * ================================
		 * IF NOT ADDED/REMOVED/UPDATED IT RETURNS THE ALREADY EXISTING LIST
		 * THEREBY REDUCING THE NUMBER OF CALLS TO THE CONNECTION
		 */
		
		
		
		public int addGroup(Group group) {
			int rowsAdded=groupDaoObject.add(group);
			this.updateGroupList();
			return rowsAdded;
		}
		
		//updates only when new group is added 
		public void updateGroupList() {
			this.groupList= groupDaoObject.getAll();
		}
		
		//get group list 
		public List<Group> getGroupList(){
			if(this.groupList.isEmpty()) {
				this.updateGroupList();
			}
			return this.groupList;
		}
		
		
		
//=================QUERY SERVICES======================
		
		
		/*METHOD USES THE STREAM FUNCTIONALITY BY JAVA 8
		 * STREAMS FILTER THE CONTACTS WITH THEIR BIRTH MONTH 
		 * ADDS IT TO THE FILTERED LIST IF THE MONTH MATCHES WITH THE PASSED VALUE
		 * RETURNS THE NEW ARRAY OF FILTERED VALUES
		 */
		
		public List<Contact> getContactByBirthDay(int month,List<Contact> contactList){
			List<Contact> birthDayContacts = contactList.
											 stream().
											 filter(
											(Contact contact)->
											contact.getDateOfBirth().getMonthValue()==month).
											 collect(toList());
			
			return birthDayContacts;
		}
		
		
		
		public Map<String,List<Contact>> getContactWithGroup(){
			Map<String,List<Contact>> contactMap = contactDaoObject.getContactsWithGroupName();
			return contactMap;
		}
		

//==================WRITING TO FILE SERVICES=================== 	
		
		//WRITING THE BIRTHDAY CONTACTS
		public boolean writeContacts(List<Contact> contactList)  {
			boolean result=false;
			File outPutFile = new File("birthday.txt");
			try(PrintWriter pWriter = new PrintWriter(outPutFile)){
				contactList.stream().
				forEach(pWriter::println);
				result=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		
		/*THIS METHOD WILL WRITE ANY TWO FIELDS FROM THE TABLE
		 * EXAMPLE := NAME EMAIL
		 */
		public void writeTransformedContacts(Map<String, String[]> transformedContacts,File file) {
			try(PrintWriter pWriter = new PrintWriter(file)){
				Set<Map.Entry<String, String[]>> entrySet = transformedContacts.entrySet();
				for(Map.Entry<String, String[]> eachContact : entrySet) {
					pWriter.println(eachContact.getKey()+" "+Arrays.toString(eachContact.getValue()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

//====================TRANSFORM TO MAP ==============================
		
		
		
		public Map<String,String[]> getMappedTranform(List<Contact> contactList,int option){
			Map<String,String[]> transformedContacts= new HashMap<String, String[]>();
			if(option==1) {
				transformedContacts= contactList.stream().
						collect(toMap(Contact::getName, Contact::getMobileNumber));
			}
			else {
				transformedContacts= contactList.stream().
						collect(toMap(Contact::getName, Contact::getEmail));

			}
			return transformedContacts;
		}
		
		
		
		
//USED FOR TESTING PURPOSE / WILL BE DEPRECATED BEFORE PRODUCTION
		public ContactDaoImpl getContactDao() {
			return this.contactDaoObject;
		}
}
