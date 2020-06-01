package group_8.FitnessAB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteConfig;

public class FitnessAB_Equipment {
														//ALL OF THE CONNECTIONS BETWEEN JAVA AND SQLITE COMES FROM A PREVIOUS TASK IN TIGX058 AND ALSO SQLITETUTORIAL.NET
	public static Connection conn = null;
	   
	   public static final String DB_URL = "jdbc:sqlite:C:\\sqlite\\fitnessAB.db";
	  
	   public static final String DRIVER = "org.sqlite.JDBC";  

	   public static void main(String[] args) throws IOException {
	  

	      try {
	         Class.forName(DRIVER);
	         SQLiteConfig config = new SQLiteConfig();  
	         config.enforceForeignKeys(true); // Denna kodrad ser till att sätta databasen i ett läge där den ger felmeddelande ifall man bryter mot någon främmande-nyckel-regel
	         conn = DriverManager.getConnection(DB_URL,config.toProperties());  
	      } catch (Exception e) {
	         // Om java-progammet inte lyckas koppla upp sig mot databasen (t ex om fel sökväg eller om driver inte hittas) så kommer ett felmeddelande skrivas ut
	         System.out.println( e.toString() );
	         System.exit(0);
	      }
	      
	      	       
	      meny();

	      }
	      
	      public static void updateEquipmentStatus(int equipmentID, String equipmentName, String equipmentType, String equipmentStatus, int facilityID, int serviceID, int supplierID) {
	    	  
	    	  String sqlEquipmentStatus = "UPDATE Equipment "
		    	  		+ "SET equipmentID = ?" 
		    	  		+ ", equipmentName = ?"
		    	  		+ ", equipmentType = ?"
		    	  		+ ", equipmentStatus = ?"
		    	  		+ ", facilityID = ?"
		    	  		+ ", serviceID = ?"
		    	  		+ ", supplierID = ?"
		    	  		+ " WHERE equipmentID = " + equipmentID;


					 try {
					         
							PreparedStatement pstmt = conn.prepareStatement(sqlEquipmentStatus);
						  	System.out.println("insertEquipment");
							pstmt.setInt(1, equipmentID);
							pstmt.setString(2, equipmentName); 
							pstmt.setString(3, equipmentType);
							pstmt.setString(4, equipmentStatus);
							pstmt.setInt(5, facilityID);
							pstmt.setInt(6, serviceID); 
							pstmt.setInt(7, supplierID);
							
							pstmt.executeUpdate();
					 }
					 
					 catch (SQLException e) {
					     System.out.println(e.getMessage());
					 }
			      }						
		  
	      public static void updateServiceDate(int serviceID, int serviceDate) {
	    	  
	    	  String sqlEquipmentStatus = "UPDATE Service "
		    	  		+ "SET serviceID = ?" 
		    	  		+ ", serviceDate = ?"
		    	  		+ " WHERE serviceID = " + serviceID;


					 try {
					         
							PreparedStatement pstmt = conn.prepareStatement(sqlEquipmentStatus);
						  	System.out.println("updateEquipmentStatus");
							pstmt.setInt(1, serviceID);
							pstmt.setInt(2, serviceDate); 
							
							pstmt.executeUpdate();
					 }
					 
					 catch (SQLException e) {
					     System.out.println(e.getMessage());
					 }
			      }	
		 		 
	      
	      
//---------------------------------------------------------------------------------------------------------------------------//
	      										//METHODS FOR EQUIPMENT SYSTEM//
	      public static void meny() {
	    	  while(true) {
	    	      
	   	       String meny= JOptionPane.showInputDialog (
	   	       "Menu for service of equipments\n" +
	   	       "All - See all equipments \n" +
	   	       "Broken - Equipment that are broken \n" +
	   	       "Done - To be able to see which equipment have been through service \n" +
	   	       "Service - Report equipment on service \n" +
	   	       "Report - Report broken equipment \n" +
	   	       "Now - Equipment coming back from service \n" +
	   	       "\nQ- Avsluta\n" + 
	   	       " " +
	   	       "\nAnge operation"); 
	   	       
	   	        if(meny == null) {
	   	            System.exit(0);
	   	            }
	   	      
	   	      
	   	      
	   	      switch(meny.toUpperCase()) {

	   	      	  case "ALL":
	   		      all();
	   		      break;
	   		      case "BROKEN":
	   		      broken();
	   		      break;
	   		      case "DONE":
	   		      serviceDone();
	   		      break; 
	   		      case "SERVICE":
	   		      reportService();
		   		  break; 
	   		      case "REPORT":
	   			  reportBroken();
			   	  break;
	   		      case "NOW":
		   		  serviceDoneNow();
				  break;
	   		      case "Q": 
	   		      System.out.println("q");
	   		      System.exit(0);
	   		      break;
	   		      default:
	   		      JOptionPane.showMessageDialog(null, "No");
	   		      }
	   	      }  
	      }
	      
