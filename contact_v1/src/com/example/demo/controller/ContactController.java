package com.example.demo.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

import com.example.demo.models.Contact;
import com.example.demo.models.Group;
import com.example.demo.services.Service;

public class ContactController {
	
	private Service service;
	public Scanner scanner;
	public ContactController() {
		service = new Service();
		scanner = new Scanner(System.in);
	}
	

//simulating the add contact service with the user	
	public void addContacts() {
		List<String> numberList= new ArrayList<String>();
		List<String> emailList= new ArrayList<String>();
		System.out.println("enter the contact name ");
		String name = scanner.nextLine();
		
		System.out.println("enter the contact address ");
		String address = scanner.nextLine();
		
		System.out.println("enter the contact mobile Number ");
		String number = scanner.nextLine();
		numberList.add(number);
		
//allowing user to add one or more contact numbers
		String choice=null;
		do {
			System.out.println("Do you want to add more Number?");
			System.out.println("yes or no?");
			choice=scanner.nextLine();
			if(choice.equalsIgnoreCase("yes")) {
				System.out.println("enter the mobile number");
				number = scanner.nextLine();
				numberList.add(number);
			}
		}while(choice.equalsIgnoreCase("yes"));
		
		String[] mobileNumber =  numberList.toArray(new String[0]);
		
		

		System.out.println("choose the profile picture");
		String imageReference = scanner.nextLine();
		
		
		
		System.out.println("enter your date of birth as yyyy-MM-dd");
		String strDateOfBirth = scanner.nextLine();
		LocalDate dateOfBirth = LocalDate.parse(strDateOfBirth);
		
		
		
		System.out.println("enter the contact email Id ");
		String email = scanner.nextLine();
		emailList.add(email);
		
		
//allowing user to add one or more email ids
		choice=null;
		do {
			System.out.println("Do you want to add more email Id?");
			System.out.println("yes or no?");
			choice=scanner.nextLine();
			if(choice.equalsIgnoreCase("yes")) {
				System.out.println("enter the email Id");
				email = scanner.nextLine();
				emailList.add(email);
			}
		}while(choice.equalsIgnoreCase("yes"));
		
		String emailArray[]=  emailList.toArray(new String[0]);
		
		
		
		
		System.out.println("select the group you want to add the contact to");
		showGroups(service);
		int groupId = Integer.parseInt(scanner.nextLine());
		
		//setting up the contact object with the user defined details
		Contact contact = new Contact(name, address, mobileNumber, imageReference, dateOfBirth, emailArray, groupId); 
		
		//calling the addContact service which in turn calls add method of DAO
		int rowsAdded = service.addContact(contact);
		System.out.println(rowsAdded+" rows added ");
	}
	
	
	
