import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AfisareAdministrare {

	private JFrame frame;
	private JTextField stergere;
	private JTextField adaugare;
	private JTextField categorie;
	private JTextField pret;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AfisareAdministrare window = new AfisareAdministrare();
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
	public AfisareAdministrare() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Gestiune gestiune = Gestiune.getInstance();
		gestiune.produse =  new LinkedList<Produs>();
		gestiune.magazine = new LinkedList<Magazin>();
		gestiune.taxe = new TreeMap<String, TreeMap<String, Double>>();
		Vector<String> tari = new Vector<String>();
		Vector<String> categorii = new Vector<String>();
		Vector<Integer> T = new Vector<Integer>();
		Scanner scanner = null;
		try{
			
			scanner = new Scanner(new File("taxe.txt"));
			
		}catch(FileNotFoundException exception){
			
			exception.printStackTrace();
		
		}
		String line = scanner.nextLine();
		StringTokenizer token = new StringTokenizer(line," ");
		token.nextToken();
		while (token.hasMoreTokens()){
		
			tari.add(token.nextToken());
		
		}
		do{
			
			line = scanner.nextLine();
			token = new StringTokenizer(line," ");
			categorii.add(token.nextToken());
				
		}while(scanner.hasNextLine());
		scanner.close();
		for(int n = 0; n < tari.size(); n++){
			
			TreeMap<String, Double> dictionar = new TreeMap<String, Double>();
			try{
				
				scanner = new Scanner(new File("taxe.txt"));
				
			}catch(FileNotFoundException exception){
				
				exception.printStackTrace();
			
			}
			line = scanner.nextLine();
			for(int m = 0; m < categorii.size(); m++){
				
				if(scanner.hasNextLine())
					line = scanner.nextLine();
				token = new StringTokenizer(line," ");
				for(int o = 0; o <= n; o++)
						token.nextToken();
				dictionar.put(categorii.get(m), Double.parseDouble(token.nextToken()));
				
			}
			scanner.close();
			gestiune.taxe.put(tari.get(n), dictionar);
			
		}
		
		try{
			
			scanner = new Scanner(new File("produse.txt"));
		
		}catch(FileNotFoundException exception){
			
			exception.printStackTrace();
		
		}
		line = scanner.nextLine();
		do {
			
			line = scanner.nextLine();
			token = new StringTokenizer(line," ");
			String denumire = token.nextToken();
			String categorie = token.nextToken();
			for(int n = 0; n < tari.size(); n++){
				
				Produs produs = new Produs();
				produs.setDenumire(denumire);
				produs.setCategorie(categorie);
				produs.setPret(Double.parseDouble(token.nextToken()));
				produs.setTarOrigine(tari.get(n));
				gestiune.produse.add(produs);
				
			}
			
		}while(scanner.hasNextLine());
		try{
			
			scanner = new Scanner(new File("facturi.txt"));
			
		}catch(FileNotFoundException exception){
			
			exception.printStackTrace();
		
		}
		int n = 0;
		int m = 0;
		int o = 0;
		do {
			
			line = scanner.nextLine();
			token = new StringTokenizer(line," :");
			if(token.hasMoreTokens()){
				if(line.contains("Magazin")){
				
					token.nextToken();
					Magazin magazin = MagazinFactory.magazin(token.nextToken());
					magazin.nume = token.nextToken();
					magazin.V = new Vector<Factura>();
					gestiune.magazine.add(magazin);
					m = 0;
					n = n + 1;
			
				}
				else if(line.contains("Factura")) {
					

					gestiune.magazine.get(n-1).V.add(new Factura());
					gestiune.magazine.get(n-1).V.get(m).denumire = line;
					gestiune.magazine.get(n-1).V.get(m).V = new Vector<ProdusComandat>();
					o = 0;
					m = m + 1;
					line = scanner.nextLine();
					
				}
				else {
					
					gestiune.magazine.get(n-1).V.get(m-1).V.add(new ProdusComandat());
					String denumire = token.nextToken();
					String taraOrigine = token.nextToken();
					gestiune.magazine.get(n-1).V.get(m-1).V.get(o).setCantitate(Integer.parseInt(token.nextToken()));
					for(int k = 0; k < gestiune.produse.size(); k++)
						if(gestiune.produse.get(k).getDenumire().equals(denumire) && gestiune.produse.get(k).getTaraOrigine().equals(taraOrigine)){	
						
							gestiune.magazine.get(n-1).V.get(m-1).V.get(o).setProdus(gestiune.produse.get(k));
							gestiune.magazine.get(n-1).V.get(m-1).V.get(o).setTaxa(gestiune.taxe.get(taraOrigine).get(gestiune.produse.get(k).getCategorie()));
							
						}
					o = o + 1;
				}
						
			}
			
		}while(scanner.hasNextLine());
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 300, 400, 60);
		frame.getContentPane().add(scrollPane);
		JTextArea txtrOut = new JTextArea();
		scrollPane.setColumnHeaderView(txtrOut);
		JList Jlist = new JList();
		scrollPane.setViewportView(Jlist);
		DefaultListModel list = new DefaultListModel();
		
		JButton denumire = new JButton("Afisare dupa denumire");
		denumire.setBounds(100, 0, 200, 20);
		denumire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtrOut.setText("Afisare dupa denumire");
				txtrOut.requestFocus();
				Collections.sort(gestiune.produse);
				Collections.sort(tari);
				for(int n = 0; n < tari.size(); n++) 
					for(int m = 0; m < gestiune.produse.size(); m++) {
						
						String element = null;
						if(gestiune.produse.get(m).getTaraOrigine().equals(tari.get(n)))
							for(int o = 0; o < gestiune.magazine.size(); o++)
								for(int k = 0; k < gestiune.magazine.get(o).V.size(); k++)
									for(int l = 0; l < gestiune.magazine.get(o).V.get(k).V.size(); l++)
										if(gestiune.magazine.get(o).V.get(k).V.get(l).getProdus().getDenumire().equals(gestiune.produse.get(m).getDenumire()) && gestiune.magazine.get(o).V.get(k).V.get(l).getProdus().getTaraOrigine().equals(tari.get(n)))
											element = gestiune.produse.get(m).getDenumire() + " " + tari.get(n);
					
						if(element != null)
							list.addElement(element);
					
				}
				Jlist.setModel(list);
				
			}
			
		});
		frame.getContentPane().add(denumire);
		
		JButton tara = new JButton("Afisare dupa tara");
		tara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				txtrOut.setText("Afisare dupa tara");
				txtrOut.requestFocus();
				Collections.sort(gestiune.produse);
				Collections.sort(tari);
				int m = 0;
				for(int n = 0; n < gestiune.produse.size(); n++) {
					
					String element = null;
					m = n % tari.size();
					for(int o = 0; o < gestiune.magazine.size(); o++)
						for(int k = 0; k < gestiune.magazine.get(o).V.size(); k++)
							for(int l = 0; l < gestiune.magazine.get(o).V.get(k).V.size(); l++)
								if(gestiune.magazine.get(o).V.get(k).V.get(l).getProdus().getDenumire().equals(gestiune.produse.get(n).getDenumire()) && gestiune.magazine.get(o).V.get(k).V.get(l).getProdus().getTaraOrigine().equals(tari.get(m)))
									element = gestiune.produse.get(n).getDenumire() + " " + tari.get(m);
									
					if(element != null)
						list.addElement(element);
				
				}
				Jlist.setModel(list);
				
			}
			
		});
		tara.setBounds(120, 30, 160, 20);
		frame.getContentPane().add(tara);
		
		stergere = new JTextField();
		stergere.setBounds(40, 60, 120, 20);
		frame.getContentPane().add(stergere);
		stergere.setColumns(10);
		
		JButton Stergere = new JButton("Stergeti produsul");
		Stergere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String produs = stergere.getText();
				int k = 0;
				int l = gestiune.produse.size();
				for(int n = 0; n < gestiune.produse.size(); n++)
					if(gestiune.produse.get(n).getDenumire().equals(produs)) {
						
						gestiune.produse.remove(n);
						n = n - 1;
					
					}
					else
						k = k + 1;
				if(k != l){
				for(int n = 0; n < gestiune.magazine.size(); n++)
						for(int m = 0; m < gestiune.magazine.get(n).V.size(); m++)
							for(int o = 0; o < gestiune.magazine.get(n).V.get(m).V.size(); o++)
								if(gestiune.magazine.get(n).V.get(m).V.get(o).getProdus().getDenumire().equals(produs))
									gestiune.magazine.get(n).V.get(m).V.remove(o);
				File produse = new File("new_produse.txt");
				File old = new File("produse.txt");
				Scanner scanner = null;
				try{
					
					scanner = new Scanner(old);
				
				}catch(FileNotFoundException exception){
					
					exception.printStackTrace();
				
				}
				PrintWriter writer = null;
				try {
					
					writer = new PrintWriter(produse, "UTF-8");
				
				} catch (FileNotFoundException exception){
				
					exception.printStackTrace();
				
				} catch (UnsupportedEncodingException exception){
					
					exception.printStackTrace();
				
				}
				String line = scanner.nextLine();
				writer.println(line);
				do {
					
					line = scanner.nextLine();
					if(!line.contains(produs))
						writer.println(line);
							
				}while(scanner.hasNextLine());
				writer.close();
				scanner.close();
				old.delete();
				old = new File("produse.txt");
				produse.renameTo(old);
			
				produse = new File("new_facturi.txt");
				old = new File("facturi.txt");
				try{
					
					scanner = new Scanner(old);
				
				}catch(FileNotFoundException exception){
					
					exception.printStackTrace();
				
				}
				try {
					
					writer = new PrintWriter(produse, "UTF-8");
				
				} catch (FileNotFoundException exception){
				
					exception.printStackTrace();
				
				} catch (UnsupportedEncodingException exception){
					
					exception.printStackTrace();
				
				}
				line = scanner.nextLine();
				writer.println(line);
				do {
					
					line = scanner.nextLine();
					if(!line.contains(produs))
						writer.println(line);
							
				}while(scanner.hasNextLine());
				writer.close();
				scanner.close();
				old.delete();
				old = new File("facturi.txt");
				produse.renameTo(old);
				
				}
				else
					JOptionPane.showMessageDialog(frame, "Produsul: " + produs + " nu exista!");
			
			}
				
		});
		Stergere.setBounds(180, 60, 180, 20);
		frame.getContentPane().add(Stergere);
		
		adaugare = new JTextField();
		adaugare.setBounds(40, 90, 120, 20);
		frame.getContentPane().add(adaugare);
		adaugare.setColumns(10);
		
		JLabel Denumire = new JLabel("Denumire");
		Denumire.setBounds(60, 120, 80, 10);
		frame.getContentPane().add(Denumire);
		
		categorie = new JTextField();
		categorie.setBounds(40, 140, 120, 20);
		frame.getContentPane().add(categorie);
		categorie.setColumns(10);
		
		JLabel Categorie = new JLabel("Categorie");
		Categorie.setBounds(60, 170, 80, 20);
		frame.getContentPane().add(Categorie);
		
		pret = new JTextField();
		pret.setBounds(40, 200, 120, 20);
		frame.getContentPane().add(pret);
		pret.setColumns(10);
		
		JLabel Pret = new JLabel("Pret in functe de tara:");
		Pret.setBounds(40, 230, 160, 10);
		frame.getContentPane().add(Pret);
		
		JTextArea Tara = new JTextArea();
		Tara.setBounds(210, 230, 20, 20);
		T.add(0);
		Tara.setText(tari.get(T.get(0)));
		frame.getContentPane().add(Tara);
		
		JButton Adaugare = new JButton("Adaugati produsul");
		Adaugare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int o = 0;
				int n = T.size();
				if(n == tari.size())
					o = 1;
				n = T.size() % tari.size();
				Tara.setText(tari.get(n));
				Tara.requestFocus();
				T.add(n);
				String denumire = null;
				if(!adaugare.getText().isEmpty())
						denumire = adaugare.getText();
				String c = null;
				if(!categorie.getText().isEmpty())
					c = categorie.getText();
				double p = 0;
				if(!pret.getText().isEmpty())
					p = Double.parseDouble(pret.getText());
				String eroare = "";
				if(denumire == null)
					eroare = eroare + "Dati numele produsului!\n";
				if(c == null)
					eroare = eroare + "Dati categoria din care face parte produsul!\n";
				if(p == 0)
					eroare = eroare + "Dati pretul produsului pentru tara " + tari.get(n) + "!\n";
				if(eroare == "") {
				
					int k = 0;
					for(int m = 0; m < gestiune.produse.size(); m++)
						if(denumire.equals(gestiune.produse.get(m).getDenumire()))
							k = k + 1;
					if(k == 3) 
							JOptionPane.showMessageDialog(frame, "Produsul: " + denumire + " exista deja!");
					else {
						
						Produs produs = new Produs();
						produs.setDenumire(denumire);
						produs.setCategorie(c);
						produs.setPret(p);
						produs.setTarOrigine(tari.get(n));
						gestiune.produse.add(produs);
							
							if(n != 0) 
								
									JOptionPane.showMessageDialog(frame, "Intoduceti pretul pentru tara: " + tari.get(n));
					
							else{
								
								FileWriter writer = null;
								try {
									writer = new FileWriter("produse.txt", true);
								} catch (IOException exception) {
									
									exception.printStackTrace();
								}
								String linie = "\n" + produs.getDenumire() + " " + produs.getCategorie();
								for(int m = 0; m < gestiune.produse.size(); m++)
									if(gestiune.produse.get(m).getDenumire().equals(produs.getDenumire()))
										linie = linie + " " + gestiune.produse.get(m).getPret();
								try {
									
									writer.write(linie);
								
								} catch (IOException exception) {
									
									exception.printStackTrace();
								
								}
								try {
								
									writer.close();
								
								} catch (IOException e1) {
									
									e1.printStackTrace();
								
								}
								if(o == 1)
									JOptionPane.showMessageDialog(frame, "Produsul a fost adaugat");
								
								}	
						
					}
					
				}
				else
					JOptionPane.showMessageDialog(frame, eroare);
		
			}
		
		});
		Adaugare.setBounds(180, 90, 180, 20);
		frame.getContentPane().add(Adaugare);
	}
}
