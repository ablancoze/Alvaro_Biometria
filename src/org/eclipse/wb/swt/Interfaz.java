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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel img1 = new JLabel("");
		img1.setIcon(new ImageIcon("C:\\Users\\alvma\\Desktop\\Uni\\BSS\\Workspace\\BSS_Alvaro\\Imagenes\\download.jpg"));
		img1.setBounds(10, 54, 360, 480);
		frame.getContentPane().add(img1);
		
		JLabel img2 = new JLabel("");
		img2.setIcon(new ImageIcon("C:\\Users\\alvma\\Desktop\\Uni\\BSS\\Workspace\\BSS_Alvaro\\Imagenes\\download.jpg"));
		img2.setBounds(894, 54, 360, 480);
		frame.getContentPane().add(img2);
		
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
		
		JSlider slider = new JSlider();
		slider.setBounds(489, 230, 200, 26);
		frame.getContentPane().add(slider);
	}
}
