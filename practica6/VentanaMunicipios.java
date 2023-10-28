package cap06.practica6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.xml.sax.helpers.ParserAdapter;




public class VentanaMunicipios extends JFrame {
	
	private DataSetMunicipios datosMunis;
	
	private HashMap<String,ArrayList<String>> comunidadesProvincias;
	private HashMap<String, Municipio> buscarNombresMunicipio;

	
//	Paneles
	private JPanel pSuperior;
	private JPanel pInferior;
	private JPanel pIzquierda;
	private JPanel pnlGrafico;
	private JPanel pCentro;
	
	
	private JLabel lblMensaje;
	
	private DefaultMutableTreeNode nodoRaiz;
	private DefaultTreeModel modeloArbol;
	private JTree arbol;
	
	private DefaultTableModel modeloTabla;
	private JTable tablaDatos;
	
	private JScrollPane scrollPaneArbol;
	private JScrollPane scrollPaneTabla;
	
	//Pintado tabla
	private boolean hacerClickDerecho;
	private String muniNoSel = "";
	private Municipio muniSel;
	
	private JButton bAnyadir;
	private JButton bBorrar;
	private JButton bOrden;
	
	private boolean ordenarPorNombre = true;
	
	//Boton orden
	private boolean sortByNames = true;
	
	
	

	public VentanaMunicipios() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
		
		pSuperior = new JPanel();
		pInferior = new JPanel();
		pIzquierda = new JPanel();
		pCentro = new JPanel();
		pIzquierda.setLayout(new BorderLayout());
		
		pSuperior.setBackground(Color.CYAN);
		pInferior.setBackground(Color.RED);
		pIzquierda.setBackground(Color.GREEN);
//		pnlGrafico.setBackground(Color.ORANGE);
//		pCentro.setBackground(Color.PINK);
		
		pSuperior.setPreferredSize(new Dimension(800,70));
		pInferior.setPreferredSize(new Dimension(800,70));
		pIzquierda.setPreferredSize(new Dimension(300,600));
//		pnlGrafico.setPreferredSize(new Dimension(140,70));
		
		lblMensaje = new JLabel("Mensaje");
		pSuperior.add(lblMensaje);
		
		
		arbol = new JTree();
		tablaDatos = new JTable();
		

		
		
//		
		
		
				
		scrollPaneArbol = new JScrollPane(arbol);
		scrollPaneTabla = new JScrollPane(tablaDatos);
		scrollPaneArbol.setPreferredSize(new Dimension(290,595));
		scrollPaneTabla.setPreferredSize(new Dimension(700,600));

		
		pIzquierda.add(scrollPaneArbol,BorderLayout.EAST);
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
//		add(pnlGrafico,BorderLayout.EAST);
		add(pCentro, BorderLayout.CENTER);
		
		
		
		
		
