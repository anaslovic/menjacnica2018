package menjacnica.sistemskeoperacije;

import java.io.*;
import java.util.LinkedList;
import menjacnica.Valuta;

public class SOUcitajIzFajla {
	public static LinkedList<Valuta> izvrsi(String putanja) {
		try(ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(putanja)));){
			
			return (LinkedList<Valuta>)(in.readObject());

		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