	//controller for removing contacts
	public void removeContact() {
		System.out.println("enter the name of the contact you want to delete?");
		String name = scanner.nextLine();
		int rowsEffected = service.removeContact(name);
		System.out.println(rowsEffected+" row was removed");
	}
	
	
	//controller for updating the contact
	public void updateContact() {
		System.out.println("enter the name of the contact whose details are to be updated");
		String name = scanner.nextLine();
		Contact existing = service.getContact(name); //get the existing contact details from service<-dao
		
		
		System.out.println("1. keep the same address\n2. update the address");
		int choice = Integer.parseInt(scanner.nextLine());
		String address;
		if(choice==1) {
			address=existing.getAddress();
		}
		else {
			System.out.println("enter new address");
			address = scanner.nextLine();
		}
		
		//mobile number updation
		System.out.println("1. keep the same mobile number(s)\n2. add new mobile number\n3.remove existing added number");
		choice = Integer.parseInt(scanner.nextLine());
		String mobileNumber[];
		
		//if the user wants to keep same mobile number(s)
		if(choice==1) {
			mobileNumber=existing.getMobileNumber();
		}
		
		//if the user wants to add a new mobile number to already existing 
		else if(choice==2) {
			mobileNumber=existing.getMobileNumber();
			List<String> numberList = new ArrayList<>(Arrays.asList(mobileNumber));
			System.out.println("enter new mobile number");
			String newNumber = scanner.nextLine();
			if(numberList.contains(newNumber)) {
				System.out.println("the number is already a part of this contact");
			}
			else {
				numberList.add(newNumber);
			}
			mobileNumber=numberList.toArray(new String[0]);
		}
		
		//if the user wants to remove some number from existing numbers
		else {
			mobileNumber=existing.getMobileNumber();
			List<String> numberList = new ArrayList<>(Arrays.asList(mobileNumber));
			System.out.println("enter mobile number to remove");
			String removingNumber = scanner.nextLine();
			if(!numberList.contains(removingNumber)) {
				System.out.println("no such mobile number exist");
			}
			else {
				numberList.remove(removingNumber);
			}
			mobileNumber=numberList.toArray(new String[0]);
		}
		
		
		
		//updating the profile picture
		System.out.println("1. keep the same image\n2. update the image");
		choice = Integer.parseInt(scanner.nextLine());
		String imageReference;
		if(choice==1) {
			imageReference=existing.getImageReference();
		}
		else {
			System.out.println("enter new image path");
			imageReference = scanner.nextLine();
		}
		
		
		
		//updating the date of birth
		System.out.println("1. keep the same date of birth\n2. update the date of birth");
		choice = Integer.parseInt(scanner.nextLine());
		LocalDate dateOfBirth;
		if(choice==1) {
			dateOfBirth=existing.getDateOfBirth();
		}
		else {
			System.out.println("enter new date of birth");
			dateOfBirth = LocalDate.parse(scanner.nextLine());
		}
		
		
		
		//updating the email - same logic as that of updating mobile number
		System.out.println("1. keep the same email Id(s)\n2. add new email Id\n3.remove existing email Id");
		choice = Integer.parseInt(scanner.nextLine());
		String email[];
		if(choice==1) {
			email=existing.getEmail();
		}
		else if(choice==2) {
			email=existing.getEmail();
			List<String> emailList = new ArrayList<>(Arrays.asList(email));
			System.out.println("enter new email Id");
			String newEmail = scanner.nextLine();
			if(emailList.contains(newEmail)) {
				System.out.println("the email is already a part of this contact");
			}
			else {
				emailList.add(newEmail);
			}
			email=emailList.toArray(new String[0]);
		}
		else {
			email=existing.getEmail();
			List<String> emailList = new ArrayList<>(Arrays.asList(email));
			System.out.println("enter email to remove");
			String removingEmail = scanner.nextLine();
			if(!emailList.contains(removingEmail)) {
				System.out.println("no such email exist");
			}
			else {
				emailList.remove(removingEmail);
			}
			email=emailList.toArray(new String[0]);
		}
		
		
		//updating the group id 
		System.out.println("1. keep the same group Id\n2. update the group Id");
		choice = Integer.parseInt(scanner.nextLine());
		int groupId;
		if(choice==1) {
			groupId = existing.getGroupId();
		}
		else {
			System.out.println("enter new group Id");
			showGroups(service); 					//this is static method which shows the available groups
			groupId = Integer.parseInt(scanner.nextLine());
		}
		
		//setting up the contact object 
		Contact updated = new Contact(name, address, mobileNumber, imageReference, dateOfBirth, email, groupId);
		
		//calling the service update method which calls the DAO to update the contact
		int rowsEffected = service.updateExistingContact(existing, updated);
		System.out.println(rowsEffected+" row(s) updated");
		
	}
	
	

//===============================CONTROLLERS FOR GENERATING DATA================================
	
	
	public void queryContactByBirthday() {
		System.out.println("enter the month in terms of number : [eg:= January -1, December -2]");
		int month = Integer.parseInt(scanner.nextLine());
		List<Contact> contactList = service.getContactList();
		contactList = service.getContactByBirthDay(month,contactList);
		
		
		System.out.println("1. show in console\n2. write to a file");
		int choice = Integer.parseInt(scanner.nextLine());
		if(choice==1) {
			showContacts(contactList);
		}
		else {
			boolean written=service.writeContacts(contactList);
			if(written) {
			System.out.println("contacts written to a file");
			}
		}
		
	}
	
	
	