	      		private static void all() {
	      			String all = "";
	      			String sqlAll = "select equipment.equipmentID, equipment.equipmentName, equipment.equipmentType, equipment.equipmentStatus, equipment.facilityID, equipment.serviceID, equipment.supplierID, service.serviceDate from equipment inner join service on equipment.serviceID = service.serviceID inner join facility on equipment.facilityID = facility.facilityID inner join supplier on equipment.supplierID = supplier.supplierID;)";

	      			System.out.println("sqlFacility");
			          try  {
		
			               Statement stmt = conn.createStatement();
			                ResultSet rs = stmt.executeQuery(sqlAll);
			                  while (rs.next()) {
			                 all += (rs.getInt("equipmentID") + "    	                   |" +  
			                		 rs.getString("equipmentName") + "           	    |" + 
			                		 rs.getString("equipmentType") + "    	          |" + 
			                		 rs.getString("equipmentStatus") + "    	    |" + 
			                		 rs.getInt("facilityID") + "    	      |" + 
			                		 rs.getInt("serviceID") + "    	        |" + 
			                		 rs.getInt("supplierID") + "    	   |" + 
			                         rs.getInt("serviceDate") + "\n");
		
			              }
			              }
			              
			        catch (SQLException e) {
			            System.out.println(e.getMessage());
			            JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
			       }
			        
			       JOptionPane.showMessageDialog(null, "All the equipmentID the equipments \n\n" 
			    		   							+ "equipmentID | equipment name | equipment type | Status | facilityID | serviceID | supplierID | Date of service \n" + all);
	      		}
	      
