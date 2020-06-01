package group_8.FitnessAB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteConfig;

public class FitnessAB_Customer_Booking {

											//ALL OF THE CONNECTIONS BETWEEN JAVA AND SQLITE COMES FROM A PREVIOUS TASK IN TIGX058 AND ALSO SQLITETUTORIAL.NET
	public static Connection conn = null;
	   
	   public static final String DB_URL = "jdbc:sqlite:C:\\sqlite\\fitnessAB.db";
	  
	   public static final String DRIVER = "org.sqlite.JDBC";
	   
	   public static void main(String[] args) throws IOException {
			  

		      try {
		         Class.forName(DRIVER);
		         SQLiteConfig config = new SQLiteConfig();  
		         config.enforceForeignKeys(true); 
		         conn = DriverManager.getConnection(DB_URL,config.toProperties());  
		      } catch (Exception e) {
		         System.out.println( e.toString() );
		         System.exit(0);
		      }
		      
		      
		       
		      meny();


		      }
		   
		   	  public static void insertMembership(int custNumber, int facilityID, String memberType, int startDate, int endDate, int memberID) {
					   	  String sqlMembership = "INSERT INTO Membership VALUES(?, ?, ?, ?, ?, ?)";
				  
						   	try  {
					
							    PreparedStatement pstmt = conn.prepareStatement(sqlMembership);
							    System.out.println("insertMembership");
							    pstmt.setInt(1, custNumber);
							    pstmt.setInt(2, facilityID);				
							    pstmt.setString(3, memberType);
							    pstmt.setInt(4, startDate);
							    pstmt.setInt(5, endDate);
							    pstmt.setInt(6, memberID);
							    
							    pstmt.executeUpdate();
							            
					           } 
						    catch (SQLException e) {
						        System.out.println(e.getMessage());
						        JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
							       }
					      }
					   	  
			  public static void insertPayment(int paymentID, String paymentType, int amount, int payDate) { 
						  String sqlPayment = "INSERT INTO Payment VALUES(?, ?, ?, ?)";
			
						     try  {
			
							    PreparedStatement pstmt = conn.prepareStatement(sqlPayment);
							    System.out.println("insertPayment");
							    pstmt.setInt(1, paymentID);
							    pstmt.setString(2, paymentType);
							    pstmt.setInt(3, amount);
							    pstmt.setInt(4, payDate);
							            
							    pstmt.executeUpdate();
							            
					           } 
						    catch (SQLException e) {
						        System.out.println(e.getMessage());
						        JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
							       }    	  
					      }
			       
			  public static void insertCustomer(int custNumber, String fName, String lName, int personNumber, int phoneNumber, String billAddress, int paymentID){ 
				         String sqlCustomer = "INSERT INTO Customer VALUES(?, ?, ?, ?, ?, ?, ?)";

				         	try  {

				         		PreparedStatement pstmt = conn.prepareStatement(sqlCustomer);
				         		System.out.println("insertCustomer");
				         		pstmt.setInt(1, custNumber);
				         		pstmt.setString(2, fName);
				         		pstmt.setString(3, lName);
				         		pstmt.setInt(4, personNumber);
				         		pstmt.setInt(5, phoneNumber);
				         		pstmt.setString(6, billAddress);
				         		pstmt.setInt(7, paymentID);
				            
				         		pstmt.executeUpdate();
				            
				            } 
				         	catch (SQLException e) {
				         		System.out.println(e.getMessage());
				         			JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
				         	}
				       }
			      
			  public static void insertBooking(int custNumber, int sectionID, int classNumber) {
			    	  
			    	  String sqlBooking = "INSERT INTO Booking VALUES(?, ?, ?);";
			    	  
					       try  {
					    	   
					            PreparedStatement pstmt = conn.prepareStatement(sqlBooking);
					            System.out.println("insertBooking");
					            pstmt.setInt(1, custNumber);
					            pstmt.setInt(2, sectionID);
					            pstmt.setInt(3, classNumber);
					            
					            pstmt.executeUpdate();
					            
					            } 
					       catch (SQLException e) {
					            System.out.println(e.getMessage());
					            JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
					       }
				        
				   }
			      
			  public static void delete(int custNumber, int sectionID, int classNumber) {
				  
					  String sqlEquipmentStatus = "DELETE FROM Booking WHERE custNumber = ? and sectionID = ? and classNumber = ?;";

		
		
							 try {
							         
									PreparedStatement pstmt = conn.prepareStatement(sqlEquipmentStatus);
								  	System.out.println("updateEquipmentStatus");
								  	pstmt.setInt(1, custNumber);
								  	pstmt.setInt(2, sectionID);
									pstmt.setInt(3, classNumber);
									
									pstmt.executeUpdate();
							 }
							 
							 catch (SQLException e) {
							     System.out.println(e.getMessage());
							 }
			  }
		//--------------------------------------------------------------------------------------------------------------------      
			      							//METHODS FOR BOOKING & CUSTOMER SYSTEM//

			      private static void meny() {
				      while(true) {
				      
				       String meny= JOptionPane.showInputDialog (
				       "Menu for Fitness AB:\n" +
				       "Enroll - Enroll for membership \n" +
				       "Login - Login to book classes \n" +
				       "Class - See classes \n" +
				       "Facility \n" +
				       "Contact \n" +
				       "\nQ- Avsluta\n" + 
				       " " +
				       "\nAnge operation"); 
				       
				        if(meny == null) {
				            System.exit(0);
				            }
				      
				      switch(meny.toUpperCase()) {

					      case "ENROLL":
					      enroll();
					      break;
					      case "LOGIN":  
					      login();
					      break;
					      case "CLASS": 
					      classes();
					      break;
					      case "FACILITY": 
						  facility();
						  break;
					      case "CONTACT": 
						  contact();
						  break;
					      case "Q": 
					      System.out.println("q");
					      System.exit(0);
					      break;
					      default:
					      JOptionPane.showMessageDialog(null, "You can't enter blank!");
					      }
				      }
				  }	 
			     
