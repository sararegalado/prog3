package cap06.practica6;

import java.io.*;
import java.util.*;


/** Permite gestionar datasets de municipios. Cada objeto contiene un dataset de 'n' municipios
 */
public class DataSetMunicipios {
	private int poblacionTotal = 0;
	
	//Crear Array de municipios
	private ArrayList<Municipio> listaMunicipios = new ArrayList<Municipio>();
	
	
	
	public DataSetMunicipios( String nombreFichero ) throws IOException {
		File ficMunicipios = new File( nombreFichero );
		Scanner lecturaFic = null;
		if (ficMunicipios.exists()) {
			lecturaFic = new Scanner( ficMunicipios );
		} else {
			lecturaFic = new Scanner( DataSetMunicipios.class.getResourceAsStream( nombreFichero ) );
		}
		int numLinea = 0;
		while (lecturaFic.hasNextLine()) {
			numLinea++;
			String linea = lecturaFic.nextLine();
			String[] partes = linea.split( "\t" );
			try {
				int codigo = Integer.parseInt( partes[0] );
				String nombre = partes[1];
				int habitantes = Integer.parseInt( partes[2] );
				poblacionTotal = poblacionTotal + habitantes;
				String provincia = partes[3];
				String comunidad = partes[4];
				Municipio muni = new Municipio( codigo, nombre, habitantes, provincia, comunidad );
				listaMunicipios.add( muni );
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.err.println( "Error en lectura de línea " + numLinea );
			}
		}
	}
	
	
	
	
	
	
	
	public void setPoblacionTotal(int poblacionTotal) {
		this.poblacionTotal = poblacionTotal;
	}







	public void setListaMunicipios(ArrayList<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}
	
	
	public ArrayList<Municipio> getListaMunicipios() {
		return listaMunicipios;
	}
	
	
	/** Añade un municipio al final
	 * @param muni	Municipio a añadir
	 */
	public void anyadir( Municipio muni ) {
		listaMunicipios.add( muni );
	}
	
	public HashMap<String,ArrayList<String>> ComunidadesProvincias(){
		HashMap<String,ArrayList<String>> mapa = new HashMap<String,ArrayList<String>>();
		for (Municipio m : listaMunicipios) {
			if (!mapa.containsKey(m.getAutonomia())) {
				mapa.put(m.getAutonomia(), new ArrayList<String>());
				mapa.get(m.getAutonomia()).add(m.getProvincia());
			
			} else {
				if (! mapa.get(m.getAutonomia()).contains(m.getProvincia())) {
					mapa.get(m.getAutonomia()).add(m.getProvincia());
					Collections.sort(mapa.get(m.getAutonomia()));
				}
			}
		}
		return mapa;
	}
	
	public HashMap<String,Municipio> BucarNombresMunicipio(){
		HashMap<String,Municipio> mapa = new HashMap<String,Municipio>();
		for (Municipio m : listaMunicipios) {
			mapa.put(m.getNombre(), m);
		}
		return mapa;
	}
	
	public HashMap<String, ArrayList<String>> MunicipiosPorProvincia(){
		HashMap<String,ArrayList<String>> mapa = new HashMap<String,ArrayList<String>>();
		for (Municipio m : listaMunicipios) {
			String mun = m.getNombre();
			String prov = m.getProvincia();
			
			if (!mapa.containsKey(prov)) {
				mapa.put(prov, new ArrayList<String>());
				mapa.get(prov).add(mun);
			} else {
				mapa.get(prov).add(mun);
				Collections.sort(mapa.get(prov));
			}
		}
		return mapa;
	}
	
	public int HabitantesPorProv(String provincia) {
		int total = 0;
		for (String nombre : this.MunicipiosPorProvincia().get(provincia)) {
			Municipio muni = this.BucarNombresMunicipio().get(nombre);
			total = total + muni.getHabitantes();
		}
		return total;			
	}
	
	public int getPoblacionTotal() {
		return poblacionTotal;
		
	}
	
	

	
		
	
}