	      		private static void broken() {
	      		while (true) {
	      			String eStatus = "";
	      			String broken = "";
	      				      			
	      			String status = "'Broke'";
	      			String sqlReportBroken = ("SELECT * FROM equipment WHERE equipmentStatus = " + status);
	      					System.out.println("sqlReportService"); 
	      					
	      					 try  {
	 				            
	 			                Statement stmt = conn.createStatement();
	 			                 ResultSet rs = stmt.executeQuery(sqlReportBroken);
	 			                 while (rs.next()) {
	 			             broken += (rs.getInt("equipmentID") + "                      |" +
		 			                	rs.getString("equipmentName") + "               |" +
		 			                	rs.getString("equipmentType") + "             |" +
		 			                	rs.getString("equipmentStatus") + "        |" +
		 			                	rs.getInt("facilityID") + "               |" +
		 			                	rs.getInt("serviceID") + "              |" +
		 			                	rs.getInt("supplierID") + "\n");
	 			             eStatus += (rs.getString("equipmentStatus"));
	 			                       
	 			            }
	 			            }
	 					     catch (SQLException e) {
	 					         System.out.println(e.getMessage());
	 					         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	 					     	}
	      					 	System.out.println("sqlReportBroken");
	   				    
	      					 String broke = "Broke"; 
	      					 	if (broke.equals(eStatus)) {
	      					 		JOptionPane.showMessageDialog(null, "Below are all the broken equipments  \n \n "
															+ "equipmentID | equipmentName | equipmentType | statusID | facilityID | serviceID | supplierID \n" + broken);
	      					 		
	      					 		meny();
	      					 		
	      					 	}
	      					 	else {
	      					 		JOptionPane.showMessageDialog(null, "No equipment are broken");
	      					 		meny();
	
	      					 	}
	      			}
	      		}
	      												//TESTA DENNA EFTERÅT
	      		private static void serviceDone() {
	      			String servD = "";
	      			String done = "";
	      			//String zero = "0";
	      			String sqlServiceDone = "Select Equipment.equipmentID, Equipment.equipmentName, Service.serviceDate, Equipment.equipmentStatus from service inner join equipment on equipment.serviceID = service.serviceID where service.serviceDate != '0'";
	      			System.out.println("Service Done"); 
		            
		            try  {
			            
		                Statement stmt = conn.createStatement();
		                 ResultSet rs = stmt.executeQuery(sqlServiceDone);
		                  while (rs.next()) {
		                done += (rs.getInt("equipmentID") + "                    |" +
		                		 rs.getString("equipmentName") + "               |" +
		                		 rs.getInt("serviceDate") + "                    |" +
		                		 rs.getString("equipmentStatus")+ "\n" );
		                servD += (rs.getInt("serviceDate"));
		                      
		            }
		            }
				     catch (SQLException e) {
				         System.out.println(e.getMessage());
				         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
				     	}
		           
		            String empty = "0";
		            
		            if (servD.equals(empty)) {
	                	 JOptionPane.showMessageDialog(null, "There is no equipment on service right now");
	                	 System.out.println("No equipment on service");
	                 	}
		            else {
			            	String s = JOptionPane.showInputDialog("All the equipmentID that have been through service \n \n "
																+ "equipmentID | equipmentName | serviceDate | equipmentStatus \n " + done
																+ "\n \n Do you want to send an equipment through service? \n"
																+ "Yes/no ");
			            								System.out.println(s);	
	      			String yes = "yes";
	      			String no = "no";
		      			if (s.equals(yes)) {
		      				reportService();
		      			}
		      			if (s.equals(no)) {
		      				meny();
		      			}
		            } 
	      		}

	      		private static void reportService() {
	      			String brokenService = "";
	      			
	      			String eID = "";
	      			String eN = "";
	      			String eT = "";
	      			String eS = "";
	      			String fID = "";
	      			String servID = "";
	      			String supID = "";
	      			
	      			String sqlReportService = ("SELECT * FROM equipment;");
	      					System.out.println("sqlReportService"); 
	      					
	      					 try  {
	 				            
	 			                Statement stmt = conn.createStatement();
	 			                 ResultSet rs = stmt.executeQuery(sqlReportService);
	 			                  while (rs.next()) { 
	 			       brokenService += (rs.getInt("equipmentID") + "                       |" +
	 		 			               	 rs.getString("equipmentName") + "               |" +
	 		 			                 rs.getString("equipmentType") + "                 |" +
	 		 			                 rs.getString("equipmentStatus") + "       |" +
	 		 			                 rs.getInt("facilityID") + "                 |" +
	 		 			                 rs.getInt("serviceID") + "             |" +
	 		 			                 rs.getInt("supplierID") + "\n");
	 			                	
	 			                 eID += (rs.getInt("equipmentID"));
	 			                  eN += (rs.getString("equipmentName"));
	 			                  eT += (rs.getString("equipmentType"));
	 			                  eS += (rs.getString("equipmentStatus"));
	 			                 fID += (rs.getInt("facilityID"));
	 			              servID += (rs.getInt("serviceID"));
	 			               supID += (rs.getInt("supplierID"));
	 			                       
	 			            }
	 			            }
	 					     catch (SQLException e) {
	 					         System.out.println(e.getMessage());
	 					         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	 					     	}
	      					 	System.out.println("sqlReportService");
	   				     
	      			eID = JOptionPane.showInputDialog("Which equipment are you sending for service? (Write down the equipmentID)  \n \n "
	 												+ "equipmentID | equipment name | equipment type | statusID | facilityID | serviceID | supplierID \n" + brokenService); 
	 			      							System.out.println(eID);
	 			      							
	 			      		if (eID.isBlank()) {
	 			      			JOptionPane.showMessageDialog(null, "You can't enter blank!");
	 			      			reportService();
	 			      		}	 
	 			  
	 			     int equipmentID = Integer.parseInt(eID);
	 			     
	 			  reportingService(equipmentID);
	      		}