				      private static void enroll() { 
				    	  Date date = new Date();
							System.out.println(date.toString());
								int year = (1900 + date.getYear());
								int month =(1 + date.getMonth());
								int day = (date.getDate());
								int endYear = (1901 + date.getYear());
								
						//questions for enrollment
				    	 String cNr =JOptionPane.showInputDialog("Choose a customer number");			
						  	 if (cNr== null){
						  		meny();
						  		}
						  	 int custNumber = Integer.parseInt(cNr);
				    	  
				    	 String fID = JOptionPane.showInputDialog("Which facility would you like to have as home facility? \n"
				    	 										+ "1 - Vasagatan 29 \n" 
				    	 										+ "2 - Hamngatan 2 \n" 
				    	 										+ "3 - Kompassen 3"); 		
					 		 if (fID == null){
					 		      meny();
					 		      }
					 		 int facilityID = Integer.parseInt(fID);
				    	 
					 	 String memberType = JOptionPane.showInputDialog (null,"What kind of membership are you interested in? \n"
				 		  														+ "gym only, class only, plus");
					 		 int amount = ' ';
				 		  
								switch(memberType.toUpperCase()) {
					
						   		      case "GYM ONLY":
						   		      	amount = 350;
						   		      break;
						   		      case "CLASS ONLY":
						   		      	amount = 350;
						   		      break; 
								      case "PLUS":
						   		      	amount = 500;
						   		      break;
						   		      case "Q": 
						   		      System.out.println("q");
						   		      System.exit(0);
						   		      break;
						   		      default:
						   		      JOptionPane.showMessageDialog(null, "You can't enter blank!");
				   		      }
				
						String sDate = year + "" + month + "" + day;
							int startDate = Integer.parseInt(sDate);
				    	  
						Random random = new Random();
							//100 000 -> 999 999
							int memberID = 100000 + random.nextInt(1000000 - 100000);
								System.out.println("MemberID" + memberID);
					     		  	 
						 String eDate = endYear + "" + month + "" + day;
							 int endDate = Integer.parseInt(eDate);
							
				 	     String pID =JOptionPane.showInputDialog("Card number");	
				 		     if (pID== null){
				 		       meny();
				 		       }
				 		     int paymentID = Integer.parseInt(pID); 
				 	      
				 		  String paymentType = JOptionPane.showInputDialog (null,"How would you like to pay? \n"
				 		  														+ "Invoice or Autogiro");		
								switch(paymentType.toUpperCase()) {
				
						   		      case "INVOICE":
						   		    	paymentType = "Invoice";
						   		      break;
						   		      case "AUTOGIRO":
						   		    	paymentType = "Autogiro";
						   		      break; 
						   		      case "Q":
						   		    	  
						   		      System.out.println("q");
						   		      System.exit(0);
						   		      break;
						   		      default:
						   		      JOptionPane.showMessageDialog(null, "You can't enter blank!");
						   		      }
				 		 				
				 		  int payDate = 25;													//CHANGE EVERY MONTH
				 		  		 		  	   
					      String fName = JOptionPane.showInputDialog (null,"First name");		
					      
					      String lName = JOptionPane.showInputDialog (null,"Last name");		
					      
					      String pNr =JOptionPane.showInputDialog("Person number, YYMMDD");	
					      	if (pNr== null){
					      		meny();
					      		}
					      	int personNumber = Integer.parseInt(pNr);
					      	
						  String phNr =JOptionPane.showInputDialog("Phone number");	
						    if (phNr== null){
						      meny();
						      }
						    int phoneNumber = Integer.parseInt(phNr);
						    
						  
						  String billAddress = JOptionPane.showInputDialog (null,"Bill address");		
					 
						  
					insertPayment(paymentID, paymentType, amount, payDate);
					insertCustomer(custNumber, fName, lName, personNumber, phoneNumber, billAddress, paymentID); 
					insertMembership(custNumber, facilityID, memberType, startDate, endDate, memberID);
					
						  JOptionPane.showMessageDialog(null, "You have successfully joined Fitness AB \n"
						  									+ "You will now be able to log in to your account");
				      }
				      
				      private static void login() {
				      String s = JOptionPane.showInputDialog(null, "Customer number");
					      while (s.length() != 4){
				              s = JOptionPane.showInputDialog(null, "The customer number should have four numbers");
				              if (s == null){
				            	  meny();
				              }	            	  
				           }
					      
				      int custNumber = Integer.parseInt(s);
				      
				      loginMembership(custNumber);
				      }
				      
				      	private static void loginMembership(int custNumber) {
					    
				    	  String memberType = "";
				    	  String sqlLoginMembership = "SELECT memberType FROM Membership WHERE custNumber = " + custNumber;
				    	  
					    	  try  {
						            
					                Statement stmt = conn.createStatement();
					                 ResultSet rs = stmt.executeQuery(sqlLoginMembership);
					                  while (rs.next()) {
					                	  memberType += (rs.getString("memberType"));
					                      
					            }
					            }
				           
							     catch (SQLException e) {
							         System.out.println(e.getMessage());
							         JOptionPane.showMessageDialog(null, "There is no value:\n\n" + e.getMessage());
							     	}
				    	  
					    	  System.out.println(memberType);
					    	  
					    	  String plus = "plus";
					    	  String classO = "class only";
					    	  String gymO = "gym only";
					    	  
					    		  if (memberType.equals(plus)) {
						    		  searchCustomer(custNumber);
						    	  }
						    	  if (memberType.equals(classO)) {
						    		  searchCustomer(custNumber);
						    	  }
						    	  if (memberType.equals(gymO)) {
						    		  gymOnly(custNumber);
						    	  }
						       
					    	  
					    	  
					    	  
				      }
				      
