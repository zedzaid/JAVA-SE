package com.example.demo;



import java.util.Scanner;

import com.example.demo.controller.ContactController;



public class Application {

	public static void main(String[] args) {
		
		ContactController controller = new ContactController();
		Scanner scanner = new Scanner(System.in);
		boolean menuLoop = true;
		System.out.println("******WLECOME TO CONTACT MANAGEMENT*******");
		while(menuLoop) {
		System.out.println("\n====MENU====\n");
		System.out.println("\n1. ADD\n"
						   + "2. DELETE\n"
						   + "3. UPDATE\n"
						   + "4. GET CONTACTS BIRTHDAYS BY MONTH\n"
						   + "5. GET CONTACTS WITH ONLY NAME AND EMAIL/NUMBER\n"
						   + "6. GET CONTACTS ALONG WITH THEIR GROUP NAME\n"
						   + "7. GET CONTACTS WITH SORTED GROUP SIZE\n"
						   + "8. EXIT");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			controller.addContacts();
			break;
		case 2:
			controller.removeContact();
			break;
		case 3:
			controller.updateContact();
			break;
		case 4:
			controller.queryContactByBirthday();
			break;
		case 5:
			controller.queryLimitedAttributes();
			break;
		case 6:
			controller.queryContactByGroup();
			break;
		case 7:
			controller.queryContactByGroupCount();
			break;
		case 8:
			menuLoop=false;
			controller.closeAll();
			scanner.close();
			break;
		default:
			System.out.println("Please select a vaild option");
			break;
		}
		}
	
	}

}
