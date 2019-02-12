import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;


interface IMagazin{
	
	double getTotalFaraTaxe();
	double getTotalCuTaxe();
	double getTotalCuTaxeScutite();
	double getTotalTaraFaraTaxe(String tara);
	double getTotalTaraCuTaxe(String tara);
	double getTotalTaraCuTaxeScutite(String tara);

}

class Produs implements Comparable<Produs>{

	private String denumire;
	private String categorie;
	private String taraOrigine;
	private double pret;
	
	void setDenumire(String denumire){
		
		this.denumire = denumire;
		
	}
	
	String getDenumire(){
		
		String S = this.denumire;
		return S;
		
	}
	
	void setCategorie(String categorie){
		
		this.categorie = categorie;
		
	}
	
	String getCategorie(){
		
		String S = this.categorie;
		return S;
		
	}
	
	void setTarOrigine(String taraOrigine){
		
		this.taraOrigine = taraOrigine;
		
	}
	
	String getTaraOrigine(){
		
		String S = this.taraOrigine;
		return S;
		
	}
	
	void setPret(double pret){
		
		this.pret = pret;
		
	}
	
	double getPret(){
		
		double pret = this.pret;
		return pret;
		
	}

	@Override
	public int compareTo(Produs produs) {
		
		return this.getDenumire().compareTo(produs.getDenumire());
	}
	
}

class ProdusComandat{
	
	private Produs produs;
	private double taxa;
	private int cantitate;
	
	void setProdus(Produs produs){
		
		this.produs = produs;
		
	}
	
	Produs getProdus(){
		
		Produs produs = this.produs;
		return produs;
		
	}
	
	void setTaxa(double taxa){
		
		this.taxa = taxa;
		
	}
	
	double getTaxa(){
		
		double taxa = this.taxa;
		return taxa * this.getProdus().getPret() / 100;
		
	}
	
	void setCantitate(int cantitate){
		
		this.cantitate = cantitate;
		
	}
	
	int getCantitate(){
		
		int cantitate = this.cantitate;
		return cantitate;
		
	}
	
}

class Factura implements Comparable<Factura>{ 
	
	String denumire;
	Vector<ProdusComandat> V;
	
	double getTotalFaraTaxe(){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)		
			total = total + this.V.get(n).getCantitate() * this.V.get(n).getProdus().getPret();
		return total;
		
	}
	
	double getTotalCuTaxe(){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)
			total = total + this.V.get(n).getCantitate() * (this.V.get(n).getProdus().getPret() + this.V.get(n).getTaxa());
		return total;
		
	}
	
	double getTaxe(){
		
		double taxe = 0;
		for(int n = 0; n < this.V.size(); n++)
			taxe = taxe + this.V.get(n).getCantitate() * this.V.get(n).getTaxa();
		return taxe;
		
	}
	
	double getTotalTaraFaraTaxe(String tara){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)		
			if(this.V.get(n).getProdus().getTaraOrigine().equals(tara))
				total = total + this.V.get(n).getCantitate() * this.V.get(n).getProdus().getPret();
		return total;
		
	}
	
	double getTotalTaraCuTaxe(String tara){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)		
			if(this.V.get(n).getProdus().getTaraOrigine().equals(tara))
				total = total + this.V.get(n).getCantitate() * (this.V.get(n).getProdus().getPret() + this.V.get(n).getTaxa());
		return total;
		
	}
	
	double getTaxeTara(String tara){
		
		double taxe = 0;
		for(int n = 0; n < this.V.size(); n++)
			if(this.V.get(n).getProdus().getTaraOrigine().equals(tara))
				taxe = taxe + this.V.get(n).getCantitate() * this.V.get(n).getTaxa();
		return taxe;
		
	}
	
	public int compareTo(Factura factura) {

		return (int)(this.getTotalCuTaxe() - factura.getTotalCuTaxe());

   }
	
}

abstract class Magazin implements IMagazin, Comparable<Magazin>{
	
	String nume;
	String tip;
	Vector<Factura> V;
	