				      			public static void gymOnly(int custNumber) {
				      			String x = "";
						        String sqlGymOnly = "SELECT * FROM Customer WHERE custNumber = " + custNumber;
						            	System.out.println("login"); 
						            
						            try  {
							            
						                Statement stmt = conn.createStatement();
						                 ResultSet rs = stmt.executeQuery(sqlGymOnly);
						                  while (rs.next()) {
						                      x += (rs.getInt("custNumber"));
						                      
						            }
						            }
					           
								     catch (SQLException e) {
								         System.out.println(e.getMessage());
								         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								     	}
								     
						            
						            
						         String name = "";   
							     String firstName = "SELECT fName FROM Customer WHERE custNumber = " + custNumber;
							     	System.out.println("welcome name"); 
					            
						            try  {
							            
						                Statement stmt = conn.createStatement();
						                 ResultSet rs = stmt.executeQuery(firstName);
						                  while (rs.next()) {
						                      name += (rs.getString("fName"));
						                      
						            }
						            }
						       
								     catch (SQLException e) {
								         System.out.println(e.getMessage());
								         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								     	}
								     
						            
								    if(x == "") {
								    	JOptionPane.showMessageDialog(null, "You are not a member with Fitness AB, please enroll");
								    }
								    else {
								    	while(true) {
								    		String gymOnlyMeny= JOptionPane.showInputDialog (
										   	 	       "Welcome to Fitness AB " + name +  "!\n" +
										   	 	       "Write down you choice \n" +
										   	 	       "\n" +
										   	 	       "Membership - If you want to see you personal information \n" +
										   	 	       "Out - Log out" +
										   	 	       "\nQ- Break\n"); 
										   	 	       
										   	if(gymOnlyMeny == null) {
										   	    System.exit(0);
										   	}
										   	 	         
										switch(gymOnlyMeny.toUpperCase()) {
										
											case "MEMBERSHIP":
											membership(custNumber);
											break;														
											case "OUT":
											meny();
											break;
										
											case "Q": 
											System.out.println("q");
											System.exit(0);
											break;
											default:
											JOptionPane.showMessageDialog(null, "You can't enter blank!");
										   }
								 	 }
								}
				      		}
				      		
				      		public static void searchCustomer(int custNumber){ 
						        String x = "";     
						        String sqlLogin = "SELECT * FROM Customer WHERE custNumber = " + custNumber;
						            	System.out.println("login"); 
						            
						            try  {
							            
						                Statement stmt = conn.createStatement();
						                 ResultSet rs = stmt.executeQuery(sqlLogin);
						                  while (rs.next()) {
						                      x += (rs.getInt("custNumber"));
						                      
						            }
						            }
					           
								     catch (SQLException e) {
								         System.out.println(e.getMessage());
								         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								     	}
								     
						            
						            
						         String name = "";
							     String firstName = "SELECT fName FROM Customer WHERE custNumber = " + custNumber;
							     	System.out.println("welcome name"); 
					            
						            try  {
							            
						                Statement stmt = conn.createStatement();
						                 ResultSet rs = stmt.executeQuery(firstName);
						                  while (rs.next()) {
						                      name += (rs.getString("fName"));
						                      
						            }
						            }
						       
								     catch (SQLException e) {
								         System.out.println(e.getMessage());
								         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								     	}
								     
						            
								    if(x == "") {
								    	JOptionPane.showMessageDialog(null, "You are not a member with Fitness AB, please enroll");
								    }
								    else {
								    	while(true) {
								    		String loginMeny= JOptionPane.showInputDialog (
										   	 	       "Welcome to Fitness AB " + name +  "!\n" +
										   	 	       "You can now choose how you want to train \n" +
										   	 	       "Write down you choice \n" +
										   	 	       "\n" +
										   	 	       "Booking - To be able to book class \n" +
										   	 	       "Booked - Classes you have joined \n" +
										   	 	       "Cancel - To cancel a class \n" +
										   	 	       "Membership - If you want to see you personal information \n" +
										   	 	       "Out - If you want to log out" +
										   	 	       "\nQ- Break\n"); 
										   	 	       
										   	 if(loginMeny == null) {
										   		 System.exit(0);
										   	 }
										   	 	        
										   	switch(loginMeny.toUpperCase()) {
										
											    case "BOOKING":
											  	booking(custNumber);
											    break;
											 	case "BOOKED":
											   	bookedClass(custNumber);
												break;
											 	case "CANCEL":
												cancelClass(custNumber);
												break;
											   	case "MEMBERSHIP":
											   	membership(custNumber);
											   	break;														
											   	case "OUT":
											   	meny();
												break;
										
											   	case "Q": 
											   	System.out.println("q");
											   	System.exit(0);
											   	break;
											   	default:
											   	JOptionPane.showMessageDialog(null, "You can't enter blank!");
										   		}
										    }
									  }
						    	}
				      															