	      			private static void reportingService(int equipmentID) {
	      				
	      				String equipmentName = "";
	      				String sqlEN = "select equipmentName from Equipment where equipmentID = " + equipmentID;
		      				
	      					try  {
				                Statement stmt = conn.createStatement();
				                 ResultSet rs = stmt.executeQuery(sqlEN);
				                  while (rs.next()) {
				                	  equipmentName += (rs.getString("equipmentName"));          
				            }
				            }
						    catch (SQLException e) {
						         System.out.println(e.getMessage());
						         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						     	}
	      						System.out.println(equipmentName);
	      				
	      				String equipmentType = "";
	    	      		String sqlET = "select equipmentType from Equipment where equipmentID = " + equipmentID;
	    		      				
	    	      			try  {        
	    				        Statement stmt = conn.createStatement();
	    				         ResultSet rs = stmt.executeQuery(sqlET);
	    				          while (rs.next()) {
	    				        	  equipmentType += (rs.getString("equipmentType"));                 
	    				    }
	    				    }   
	    					catch (SQLException e) {
	    					     System.out.println(e.getMessage());
	    					     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    					}
	    	      				System.out.println(equipmentType);
	    	      		
	    	      		String fID = "";
	    	    	    String sqlfID = "select facility.facilityID from facility join equipment on facility.facilityID = equipment.facilityID where equipment.equipmentID = " + equipmentID;
	    	    		      				
	    	    	    	try  {        
	    	    		        Statement stmt = conn.createStatement();
	    	    		         ResultSet rs = stmt.executeQuery(sqlfID);
	    	    		          while (rs.next()) {
	    	    		         fID += (rs.getInt("facilityID"));                 
	    	    		    }
	    	    		    }   
	    	    			catch (SQLException e) {
	    	    			     System.out.println(e.getMessage());
	    	    			     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    	    		  	}
	    	    	      		System.out.println(fID);
	    	    	      	int facilityID = Integer.parseInt(fID);
	    	    	      	
	    	    	   String servID = "";
	    	    	   String sqlservID = "select service.serviceID from service join equipment on service.serviceID = equipment.serviceID where equipment.equipmentID = " + equipmentID;
		    	    		      				
		    	    	    try  {        
		    	    		    Statement stmt = conn.createStatement();
		    	    		     ResultSet rs = stmt.executeQuery(sqlservID);
		    	    		       while (rs.next()) {
		    	    		   servID += (rs.getInt("serviceID"));                 
		    	    		}
		    	    		}   
		    	    		catch (SQLException e) {
		    	    		     System.out.println(e.getMessage());
		    	    		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
		    	    		}
		    	    	    	System.out.println(servID);
		    	    	    int serviceID = Integer.parseInt(servID);
		    	    	    
		    	       String supID = "";
			    	   String sqlsupID = "select supplier.supplierID from supplier join equipment on supplier.supplierID = equipment.supplierID where equipment.equipmentID = " + equipmentID;
				    	    		      				
				    	    try  {        
				    		    Statement stmt = conn.createStatement();
				       		     ResultSet rs = stmt.executeQuery(sqlsupID);
			 	    		       while (rs.next()) {
			 	    		    supID += (rs.getInt("supplierID"));                 
		    	    		}
				    	  	}   
				    		catch (SQLException e) {
				    		     System.out.println(e.getMessage());
				     		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
			   	    		}
				    	    	 System.out.println(supID);
				    	    int supplierID = Integer.parseInt(supID);
				   
				       String equipmentStatus = "Service";
				    
				     updateEquipmentStatus(equipmentID, equipmentName, equipmentType, equipmentStatus, facilityID, serviceID, supplierID);
				    	
				     JOptionPane.showMessageDialog(null, "You have now reported the " + equipmentName + " for service");
	      			}
	      			
