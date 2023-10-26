package cap06.practica6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.xml.sax.helpers.ParserAdapter;




public class VentanaMunicipios extends JFrame {
	
	DataSetMunicipios datosMunis;
	
//	Paneles
	private JPanel pSuperior;
	private JPanel pInferior;
	private JPanel pIzquierda;
	private JPanel pDerecha;
	private JPanel pCentro;
	
	private JLabel lblMensaje;
	
	private DefaultMutableTreeNode nodoRaiz;
	private DefaultTreeModel modeloArbol;
	private JTree arbol = new JTree();
	
	private DefaultTableModel modeloTabla;
	private JTable tablaDatos;
	
	private JScrollPane scrollPaneArbol;
	private JScrollPane scrollPaneTabla;
	
	//Pintado tabla
	private boolean coloreadoActivo = false;
	
	private JButton bAnyadir;
	private JButton bBorrar;
	private JButton bOrden;
	
	private boolean ordenarPorNombre = true;
	
	
	

	public VentanaMunicipios() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
		
		pSuperior = new JPanel();
		pInferior = new JPanel();
		pIzquierda = new JPanel();
		pDerecha = new JPanel();
		pCentro = new JPanel();
		
		pSuperior.setBackground(Color.CYAN);
		pInferior.setBackground(Color.RED);
		pIzquierda.setBackground(Color.GREEN);
		pDerecha.setBackground(Color.ORANGE);
//		pCentro.setBackground(Color.PINK);
		
		pSuperior.setPreferredSize(new Dimension(800,70));
		pInferior.setPreferredSize(new Dimension(800,70));
		pIzquierda.setPreferredSize(new Dimension(190,70));
		pDerecha.setPreferredSize(new Dimension(140,70));
		
		lblMensaje = new JLabel("Mensaje");
		pSuperior.add(lblMensaje);
		
		nodoRaiz = new DefaultMutableTreeNode("Municipios");
		modeloArbol = new DefaultTreeModel(nodoRaiz);
		arbol = new JTree(modeloArbol);
		
		
		modeloTabla = new DefaultTableModel();
	
//		Columnas de la tabla, editar el modelo
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Nombre");
		modeloTabla.addColumn("Habitantes");
		modeloTabla.addColumn("Población");
		

		tablaDatos = new JTable(modeloTabla);
		tablaDatos.setRowHeight(30);
		

		
		
//		Añadir renderer
		tablaDatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		
			private JProgressBar pb = new JProgressBar(50000,5000000);
			
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			if (column == 3) {
				
			int poblacion = Integer.parseInt(value.toString());
			System.out.println("Población: " + poblacion); 
	        pb.setValue(poblacion);
	       
	        int red = (int)Math.min(255,Math.max(0,((poblacion - 50000.0) / 4950000.0) * 255));
	        int green = (int)Math.min(255, Math.max(0, 255 - ((poblacion - 50000.0) / 4950000.0) * 255));
	        Color colorValor = new Color(red, green, 0);
	        
	        pb.setForeground(colorValor);
	        return pb;
			}
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return comp;
					
		}
		});
		
		
				
		scrollPaneArbol = new JScrollPane(arbol);
		scrollPaneTabla = new JScrollPane(tablaDatos);
		scrollPaneArbol.setPreferredSize(new Dimension(180,600));

		
		pIzquierda.add(scrollPaneArbol);
		pCentro.add(scrollPaneTabla);
		
		bAnyadir = new JButton("Añadir");
		bBorrar = new JButton("Borrar");
		bOrden = new JButton("Orden");
		
		pInferior.add(bAnyadir);
		pInferior.add(bBorrar);
		pInferior.add(bOrden);
		
		add(pSuperior, BorderLayout.NORTH);
		add(pInferior,BorderLayout.SOUTH);
		add(pIzquierda,BorderLayout.WEST);
		add(pDerecha,BorderLayout.EAST);
		add(pCentro, BorderLayout.CENTER);
		
		
		