					      		private static void booking(int custNumber) {
					      			while(true) {
					      			String bookingMeny= JOptionPane.showInputDialog (
						   	 	       "How do you want to train? \n" +
						   	 	       "Choose which kind of training you would like \n" +
						   	 	       "Write down you choice \n" +
						   	 	       "\n" +
						   	 	       "Yoga \n" +
						   	 	       "Spinnning \n" +
						   	 	       "Crossfit \n" +
						   	 	       "All \n" +
						   	 	       "Menu - Get back to member Menu \n" +
						   	 	       "Q- Break\n"); 
						   	 	       
						   	 	        if(bookingMeny == null) {
						   	 	            System.exit(0);
						   	 	        }
						   	 	        
								       
								       switch(bookingMeny.toUpperCase()) {
						
								       case "YOGA":							
								       yoga(custNumber);
								       break;
								       case "SPINNING":						
								       spinnning(custNumber);
								       break;
								       case "CROSSFIT":						
								       crossfit(custNumber);
								       break;
								       case "ALL":							
									   all(custNumber);
								       break;
								       case "MENU":							
								       searchCustomer(custNumber);
									   break;
								      
								       case "Q": 
								       System.out.println("q");
								       System.exit(0);
								       break;
								       default:
								       JOptionPane.showMessageDialog(null, "You can't enter blank!");
								   	   }
								   	}
					      		}
				    									
					       			private static void yoga(int custNumber) {
					       				String section = "";
					       				String yoga = "";
					       				String yogaChoice = "'Yoga';";
					       				String sqlYoga = "select ClassSection.sectionID, Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor, Studio.studioNumber, Facility.facilityID "
					       								+ "from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber "
					       								+ "inner join ClassSectionStudioBooking on ClassSectionStudioBooking.classNumber = ClassSection.classNumber "
					       								+ "inner join Studio on Studio.studioNumber = ClassSectionStudioBooking.studioNumber "
					       								+ "inner join Facility on Facility.facilityID = Studio.facilityID "
					       								+ "where Class.className = " + yogaChoice;	
					                    					System.out.println("Yoga Class");
					                    
								                  try  {
								                       Statement stmt = conn.createStatement();
								                        ResultSet rs = stmt.executeQuery(sqlYoga);
								                         while (rs.next()) {
								                        	 yoga += (rs.getInt("sectionID") +  "          |" +
								                        			 	rs.getString("className") +  "          |" +
								                        			 	rs.getInt("classDate") + "     |" +
								                        		  		rs.getInt("startTime") + "       |" +
								                        		  		rs.getInt("classDuration") + " min  |" +
								                        		  		rs.getString("instructor") + "  |" +
								                        		  		rs.getInt("studioNumber") + "        |" +
								                        		  		rs.getInt("facilityID") + "      \n");
								                      }
								                  }
								                      
								                  catch (SQLException e) {
								                	  System.out.println(e.getMessage());
								                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								                  } 
					                 
								       String sqlSection = "select booking.sectionID "
								       					+ "from Booking inner join ClassSection on booking.classNumber = classSection.classNumber "
								       					+ "inner join class on classSection.classNumber = class.classNumber "
								       					+ "where class.className = " + yogaChoice;
								       			System.out.println("search sectionID");
					  	        
								  		        try  {
								  		             Statement stmt = conn.createStatement();
								  		              ResultSet rs = stmt.executeQuery(sqlSection);
								  		               while (rs.next()) {
								  		              	section += (rs.getInt("sectionID"));
								  		            }
								  		        }
								  		            
								  		        catch (SQLException e) {
								  		      	  System.out.println(e.getMessage());
								  		      	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								  		        } 
								                  
					                  
					                  if (yoga == "") {
					                	 JOptionPane.showMessageDialog(null, "There is no classes by that number");
					                 	}	
					                 
					                 System.out.println("customer# " + custNumber);
					                 
					                 String sID = JOptionPane.showInputDialog("Select section depending on which yoga classes you would like to attend \n \n "
				 															+ "Section | Class name | Date        | Time | Duration | Instructor | Studio | facility \n " + yoga); 
						                 
					                 		System.out.println("sectionID " + sID);
						                 
					                 		while (sID.length() != 1){
						  			              sID = JOptionPane.showInputDialog(null, "Please enter the sectionID");
						  			              if (sID == null){
						  			            	JOptionPane.showMessageDialog(null, "You can't leave it blank!");
						  			            	  booking(custNumber);
						  			              }	            	  
						  			        }
						                 	if (section.equalsIgnoreCase(sID)) {
						                 		JOptionPane.showMessageDialog(null, "You have already booked this class!");
						                 		booking(custNumber);
						                 	}
					                 	int sectionID = Integer.parseInt(sID);
					                 		System.out.println("sectionID " + sectionID);
					                 	
					                 String cNr = "";
					                 String sqlcNr = "select distinct class.classNumber "
					                 				+ "from class inner join classSection on class.classNumber = classSection.classNumber "
					                 				+ "where classSection.sectionID = '" + sectionID + "';";
					                 	
						                  try  {
						                       Statement stmt = conn.createStatement();
						                        ResultSet rs = stmt.executeQuery(sqlcNr);
						                         while (rs.next()) {
						                        	 cNr += (rs.getInt("classNumber"));
						                         }   
						                  }
						                      
						                  catch (SQLException e) {
						                	  System.out.println(e.getMessage());
						                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						                  } 
						                  
						                  int classNumber = Integer.parseInt(cNr);	
						                  System.out.println("class number " + classNumber);
					       				
					                 	
					       	insertBooking(custNumber, sectionID, classNumber);
					       			
					       			JOptionPane.showMessageDialog(null, "You have successfully been signed up for the Yoga class");
										
					       			searchCustomer(custNumber);
					       			}
					       							
