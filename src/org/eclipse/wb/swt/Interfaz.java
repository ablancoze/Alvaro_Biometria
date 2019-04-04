package org.eclipse.wb.swt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JSlider;

import imagen.FingerPrintImage;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

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

		btnConvertirAGris.setBounds(569, 601, 143, 25);
		frame.getContentPane().add(btnConvertirAGris);
		
		FingerPrintImage p = new FingerPrintImage();
		
		
		
		mntmCargarArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int i = 0;
				while(i<2) {
					try {
						img1.setIcon(new ImageIcon(p.cargarImagen(System.getProperty("user.dir")+"/Imagenes/102_2.jpg")));
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
				imgGris=p.convertirRGB(1);
				int i = 0;
				while (i < 2) {
					img2.setIcon(new ImageIcon(imgGris));
					img2.setBounds(904, 54, 360, 480);
					frame.getContentPane().add(img2);
					i++;
				}
			}
		});
		
		
		
	}
}