	public double getTotalFaraTaxe(){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)
			total = total + this.V.get(n).getTotalFaraTaxe();
		return total;
		
	}
	
   public double getTotalCuTaxe(){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)
			total = total + this.V.get(n).getTotalCuTaxe();
		return total;
		
	}
	
   public double getTotalCuTaxeScutite(){
	   
	   double total = this.getTotalCuTaxe();
	   	if(this.tip.equals("MiniMarket") )
	   		total = total  * (1 - ((MiniMarket)this).calculScutiriTaxe());
		if(this.tip.equals("MediumMarket"))
			total = total  * (1 - ((MediumMarket)this).calculScutiriTaxe());
		if(this.tip.equals("HyperMarket"))
			total = total  * (1 - ((HyperMarket)this).calculScutiriTaxe());
	   return total;
	   
   }
   
   public double getTotalTaraFaraTaxe(String tara){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)
			total = total + this.V.get(n).getTotalTaraFaraTaxe(tara);
		return total;
		
   }
   
   public double getTotalTaraCuTaxe(String tara){
		
		double total = 0;
		for(int n = 0; n < this.V.size(); n++)
			total = total + this.V.get(n).getTotalTaraCuTaxe(tara);
		return total;
		
   }
   
   public double getTotalTaraCuTaxeScutite(String tara){

	    double total = this.getTotalTaraCuTaxe(tara);
	   	if(this.tip.equals("MiniMarket") )
	   		total = total  * (1 - ((MiniMarket)this).calculScutiriTaxe());
		if(this.tip.equals("MediumMarket"))
			total = total  * (1 - ((MediumMarket)this).calculScutiriTaxe());
		if(this.tip.equals("HyperMarket"))
			total = total  * (1 - ((HyperMarket)this).calculScutiriTaxe());
	   return total;
	   
   }
   
   public int compareTo(Magazin magazin) {

		return (int)(this.getTotalCuTaxe() - magazin.getTotalCuTaxe());

   }
   
}

class MagazinFactory{
		
	public static Magazin magazin(String tip) {
	        
		Magazin magazin = null;
		if(tip.equals("MiniMarket")) {
				
			magazin = new MiniMarket();
			magazin.tip = "MiniMarket";

		}
		if(tip.equals("MediumMarket")) {
			
			magazin = new MediumMarket();
			magazin.tip = "MediumMarket";
			
		}
		if(tip.equals("HyperMarket")) {
				
			magazin = new HyperMarket();	
			magazin.tip = "HyperMarket";
			
		}
		return magazin;
		    
	}
		
}

	class MiniMarket extends Magazin{
		
		public double calculScutiriTaxe(){
			
			double scutiri = 0;
			Vector<String> tari = new Vector<String>();
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
			scanner.close();
			Collections.sort(tari);
			for(int n = 0; n < tari.size(); n++)
				if(this.getTotalTaraCuTaxe(tari.get(n)) > 0.5 * this.getTotalCuTaxe())
					scutiri = 0.1;
			return scutiri;
			
		}
		
	}
	
	class MediumMarket extends Magazin{
		
		public double calculScutiriTaxe(){
			
			double scutiri = 0;
			Vector<String> categorii = new Vector<String>();
			Scanner scanner = null;
			try{
				
				scanner = new Scanner(new File("taxe.txt"));
				
			}catch(FileNotFoundException exception){
				
				exception.printStackTrace();
			
			}
			String line = scanner.nextLine();
			do{
				
				line = scanner.nextLine();
				StringTokenizer token = new StringTokenizer(line," ");
				categorii.add(token.nextToken());
					
			}while(scanner.hasNextLine());
			scanner.close();
			Collections.sort(categorii);
			for(int n = 0; n < categorii.size(); n++) {
				
				double total = 0;
				for(int m = 0; m < this.V.size(); m++) 
					for(int o = 0; o < this.V.get(m).V.size(); o++)
						if(this.V.get(m).V.get(o).getProdus().getCategorie().equals(categorii.get(n)))
								total = total + (this.V.get(m).V.get(o).getProdus().getPret() + this.V.get(m).V.get(o).getTaxa()) * this.V.get(m).V.get(o).getCantitate();
				if(total > 0.5 * this.getTotalCuTaxe())
					scutiri = 0.05;
				
			}
			return scutiri;
			
		}
		
	}
	
	class HyperMarket extends Magazin{
		
		public double calculScutiriTaxe(){
			
			double scutiri = 0;
			for(int n = 0; n < this.V.size(); n++)
				if(this.V.get(n).getTotalCuTaxe() > 0.1 * this.getTotalCuTaxe())
					scutiri = 0.01;
			return scutiri;
			
		}
		
	}

	
