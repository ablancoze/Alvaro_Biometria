package org.eclipse.wb.swt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;


import imagen.FingerPrintImage;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;


public class Interfaz {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel img1 = new JLabel("");
		
		
		JMenuBar menuBarMain = new JMenuBar();
		menuBarMain.setBounds(0, 0, 1264, 21);
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
		
		btnEliminarRuido.setBounds(651, 630, 117, 25);
		frame.getContentPane().add(btnEliminarRuido);
		
		btnConvertirAGris.setBounds(12, 630, 176, 25);
		frame.getContentPane().add(btnConvertirAGris);
		
		
		btnHacerHistograma.setBounds(200, 630, 176, 25);
		frame.getContentPane().add(btnHacerHistograma);
		
		
		btnBlancoNegro.setBounds(388, 630, 251, 25);
		frame.getContentPane().add(btnBlancoNegro);
		
		btnZangSuen.setBounds(780, 630, 117, 25);
		frame.getContentPane().add(btnZangSuen);
		
		JSlider slider = new JSlider();
		slider.setBounds(532, 425, 200, 16);
		frame.getContentPane().add(slider);
		


		FingerPrintImage p = new FingerPrintImage();		
		
		mntmCargarArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int i = 0;
				while(i<2) {
					try {
						img1.setIcon(new ImageIcon(p.cargarImagen(System.getProperty("user.dir")+"/Imagenes/101_2.jpg")));
						img1.setBounds(10, 54, 360, 480);
						frame.getContentPane().add(img1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					i++;
				}

			}
		});
		
		btnConvertirAGris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgGris;
				imgGris=p.convertirAgris(1);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgGris));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		
		btnHacerHistograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgHistograma;
				imgHistograma=p.histogramaEnEscalaGris(1);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgHistograma));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		
		btnBlancoNegro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgBlancoNegro;
				imgBlancoNegro=p.convertiBlancoNegro(68);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgBlancoNegro));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		
		
		btnEliminarRuido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgByNSinRuido;
				imgByNSinRuido = p.eliminarRuidoBinario();
				int i = 0;
				while(i<2) {
					img2.setIcon(new ImageIcon(imgByNSinRuido));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		btnZangSuen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imgZhanSuen;
				imgZhanSuen = p.zangSuen();
				int i = 0;
				while(i<2) {
					img2.setIcon(new ImageIcon(imgZhanSuen));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
				
			}
		});
		
		
		
	}
}
