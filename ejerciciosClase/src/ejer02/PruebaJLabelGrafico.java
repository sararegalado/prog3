package ejer02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PruebaJLabelGrafico extends JFrame{
	public static void main(String[] args) {
		PruebaJLabelGrafico vent = new PruebaJLabelGrafico();
		vent.setVisible(true);
		
		
	}
	
	// no static
	private JPanel pJuego;
	private JLabel coche;
	private JLabel coche2;

	
	

	public PruebaJLabelGrafico() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //SE CIERRA EL PROGRAMA
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //SE CIERRA LA VENTANA SOLO
		setSize(600,400);
		setLocation(500,0);
		
		//Creamos contenedores
		pJuego = new JPanel();
		
		//Creamos componentes
		coche = new JLabel(new ImageIcon("coche.png"));
		
		//coche2 = new JLabel(new ImageIcon( PruebaJLabelGrafico.class.getResource("coche.png")));
		
		//Asociamos componentes a contenedores
		pJuego.add(coche);
		getContentPane().add(pJuego,BorderLayout.CENTER);
		
		
		//Gestion de eventos
		
		
		
		
	}

}