		arbol.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
//		Añadir escuchador al JTree para manejar seleccion ded prov.
		arbol.addTreeSelectionListener(new TreeSelectionListener() {
					
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
				TreePath p = e.getPath();
				
				if (p != null && p.getPathCount() == 3) {
					Object provSeleccionada = arbol.getLastSelectedPathComponent();
					modeloTabla.setRowCount(0);
					setDatosTabla(provSeleccionada.toString());
					muniSel = null;
					muniNoSel = "";
					hacerClickDerecho = false;
					tablaDatos.repaint();
					
					
					
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
				
				
				
				datosMunis.anyadir( new Municipio( datosMunis.getListaMunicipios().size()+1, "Nombre", 50001, "Provincia","Autonomia" ) );
					
					
				
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
		                datosMunis.getListaMunicipios().remove(filaSel);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Selecciona un municipio para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
		});
		
		//Boton orden
		bOrden.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        sortByNames = !sortByNames; // Toggle sorting order
		        
		        // Update the table data based on the new sorting order
		        updateTableData();
		    }
		});
		
		
		//Renderer arbol
		arbol.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

		    @Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
		            boolean leaf, int row, boolean hasFocus) {
		        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;

		        if (leaf) {
		            JPanel pnlPb = new JPanel(new BorderLayout());
		            pnlPb.setPreferredSize(new Dimension(200,600));
		            int habitantes = datosMunis.HabitantesPorProv(nodo.getUserObject().toString());
		            JProgressBar pb = new JProgressBar(50000, 1000000);
		            pb.setPreferredSize(new Dimension(50,30));
		            pb.setValue(habitantes);
		            pb.setStringPainted(false);
		            pb.setString(String.format("%,d", habitantes));

		            JLabel label = new JLabel(nodo.getUserObject().toString());
		            label.setPreferredSize(new Dimension(150,30));
		            
		            pnlPb.add(label, BorderLayout.WEST);
		            pnlPb.add(pb, BorderLayout.CENTER);
		            
		            return pnlPb;
		        }

		        return c;
			}
			
			
		});

		
		
		
		
	}
	
	
	
	
	
	public void setDatosArbol( DataSetMunicipios datosMunis ) {
		
		
		this.datosMunis = datosMunis;
		
		comunidadesProvincias = datosMunis.ComunidadesProvincias();
		buscarNombresMunicipio = datosMunis.BucarNombresMunicipio();
		
		pnlGrafico = new PnlGrafico(datosMunis);
		JScrollPane spGrafico = new JScrollPane(pnlGrafico);
		spGrafico.setPreferredSize(new Dimension(280,600));
		this.add(spGrafico, BorderLayout.EAST);
		pnlGrafico.setVisible(true);
		
		
		
		//Definir el modelo del JTree
		DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode("Municipios");
		modeloArbol = new DefaultTreeModel(nodoRaiz);
		int count = 0;
		
		//Crear el JTree y asignarle el modelo
		
		ArrayList<String> autonomiasOrdenadas = new ArrayList<>(comunidadesProvincias.keySet());
		Collections.sort(autonomiasOrdenadas);
		
		for (String a : autonomiasOrdenadas) {
			DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(a);
			modeloArbol.insertNodeInto(nodo, nodoRaiz, count);
			count = count + 1;
			ArrayList<String> provinciasA = comunidadesProvincias.get(a);
			for (int i=0 ; i< provinciasA.size() ; i++ ) {
				DefaultMutableTreeNode nodoProv = new DefaultMutableTreeNode(provinciasA.get(i));
				modeloArbol.insertNodeInto(nodoProv, nodo, i);
			}
		}
		arbol.setModel(modeloArbol);
		
		//Definir el modelo de la tabla
		
		modeloTabla = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			
			
			//Que columnas se pueden editar
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column ==1 || column == 2) {
					return true;
				} else {
					return false;
				}
			}
			
			//Actualizar tabla
			@Override
			public void setValueAt(Object value, int row, int column) {
				if (column == 2) {
					int numero = Integer.parseInt(value+"");
					setValueAt(numero,row,3);
					String nombre = getValueAt(row,1).toString();
					Municipio m = buscarNombresMunicipio.get(nombre);
					m.setHabitantes(numero);
					pCentro.repaint();
				
				} if(column == 1) {
					String nombre = tablaDatos.getValueAt(row, 1).toString();
					String nombreNuevo = value.toString();
					Municipio municipio = buscarNombresMunicipio.get(nombre);
					municipio.setNombre(nombreNuevo);
					buscarNombresMunicipio.remove(nombre);
					buscarNombresMunicipio.put(nombreNuevo, municipio);
					pCentro.repaint();
				}
				super.setValueAt(value, row, column);
			}
			
		};
		
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Municipio");
		modeloTabla.addColumn("Habitantes");
		modeloTabla.addColumn("Población");
		modeloTabla.addColumn("Provincia");
		modeloTabla.addColumn("Autonomía");
		
		tablaDatos.setModel(modeloTabla);
		
		
		


//		Añadir mouse listener a la tabla (solucion buena)
		tablaDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					
					int fila = tablaDatos.rowAtPoint(e.getPoint());
					int col = tablaDatos.columnAtPoint(e.getPoint());
					
					if (col == 1 && fila >= 0) {
						
						String municipio = (String) tablaDatos.getValueAt(fila, col);
						if (hacerClickDerecho == false) {
							muniNoSel = municipio;
							
							muniSel = buscarNombresMunicipio.get(municipio);
							
							hacerClickDerecho = true;
							
						} else {
							if (municipio == muniNoSel) {
								muniNoSel = "";
								muniSel = null;
								hacerClickDerecho = false;
							} else {
								muniNoSel = municipio;
								muniSel = buscarNombresMunicipio.get(municipio);
								hacerClickDerecho = true;
							}
						}
					}
					tablaDatos.repaint();
				}
				
				
			}
		});
		
		//Añadir renderer de la tabla
		tablaDatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		
			private static final long serialVersionUID = 1L;
			private JProgressBar pb = new JProgressBar(50000,5000000);
			
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			comp.setBackground(Color.WHITE);
			
			if (isSelected) {
	            // Cambia el fondo de la celda seleccionada a un color diferente (por ejemplo, gris)
	            comp.setForeground(Color.BLACK);
	            comp.setBackground(Color.WHITE);
	            
	            }
	        	else {
	            // Establece el fondo de las celdas no seleccionadas
	            comp.setBackground(Color.WHITE);
	        }
