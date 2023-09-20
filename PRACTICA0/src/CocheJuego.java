
public class CocheJuego extends Coche{
	private JLabelCoche labelCoche;
	
	public CocheJuego() {
        labelCoche = new JLabelCoche();
    }
	
    public void setPosX(double posX) {
        super.setPosX(posX);
        labelCoche.setLocation((int) posX, (int) getPosY());
    }

    public void setPosY(double posY) {
        super.setPosY(posY);
        labelCoche.setLocation((int) getPosX(), (int) posY);
    }

    public JLabelCoche getLabelCoche() {
        return labelCoche;
    }
  
    
    @Override
    public void setDireccionActual(final double dir) {
    	super.setDireccionActual(dir);
        this.labelCoche.setGiro(this.direccionActual);
        }
}


