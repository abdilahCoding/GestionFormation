package com.Controller.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.URISyntaxException;

import Connection.Config;
import javax.swing.SwingConstants;


public class Session {

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	 Config connect;
	    public Connection conn;
	    public PreparedStatement stmt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Session window = new Session();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public Session() throws Exception {
		connect=new Config("jdbc:mysql://localhost:3306/gestion_formations","root","","com.mysql.jdbc.Driver");
		conn=connect.ConnectionLoad();
		initialize();
		show();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 1147, 665);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("< Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeAdmin home = new HomeAdmin();
				frame.dispose();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(28, 24, 116, 45);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login lg = new Login();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogOut.setBounds(976, 24, 115, 45);
		frame.getContentPane().add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("Management Session");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(28, 92, 216, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		panel.setBounds(706, 92, 367, 448);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("Session Date :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 91, 109, 23);
		panel.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(22, 332, 326, 98);
		panel.add(panel_1);
		
		JComboBox<String> formationCmbx = new JComboBox<String>();
		formationCmbx.setBounds(155, 172, 193, 32);
		panel.add(formationCmbx);
		try {
			if(conn!=null) {
			 stmt = conn.prepareStatement("SELECT * FROM formation");
				
			 ResultSet result = stmt.executeQuery();
			 
			 while(result.next()) {
				String f = result.getString("libelle");
				String idFormation=String.valueOf(result.getInt("idFormation"));
	            formationCmbx.addItem(f);
	            formationCmbx.setPrototypeDisplayValue(idFormation);
				 
	         }
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String date_session =textField.getText();
				String Formation = formationCmbx.getSelectedItem().toString();
				int idFormation = Integer.parseInt(formationCmbx.getPrototypeDisplayValue());

System.out.println("hhh" + Formation + " ddd" + date_session);
				try {
					if(conn!=null) {
					 
					 String sql = "INSERT INTO session (libelle,formation,idFormation) VALUES(?,?,?) ";
					
					 
					 stmt = conn.prepareStatement(sql);
						
					 stmt.setString(1,date_session);
					 stmt.setString(2,Formation);
					 stmt.setInt(3, idFormation);
					
			
				      int add=stmt.executeUpdate();
				if(add>0) {
					 JOptionPane.showMessageDialog(btnAdd, "Session Added Successfully");
					  show();
					  Clear();
				}else {
					JOptionPane.showMessageDialog(btnAdd,"Sorry dont add");
				}
					}
			
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdd.setBounds(10, 11, 89, 32);
		panel_1.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel df = (DefaultTableModel)table.getModel();	
			     int selectedIndex = table.getSelectedRow();
				
			    
					try {
						
						String date_session =textField.getText();
						String Formation = formationCmbx.getSelectedItem().toString();

							
							if(conn!=null) {
							int id = Integer.parseInt(df.getValueAt(selectedIndex, 0).toString());

						
						 
						 
						 stmt = conn.prepareStatement("UPDATE session SET " + "libelle = '" + date_session+ "', formation = '" + Formation + "' where idSession = '" + id + "'");
						 stmt.executeUpdate();
					  
					     JOptionPane.showMessageDialog(btnAdd, "Session edited Successfully");
				
					     show();
						 Clear();
							}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(109, 11, 108, 32);
		panel_1.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int msg = JOptionPane.showConfirmDialog(null, "Are you sure to delete", "Delete", JOptionPane.YES_NO_OPTION);
				if(msg==0) {
					
			
				
				 DefaultTableModel df = (DefaultTableModel)table.getModel();	
			     int selectedIndex = table.getSelectedRow();	
				
			
				
			     try {
						
						if(conn!=null) {
						int id = Integer.parseInt(df.getValueAt(selectedIndex, 0).toString());
						
						 
					
					     stmt = conn.prepareStatement("DELETE from session  where idSession = '" + id + "'");
						 stmt.executeUpdate();
						  
						 JOptionPane.showMessageDialog(btnAdd, "Session Deleted Successfully");
						 show();
			             Clear();
						}
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(227, 11, 89, 32);
		panel_1.add(btnDelete);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clear();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnReset.setBounds(109, 54, 106, 32);
		panel_1.add(btnReset);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 13));
		textField.setColumns(10);
		textField.setBounds(155, 87, 193, 32);
		panel.add(textField);
		
		JLabel lblNewLabel_1_2 = new JLabel("Formation :");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(34, 175, 92, 23);
		panel.add(lblNewLabel_1_2);
		
		
	
		
	
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(10, 178, 670, 199);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("List Sessions :");
		lblNewLabel_3.setBounds(10, 11, 171, 22);
		lblNewLabel_3.setForeground(Color.DARK_GRAY);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_2.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 650, 133);
		panel_2.add(scrollPane);
		
	
		JLabel link = new JLabel();
		link.setFont(new Font("Tahoma", Font.BOLD, 16));
		link.setForeground(Color.WHITE);
		link.setBounds(155, 285, 101, 14);
		link.setVisible(false);
		 panel.add(link);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 DefaultTableModel df = (DefaultTableModel)table.getModel();	
			     int selectedIndex = table.getSelectedRow();
				 textField.setText(df.getValueAt(selectedIndex, 1).toString());
				 formationCmbx.setModel(new DefaultComboBoxModel(new String[] {df.getValueAt(selectedIndex, 2).toString()}));
				 link.setVisible(true);
				
				 link.setText("Click Here");
				 link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				 link.addMouseListener(new MouseAdapter() {
				   public void mouseClicked(MouseEvent e) {
				      if (e.getClickCount() > 0) {
				          if (Desktop.isDesktopSupported()) {
				                try {
				                	//Port
				                	Desktop.getDesktop().browse(new URI("http://localhost:3333/Session/" + df.getValueAt(selectedIndex, 0).toString()));
				                } catch (IOException ex) {
				                    ex.printStackTrace();
				                } catch (URISyntaxException ex) {
				                    ex.printStackTrace();
				                }
				            	
				        }
				      }
				   }
				});
			}
		});
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID_Session", "Session Date", "Formation"
			}
		));
		scrollPane.setViewportView(table);
		
	}
	public void Clear() {
	    textField.setText("");

		
		textField.requestFocus();
		
		
		
	}


public void show() {
	try {
		if(conn!=null) {
				 
		 stmt = conn.prepareStatement("SELECT * FROM session");
			
		 ResultSet result = stmt.executeQuery();
		 

		 DefaultTableModel df = (DefaultTableModel)table.getModel();
		 
		 df.setRowCount(0);
		 
		 while(result.next()) {
			 int idS = result.getInt("idSession");
			
			String libell� = result.getString("libelle");
			String description = result.getString("formation");

		
			
            df.addRow(new Object[] {idS,libell�,description});
            
         }
		}

	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
	
}
}