	      		private static void reportBroken() {
	      			String brokenService = "";
	      			String eID = "";
	      			
	      			String sqlReportService = ("SELECT * FROM equipment;");
	      					System.out.println("sqlReportService"); 
	      					
	      					 try  {
	 				            
	 			                Statement stmt = conn.createStatement();
	 			                 ResultSet rs = stmt.executeQuery(sqlReportService);
	 			                 while (rs.next()) {
	 			      brokenService += (rs.getInt("equipmentID") + "                       |" +
		 			                	rs.getString("equipmentName") + "               |" +
		 			                	rs.getString("equipmentType") + "                 |" +
		 			                	rs.getString("equipmentStatus") + "       |" +
		 			                	rs.getInt("facilityID") + "                 |" +
		 			                	rs.getInt("serviceID") + "             |" +
		 			                	rs.getInt("supplierID") + "\n");
	 			      
				 			   eID += (rs.getInt("equipmentID"));
				               	
	 			            }
	 			            }
	 					     catch (SQLException e) {
	 					         System.out.println(e.getMessage());
	 					         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	 					     	}
	      					 	System.out.println("sqlReportService");
	   				     
	      			eID = JOptionPane.showInputDialog("Which equipment are broken? (Write down the equipmentID)  \n \n "
	 												+ "equipmentID | equipment name | equipment type | statusID | facilityID | serviceID | supplierID  \n" + brokenService); 
	 			      							System.out.println(eID);
	 			      							
	 			      		if (eID.isBlank()) {
	 			      			JOptionPane.showMessageDialog(null, "You can't enter blank!");
	 			      			reportService();
	 			      		}	 
	 			  
	 			     int equipmentID = Integer.parseInt(eID);
	 			     
	 			  reportingBroken(equipmentID);
	  	    	  
	  	        }
	      			
	      			private static void reportingBroken(int equipmentID) {
	      				
	      				String equipmentName = "";
	      				String sqlEN = "select equipmentName from Equipment where equipmentID = " + equipmentID;
		      				
	      					try  {
				                Statement stmt = conn.createStatement();
				                 ResultSet rs = stmt.executeQuery(sqlEN);
				                  while (rs.next()) {
				                	  equipmentName += (rs.getString("equipmentName"));          
				            }
				            }
						    catch (SQLException e) {
						         System.out.println(e.getMessage());
						         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						     	}
	      						System.out.println(equipmentName);
	      				
	      				String equipmentType = "";
	    	      		String sqlET = "select equipmentType from Equipment where equipmentID = " + equipmentID;
	    		      				
	    	      			try  {        
	    				        Statement stmt = conn.createStatement();
	    				         ResultSet rs = stmt.executeQuery(sqlET);
	    				          while (rs.next()) {
	    				        	  equipmentType += (rs.getString("equipmentType"));                 
	    				    }
	    				    }   
	    					catch (SQLException e) {
	    					     System.out.println(e.getMessage());
	    					     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    					}
	    	      				System.out.println(equipmentType);
	    	      		
	    	      		String fID = "";
	    	    	    String sqlfID = "select facility.facilityID from facility join equipment on facility.facilityID = equipment.facilityID where equipment.equipmentID = " + equipmentID;
	    	    		      				
	    	    	    	try  {        
	    	    		        Statement stmt = conn.createStatement();
	    	    		         ResultSet rs = stmt.executeQuery(sqlfID);
	    	    		          while (rs.next()) {
	    	    		         fID += (rs.getInt("facilityID"));                 
	    	    		    }
	    	    		    }   
	    	    			catch (SQLException e) {
	    	    			     System.out.println(e.getMessage());
	    	    			     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    	    		  	}
	    	    	      		System.out.println(fID);
	    	    	      	int facilityID = Integer.parseInt(fID);
	    	    	      	
	    	    	   String servID = "";
	    	    	   String sqlservID = "select service.serviceID from service join equipment on service.serviceID = equipment.serviceID where equipment.equipmentID = " + equipmentID;
		    	    		      				
		    	    	    try  {        
		    	    		    Statement stmt = conn.createStatement();
		    	    		     ResultSet rs = stmt.executeQuery(sqlservID);
		    	    		       while (rs.next()) {
		    	    		   servID += (rs.getInt("serviceID"));                 
		    	    		}
		    	    		}   
		    	    		catch (SQLException e) {
		    	    		     System.out.println(e.getMessage());
		    	    		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
		    	    		}
		    	    	    	System.out.println(servID);
		    	    	    int serviceID = Integer.parseInt(servID);
		    	    	    
		    	       String supID = "";
			    	   String sqlsupID = "select supplier.supplierID from supplier join equipment on supplier.supplierID = equipment.supplierID where equipment.equipmentID = " + equipmentID;
				    	    		      				
				    	    try  {        
				    		    Statement stmt = conn.createStatement();
				       		     ResultSet rs = stmt.executeQuery(sqlsupID);
			 	    		       while (rs.next()) {
			 	    		    supID += (rs.getInt("supplierID"));                 
		    	    		}
				    	  	}   
				    		catch (SQLException e) {
				    		     System.out.println(e.getMessage());
				     		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
			   	    		}
				    	    	 System.out.println(supID);
				    	    int supplierID = Integer.parseInt(supID);
				   
				     String equipmentStatus = "Broke";
				    
				     updateEquipmentStatus(equipmentID, equipmentName, equipmentType, equipmentStatus, facilityID, serviceID, supplierID);
				    	
				     JOptionPane.showMessageDialog(null, "You have now reported the " + equipmentName + " broken");
	      			}
	      			
