import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Meniu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Meniu window = new Meniu();
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
	public Meniu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton fisiere = new JButton("Incarcare fisiere");
		fisiere.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				
				IncarcareFisiere incarcare = new IncarcareFisiere();
				incarcare.main(null);
					
			}
		});
		
		fisiere.setBounds(120,0,160,20);
		frame.getContentPane().add(fisiere);
		
		JButton afisareAdministrare = new JButton("Afisare si administrare a produselor ");
		afisareAdministrare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AfisareAdministrare afisareAdiministrare = new AfisareAdministrare();
				afisareAdiministrare.main(null);
				
			}
		});
		afisareAdministrare.setBounds(50, 40, 300, 20);
		frame.getContentPane().add(afisareAdministrare);
		
		
	}

}