	//select only 2 feilds
	public void queryLimitedAttributes() {
		File file = null;
		System.out.println("select from the options");
		System.out.println("1. query only name and mobile number\n2. query only name and email");
		int choice = Integer.parseInt(scanner.nextLine());
		
		 
		List<Contact> contactList = service.getContactList();
		Map<String, String[]> transformedContacts = service.getMappedTranform(contactList, choice);
		
		
		System.out.println("1. show in console\n2. write to a file");
		int option = Integer.parseInt(scanner.nextLine());
		if(option==1) {
			showTransformedContacts(transformedContacts);
		}
		else {
			if(choice==1) {
				file = new File("nameNumber.txt");
			}
			else {
				file = new File("nameEmail.txt");
			}
			service.writeTransformedContacts(transformedContacts,file);
			System.out.println("contacts written to a file");
		}
	}
	
	
	
	
	public void queryContactByGroup() {
		Map<String,List<Contact>> contactGroup = service.getContactWithGroup();
		Set<Map.Entry<String, List<Contact>>> entrySet = contactGroup.entrySet();
		for(Map.Entry<String, List<Contact>> eachGroup : entrySet) {
			System.out.println("===="+eachGroup.getKey()+"===========");
			for(Contact contact : eachGroup.getValue()) {
				System.out.println("---->"+contact);
			}
			System.out.println();
			
		}
	}
	
	
	public void queryContactByGroupCount() {
		Map<String,List<Contact>> contactGroup = service.getContactWithGroup();
		List<Entry<String, List<Contact>>> list = new LinkedList<Entry<String, List<Contact>>>(contactGroup.entrySet());
		Collections.sort(list,new Comparator<Entry<String, List<Contact>>>() {

			@Override
			public int compare(Entry<String, List<Contact>> o1, Entry<String, List<Contact>> o2) {
				Integer o1Size = o1.getValue().size();
				Integer o2Size = o2.getValue().size();
				return o1Size.compareTo(o2Size);
			}
		});
		contactGroup = new LinkedHashMap<String, List<Contact>>();  
		for (Entry<String, List<Contact>> entry : list)   
		{  
			contactGroup.put(entry.getKey(), entry.getValue());  
		}     
		Set<Map.Entry<String, List<Contact>>> entrySet = contactGroup.entrySet();
		for(Map.Entry<String, List<Contact>> eachGroup : entrySet) {
			System.out.println("===="+eachGroup.getKey()+"===========");
			System.out.println();
			System.out.println("Number of Contacts ====> "+eachGroup.getValue().size());
			System.out.println("\nContact Details\n");
			for(Contact contact : eachGroup.getValue()) {
				System.out.println("---->"+contact);
			}
			System.out.println();
			
		}
		
		
	}
	
	
	
//================private methods to print tables======================
	
	//a method which prints the group details
	private static void showGroups(Service service) {
		List<Group> groupList = service.getGroupList();
		for(int index=0;index<groupList.size();index++) {
			System.out.println(groupList.get(index));
		}
	}
	
	
	//method to print the contacts
	private static void showContacts(List<Contact> contactList) {
		contactList.forEach(System.out::println);
	}
	
	//method to print contacts by options
	private static void showTransformedContacts(Map<String, String[]> transformedContacts) {
		
		Set<Map.Entry<String, String[]>> entrySet = transformedContacts.entrySet();
		for(Map.Entry<String, String[]> eachContact : entrySet) {
			System.out.println(eachContact.getKey()+" "+Arrays.toString(eachContact.getValue()));
		}
	}
	
	

//==============CLOSE CONNECTION/SCANNER====================
	public void closeAll() {
		this.scanner.close();
		this.service.getContactDao().closeConnection();
	}
}