					       			private static void spinnning(int custNumber) {
					       				
					       				String section = "";
					       				String spinning = "";
					       				String spinningChoice = "'Spinning';";
					       				String sqlSpinning = "select ClassSection.sectionID, Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor, Studio.studioNumber, Facility.facilityID "
						       								+ "from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber "
						       								+ "inner join ClassSectionStudioBooking on ClassSectionStudioBooking.classNumber = ClassSection.classNumber "
						       								+ "inner join Studio on Studio.studioNumber = ClassSectionStudioBooking.studioNumber "
						       								+ "inner join Facility on Facility.facilityID = Studio.facilityID "
						       								+ "where Class.className = " + spinningChoice;	
						       	        					System.out.println("spinning Class");
					       	        
						       	                  try  {
						       	                       Statement stmt = conn.createStatement();
						       	                        ResultSet rs = stmt.executeQuery(sqlSpinning);
						       	                         while (rs.next()) {
						       	                        	 spinning += (rs.getInt("sectionID") +  "          |" +
						       	                        			 	rs.getString("className") + "        |" +
						       	                        			 	rs.getInt("classDate") + "     |" +
						       	                        		  		rs.getInt("startTime") + "       |" +
						       	                        		  		rs.getInt("classDuration") + " min  |" +
						       	                        		  		rs.getString("instructor") + "  |" +
						       	                        		  		rs.getInt("studioNumber") + "        |" +
						       	                        		  		rs.getInt("facilityID") + "      \n");
						       	                  }
						       	                  }     
						       	                  catch (SQLException e) {
						       	                	  System.out.println(e.getMessage());
						       	                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						       	                  } 
					       	     
					       	           String sqlSection = "select booking.sectionID "
								       	           		+ "from Booking inner join ClassSection on booking.classNumber = classSection.classNumber "
								       	           		+ "inner join class on classSection.classNumber = class.classNumber "
								       	           		+ "where class.className = " + spinningChoice;
					       	       					System.out.println("search sectionID");
					       	  
						       	  		        try  {
						       	  		             Statement stmt = conn.createStatement();
						       	  		              ResultSet rs = stmt.executeQuery(sqlSection);
						       	  		               while (rs.next()) {
						       	  		              	section += (rs.getInt("sectionID"));
						       	  		        }
						       	  		        }
						       	  		            
						       	  		        catch (SQLException e) {
						       	  		      	  System.out.println(e.getMessage());
						       	  		      	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						       	  		        } 
					       	                  
					       	      
										       	      if (spinning == "") {
										       	    	 JOptionPane.showMessageDialog(null, "There is no classes by that number");
										       	     	}			       	     
										       	      		System.out.println("customer# " + custNumber);
					       	     
										String sID = JOptionPane.showInputDialog("Select section depending on which spinning classes you would like to attend \n \n "
					       													+ "Section | Class name | Date        | Time | Duration | Instructor | Studio | facility \n " + spinning); 
					       	     							System.out.println("sectionID " + sID);
					       	         
					       	     						while (sID.length() != 1){
					  			  			              sID = JOptionPane.showInputDialog(null, "Please enter the sectionID");
					  			  			              if (sID == null){
					  			  			            	JOptionPane.showMessageDialog(null, "You can't leave it blank!");
					  			  			            	  booking(custNumber);
					  			  			              }	            	  
					       	     						}
									       	         	if (section.equalsIgnoreCase(sID)) {
									       	         		JOptionPane.showMessageDialog(null, "You have already booked this class!");
									       	         		booking(custNumber);
									       	         	}
									       	   int sectionID = Integer.parseInt(sID);
									       	   		System.out.println("sectionID " + sectionID);
					       	     	
							       	    String cNr = "";
							       	    String sqlcNr = "select distinct class.classNumber "
							       	    			+ "from class inner join classSection on class.classNumber = classSection.classNumber "
							       	    			+ "where classSection.sectionID = '" + sectionID + "';";
							       	     	
								       	          try  {
								       	               Statement stmt = conn.createStatement();
								       	                ResultSet rs = stmt.executeQuery(sqlcNr);
								       	                 while (rs.next()) {
								       	                	 cNr += (rs.getInt("classNumber"));
								       	                 }   
								       	          }
								       	              
								       	          catch (SQLException e) {
								       	        	 System.out.println(e.getMessage());
								       	        	 JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								       	          } 
						       	          
								       	     int classNumber = Integer.parseInt(cNr);	
								       	     		System.out.println("class number " + classNumber);
					       				
					       	     	
					       	insertBooking(custNumber, sectionID, classNumber);
					       			
					       		JOptionPane.showMessageDialog(null, "You have successfully been signed up for the spinning class");
					       			
					       			searchCustomer(custNumber);
					       		}	       			
				   					
