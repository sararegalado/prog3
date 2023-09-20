
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class VentanaJuego extends JFrame {
	private JPanel panelPrincipal;
	private JPanel panelBotones;
	private JButton boton1;
	private JButton boton2;
	private JButton boton3;
	private JButton boton4;
	
	protected MiRunnable miHilo;
	
	
	private CocheJuego miCoche;
	
	//inicializar la ventana
	public VentanaJuego() {
		//Componentes de la ventana
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.WHITE);
		panelPrincipal.setLayout(null);
		
		panelBotones = new JPanel(new GridLayout(1,4)); // 1 fila y 4 columnas
		boton1 = new JButton("Acelera");
		boton2 = new JButton("Frena");
		boton3 = new JButton("Gira izq.");
		boton4 = new JButton("Gira dcha.");
		//añadir los botones al panel de botones
		panelBotones.add(boton1);
		panelBotones.add(boton2);
		panelBotones.add(boton3);
		panelBotones.add(boton4);
		
	
		
		miCoche = new CocheJuego();
		
		//Acciones de los botones
		boton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                if (VentanaJuego.this.miCoche.getVelocidadActual() == 0.0) {
                    VentanaJuego.this.miCoche.acelera(5.0);
                }
                else {
                    VentanaJuego.this.miCoche.acelera(5.0);
                }
                System.out.println("Nueva velocidad de coche: " + VentanaJuego.this.miCoche.getVelocidadActual());
			}
		});
		
		boton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        VentanaJuego.this.miCoche.acelera(-5.0);
                System.out.println("Nueva velocidad de coche: " + VentanaJuego.this.miCoche.getVelocidadActual());
			}
		});
		
		boton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 VentanaJuego.this.miCoche.gira(10.0);
				 System.out.println("Nueva direccion del coche: " + VentanaJuego.this.miCoche.getDireccionActual());
				
			}
		});
		
		boton4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaJuego.this.miCoche.gira(-10.0);
                System.out.println("Nueva direccion del coche: " + VentanaJuego.this.miCoche.getDireccionActual());
		    }
				
		});
		this.panelPrincipal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 38: {
                        VentanaJuego.this.miCoche.acelera(5.0);
                        break;
                    }
                    case 40: {
                        VentanaJuego.this.miCoche.acelera(-5.0);
                        break;
                    }
                    case 37: {
                        VentanaJuego.this.miCoche.gira(10.0);
                        break;
                    }
                    case 39: {
                        VentanaJuego.this.miCoche.gira(-10.0);
                        break;
                    }
                }
            }
        });
			
		//Caracteristicas de la ventana
		this.setTitle("Juego");
		this.setSize(600, 400);
		
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		
		
		this.setVisible(true); 
		this.setLocationRelativeTo(null); //aparece centrada
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	class MiRunnable implements Runnable{
		boolean sigo;
	        
	        MiRunnable() {
	            this.sigo = true;
	        }
	        
	        @Override
	        public void run() {
	            while (this.sigo) {
	                VentanaJuego.this.miCoche.mueve(0.04);
	                if (VentanaJuego.this.miCoche.getPosX() < -50.0 || VentanaJuego.this.miCoche.getPosX() > VentanaJuego.this.panelPrincipal.getWidth() - 50) {
	                    System.out.println("Choca X");
	                    double dir = VentanaJuego.this.miCoche.getDireccionActual();
	                    dir = 180.0 - dir;
	                    if (dir < 0.0) {
	                        dir += 360.0;
	                    }
	                    VentanaJuego.this.miCoche.setDireccionActual(dir);
	                }
	                if (VentanaJuego.this.miCoche.getPosY() < -50.0 || VentanaJuego.this.miCoche.getPosY() > VentanaJuego.this.panelPrincipal.getHeight() - 50) {
	                    System.out.println("Choca Y");
	                    double dir = VentanaJuego.this.miCoche.getDireccionActual();
	                    dir = 360.0 - dir;
	                    VentanaJuego.this.miCoche.setDireccionActual(dir);
	                }
	                try {
	                    Thread.sleep(40L);
	                }
	                catch (Exception ex) {}
	            }
	        }
	}
	        
    public void creaCoche(final int posX, final int posY) {
        (this.miCoche = new CocheJuego()).setPosicion(posX, posY);
        this.panelPrincipal.add(this.miCoche.getLabelCoche());
    }		

	public static void main(String[] args) {
		VentanaJuego v = new VentanaJuego();
		v.setVisible(true);
		v.creaCoche(150, 100);
        v.setVisible(true);
        v.miCoche.setPiloto("Janire Diaz");
        v.miHilo = v.new MiRunnable();
        final Thread nuevoHilo = new Thread(v.miHilo);
        nuevoHilo.start();
        
        
      
        
	}
}