	      		private static void serviceDoneNow() {
	      			
	      			String all = "";
	      			String sqlDoneNow = "select equipment.equipmentID, equipment.equipmentName, equipment.equipmentType, equipment.equipmentStatus, equipment.facilityID, equipment.serviceID, equipment.supplierID, service.serviceDate from equipment inner join service on equipment.serviceID = service.serviceID inner join facility on equipment.facilityID = facility.facilityID inner join supplier on equipment.supplierID = supplier.supplierID;)";
	      				System.out.println("sqlDoneNow");
				          try  {
				        	  Statement stmt = conn.createStatement();
				                ResultSet rs = stmt.executeQuery(sqlDoneNow);
				                  while (rs.next()) {
				                 all += (rs.getInt("equipmentID") + "    	                   |" +  
				                		 rs.getString("equipmentName") + "           	    |" + 
				                		 rs.getString("equipmentType") + "    	          |" + 
				                		 rs.getString("equipmentStatus") + "    	    |" + 
				                		 rs.getInt("facilityID") + "    	      |" + 
				                		 rs.getInt("serviceID") + "    	   |" + 
				                		 rs.getInt("supplierID") + "    	   |" + 
				                         rs.getInt("serviceDate") + "\n");
				          }
				          }
				              
				        catch (SQLException e) {
				            System.out.println(e.getMessage());
				            JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
				       }
	      			
	      			String eID = JOptionPane.showInputDialog("Which equipment have come back from service? \n"
	      													+ "equipmentID | equipment name | equipment type | Status | facilityID | serviceID | supplierID | Date of service \n" + all);
				      			if (eID.isBlank()) {
						      			JOptionPane.showMessageDialog(null, "You can't enter blank!");
						      			serviceDoneNow();
						      			
						      	}
				      int equipmentID = Integer.parseInt(eID);
				   
				    Date date = new Date();
							System.out.println(date.toString());
						int year = (1900 + date.getYear());
					 	int month =(1 + date.getMonth());
					 	int day = (date.getDate());
					 	
				    String sDate = year + "" + month + "" + day;
						int serviceDate = Integer.parseInt(sDate);
							
				    JOptionPane.showMessageDialog(null, "Todays date is: " + serviceDate); 
					 
			reportServiceDoneNow(equipmentID, serviceDate);
	      		}
	      		