					       			private static void crossfit(int custNumber) {
					       			
					       				String section = "";
					       				String crossfit = "";
					       				String crossfitChoice = "'Crossfit';";
					       				String sqlCrossfit = "select ClassSection.sectionID, Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor, Studio.studioNumber, Facility.facilityID "
					       								+ "from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber "
					       								+ "inner join ClassSectionStudioBooking on ClassSectionStudioBooking.classNumber = ClassSection.classNumber "
					       								+ "inner join Studio on Studio.studioNumber = ClassSectionStudioBooking.studioNumber "
					       								+ "inner join Facility on Facility.facilityID = Studio.facilityID "
					       								+ "where Class.className = " + crossfitChoice;	
					       	        					System.out.println("crossfit Class");
					       	        
					       	                  try  {
					       	                       Statement stmt = conn.createStatement();
					       	                        ResultSet rs = stmt.executeQuery(sqlCrossfit);
					       	                         while (rs.next()) {
					       	                        	 crossfit += (rs.getInt("sectionID") +  "          |" +
					       	                        			 	rs.getString("className") +  "          |" +
					       	                        			 	rs.getInt("classDate") + "     |" +
					       	                        		  		rs.getInt("startTime") + "       |" +
					       	                        		  		rs.getInt("classDuration") + " min  |" +
					       	                        		  		rs.getString("instructor") + "  |" +
					       	                        		  		rs.getInt("studioNumber") + "        |" +
					       	                        		  		rs.getInt("facilityID") + "      \n");
					       	                      }
					       	                  }
					       	                      
					       	                  catch (SQLException e) {
					       	                	  System.out.println(e.getMessage());
					       	                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
					       	                  } 
					       	     
					       	       String sqlSection = "select booking.sectionID "
								       	       		+ "from Booking inner join ClassSection on booking.classNumber = classSection.classNumber "
								       	       		+ "inner join class on classSection.classNumber = class.classNumber "
								       	       		+ "where class.className = " + crossfitChoice;
					       	       			System.out.println("search sectionID");
					       	  
					       	  		        try  {
					       	  		             Statement stmt = conn.createStatement();
					       	  		              ResultSet rs = stmt.executeQuery(sqlSection);
					       	  		               while (rs.next()) {
					       	  		              	section += (rs.getInt("sectionID"));
					       	  		            }
					       	  		        }
					       	  		            
					       	  		        catch (SQLException e) {
					       	  		      	  System.out.println(e.getMessage());
					       	  		      	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
					       	  		        } 
					       	                  
					       	      
					       	      if (crossfit == "") {
					       	    	 JOptionPane.showMessageDialog(null, "There is no classes by that number");
					       	     	}	
					       	     
					       	     System.out.println("customer# " + custNumber);
					       	     
					       	     String sID = JOptionPane.showInputDialog("Select section depending on which crossfit classes you would like to attend \n \n "
					       													+ "Section | Class name | Date        | Time | Duration | Instructor | Studio | facility \n " + crossfit); 
					       	         
					       	     		System.out.println("sectionID " + sID);
					       	         
					       	     	while (sID.length() != 1){
				  			              sID = JOptionPane.showInputDialog(null, "Please enter the sectionID");
				  			              if (sID == null){
				  			            	JOptionPane.showMessageDialog(null, "You can't leave it blank!");
				  			            	  booking(custNumber);
				  			              }	            	  
				  			        }
					       	         	if (section.equalsIgnoreCase(sID)) {
					       	         		JOptionPane.showMessageDialog(null, "You have already booked this class!");
					       	         		booking(custNumber);
					       	         	}
					       	     	int sectionID = Integer.parseInt(sID);
					       	     		System.out.println("sectionID " + sectionID);
					       	     	
					       	     String cNr = "";
					       	     String sqlcNr = "select distinct class.classNumber "
					       	     			+ "from class inner join classSection on class.classNumber = classSection.classNumber "
					       	     			+ "where classSection.sectionID = '" + sectionID + "';";
					       	     	
					       	          try  {
					       	               Statement stmt = conn.createStatement();
					       	                ResultSet rs = stmt.executeQuery(sqlcNr);
					       	                 while (rs.next()) {
					       	                	 cNr += (rs.getInt("classNumber"));
					       	                 }   
					       	          }
					       	              
					       	          catch (SQLException e) {
					       	        	  System.out.println(e.getMessage());
					       	        	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
					       	          } 
					       	          
					       	          int classNumber = Integer.parseInt(cNr);	
					       	          System.out.println("class number " + classNumber);
					       				
					       	     	
					       	insertBooking(custNumber, sectionID, classNumber);
					       			
					       			JOptionPane.showMessageDialog(null, "You have successfully been signed up for the crossfit class");
					       			
					       			searchCustomer(custNumber);
					       		}
					       							
					       			private static void all(int custNumber) {
					       				
					       				String all = "";
										String sqlAll = "select ClassSection.sectionID, Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor, Studio.studioNumber, Facility.facilityID "
													+ "from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber "
													+ "inner join ClassSectionStudioBooking on ClassSectionStudioBooking.classNumber = ClassSection.classNumber "
													+ "inner join Studio on Studio.studioNumber = ClassSectionStudioBooking.studioNumber inner join "
													+ "Facility on Facility.facilityID = Studio.facilityID;";	
							      					System.out.println("All Classes");
							      
								                  try  {
								                       Statement stmt = conn.createStatement();
								                        ResultSet rs = stmt.executeQuery(sqlAll);
								                         while (rs.next()) {
								                        	 all += (rs.getInt("sectionID") +  "          |" +
								                        			 	rs.getString("className") + "        |" +
								                        			 	rs.getInt("classDate") + "     |" +
								                        		  		rs.getInt("startTime") + "       |" +
								                        		  		rs.getInt("classDuration") + " min  |" +
								                        		  		rs.getString("instructor") + "  |" +
								                        		  		rs.getInt("studioNumber") + "        |" +
								                        		  		rs.getInt("facilityID") + "      \n");
								                  }
								                  }     
								                  catch (SQLException e) {
								                	  System.out.println(e.getMessage());
								                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
								                  } 
								      
							   
									 String sID = JOptionPane.showInputDialog("Select section depending on which class you would like to attend \n \n "
																			+ "Section | Class name  | Date        | Time | Duration | Instructor | Studio | facility \n " + all); 
							   							System.out.println("sectionID " + sID);
							       
							   							while (sID.length() != 1){
									  			              sID = JOptionPane.showInputDialog(null, "Please enter the sectionID");
									  			              if (sID == null){
									  			            	JOptionPane.showMessageDialog(null, "You can't leave it blank!");
									  			            	  booking(custNumber);
									  			              }	            	  
									  			           }
								       	         	
								      String section = "";
									  String sqlSection = "select booking.sectionID "
									  					+ "from Booking inner join ClassSection on booking.classNumber = classSection.classNumber "
									  					+ "inner join class on classSection.classNumber = class.classNumber "
									  					+ "where classSection.sectionID = " + sID;
														System.out.println("search sectionID");
										
											  		 try  {
											              Statement stmt = conn.createStatement();
											              ResultSet rs = stmt.executeQuery(sqlSection);
									  		               while (rs.next()) {
			   				  		              	  section += (rs.getInt("sectionID"));
											  		  }
											          }
											  		            
											         catch (SQLException e) {
										  		      	  System.out.println(e.getMessage());
										  		      	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
									  		         }
											  		 
								       	         	if (section.equalsIgnoreCase(sID)) {
								       	         		JOptionPane.showMessageDialog(null, "You have already booked this class!");
								       	         		booking(custNumber);
								       	         	}
								       	   int sectionID = Integer.parseInt(sID);
								       	   		System.out.println("sectionID " + sectionID);
							   	
							  	    String cNr = "";
							  	    String sqlcNr = "select distinct class.classNumber "
									  	    		+ "from class inner join classSection on class.classNumber = classSection.classNumber "
									  	    		+ "where classSection.sectionID = '" + sectionID + "';";
							  	     	
							      	          try  {
							      	               Statement stmt = conn.createStatement();
							      	                ResultSet rs = stmt.executeQuery(sqlcNr);
							      	                 while (rs.next()) {
							      	                	 cNr += (rs.getInt("classNumber"));
							      	                 }   
							      	          }
							      	              
							      	          catch (SQLException e) {
							      	        	 System.out.println(e.getMessage());
							      	        	 JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
							      	          } 
								          
							      	     int classNumber = Integer.parseInt(cNr);	
							      	     		System.out.println("class number " + classNumber);
										
							   	
							insertBooking(custNumber, sectionID, classNumber);
									
								JOptionPane.showMessageDialog(null, "You have successfully been signed up for the class");
									
									searchCustomer(custNumber);
					       			}
					       														
