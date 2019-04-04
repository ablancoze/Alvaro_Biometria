package imagen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FingerPrintImage {
	
	public BufferedImage imgOriginal;
	
	public BufferedImage imgGris;
	
	
	public BufferedImage imgGrisRGB;
	
	
	public FingerPrintImage() throws IOException {

		
		imgOriginal = new BufferedImage (360, 480, BufferedImage.TYPE_INT_RGB);
		
		imgGris = new BufferedImage (360, 480, BufferedImage.TYPE_INT_RGB);
		
		imgGrisRGB = new BufferedImage (360, 480, BufferedImage.TYPE_INT_RGB);
	}
	
	
	public BufferedImage cargarImagen (String imgPath) throws IOException {
		
		imgOriginal = ImageIO.read(new File(imgPath)) ;
		
		convertirGris();
		
		return imgOriginal;
		
	}
	
	
	private  BufferedImage convertirGris() {
		
		for (int x = 0; x < imgOriginal.getWidth(); ++x) {
			for (int y = 0; y < imgOriginal.getHeight(); ++y) {
				int rgb = imgOriginal.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				int nivelGris = (r + g + b) / 3;
				imgGris.setRGB(x, y, nivelGris);
			}
		}
		return imgGris;
	}
	
	public BufferedImage convertirRGB(int modo) {
		for (int x = 0; x < imgGris.getWidth(); ++x) {
			for (int y = 0; y < imgGris.getHeight(); ++y) {
				int valor = imgGris.getRGB(x, y);
				if (modo == 0) {
					valor = valor * 255;
				}
				int pixelRGB = (255 << 24 | valor << 16 | valor << 8 | valor);
				imgGrisRGB.setRGB(x, y, pixelRGB);
			}
		}
		return imgGrisRGB;
	}


	public BufferedImage getImgOriginal() {
		return imgOriginal;
	}


	public void setImgOriginal(BufferedImage imgOriginal) {
		this.imgOriginal = imgOriginal;
	}


	public BufferedImage getImgGris() {
		return imgGris;
	}


	public void setImgGris(BufferedImage imgGris) {
		this.imgGris = imgGris;
	}

}
