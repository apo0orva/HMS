package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class HMS {
	Scanner inp = new Scanner(System.in);
	Connection con;
	Statement st;
	ResultSet rs, rs1;
	String url, portno, Musername, Mpassword, username, password, repassword, q, choice;
	String did, dname, speciality, dgender, disease, i_c;
	String pid, pname, pgender, pbg, pcity;
	String symptoms, diagnosis, pmedicines, i_o, admit_d, discharge_d, appoinment_d;
	int flag, intchoice;
	char yn;
	static JFrame f;
	//boolean isConnected = false;
	static ArrayList<String> login = new ArrayList<String>();
	static ArrayList<String> data = new ArrayList<String>();
	static ArrayList<String> data1 = new ArrayList<String>();

	public static void main(String[] args) {
		f = new JFrame();
		f.setVisible(false);
		HMS hms = new HMS();
		hms.menu();

	}

	private void menu() {
		do {
			System.out.println("WELCOME TO HOSPITAL MANAGEMENT SYSTEM");
			System.out.println("1. Install Software: ");
			System.out.println("2. Uninstall Software: ");
			System.out.println("3. Run Software: ");
			
			do {
				flag = 0;
				System.out.println("Enter choice: ");
				choice = inp.next();
				
				if(choice.isEmpty() == true) {
					flag = 1;
					System.out.println("Enter choice!");
				}
				
				flag = 0;
				
				try {
					flag = 0;
					intchoice = Integer.parseInt(choice);
					if(intchoice == 1) {
						install();
					}
					
					else if(intchoice == 2) {
						uninstall();
					}
					
					else if(intchoice == 3) {
						mainp();
					}
					
					else {
						flag = 1;
						System.out.println("Please enter valid choice!");
						break;
					}
				}
				
				catch(Exception e) {
					flag = 1;
					System.out.println("Please enter valid choice!");
				}
				
			}
			
			while(flag == 1);
			
			System.out.println("\nReturn to main menu?(Y/n) ");
			yn = inp.next().charAt(0);
		}
		while (yn == 'Y' || yn == 'y');
		System.out.println("Exit.......");
	}
	
	private void user_login() {
		
		try {
			q = "select * from login;";
			rs = st.executeQuery(q);
			while(rs.next()) {
				login.add(rs.getString(1));
				login.add(rs.getString(2));
				login.add(rs.getString(3));
				login.add(rs.getString(4));
			}
			
			System.out.println(login);
		}
			
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		System.out.println("\nUser Login\nEnter username=x and password=x to exit");
		System.out.println("Enter username: ");
		username = inp.next();
		System.out.println("Enter password: ");
		password = inp.next();
		//System.out.println("YUIO");
		
		if(username!="" || password!="") {
			if(username=="x" & password=="x") {
				
				mainp();
			}
			else {
				for (int i = 0; i < login.size(); i+=4)
				{
					//String un = login.get(i);
					if(username.equals(login.get(i)))
					{
						//System.out.println(username+" found");
						if(password.equals(login.get(i+1))) {
							System.out.println("Welcome "+ login.get(i));
							
							if(("1").equals(login.get(i+2))) {
								login_management();
							}
							
							if(("2").equals(login.get(i+2))) {
								login_receptionist();
							}
							
							if(("3").equals(login.get(i+2))) {
								did = login.get(i+3);
								login_doctor(did);
							}
							
						}
					}
				}
			}
			
		}
		else {
			System.out.println("Enter BOTH username and password!");
		}
	}

	private void mainp() {
		System.out.println("WELCOME TO HOSPITAL MANAGEMENT SYSTEM");
		
		portno = "3306";
		url = "jdbc:mysql://localhost:"+portno;
		
		do {
			flag = 0;
			System.out.println("\nMySQL Login");
			System.out.println("Enter MySQL username: ");
			Musername = inp.next();
			System.out.println("Enter MySQL password: ");
			Mpassword = inp.next();
			
			if(Musername.isEmpty() || Mpassword.isEmpty()) {
				flag = 1;
				System.out.println("Please enter both username and password!");
			}
		}
	
		while(flag == 1);
		
		System.out.println("Default port number is 3306. Do you want to change?(Y/n) ");
		yn = inp.next().charAt(0);
		
		if(yn == 'y' || yn == 'Y') {
			System.out.println("Enter updated MySQL port number: ");
			portno = inp.next();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, Musername, Mpassword);
			
			if(con.isClosed()==false) {
				
			st = con.createStatement();
			
			q = "use hospital;";
			st.executeUpdate(q);
			
			user_login();
			
			
			//st.close();
			}
			
			//con.close();
		}
		catch (Exception e) {
			System.out.println("Enter correct username and password!");
		}
		
	}
	
	private void login_doctor(String did) {
		login.clear();
		choice = null;
		System.out.println("\nFollowing are Doctor's role: ");
		System.out.println("1. Diagnose a Patient");
		System.out.println("2. Logout");
		
		do {
			flag = 0;
			System.out.println("Enter choice: ");
			choice = inp.next();
			
			if(choice.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter choice!");
			}
			
			flag = 0;
			
			try {
				flag = 0;
				intchoice = Integer.parseInt(choice);
				if(intchoice == 1) {
					doctor_1();
				}
				
				else if(intchoice == 2) {
					user_login();
				}
				
				else if(intchoice > 5) {
					flag = 1;
					System.out.println("Please enter valid choice!");
				}
			}
			
			catch(Exception e) {
				flag = 1;
				System.out.println("Please enter valid choice!");
			}
			
		}
		
		while(flag == 1);
		
	}
	
	private void doctor_1() {
		System.out.println("\nDiagnose a Patient");
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's id (pid): ");
			pid = inp.next();
			
			if(pid.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's name!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = String.format("select * from patient where pid='%s';", pid);
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
				data.add(rs.getString(2));
				data.add(rs.getString(3));
				data.add(rs.getString(4));
				data.add(rs.getString(5));
				data.add(rs.getString(6));
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		if(data.isEmpty()) {
			System.out.println("No patient registered with pid="+pid);
		}
		
		for(int i = 0; i < data.size(); i+=6) {
			System.out.println("Patient's Details");
			System.out.println("pid = "+data.get(i));
			System.out.println("Name = "+data.get(i+1));
			System.out.println("Gender = "+data.get(i+2));
			System.out.println("Blood Group = "+data.get(i+3));
	
		}
		
		try {
			q = String.format("select * from d_pat_%s;", pid);
			rs = st.executeQuery(q);
			while(rs.next()) {
				data1.add(rs.getString(1));
				data1.add(rs.getString(2));
				data1.add(rs.getString(3));
				data1.add(rs.getString(4));
				data1.add(rs.getString(5));
				data1.add(rs.getString(6));
				data1.add(rs.getString(7));
				data1.add(rs.getString(8));
				data1.add(rs.getString(9));
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Patients's History: "+data1.size());
		
		int sno = 0;
		for(int i = 0; i < data1.size(); i+=9) {
			sno++;
			System.out.println("History: "+sno);
			System.out.println("Entry date: "+data1.get(i));
			System.out.println("Symptoms: "+data1.get(i+1));
			System.out.println("Disease: "+data1.get(i+2));
			System.out.println("Diagnosis: "+data1.get(i+3));
			System.out.println("Prescribed medicines: "+data1.get(i+4));
			System.out.println("Patient admited: "+data1.get(i+5));
			System.out.println("Admit date (if admited): "+data1.get(i+6));
			System.out.println("Discharge date: "+data1.get(i+7));
			System.out.println("Appoinment date: "+data1.get(i+8));
			
		}
		
		do {
			flag = 0;
			
			System.out.println("\nEnter Patient's symptoms: ");
			symptoms = inp.next();
			
			if(symptoms.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's symptoms!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's disease: ");
			disease = inp.next();
			
			if(disease.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's disease!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's diagnosis: ");
			diagnosis = inp.next();
			
			if(diagnosis.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's diagnosis!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's medicines: ");
			pmedicines = inp.next();
			
			if(pmedicines.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's medicines!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's In/Out-door status (I/O): ");
			i_o = inp.next();
			
			if(i_o.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's In/Out-door status!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			//System.out.println("Enter Patient's Admit date (YYYY-MM-DD HH:MM:SS)(def = 1000-01-01 00:00:00): ");
			admit_d = JOptionPane.showInputDialog("Enter Patient's Admit date (YYYY-MM-DD HH:MM:SS): ", "1000-01-01 00:00:00");
	
			if(admit_d.isEmpty() == true) {
				flag = 1;
				//System.out.println("Enter Patient's Admit date!");
				JOptionPane.showMessageDialog(f, "Enter Patient's Admit date!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			//System.out.println("Enter Patient's Discharge date (YYYY-MM-DD HH:MM:SS)(def = 1000-01-01 00:00:00): ");
			discharge_d = JOptionPane.showInputDialog("Enter Patient's Discharge date (YYYY-MM-DD HH:MM:SS): ", "1000-01-01 00:00:00");
			
			if(discharge_d.isEmpty() == true) {
				flag = 1;
				//System.out.println("Enter Patient's Discharge date!");
				JOptionPane.showMessageDialog(f, "Enter Patient's Discharge date!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			//System.out.println("Enter Patient's next Appoinment date (YYYY-MM-DD HH:MM:SS)(def = 1000-01-01 00:00:00): ");
			appoinment_d = JOptionPane.showInputDialog("Enter Patient's Appoinment date (YYYY-MM-DD HH:MM:SS): ", "1000-01-01 00:00:00");
			if(appoinment_d.isEmpty() == true) {
				flag = 1;
				//System.out.println("Enter Patient's next Appoinment date!");
				JOptionPane.showMessageDialog(f, "Enter Patient's Appoinment date!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter whether Patient's Infected or Cured (I/C): ");
			i_c = inp.next();
			
			if(i_c.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter whether Patient's Infected or Cured!");
			}
			
		}
		
		while(flag == 1);
		
		if("1000-01-01 00:00:00".equals(admit_d) & "1000-01-01 00:00:00".equals(appoinment_d)) {
			try {
				q = String.format("insert into d_pat_"+pid+" (entry_dates,symptoms,disease,diagnosis,medicines,iop,discharge_date) values"
						+"(CURRENT_TIMESTAMP(),'%s','%s','%s','%s','%s','%s','%s');", symptoms, disease, diagnosis, pmedicines, i_o, discharge_d);
				st.executeUpdate(q);
				
				q = String.format("insert into d_patient values ('%s','%s','%s','%s','%s')", pid, did, disease, i_o, i_c);
				st.executeUpdate(q);
			}
			
			catch(Exception e) {
				System.out.println(e);
			}
			
			System.out.println("Data recorded!");
		}
		
		if("1000-01-01 00:00:00".equals(discharge_d)) {
			try {
				q = String.format("insert into d_pat_"+pid+" (entry_dates,symptoms,disease,diagnosis,medicines,iop,admit_date,appoinment_date) values"
						+"(CURRENT_TIMESTAMP(),'%s','%s','%s','%s','%s','%s','%s');", symptoms, disease, diagnosis, pmedicines, i_o, admit_d, appoinment_d);
				st.executeUpdate(q);
				
				q = String.format("insert into d_patient values ('%s','%s','%s','%s','%s')", pid, did, disease, i_o, i_c);
				st.executeUpdate(q);
			}
			
			catch(Exception e) {
				System.out.println(e);
			}
			
			System.out.println("Data recorded!");
		}
		
		if("1000-01-01 00:00:00".equals(admit_d)) {
			try {
				q = String.format("insert into d_pat_"+pid+" (entry_dates,symptoms,disease,diagnosis,medicines,iop,discharge_date,appoinment_date) values"
						+"(CURRENT_TIMESTAMP(),'%s','%s','%s','%s','%s','%s','%s');", symptoms, disease, diagnosis, pmedicines, i_o, discharge_d, appoinment_d);
				st.executeUpdate(q);
				
				q = String.format("insert into d_patient values ('%s','%s','%s','%s','%s')", pid, did, disease, i_o, i_c);
				st.executeUpdate(q);
			}
			
			catch(Exception e) {
				System.out.println(e);
			}
			
			System.out.println("Data recorded!");
		}
		
		
		
		else {
			System.out.println("Date Error, check dates again.");
		}
		
		data.clear();
		data1.clear();
		//System.out.println(did);
		login_doctor(did);
		
		
	}

	private void login_receptionist() {
		login.clear();
		choice = null;
		System.out.println("\nFollowing are Receptionist's role: ");
		System.out.println("1. Register a new Patient");
		System.out.println("2. Search for Patient");
		System.out.println("3. Check for appoinments");
		System.out.println("4. Logout");
		
		do {
			flag = 0;
			System.out.println("Enter choice: ");
			choice = inp.next();
			
			if(choice.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter choice!");
			}
			
			flag = 0;
			
			try {
				flag = 0;
				intchoice = Integer.parseInt(choice);
				if(intchoice == 1) {
					receptionist_1();
				}
				
				else if(intchoice == 2) {
					receptionist_2();
				}
				
				else if(intchoice == 3) {
					receptionist_3();
				}
				
				else if(intchoice == 4) {
					user_login();
				}
				
				else if(intchoice > 5) {
					flag = 1;
					System.out.println("Please enter valid choice!");
				}
			}
			
			catch(Exception e) {
				flag = 1;
				System.out.println("Please enter valid choice!");
			}
			
		}
		
		while(flag == 1);
		
	}

	private void receptionist_3() {
		System.out.println("\nCheck for appoinments");
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's id: ");
			pid = inp.next();
			
			if(pid.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's id!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = String.format("select * from patient where pid = '%s';", pid);
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		if(data.isEmpty()) {
			System.out.println("No patients with pid: "+pid+" is registered!");
		}
		
		else {
			data.clear();
			try {
				q = String.format("select date(appoinment_date) from d_pat_%s order by appoinment_date desc limit 1;", pid);
				rs = st.executeQuery(q);
				while(rs.next()) {
					data.add(rs.getString(1));
				}
			}
			
			catch(Exception e) {
				System.out.println(e);
			}
		}
		
		if(data.isEmpty()) {
			System.out.println("No appoinments found!");
		}
		
		else if("1000-01-01 00:00:00".equals(data.get(0))) {
			System.out.println("No appoinments found!");
		}
		
		else {
			System.out.println("Pid: "+pid+"\nLast appoinment date: "+data.get(0));
		}
		
		data.clear();
		login_receptionist();
		
	}

	private void receptionist_2() {
		System.out.println("\nSearch for Patient");
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's name: ");
			pname = inp.next();
			
			if(pname.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's name!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's gender (M/F/O): ");
			pgender = inp.next();
			
			if(pgender.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's gender (M/F/O)!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = "select pid from patient where pname like '%"+pname+"%' and gender = '"+pgender+"';";
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
			}
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Result:");
		
		if(data.isEmpty()) {
			System.out.println("No patient found, edit you search result!");
		}
		
		else {
			int sno = 0;
			for(int i = 0; i < data.size(); i++) {
				sno++;
				System.out.println("S.no.: "+sno+" -> "+data.get(i));
			}
		}
		
		data.clear();
		login_receptionist();
		
	}

	private void receptionist_1() {
		System.out.println("\nRegister a new Patient");
		
		try {
			q = "select pid from patient order by pid desc limit 1;";
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
			}
			
			if(data.isEmpty()) {
				pid = "1";
				System.out.println("Previous pid: 0");
			}
			
			else {
				System.out.println("Previous pid: "+data.get(0));
				pid = String.valueOf(Integer.parseInt(data.get(0))+1);	
			}
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's name: ");
			pname = inp.next();
			
			if(pname.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's name!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's gender (M/F/O): ");
			pgender = inp.next();
			
			if(pgender.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's gender (M/F/O)!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's Blood group (Caps+/-): ");
			pbg = inp.next();
			
			if(pbg.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's Blood group (Caps+/-)!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Patient's City: ");
			pcity = inp.next();
			
			if(pcity.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Patient's City!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = String.format("insert into patient values ('%s','%s','%s','%s','%s',now());", pid, pname, pgender, pcity, pbg);
			st.executeUpdate(q);
//			System.out.println("Patient registered successfully!");
//			System.out.println("Patient id: "+pid);
			
			q = String.format("create table d_pat_%s(entry_dates datetime , symptoms varchar(100), disease varchar(50) , diagnosis varchar(100), medicines varchar(100), iop char(1) , admit_date datetime, discharge_date datetime , appoinment_date datetime , check(iop in('i','o','I','O')));;", pid);
			st.executeUpdate(q);
			
			System.out.println("Patient id: "+pid+" registered successfully!");
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		data.clear();
		login_receptionist();
		
	}

	private void login_management() {
		login.clear();
		choice = null;
		System.out.println("\nFollowing are Management's role: ");
		System.out.println("1. Register a new Doctor");
		System.out.println("2. Remove a Doctor");
		System.out.println("3. View monthly report of Doctors");
		System.out.println("4. View disease wise patient registration");
		System.out.println("5. Logout");
		
		do {
			flag = 0;
			System.out.println("Enter choice: ");
			choice = inp.next();
			
			if(choice.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter choice!");
			}
			
			flag = 0;
			
			try {
				flag = 0;
				intchoice = Integer.parseInt(choice);
				if(intchoice == 1) {
					management_1();
				}
				
				else if(intchoice == 2) {
					management_2();
				}
				
				else if(intchoice == 3) {
					management_3();
				}
				
				else if(intchoice == 4) {
					management_4();
				}
				
				else if(intchoice == 5) {
					user_login();
				}
				
				else if(intchoice > 5) {
					flag = 1;
					System.out.println("Please enter valid choice!");
				}
			}
			
			catch(Exception e) {
				flag = 1;
				System.out.println("Please enter valid choice!");
			}
			
		}
		
		while(flag == 1);
		
		
	}

	private void management_4() {
		System.out.println("\nView disease wise patient registration");
		
		do {
			flag = 0;
			
			System.out.println("Enter Disease: ");
			disease = inp.next();
			
			if(disease.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Disease!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Infected or Cured (I/C): ");
			i_c = inp.next();
			
			i_c = i_c.toUpperCase();
			
//			boolean t1 = !"I".equals(i_c);
//			boolean t2 = "C".equals(i_c);
//			
//			System.out.println("t1 "+t1);
			
			if(i_c.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter (I/C)!");
			}
			
//			boolean exp = "I".equals(i_c);
//			
//			switch() {
//			
//			}
			
			
			
			else if((!"I".equals(i_c)) == true || (!"C".equals(i_c))) {
				flag = 1;
				System.out.println("Enter (i/I/c/C)!");
				
			}
			
			
			
			System.out.println("iuc"+ i_c);

		}
		
		while(flag == 1);
		
		try {
			q = String.format("select pid, pname, address from d_patient natural join patient where (disease='%s') and (status='%s');", disease, i_c);
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
				data.add(rs.getString(2));
				data.add(rs.getString(3));
			}
			
			q = String.format("select count(pid) from d_patient where disease='%s';", disease);
			rs1 = st.executeQuery(q);
			while(rs1.next()) {
				data1.add(rs1.getString(1));
			}
			
			//System.out.println(data+"\n"+data1);
			//System.out.println(data1.get(0).getClass());
			
			if(data.isEmpty() && ("0").equals(data1.get(0))) {
				System.out.println("\nNo Reports found!");
			}
			
			else {
				int sno = 0;
				System.out.println("Report: ");
				for(int i = 0; i < data.size(); i+=3) {
					sno++;
					System.out.println("S.no.: "+ sno);
					System.out.println("Pid: "+ data.get(i));
					System.out.println("Name: "+ data.get(i+1));
					System.out.println("Address: "+data.get(i+2));
				}
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		data.clear();
		data1.clear();
		login_management();
		
	}

	private void management_3() {
		System.out.println("\nView monthly report of Doctors");
		
		do {
			flag = 0;
			
			System.out.println("Enter Doctor's id: ");
			did = inp.next();
			
			if(did.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Doctor's id!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = String.format("select did from doctor where did=%d", Integer.parseInt(did));
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(String.valueOf(rs.getInt(1)));
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		for(int i = 0; i < data.size(); i++) {
			if(did.equals(data.get(i))) {
				try {
					data.clear();
					q = String.format("select pid, disease, iop, status from d_patient natural join doctor where did='%s';", did);	
					rs = st.executeQuery(q);
					while(rs.next()) {
						data.add(rs.getString(1));
						data.add(rs.getString(2));
						data.add(rs.getString(3));
						data.add(rs.getString(4));
					}
				}
				
				catch(Exception e) {
					System.out.println(e);
				}
			}
		}
		
		if(data.isEmpty()) {
			System.out.println("No records found!");
		}
		else {
			int sno = 0;
			for(int i = 0; 1 < data.size(); i+=4) {
				sno++;
				System.out.println("S.no.: "+String.valueOf(sno));
				System.out.println("Pid: " + data.get(i));
				System.out.println("Disease: " + data.get(i+1));
				System.out.println("In/Out-door: " + data.get(i+2));
				System.out.println("Status: " + data.get(i+3));
			}
		}

		data.clear();
		login_management();
		
	}

	private void management_2() {
		System.out.println("\nRemove a Doctor");
		
		do {
			flag = 0;
			
			System.out.println("Enter Doctor's id: ");
			did = inp.next();
			
			if(did.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Doctor's id!");
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = "select id from login;";
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(String.valueOf(rs.getInt(1)));
			}
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		for(int i = 0; i < data.size(); i++) {
			if(did.equals(data.get(i))) {
				try {
					//int tdid = Integer.parseInt(did);
					q = String.format("delete from login where id='%d';", Integer.parseInt(did));
					st.executeUpdate(q);
					
					//q = String.format("insert into login values ('%s', '%s', 3, '%s');", did);
					
					System.out.println("Doctor id: "+did+" removed successfully!");
				}
				
				catch(Exception e) {
					System.out.println(e);
				}
			}
		}

		data.clear();
		login_management();
		
	}

	private void management_1() {
		System.out.println("\nRegister a new Doctor");
		
		try {
			q = "select did from doctor order by did desc limit 1;";
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
			}
			
			System.out.println("Previous did: "+data.get(0));
			
			did = String.valueOf(Integer.parseInt(data.get(0))+1);
//			System.out.println(did);
	
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		do {
			flag = 0;
			
			System.out.println("Enter Doctor's name: ");
			dname = inp.next();
			
			if(dname.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Doctor's name!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Doctor's specialization: ");
			speciality = inp.next();
			
			if(speciality.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Doctor's specialization!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Enter Doctor's gender (M/F/O): ");
			dgender = inp.next();
			
			if(dgender.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter Doctor's gender!");
			}
			
		}
		
		while(flag == 1);
		
		try {
//			q = "insert into doctor values('"+did+"', '"+dname+"', '"+speciality+"', '"+dgender+"');";
			q = String.format("insert into doctor values('%s', '%s', '%s', '%s');", did, dname, speciality, dgender);
			//System.out.println(q);
			st.executeUpdate(q);
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		createup();
		data.clear();
		login_management();
		
	}

	private void createup() {
		data.clear();
		int flag1;
		
		do {
			data.clear();
			flag1 = 0;
		
		System.out.println("\nCreating Account");
		do {
			flag = 0;
			
			System.out.println("Create username: ");
			username = inp.next();
			
			if(username.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter username!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Create password: ");
			password = inp.next();
			
			if(password.isEmpty() == true) {
				flag = 1;
				System.out.println("Enter password!");
			}
			
		}
		
		while(flag == 1);
		
		do {
			flag = 0;
			
			System.out.println("Re-enter password: ");
			repassword = inp.next();
			
			if(repassword.isEmpty() == true) {
				flag = 1;
				System.out.println("Re-enter password!");
			}
			
			else if(!(password.equals(repassword))) {
				flag = 1;
				System.out.println("Password do not match!");
				createup();
				break;
			}
			
		}
		
		while(flag == 1);
		
		try {
			q = String.format("select username from login where username = '%s';", username);
			rs = st.executeQuery(q);
			while(rs.next()) {
				data.add(rs.getString(1));
				System.out.println(data+"d");
			}
	
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		if(data.isEmpty()) {
			try {
				q = String.format("insert into login values ('%s', '%s', 3, '%s');", username, password, did);
				st.executeUpdate(q);
				System.out.println("Account created!");
				System.out.println("Account details:");
				System.out.println("Username: "+username);
				System.out.println("Password: "+password);
				data.clear();
				login_management();
			}
				
			catch(Exception e) {
				System.out.println(e);
			}
		}
		
		else {
			flag1 = 1;
			System.out.println("Username not available!");
		}}
		while(flag1 == 1);
		
		
	
		
	}

	private void uninstall() {
		System.out.println("Uninstalling software...");
		
		portno = "3306";
		url = "jdbc:mysql://localhost:"+portno;
		username = "root";
		password = "MySQL@Java";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			
			if(con.isClosed()==false) {
			st = con.createStatement();
//			String q = "select 1;";
//			st.executeUpdate(q);
//			
//			isConnected = true;
			
			q = "drop database hospital;";
			st.executeUpdate(q);
			st.close();
			}
			
			con.close();
			System.out.println("\nUninstallation done!");
			
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
		
		
	}

	private void install() {
		System.out.println("Installing software...");
		
		
		portno = "3306";
		url = "jdbc:mysql://localhost:"+portno;
		username = "root";
		password = "MySQL@Java";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			if(con.isClosed()==false) {
			st = con.createStatement();
//			String q = "select 1;";
//			st.executeUpdate(q);
//			
//			isConnected = true;
			
			q = "create database if not exists hospital;";
      
			//ResultSet rs = st.executeQuery(q);
			st.executeUpdate(q);
			
			q = "use hospital;";
			st.executeUpdate(q);
			
			q = "create table if not exists patient"
					+ "(pid int primary key not null, pname varchar(30) not null, gender char(1) not null, "
					+ "address varchar(50) not null, blood_group varchar(5) not null,"
					+ "reg_pat_date datetime not null, check(gender in('M','F','O')),"
					+ "check (blood_group in('A+ve','A-ve','B+ve','B-ve','AB+ve','AB-ve','O+ve','O-ve')));";
			st.executeUpdate(q);
			
			q = "create table if not exists doctor"
					+ "(did int primary key not null, dname varchar(30) not null, speciality varchar(50) not null, gender char(1) not null,"
					+ "check(gender in ('M','F','O')));";
		    st.executeUpdate(q);
		    
		    q = "create table if not exists d_patient"
		    		+ "(pid int references patient(pid), did int references doctor(did), disease varchar(30) not null, iop char(1) not null,"
		    		+ "status char(1) not null, check (iop in('I','O')));";
		    st.executeUpdate(q);
		    
		    q = "create table if not exists login(username char(20) primary key, "
		    		+ "password char(20), role int(1), id int, check( role in (1,2,3)));";
		    st.executeUpdate(q);
		    
		    q = "insert into login (username, password, role) values('u1','p1',1),('u2','p2',2);";
		    st.executeUpdate(q);
		    
		    q = "insert into login values ('u3','p3',3, 1);";
		    st.executeUpdate(q);
		    
		    q = "insert into doctor values(1, 'Test doctor', 'Test Speciality', 'O');";
		    st.executeUpdate(q);
		    st.close();
			}
		    
			//rs.close();
			con.close();
			System.out.println("\nInstallation done!");
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}	
	}
}
