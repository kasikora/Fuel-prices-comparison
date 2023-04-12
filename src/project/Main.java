package project;

import java.awt.BorderLayout; 
import org.apache.log4j.Logger;  
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;





public class Main extends ParsePrices {
     final static Logger logger = Logger.getLogger(Main.class);

	
	 public static void createAndShowGUI() {
		 
		 	final String[] countriesTable = {"Germany", "Spain", "France", "Portugal"};
		 	final String[] fuelTable = {"Diesel", "Premium", "Premium Plus", "Autogas", "Premium Diesel"};
		 	String cityName1 = "First city";
		 	String cityName2 = "Second city";
	        JFrame jf = new JFrame("Fuel price comparison - K.Sikora");
	        JPanel jp = new JPanel(new BorderLayout());
	        JComboBox jcbCountries1 = new JComboBox(countriesTable);
	        JComboBox jcbCountries2 = new JComboBox(countriesTable);
	        JComboBox jcbFuel1 = new JComboBox(fuelTable);
	        JComboBox jcbFuel2 = new JComboBox(fuelTable);
	        JTextField city1 = new JTextField("Select a city");
	        JTextField city2 = new JTextField("Select a city");
	        
	       

	        JButton check = new JButton("Check");
	        JButton clear = new JButton("Clear");

	        JPanel jpNorth = new JPanel(new GridLayout(0,2));
	        JPanel jpCenter = new JPanel(new GridLayout());
	        
	        String[][] data = {
	        		{ "Mean[€]", "" , "" },
	                { "Median [€]", "", "" },
	                { "Mode [€]", "", "" },
	                { "Most expensive [€]", "", ""},
	                { "Cheapest [€]", "", ""},
	                { "Number of data samples", "", ""}
	            };
	        
	        String[] columnNames = {"", cityName1, cityName2
	        		};
	        
	        DefaultTableModel model = new DefaultTableModel(data,columnNames);

	        JTable table = new JTable(model);
	        table.setDefaultEditor(Object.class, null);

	        
	        
	        jf.add(jp);
	        jp.add(jpNorth, BorderLayout.NORTH);
	        jpNorth.add(jcbCountries1);
	        jpNorth.add(jcbCountries2);
	        jpNorth.add(jcbFuel1);
	        jpNorth.add(jcbFuel2);
	        jpNorth.add(city1);
	        jpNorth.add(city2);
	        jpNorth.add(check);
	        jpNorth.add(clear);
	        jp.add(jpCenter);
	        jpCenter.add(new JScrollPane(table));

	        jf.pack();
	        jf.setSize(500,270);
	        jf.setVisible(true);
	        jf.setResizable(false);
	        jf.setLocationRelativeTo(null);
	        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        
	         
	      
	        city1.getDocument().addDocumentListener(new DocumentListener(){
	        	
	            @Override
	            public void insertUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            	
	            }

	            @Override
	            public void changedUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            }
	            private void assistDateText() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                           
                            String input = city1.getText();
                            
                            if (input.matches("^[0-9]{4}")) {
                                city1.setText(input + "-");
                            } else if (input.matches("^[0-9]{4}-[0-9]{2}")) {
                                city1.setText(input + "-");
                            }
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }

	        });
	        
	        city2.getDocument().addDocumentListener(new DocumentListener(){
	        	
	            @Override
	            public void insertUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            	
	            }

	            @Override
	            public void changedUpdate(DocumentEvent de)
	            {
                    assistDateText();
	            }
	            private void assistDateText() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                           
                            String input = city1.getText();
                            if (input.matches("^[0-9]{4}")) {
                                city1.setText(input + "-");
                            } else if (input.matches("^[0-9]{4}-[0-9]{2}")) {
                                city1.setText(input + "-");
                            }
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }

	        });


	        
	        ActionListener myActionListener = new ActionListener() {
	        
	        
			 String city1_;
			 String city2_;
			 String address1;
			 String address2;
			 String [] values;
			 String jcbc1;
			 String jcbc2;
			 String jcbf1;
			 String jcbf2;
			  

				@Override
				public void actionPerformed(ActionEvent e) {
	                jcbc1 = (String) jcbCountries1.getSelectedItem();
	                jcbc2 = (String) jcbCountries2.getSelectedItem();
	                jcbf1 = (String) jcbFuel1.getSelectedItem();
	                jcbf2 = (String) jcbFuel2.getSelectedItem();
	                city1_ = city1.getText().strip();
	                city2_ = city2.getText().strip();

	                
	                
	               if(e.getActionCommand().equals(check.getText())) {

					   logger.info("Checking if first city is in database");

					   if(ParseCities.checkIfPropertyExists(jcbc1,city1_)) {
	                		jcbc1 = ParsePrices.changeCountry(jcbc1);
	                		jcbf1 = ParsePrices.changeFuel(jcbf1);
	    	                
	    	                address1 = "https://www.fuelflash.eu/en/?land=" + jcbc1 + "&suchfeld=" + city1_ + "&entfernung=15&sorte=" + jcbf1 + "&sortierung=preis";

							logger.info("Downloading data from "+ address1);



							values = ParsePrices.parsePrices(address1);
						   logger.info("Setting values in table");

						   model.setValueAt(values[0], 0,1);
	                		model.setValueAt(values[2], 1, 1);
	                		model.setValueAt(values[1], 2, 1);
	                		model.setValueAt(values[3], 3, 1);
	                		model.setValueAt(values[4], 4, 1);
	                		model.setValueAt(values[5], 5, 1);
	                	} else {
	                        logger.info("No data for first city");

	                		city1.setText("No data for this city");
	                		model.setValueAt("", 0, 1);
	                		model.setValueAt("", 1, 1);
	                		model.setValueAt("", 2, 1);
	                		model.setValueAt("", 3, 1);
	                		model.setValueAt("", 4, 1);
	                		model.setValueAt("", 5, 1);
	                		
	                	}
					   logger.info("Checking if second city is in database");

					   if(ParseCities.checkIfPropertyExists(jcbc2,city2_)) {



						   jcbf2 = ParsePrices.changeFuel(jcbf2);
	                		jcbc2 = ParsePrices.changeCountry(jcbc2);
	                		address2 = "https://www.fuelflash.eu/en/?land=" + jcbc2 + "&suchfeld=" + city2_ + "&entfernung=15&sorte=" + jcbf2 + "&sortierung=preis";
						   logger.info("Downloading data from "+ address2);

						   values = ParsePrices.parsePrices(address2);

	                		model.setValueAt(values[0], 0, 2);
	                		model.setValueAt(values[2], 1, 2);
	                		model.setValueAt(values[1], 2, 2);
	                		model.setValueAt(values[3], 3, 2);
	                		model.setValueAt(values[4], 4, 2);
	                		model.setValueAt(values[5], 5, 2);

	                	} else {
							logger.info("No data for second city");
	                		city2.setText("No data for this city");
	                		model.setValueAt("", 0, 2);
	                		model.setValueAt("", 1, 2);
	                		model.setValueAt("", 2, 2);
	                		model.setValueAt("", 3, 2);
	                		model.setValueAt("", 4, 2);
	                		model.setValueAt("", 5, 2);
	                	}
	                	
	                }
	               
	               if(e.getActionCommand().equals(clear.getText())) {
	                    logger.info("Clearing table");


	            	    model.setValueAt("", 0, 1);
               			model.setValueAt("", 1, 1);
               			model.setValueAt("", 2, 1);
               			model.setValueAt("", 3, 1);
               			model.setValueAt("", 4, 1);
               			model.setValueAt("", 5, 1);
               			model.setValueAt("", 0, 2);
                		model.setValueAt("", 1, 2);
                		model.setValueAt("", 2, 2);
                		model.setValueAt("", 3, 2);
                		model.setValueAt("", 4, 2);
                		model.setValueAt("", 5, 2);
                		city1.setText("Select a city");
                		city2.setText("Select a city");
	            	   
	            	   
	            	   
	            	   
	               }
	    	        	



	            	
	            	


					
				}
	        
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        };
	        
	        
	        
	        jcbCountries1.addActionListener(myActionListener);
	        jcbCountries2.addActionListener(myActionListener);
	        jcbFuel1.addActionListener(myActionListener);
	        jcbFuel2.addActionListener(myActionListener);
	        check.addActionListener(myActionListener);
	        clear.addActionListener(myActionListener);
	        
		 
	 }
	 
	 
	 
	 
	 
	 
	 public static void main(String[] args) {
	        BasicConfigurator.configure();

	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }

}