					       		private static void bookedClass(int custNumber) {
					       			
					       			String classBooked = "";
					       			String sqlBookedClass = "select Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber inner join Booking on Booking.classNumber = ClassSection.classNumber where Booking.custNumber = " + custNumber;
					       							System.out.println("Class");
						               
							             try  {
							
							                  Statement stmt = conn.createStatement();
							                   ResultSet rs = stmt.executeQuery(sqlBookedClass);
							                    while (rs.next()) {
							                    	classBooked += (rs.getString("className") +  "         |" +
							                    					rs.getInt("classDate") + "       |" +
									                   		  		rs.getInt("startTime") + "       |" +
									                   		  		rs.getInt("classDuration") + " min     |" +
									                   		  		rs.getString("instructor") + "         \n");
							                 }
							                 }
							                 
							           catch (SQLException e) {
							               System.out.println(e.getMessage());
							               JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
							         
							           }
							             if(classBooked == "") {
							                 JOptionPane.showMessageDialog(null, "No classes booked");
							                 }
							                 else{
							                	 JOptionPane.showMessageDialog(null, "Class name  |Date       |Class start |Duration  |Instructor  "
							                     		+ " \n _________________________________________________________________\n " + classBooked);
							                 } 
					       		}
					       		
					       		private static void cancelClass(int custNumber) {
					       			
					       			String cancel = "";
					       			String sqlCancel = "select ClassSection.sectionID, Class.className, ClassSection.classDate, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor from Class inner join ClassSection on ClassSection.classNumber = Class.classNumber inner join Booking on Booking.classNumber = ClassSection.classNumber where Booking.custNumber = " + custNumber;
					       				System.out.println("Cancel Class");
				                    
						                  try  {
						                       Statement stmt = conn.createStatement();
						                        ResultSet rs = stmt.executeQuery(sqlCancel);
						                         while (rs.next()) {
						                     cancel += (rs.getInt("sectionID") + "       |" +
						                    		 	rs.getString("className") +  "         |" +
				                    					rs.getInt("classDate") + "       |" +
						                   		  		rs.getInt("startTime") + "       |" +
						                   		  		rs.getInt("classDuration") + " min     |" +
						                   		  		rs.getString("instructor") + "         \n");
						                  }
						                  }
						                      
						                  catch (SQLException e) {
						                	  System.out.println(e.getMessage());
						                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						                  }
						                  
						                  if(cancel == "") {
								                 JOptionPane.showMessageDialog(null, "You can't cancel any classes, for you have not booked any");
								                 }
								          else{
								                String sID = JOptionPane.showInputDialog("Which class would you like to cancel? (Enter sectionID) \n "
															                	 		+ "SectionID | Class name  | Date       | Class start | Duration  | Instructor "
															                     		+ " \n _________________________________________________________________\n " + cancel);
								                		System.out.println("sectionID: " + sID);
								                		while (sID.length() != 1){
								  			              sID = JOptionPane.showInputDialog(null, "Please enter the sectionID");
								  			              if (sID == null){
								  			            	JOptionPane.showMessageDialog(null, "You can't leave it blank!");
								  			            	  booking(custNumber);
								  			              }	            	  
								  			           }
								                		
								                	int sectionID = Integer.parseInt(sID);
								                
								      String cNr = "";
								      String sqlcNr = "select distinct Booking.classNumber from booking inner join classSection on booking.classNumber = classSection.classNumber where Booking.sectionID = '" + sectionID + "';";
									      try  {
						                       Statement stmt = conn.createStatement();
						                        ResultSet rs = stmt.executeQuery(sqlcNr);
						                         while (rs.next()) {
						                        cNr += (rs.getInt("classNumber"));
						                  }   
						                  }
						                      
						                  catch (SQLException e) {
						                	  System.out.println(e.getMessage());
						                	  JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						                  } 
						                  
						                  int classNumber = Integer.parseInt(cNr);	
						                  System.out.println("class number: " + classNumber);
						                  
						delete(custNumber, sectionID, classNumber);
								         }        
						                  
					       		 }
					       		
