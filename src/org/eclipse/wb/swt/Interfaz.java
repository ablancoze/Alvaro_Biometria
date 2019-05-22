package org.eclipse.wb.swt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import imagen.FingerPrintImage;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextArea;
import java.awt.Font;


public class Interfaz {
	
	public BufferedImage imgGris = null;
	
	public BufferedImage imgHistograma = null;
	
	public BufferedImage imgBlancoNegro = null;
	
	public BufferedImage imgByNSinRuido = null;
	
	public BufferedImage imgZhanSuen = null;
	
	public BufferedImage imgZhanSuenDeteccion = null;
	
	public int umbral = 80;
	
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1300, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel img1 = new JLabel("");
		
		
		JMenuBar menuBarMain = new JMenuBar();
		menuBarMain.setBounds(0, 0, 1284, 21);
		frame.getContentPane().add(menuBarMain);
		
		JMenu mnArchivos = new JMenu("Archivos");
		menuBarMain.add(mnArchivos);
		
		JMenuItem mntmCargarArchivos = new JMenuItem("Cargar archivos");
		mnArchivos.add(mntmCargarArchivos);

		JMenu mnNewMenu = new JMenu("New menu");
		menuBarMain.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("New menu");
		menuBarMain.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("New menu");
		menuBarMain.add(mnNewMenu_2);
		
		JLabel img2 = new JLabel("");

		
		JButton btnConvertirAGris = new JButton("Convertir a gris");
		
		JButton btnHacerHistograma = new JButton("Hacer histograma");
		
		JButton btnBlancoNegro = new JButton("Convertir a blanco y negro");
		
		JButton btnEliminarRuido = new JButton("Eliminar Ruido Binario");
		
		JButton btnZangSuen = new JButton("Zhang-Suen");
		
		JButton btnDeteccion = new JButton("Identificar terminaciones y bifurcaciones");
		
		btnEliminarRuido.setBounds(559, 630, 160, 25);
		frame.getContentPane().add(btnEliminarRuido);
		
		btnConvertirAGris.setBounds(12, 630, 176, 25);
		frame.getContentPane().add(btnConvertirAGris);
		
		
		btnHacerHistograma.setBounds(200, 630, 151, 25);
		frame.getContentPane().add(btnHacerHistograma);
		
		
		btnBlancoNegro.setBounds(361, 630, 188, 25);
		frame.getContentPane().add(btnBlancoNegro);
		
		
		btnZangSuen.setBounds(729, 630, 117, 25);
		frame.getContentPane().add(btnZangSuen);
		
		
		btnDeteccion.setBounds(853, 631, 225, 23);
		frame.getContentPane().add(btnDeteccion);
		
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(5);
		slider.setValue(80);
		slider.setMaximum(127);
		slider.setBounds(380, 48, 524, 45);
		frame.getContentPane().add(slider);
		
		JLabel text = new JLabel("Umbral = 80 ");
		text.setFont(new Font("Tahoma", Font.PLAIN, 15));
		text.setBackground(Color.WHITE);
		text.setBounds(592, 104, 97, 21);
		frame.getContentPane().add(text);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(460, 156, 360, 356);
		frame.getContentPane().add(textArea);
		

		FingerPrintImage p = new FingerPrintImage();	
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				text.setText("Umbral = " +slider.getValue());
				umbral = slider.getValue();
				if (imgBlancoNegro != null) {
					imgBlancoNegro = p.convertiBlancoNegro(umbral);
					int i = 0;
					while (i < 2) {
						img2.setIcon(new ImageIcon(imgBlancoNegro));
						img2.setBounds(914, 32, 360, 480);
						frame.getContentPane().add(img2);
						i++;
					}
				}
			}
		});
		
		mntmCargarArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				File archivo = fileChooser.getSelectedFile();
				String ruta=archivo.getAbsolutePath();
				int i = 0;
				while(i<2) {
					try {
						img1.setIcon(new ImageIcon(p.cargarImagen(ruta)));
						img1.setBounds(10, 32, 360, 480);
						frame.getContentPane().add(img1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					i++;
				}

			}
		});
		
		btnConvertirAGris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgGris=p.convertirAgris(1);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgGris));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		
		btnHacerHistograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgHistograma=p.histogramaEnEscalaGris(1);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgHistograma));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					img1.setIcon(new ImageIcon(imgGris));
					img1.setBounds(10, 32, 360, 480);
					frame.getContentPane().add(img1);
					i++;
				}
			}
		});
		
		
		btnBlancoNegro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgBlancoNegro=p.convertiBlancoNegro(umbral);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgBlancoNegro));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					img1.setIcon(new ImageIcon(imgHistograma));
					img1.setBounds(10, 32, 360, 480);
					frame.getContentPane().add(img1);
					i++;
				}
			}
		});
		
		
		
		btnEliminarRuido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgByNSinRuido = p.eliminarRuidoBinario();
				int i = 0;
				while(i<2) {
					img2.setIcon(new ImageIcon(imgByNSinRuido));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					img1.setIcon(new ImageIcon(imgBlancoNegro));
					img1.setBounds(10, 32, 360, 480);
					frame.getContentPane().add(img1);
					i++;
				}
			}
		});
		
		btnZangSuen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgZhanSuen = p.zangSuen();
				int i = 0;
				while(i<2) {
					img2.setIcon(new ImageIcon(imgZhanSuen));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					img1.setIcon(new ImageIcon(imgByNSinRuido));
					img1.setBounds(10, 32, 360, 480);
					frame.getContentPane().add(img1);
					i++;
				}
				
			}
		});
		
		btnDeteccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgZhanSuenDeteccion = p.deteccionMinutias();
				int i = 0;
				
				while(i<2) {
					img2.setIcon(new ImageIcon(imgZhanSuenDeteccion));
					img2.setBounds(914, 32, 360, 480);
					frame.getContentPane().add(img2);
					img1.setIcon(new ImageIcon(imgZhanSuen));
					img1.setBounds(10, 32, 360, 480);
					frame.getContentPane().add(img1);
					i++;
				}
			}
		});
		
		
	}
}
