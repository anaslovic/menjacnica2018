package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	public static MenjacnicaInterface menjacnica = new Menjacnica();
	
	public static MenjacnicaGUI mg;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.mg= new MenjacnicaGUI();
					mg.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(mg.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mg.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(mg.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				GUIKontroler.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mg.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziSveValute() {
		MenjacnicaTableModel model = (MenjacnicaTableModel)(mg.getTable().getModel());
		model.staviSveValuteUModel(menjacnica.vratiKursnuListu());
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(mg.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI() {
		
		if (mg.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(mg.getTable().getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(
					model.vratiValutu(mg.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(mg.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (mg.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(mg.getTable().getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(model.vratiValutu(mg.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(mg.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void unesiKurs(String naziv,String skraceniNaziv,int sifra,
			double prodajni, double kupovni, double srednji,JPanel contentPane) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			GUIKontroler.prikaziSveValute();
			
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(Valuta valuta,JPanel contentPane) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			GUIKontroler.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void izvrsiZamenu(Valuta valuta,boolean selected,double iznos,
			JPanel contentPane,JTextField konacan){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,
							selected, iznos);
			konacan.setText(konacniIznos+"");
			
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
}