	      			private static void reportServiceDoneNow(int equipmentID, int serviceDate) {
	      				
	      				String equipmentName = "";
	      				String sqlEN = "select equipmentName from Equipment where equipmentID = " + equipmentID;
		      				
	      					try  {
				                Statement stmt = conn.createStatement();
				                 ResultSet rs = stmt.executeQuery(sqlEN);
				                  while (rs.next()) {
				                	  equipmentName += (rs.getString("equipmentName"));          
				            }
				            }
						    catch (SQLException e) {
						         System.out.println(e.getMessage());
						         JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
						     	}
	      						System.out.println(equipmentName);
	      				
	      				String equipmentType = "";
	    	      		String sqlET = "select equipmentType from Equipment where equipmentID = " + equipmentID;
	    		      				
	    	      			try  {        
	    				        Statement stmt = conn.createStatement();
	    				         ResultSet rs = stmt.executeQuery(sqlET);
	    				          while (rs.next()) {
	    				        	  equipmentType += (rs.getString("equipmentType"));                 
	    				    }
	    				    }   
	    					catch (SQLException e) {
	    					     System.out.println(e.getMessage());
	    					     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    					}
	    	      				System.out.println(equipmentType);
	    	      		
	    	      		String fID = "";
	    	    	    String sqlfID = "select facility.facilityID from facility join equipment on facility.facilityID = equipment.facilityID where equipment.equipmentID = " + equipmentID;
	    	    		      				
	    	    	    	try  {        
	    	    		        Statement stmt = conn.createStatement();
	    	    		         ResultSet rs = stmt.executeQuery(sqlfID);
	    	    		          while (rs.next()) {
	    	    		         fID += (rs.getInt("facilityID"));                 
	    	    		    }
	    	    		    }   
	    	    			catch (SQLException e) {
	    	    			     System.out.println(e.getMessage());
	    	    			     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
	    	    		  	}
	    	    	      		System.out.println(fID);
	    	    	      	int facilityID = Integer.parseInt(fID);
	    	    	      	
	    	    	   String servID = "";
	    	    	   String sqlservID = "select service.serviceID from service join equipment on service.serviceID = equipment.serviceID where equipment.equipmentID = " + equipmentID;
		    	    		      				
		    	    	    try  {        
		    	    		    Statement stmt = conn.createStatement();
		    	    		     ResultSet rs = stmt.executeQuery(sqlservID);
		    	    		       while (rs.next()) {
		    	    		   servID += (rs.getInt("serviceID"));                 
		    	    		}
		    	    		}   
		    	    		catch (SQLException e) {
		    	    		     System.out.println(e.getMessage());
		    	    		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
		    	    		}
		    	    	    	System.out.println(servID);
		    	    	    int serviceID = Integer.parseInt(servID);
		    	    	    
		    	       String supID = "";
			    	   String sqlsupID = "select supplier.supplierID from supplier join equipment on supplier.supplierID = equipment.supplierID where equipment.equipmentID = " + equipmentID;
				    	    		      				
				    	    try  {        
				    		    Statement stmt = conn.createStatement();
				       		     ResultSet rs = stmt.executeQuery(sqlsupID);
			 	    		       while (rs.next()) {
			 	    		    supID += (rs.getInt("supplierID"));                 
		    	    		}
				    	  	}   
				    		catch (SQLException e) {
				    		     System.out.println(e.getMessage());
				     		     JOptionPane.showMessageDialog(null, "Something went wrong:\n\n" + e.getMessage());
			   	    		}
				    	    	 System.out.println(supID);
				    	    int supplierID = Integer.parseInt(supID);
				   
				     String equipmentStatus = "Working";
				    
				     updateServiceDate(serviceID, serviceDate);
				     
				     updateEquipmentStatus(equipmentID, equipmentName, equipmentType, equipmentStatus, facilityID, serviceID, supplierID);
				    	
				     JOptionPane.showMessageDialog(null, equipmentName + "is now reported back from service");
	      			 }
}