class Gestiune{
	
	private static Gestiune instance = null;
	LinkedList<Produs> produse;
	LinkedList<Magazin> magazine;
	TreeMap<String, TreeMap<String, Double>> taxe;
	
	private Gestiune(){}
	
	public static Gestiune getInstance(){
	
		if(instance == null) {
		
			instance = new Gestiune();
		
		}
		return instance;
	
	}
	
}

public class Tema{
	
	public static void main(String[] args) {
		
		Vector<String> fisiere = new Vector<String>();
		if(args.length == 0){
		
			fisiere.add("taxe.txt");
			fisiere.add("produse.txt");
			fisiere.add("facturi.txt");
			
		}
		else{
			
			fisiere.add(args[0]);
			fisiere.add(args[1]);
			fisiere.add(args[2]);
			
		}
		Gestiune gestiune = Gestiune.getInstance();
		gestiune.produse =  new LinkedList<Produs>();
		gestiune.magazine = new LinkedList<Magazin>();
		gestiune.taxe = new TreeMap<String, TreeMap<String, Double>>();
		Vector<String> tari = new Vector<String>();
		Vector<String> categorii = new Vector<String>();
		String tip = null;
		Scanner scanner = null;
		try{
			
			scanner = new Scanner(new File(fisiere.get(0)));
			
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
				
				scanner = new Scanner(new File(fisiere.get(0)));
				
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
			
			scanner = new Scanner(new File(fisiere.get(1)));
		
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
			
			scanner = new Scanner(new File(fisiere.get(2)));
			
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
		PrintWriter writer = null;
		try {
			
			writer = new PrintWriter("out.txt", "UTF-8");
		
		} catch (FileNotFoundException exception){
		
			exception.printStackTrace();
		
		} catch (UnsupportedEncodingException exception){
			
			exception.printStackTrace();
		
		}
		Collections.sort(gestiune.magazine);
		for(n = 1; n <= 3; n++){
		
			if(n == 1){
				
				tip = "MiniMarket";
				writer.println(tip);
			
			}
			if(n == 2){
				
				tip = "MediumMarket";
				writer.println(tip);
			
			}
			if(n == 3){
				
				tip = "HyperMarket";
				writer.println(tip);
			
			}
			Collections.sort(tari);
			Collections.sort(categorii);
			for(m = 0; m < gestiune.magazine.size(); m++)
				if(gestiune.magazine.get(m).tip.equals(tip)) {
					
					writer.println(gestiune.magazine.get(m).nume + "\n");
					writer.println("Total " + gestiune.magazine.get(m).getTotalFaraTaxe() + " " + gestiune.magazine.get(m).getTotalCuTaxe() + " " + gestiune.magazine.get(m).getTotalCuTaxeScutite() + "\n");
					writer.println("Tara");
					for(o = 0; o < tari.size(); o++)
						writer.println(tari.get(o) + " " + gestiune.magazine.get(m).getTotalTaraFaraTaxe(tari.get(o)) + " " + gestiune.magazine.get(m).getTotalTaraCuTaxe(tari.get(o)) + " " + gestiune.magazine.get(m).getTotalTaraCuTaxeScutite(tari.get(o)));
					Collections.sort(gestiune.magazine.get(m).V);
					for(o = 0; o < gestiune.magazine.get(m).V.size(); o++){
						
						writer.println("\n" + gestiune.magazine.get(m).V.get(o).denumire + "\n");
						writer.println("Total " + gestiune.magazine.get(m).V.get(o).getTotalFaraTaxe() + " " + gestiune.magazine.get(m).V.get(o).getTotalCuTaxe() + "\n");
						writer.println("Tara");
						for(int k = 0; k < tari.size(); k++)
							writer.println(tari.get(k) + " " + gestiune.magazine.get(m).V.get(o).getTotalTaraFaraTaxe(tari.get(k)) + " " + gestiune.magazine.get(m).V.get(o).getTotalTaraCuTaxe(tari.get(k)));
	
					}
					writer.println("");
							
				}
			
		}
		writer.close();
		
	}
	
}

