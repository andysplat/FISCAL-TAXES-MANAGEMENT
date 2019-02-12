import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class IncarcareFisiere {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IncarcareFisiere window = new IncarcareFisiere();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IncarcareFisiere() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] V = new String[4];
		V[0] = null;
		V[1] = null;
		V[2] = null;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 160, 400, 200);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JTextArea txtrOut = new JTextArea();
		scrollPane.setColumnHeaderView(txtrOut);
		JButton taxe = new JButton("Incarcare taxe");
		taxe.setBounds(110, 0, 180, 20);
		taxe.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(V[0] == null) {
					
					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);	
					File file = chooser.getSelectedFile();
					if(file != null)
						if(file.getName().equals("taxe.txt"))
							V[0] = file.getAbsolutePath();
				
				}
				else
					JOptionPane.showMessageDialog(frame, "Fisierul taxe.txt a fost incarcat deja");
				
			}
			
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(taxe);
		
		JButton produse = new JButton("Incarcare produse");
		produse.setBounds(110, 30, 180, 20);
		produse.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(V[1] == null) {
					
					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);	
					File file = chooser.getSelectedFile();
					if(file != null)
						if(file.getName().equals("produse.txt")) 
							V[1] = file.getAbsolutePath();
				
				}
				
				else
					JOptionPane.showMessageDialog(frame, "Fisierul produse.txt a fost incarcat deja");
				
			}
			
		});
		frame.getContentPane().add(produse);
		
		JButton facturi = new JButton("Inserare facturi");
		facturi.setBounds(110, 60, 180, 20);
		facturi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if(V[2] == null) {
					
					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);	
					File file = chooser.getSelectedFile();
					if(file != null)
						if(file.getName().equals("facturi.txt"))
							V[2] = file.getAbsolutePath();
				
				}
				
				else
					JOptionPane.showMessageDialog(frame, "Fisierul facturi.txt a fost incarcat deja");
			
			}
		
		});
		frame.getContentPane().add(facturi);
		
		JButton out = new JButton("Creare out");
		out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String eroare = "";
				if(V[0] == null)
					eroare = eroare + "\ntaxe.txt";
				if(V[1] == null)
					eroare = eroare + "\nproduse.txt";
				if(V[2] == null)
					eroare = eroare + "\nfacturi.txt";
				if(eroare == ""){
					
					Tema tema = new Tema();
					tema.main(V);
					try {
	
						FileReader out = new FileReader("out.txt");
						BufferedReader reader = new BufferedReader(out);
						textArea.read(reader, null);
						txtrOut.setText("out.txt");
						textArea.requestFocus();
						txtrOut.requestFocus();
						reader.close();
						
						
					} catch (FileNotFoundException exception) {
						
						exception.printStackTrace();
						
					} catch (IOException exception) {
						
						exception.printStackTrace();
						
					}	
							
				}
			
				else
					
					JOptionPane.showMessageDialog(frame, "Urmatoarele fisiere nu au fost incarcate:" + eroare);
						
			}
		
		});
		out.setBounds(110, 90, 180, 20);
		frame.getContentPane().add(out);
		
		JButton meniu = new JButton("Meniu Principal");
		meniu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Meniu meniu = new Meniu();
				meniu.main(null);
				
			}
		});
		meniu.setBounds(110, 120, 180, 20);
		frame.getContentPane().add(meniu);
		
		
	
	}
}