//		Añadir escuchador al JTree para manejar seleccion ded prov.
		arbol.addTreeSelectionListener(new TreeSelectionListener() {
					
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode seleccionado = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
				
				if (seleccionado != null && seleccionado.getLevel()==2) {
					String provSeleccionada = seleccionado.getUserObject().toString();
					
//					Limpiar la tabla antes de meter datos de otras provincias
					while (modeloTabla.getRowCount() > 0) {
                        modeloTabla.removeRow(0);
                       
                    }
					
					for(Municipio m : datosMunis.getListaMunicipios()) {
						if(m.getProvincia().equals(provSeleccionada)) {
							modeloTabla.addRow(new Object[] {
								m.getCodigo(),
								m.getNombre(),
								m.getHabitantes(),
								m.getHabitantes()		
							});
						}
					}
				}
			}
		});
		
		
//		Añadir mouse listener a la tabla
		tablaDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(SwingUtilities.isRightMouseButton(e)) {
					int row = tablaDatos.rowAtPoint(e.getPoint());
					int col = tablaDatos.columnAtPoint(e.getPoint());
					
					if (row >= 0 && col >= 0) {
						int poblacion = Integer.parseInt(tablaDatos.getValueAt(row, 3).toString());
						
						if (poblacion <= 80000) {
							
							tablaDatos.setSelectionBackground(Color.GREEN);
							
			
							} else {
							tablaDatos.setSelectionBackground(Color.RED);
							
						}	
					}
				}
			}
		});
		
		//Añadir fila
		bAnyadir.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Object> fila = new Vector<Object>();
				fila.add(datosMunis.getListaMunicipios().size()+1); // Código
				fila.add("Nombre"); // Nombre
				fila.add(0); // Habitantes
				fila.add(0); // Provincia
				modeloTabla.addRow(fila);
				
				
				
				datosMunis.anyadir( new Municipio( datosMunis.getListaMunicipios().size()+1, "Nombre", 0, "Provincia", "Autonomía" ) );
					
					
				
			}
		});
		
		//Borrar fila
		bBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSel = tablaDatos.getSelectedRow();
		        if (filaSel >= 0) {
		            int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este municipio?", "Confirmación de borrado", JOptionPane.YES_NO_OPTION);
		            if (opcion == JOptionPane.YES_OPTION) {
		                // Eliminar el municipio de la tabla
		                modeloTabla.removeRow(filaSel);
		                
		                // Eliminar el municipio de los datos subyacentes
		                datosMunis.borraFila(filaSel); // Debes implementar el método eliminar en DataSetMunicipios
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecciona un municipio para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
		});
		
		//Boton orden
//		bOrden.addActionListener(new ActionListener() {
//		    @Override
//		    public void actionPerformed(ActionEvent e) {
//		    	if (ordenadoPorNombre) {
//		    		
//		    		
//		    	}
//		    
//		    
//		    
//		    
//		    }
//		});

		
		
		
		
	}
	
	public void setDatosArbol( DataSetMunicipios datosMunis ) {
		
//		Crear el JTree
		
		this.datosMunis = datosMunis;
		
//		Para que no se repitan las provincias
		Set<String> provincias = new HashSet<>();	
		
		for(Municipio municipio:datosMunis.getListaMunicipios()) {
			DefaultMutableTreeNode nodoAutonomia = null;
	        DefaultMutableTreeNode nodoProvincia = new DefaultMutableTreeNode(municipio.getProvincia());
		
	       
	     if (!provincias.contains(municipio.getProvincia())) {
		 for (int i = 0; i < nodoRaiz.getChildCount(); i++) {
             DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) nodoRaiz.getChildAt(i);
             if (nodo.getUserObject().toString().equals(municipio.getAutonomia())) {
                 nodoAutonomia = nodo;
                 break;
             }
         }

         if (nodoAutonomia == null) {
             nodoAutonomia = new DefaultMutableTreeNode(municipio.getAutonomia());
             nodoRaiz.add(nodoAutonomia);
         }

         nodoAutonomia.add(nodoProvincia);
         provincias.add(municipio.getProvincia());
        }
		} 
		
		

	
	}
	
	
	

	
	

	
	
	
	
		

}