//			
//			
			
			if (column == 1) {
				
				if (hacerClickDerecho) {
					
					if (Integer.parseInt(table.getValueAt(row, 2)+"") > muniSel.getHabitantes()) {
						
						comp.setBackground(Color.RED);
					
					} else if (Integer.parseInt(table.getValueAt(row, 2)+"") < muniSel.getHabitantes()) {
						comp.setBackground(Color.GREEN);
						
					}

					} else {
						comp.setBackground(Color.WHITE);
						comp.setForeground(Color.BLACK);;
					}
				
				}
				
			if (column == 3) {
				int valor = Integer.parseInt(value.toString());
				pb.setValue(valor);
				int red = (int)Math.min(255, Math.max(0,((valor - 50000.0)/4950000)*255));
				int green = (int) Math.min(255,Math.max(0,255 - ((valor -50000.0)/4950000)*255));
				Color colorValor = new Color(red,green,0);
				pb.setForeground(colorValor);
					return pb;
			}
				return comp;
							
		
			}
		});
		
		
		

		

	
	
	
		}
	
	
	
	
		public void setDatosTabla(String provincia) {
			ArrayList<String> municipios = datosMunis.MunicipiosPorProvincia().get(provincia);
			for (String n : municipios) {
				Municipio mun = datosMunis.BucarNombresMunicipio().get(n);
				modeloTabla.addRow(new Object[] {mun.getCodigo(),mun.getNombre(),mun.getHabitantes(),mun.getHabitantes(),mun.getProvincia(),mun.getAutonomia()});
			}
			tablaDatos.repaint();
			
			
		}
		
		private void updateTableData() {
		    modeloTabla.setRowCount(0); // Clear the existing table data
		    
		    String provinciaSeleccionada = arbol.getLastSelectedPathComponent().toString();
		    
		    // Get the list of municipalities for the selected province
		    ArrayList<Municipio> municipios = new ArrayList<>();
		    for (String nombreMunicipio : datosMunis.MunicipiosPorProvincia().get(provinciaSeleccionada)) {
		        municipios.add(datosMunis.BucarNombresMunicipio().get(nombreMunicipio));
		    }
		    
		    // Sort the list based on the selected criteria
		    if (sortByNames) {
		        // Sort by name
		        municipios.sort(Comparator.comparing(Municipio::getNombre));
		    } else {
		        // Sort by inhabitants (from highest to lowest)
		        municipios.sort(Comparator.comparing(Municipio::getHabitantes).reversed());
		    }
		    
		    // Populate the table with sorted data
		    for (Municipio municipio : municipios) {
		        modeloTabla.addRow(new Object[] { municipio.getCodigo(), municipio.getNombre(), municipio.getHabitantes(), municipio.getHabitantes(), municipio.getProvincia(), municipio.getAutonomia() });
		    }
		    tablaDatos.repaint();
		}
		
		private static class PnlGrafico extends JPanel implements Scrollable{
			private static final long serialVersionUID = 1L;
			private final int WIDTH_BARRA = 50;
			private final int MIN_HEIGHT_BARRA = 500;
			private final int POBLACION_ESTADO;
			
			private String provincia="";
			private static int zoomLevel = 10;
			private int preferredY = 80+MIN_HEIGHT_BARRA*zoomLevel; 
			private DataSetMunicipios dataset;
			private ArrayList<barraVis> listaBarras;
			private int heightAnterior = 0;
			
			@Override
	        public Dimension getPreferredSize() {
	            return new Dimension(280, preferredY);
	        }

	        @Override
	        public Dimension getMinimumSize() {
	            return new Dimension(280, 128);
	        }
			
		

			public void setProvincia(String provincia) {
				this.provincia = provincia;
			}

			private PnlGrafico(DataSetMunicipios dataset){
				this.dataset=dataset;
				POBLACION_ESTADO = dataset.getPoblacionTotal();
				setBorder(new LineBorder(Color.BLACK, 1));
				
				addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                if (e.getButton() == MouseEvent.BUTTON1) {
		                    incZoomLevel();
		                    PnlGrafico.this.repaint();
		                } else if (e.getButton() == MouseEvent.BUTTON3) {
		                    decZoomLevel();
		                    PnlGrafico.this.repaint();
		                }
		            }
		        });
			}
			

			public static void incZoomLevel() {
		        if (zoomLevel<10) {
		        	zoomLevel++;
		        }
		    }
			public static void decZoomLevel() {
		        if (zoomLevel>1) {
		        	zoomLevel--;
		        }
		    }
			
			
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				if (!provincia.equals("")) {
					crearListaBarras();
					heightAnterior = 0;
					
					g.setColor(Color.cyan);
					g.fillRect(200, preferredY-MIN_HEIGHT_BARRA*zoomLevel, WIDTH_BARRA, MIN_HEIGHT_BARRA*zoomLevel);
					g.setColor(Color.BLACK);
					g.drawString("Estado",200,preferredY-MIN_HEIGHT_BARRA*zoomLevel-30);
					for (barraVis bv: listaBarras) {
						
						double heightD = (MIN_HEIGHT_BARRA*bv.muni.getHabitantes())/POBLACION_ESTADO;
						int height = (int) heightD*zoomLevel;
						int y = preferredY-height-heightAnterior;
						
						g.setColor(bv.getColorBarra());
						g.fillRect(20, y, 50, height);
						
						g.setColor(Color.black);
						g.drawLine(20, y, 70, y);
						
						g.drawString(bv.getMuni().getNombre(), 73, y);
						
						heightAnterior = height+heightAnterior;
					}
					g.drawString(provincia, 20, preferredY-heightAnterior-30);
				}
				
			}
			
			public void crearListaBarras() {
				int poblacionProvincia = 0;
				ArrayList<barraVis> listaBarras2 = new ArrayList<barraVis>();
				HashMap<String,Municipio> mapaBusquedaMunis = dataset.BucarNombresMunicipio();
				HashMap<String,ArrayList<String>> mapaProvinciasMunis = dataset.MunicipiosPorProvincia();
				for (String m: mapaProvinciasMunis.get(provincia)) {
					
					Municipio muni = mapaBusquedaMunis.get(m);
					Random rand = new Random();
			        int red = rand.nextInt(256); 
			        int green = rand.nextInt(256); 
			        int blue = rand.nextInt(256);
			        poblacionProvincia = poblacionProvincia + muni.getHabitantes();
					int heightBarra = (500*muni.getHabitantes())/POBLACION_ESTADO;
					barraVis bv = new barraVis(muni,new Color(red,green,blue));				
					listaBarras2.add(bv);
				}

				if (!listaBarras2.equals(listaBarras)) {
					listaBarras = listaBarras2;
				}
			}

			
			
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				// TODO Auto-generated method stub
				return new Dimension(280,preferredY);
			}


			@Override
			public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
				// TODO Auto-generated method stub
				return 128;
			}


			@Override
			public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
				// TODO Auto-generated method stub
				return 128;
			}


			@Override
			public boolean getScrollableTracksViewportWidth() {
				// TODO Auto-generated method stub
				return getPreferredSize().width
	                    <= getParent().getSize().width;
			}


			@Override
			public boolean getScrollableTracksViewportHeight() {
				// TODO Auto-generated method stub
				return getPreferredSize().height
	                    <= getParent().getSize().height;
			}
		}
		
		private static class barraVis{
			
			private Municipio muni;
			private Color colorBarra;
			
			public Municipio getMuni() {
				return muni;
			}
			public Color getColorBarra() {
				return colorBarra;
			}

			public barraVis(Municipio muni, Color colorBarra) {
				this.muni = muni;
				this.colorBarra = colorBarra;
			}
			
			@Override
			public String toString() {
				return "barraVis [muni=" + muni + "]";
			}
			
			@Override
			public boolean equals(Object obj) {
			    if (this == obj) {
			        return true;  // Si es la misma instancia, son iguales
			    }
			    if (obj == null || getClass() != obj.getClass()) {
			        return false;  // Si el objeto es nulo o no es de la misma clase, no son iguales
			    }
			    
			    // Convierte el objeto pasado en el mismo tipo que esta instancia
			    barraVis other = (barraVis) obj;
			    
			    // Realiza la comparación de los dos atributos específicos
			    return muni.equals(other.muni);
			}
			
		}
			
			
			
			
		}



	

	
	

	
	
	
	
		


