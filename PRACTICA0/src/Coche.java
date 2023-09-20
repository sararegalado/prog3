
public class Coche {
	//Atributos
	private double velocidadActual; // Velocidad en pixels/segundo
	protected double direccionActual; // Dirección en la que estoy mirando en grados (de 0 a 360)
	protected double posX; // Posición en X (horizontal)
	protected double posY; // Posición en Y (vertical)
	private String piloto; // Nombre de piloto
	
	//Contructor vacio
	public Coche() {
		super();
		this.velocidadActual = 0;
		this.direccionActual = 0;
		this.posX = 0;
		this.posY = 0;
		this.piloto = "";
	}
	
	//Getters & Setters
	public double getVelocidadActual() {
		return velocidadActual;
	}

	public void setVelocidadActual(double velocidadActual) {
		this.velocidadActual = velocidadActual;
	}

	public double getDireccionActual() {
		return direccionActual;
	}

	public void setDireccionActual(double direccionActual) {
		if (direccionActual < 0.0) {
			direccionActual += 360.0;
        }
        if (direccionActual > 360.0) {
        	direccionActual -= 360.0;
        }
        this.direccionActual = direccionActual;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
    public void setPosicion(final double posX, final double posY) {
        this.setPosX(posX);
        this.setPosY(posY);
    }
    

	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	//METODOS
    public void acelera(final double aceleracion) {
        this.velocidadActual += aceleracion;
    }
    
    public void gira(final double giro) {
        this.setDireccionActual(this.direccionActual + giro);
    }
    
    public void mueve(final double tiempoDeMovimiento) {
        this.setPosX(this.posX + this.velocidadActual * Math.cos(this.direccionActual / 180.0 * 3.141592653589793) * tiempoDeMovimiento);
        this.setPosY(this.posY + this.velocidadActual * -Math.sin(this.direccionActual / 180.0 * 3.141592653589793) * tiempoDeMovimiento);
    }

	@Override
	public String toString() {
		return "Coche [miVelocidad=" + velocidadActual + ", miDireccionActual=" + direccionActual + ", posX=" + posX
				+ ", posY=" + posY + ", piloto=" + piloto + "]";
	}
	
	
	
}
