package ejer02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Ejercicio de hilos con ventanas. Programa esta clase para que se cree una ventana
 * que pida un dato de texto al usuario y un botón de confirmar para que se confirme.
 * Haz que al pulsar el botón de confirmación se llame al procesoConfirmar()
 * que simula un proceso de almacenamiento externo que tarda unos segundos.
 * Observa que hasta que ocurre esta confirmación la ventana no responde.
 * 1. Arréglalo para que la ventana no se quede "frita" hasta que se acabe de confirmar.
 * 2. Haz que el botón de "confirmar" no se pueda pulsar dos veces mientras el proceso
 * de confirmación se esté realizando
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class VentanaConfirmacionLenta {

		private static Random r = new Random();
	// Este método simula un proceso que tarda un tiempo en hacerse (entre 5 y 10 segundos)
	private static void procesoConfirmar() {
		try {
			Thread.sleep( 5000 + 1000*r.nextInt(6) );
		} catch (InterruptedException e) {}
	}
	
	public static void main(String[] args) {
		// TODO Desarrollar la clase de acuerdo a los comentarios de la cabecera
		// Comentario de prueba
		verHilos("antes");
		JFrame vent = new JFrame();
		vent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		vent.setSize(200,100);
		vent.setLocation(2000,0);
		
		JPanel pcentral = new JPanel();
		JTextField tfUsuario = new JTextField("Usuario:");
		pcentral.add(tfUsuario);
		vent.add(pcentral);
		
		JButton bAceptar = new JButton("Aceptar");
		vent.add(bAceptar,BorderLayout.SOUTH);
		
		bAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pulsando boton");
				bAceptar.setBackground(Color.RED);
				//Trampa: variable que uses aqui las copio en la clase interna
				//JButon btnaceptar =bAceptar clase externa
				procesoConfirmar();
			}
		});
		
		
		
		
		
		vent.setVisible(true);
		System.out.println("Fin");
		verHilos("Despues ventana");
		
	}
	public static void verHilos(String mensaje) {
		System.out.println("mensaje");
		for (Thread hilo: Thread.getAllStackTraces().keySet()) {
			System.out.println(" "+hilo.getName()+" "+ hilo.isDaemon());
		}
		
	}

}