					       		private static void membership(int custNumber) {
					     
					       			String cNr = "";
					              	String fName = "";
					              	String lName = "";
					              	String pNr = "";
					              	String phNr = "";
					              	String bA = "";
					              	String payID = "";
					              	String payT = "";
					              	String fID = "";
					              	String mT = "";
					              	String sD = "";
					              	String eD = "";
					              	String mID = "";
							   		
						            String sqlMembership = "select customer.custNumber, customer.fname, customer.lname, customer.personNumber, customer.phoneNumber, customer.billaddress, payment.paymentID, payment.paymentType, membership.facilityID, membership.membertype, membership.startDate, membership.endDate, membership.memberID "
						            					+ "from payment inner join customer on payment.paymentID = customer.paymentID "
						            					+ "inner join membership on customer.custNumber = membership.custNumber "
						            					+ "where customer.custNumber =" + custNumber;
						            				System.out.println("view membership");
						            				
							            try {
								            
								               Statement stmt = conn.createStatement();
								                ResultSet rs = stmt.executeQuery(sqlMembership);
								                  while (rs.next()) {
								                 cNr += (rs.getInt("custNumber"));
								               fName += (rs.getString("fname"));
								               lName += (rs.getString("lname"));
								                 pNr += (rs.getInt("personNumber"));
								                phNr += (rs.getInt("phoneNumber"));
								                  bA += (rs.getString("billaddress"));
								               payID += (rs.getInt("paymentID"));
								              	payT += (rs.getString("paymentType"));
								                 fID += (rs.getInt("facilityID"));
								                  mT += (rs.getString("memberType"));
								                  sD += (rs.getInt("startDate"));
								                  eD += (rs.getInt("endDate"));
								                 mID += (rs.getInt("memberID"));
						
						
									              }
									        }
									        
									            catch (SQLException e) {
									            System.out.println(e.getMessage());
									            JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
									       }
									       JOptionPane.showMessageDialog(null, "Personal information" + "\n" +
									    		   								"\nCustom number: " + cNr + 
									    		   								"\nFirst name:           " + fName +
									    		   								"\nLast name:           " + lName +
									    		   								"\nPerson number:   " + pNr +
									    		   								"\nPhone number:     " + phNr +
									    		   								"\nBilling address:    " + bA +
									    		   								"\nPaymentID:            " + payID +
									    		   								"\nPayment type:         " + payT +
									    		   								"\nFacilityID:            " + fID +
									    		   								"\nMember type:       " + mT +
									    		   								"\nStart date:              " + sD +
									    		   								"\nCancel date:          " + eD +
									    		   								"\nMemberID:      "+ mID);
					       			}
					       															
				       public static void classes() {
			
				    	   String classes = "";
			               String sqlClass = "select Class.className, ClassSection.startTime, ClassSection.classDuration, ClassSection.instructor, Studio.studioNumber, Facility.facilityID "
			               					+ "from Class inner join ClassSection "
			               					+ "on ClassSection.classNumber = Class.classNumber inner join ClassSectionStudioBooking "
			               					+ "on ClassSectionStudioBooking.classNumber = ClassSection.classNumber inner join Studio "
			               					+ "on Studio.studioNumber = ClassSectionStudioBooking.studioNumber inner join Facility "
			               					+ "on Facility.facilityID = Studio.facilityID;";	
			               System.out.println("Class");
			               
			             try  {
			
			                  Statement stmt = conn.createStatement();
			                   ResultSet rs = stmt.executeQuery(sqlClass);
			                    while (rs.next()) {
			                     classes += (rs.getString("className") +  "       |" +
			                   		  		rs.getInt("startTime") + "            |" +
			                   		  		rs.getInt("classDuration") + " min        |" +
			                   		  		rs.getString("instructor") + "         |" +
			                   		  		rs.getInt("studioNumber") + "         |" +
			                   		  		rs.getInt("facilityID") + "         \n");
			                 }
			                 }
			                 
			           catch (SQLException e) {
			               System.out.println(e.getMessage());
			               JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
			         
			           }
			             if(classes == "") {
			                 JOptionPane.showMessageDialog(null, "Finns inga instämplade pass för denna personen");
			                 }
			                 else{
			                     JOptionPane.showMessageDialog(null, "className |startTime |classDuration  |instructor | facility"
			                     		+ " \n _________________________________________________________________\n " + classes);
			                 }  
			            
				       }
				       													
				       public static void facility() {
				    	   String x = "";
				    	   String sqlFacility = "SELECT * FROM Facility;";
				    	 	           
				            System.out.println("sqlFacility");
				          try  {
			
				               Statement stmt = conn.createStatement();
				                ResultSet rs = stmt.executeQuery(sqlFacility);
				                  while (rs.next()) {
				                   x += (rs.getInt("facilityID") + "                  " +  
				                         rs.getString("address") + "\n");
			
				              }
				              }
				              
				        catch (SQLException e) {
				            System.out.println(e.getMessage());
				            JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
				       }
				       
				       JOptionPane.showMessageDialog(null, "Facility       Address \n" + x);
				       
			
				    	   
				    	   
				       }
				       												
				       public static void contact() {
			    	   JOptionPane.showMessageDialog(null, "Contact information\n"
			    	   									+ "Phone desk: +4601 234 56 78 \n"
			    	   									+ "Email: info@fitnessab.se"
			    	   									+ "\n \n");
			    	   									
			       } 
}
