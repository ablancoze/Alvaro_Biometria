package imagen;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class FingerPrintImage {
	
	public BufferedImage imgOriginal;
	
	public int [][] MatrizImgGris;
	
	public int [][] MatrizImgHistograma;
	
	public int [][] MatrizImgBlancoNegro;
	
	public int [][] MatrizByNSinRuido;
	
	public int [][] MatrizZangShuen;

	
	public int Umbral;
	
	/**
	 * 
	 * @throws IOException
	 */
	public FingerPrintImage() throws IOException {

		
		imgOriginal = new BufferedImage (360, 480, BufferedImage.TYPE_INT_RGB);
		
		MatrizImgGris = new int [360][480];
		
		MatrizImgHistograma = new int [360][480];
		
		MatrizImgBlancoNegro = new int [360][480];
		
		MatrizByNSinRuido = new int [360][480];
		
		MatrizZangShuen = new  int [360][480];
		
		Umbral = 60;
		
		
	}
	
	/**
	 * 
	 * @param imgPath
	 * @return
	 * @throws IOException
	 */
	public BufferedImage cargarImagen (String imgPath) throws IOException {
		
		imgOriginal = ImageIO.read(new File(imgPath)) ;
		
		matrizGris();
		
		return imgOriginal;
		
	}
	
	/**
	 * 
	 */
	private  void matrizGris() {
	
		for (int x = 0; x < imgOriginal.getWidth(); ++x) {
			for (int y = 0; y < imgOriginal.getHeight(); ++y) {
				int rgb = imgOriginal.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				int nivelGris = (r + g + b) / 3;
				MatrizImgGris [x][y] = nivelGris;
			}
		}

	}
	
	
	/**
	 * 
	 * @param modo
	 * @return
	 */
	public  BufferedImage convertirAgris(int modo) {
		
		BufferedImage img = null;
		
		img = convertirRGB (modo, MatrizImgGris);
		return img;
	}
	
	/**
	 * 
	 * @param modo
	 * @param matriz
	 * @return
	 */
	private BufferedImage convertirRGB(int modo, int matriz [][]) {
		BufferedImage img = new BufferedImage (360, 480, BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < matriz.length; ++x) {
			for (int y = 0; y < matriz[0].length; ++y) {
				int valor = matriz[x][y];
				if (modo == 0) {
					valor = valor * 255;
				}
				int pixelRGB = (255 << 24 | valor << 16 | valor << 8 | valor);
				img.setRGB(x, y, pixelRGB);
			}
		}
		return img;
	}
	
	/**
	 * 
	 * @param modo
	 * @return
	 */
	public BufferedImage histogramaEnEscalaGris(int modo) {
		
		BufferedImage img = null;
		
		int width = MatrizImgGris.length;
		int height = MatrizImgGris [0].length;
		
		int tampixel= width*height;
		
		int[] histograma = new int[256]; // de 0 a 255
		
		// Calculamos frecuencia relativa de ocurrencia
		// de los distintos niveles de gris en la imagen
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int valor= MatrizImgGris [x][y];
				histograma[valor]++;
			 }
		}
		int sum =0;
		
		// Construimos la Lookup table LUT
		float[] lut = new float[256];
		
		for (int i=0; i < 256; ++i ){
			sum += histograma[i];
			lut[i] = sum * 255 / tampixel;
		}
		
		// Se transforma la imagen utilizando la tabla LUT
		
		int i=0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int valor= MatrizImgGris [x][y];
				int valorNuevo= (int) lut[valor];
				MatrizImgHistograma [x][y] = valorNuevo;
				i=i+1;
			}
		}
		
		img = convertirRGB(modo, MatrizImgHistograma);
		
		return img ;
		
		
	}
	
	/**
	 * 
	 * @param umbral
	 * @return
	 */
	public BufferedImage convertiBlancoNegro(int umbral) {
		
		BufferedImage img = null;
		
		for (int x = 0; x < MatrizImgHistograma.length; ++x) {
			for (int y = 0; y < MatrizImgHistograma[0].length; ++y){
				int valor= MatrizImgHistograma[x][y];
				if(valor < umbral){
					MatrizImgBlancoNegro[x][y] = 1;
				}else{
					MatrizImgBlancoNegro[x][y] = 0;
				}
			}
		}
		
		img = convertirRGB(0, MatrizImgBlancoNegro);
		
		return img;
	}
	
	public BufferedImage eliminarRuidoBinario() {
		BufferedImage img = null;
		int [][] imgSinHuecos = rellenarHuecos();
		MatrizByNSinRuido = eliminarUnosAislados(imgSinHuecos);
		
		img = convertirRGB(0, MatrizByNSinRuido);
		return img;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private int[][] rellenarHuecos() {
        int[][] imagenSalida = new int[360][480];
        int p = 0, b = 0, d = 0, e = 0, g = 0;
        int width = MatrizImgBlancoNegro.length;
        int height = MatrizImgBlancoNegro[0].length;
        int resultado = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenSalida[i][j] = MatrizImgBlancoNegro[i][j];
            }
        }

        for (int i = 10; i < width - 10; i++) {
            for (int j = 10; j < height - 10; j++) {
                p = MatrizImgBlancoNegro[i][j];
                b = MatrizImgBlancoNegro[i - 1][j];
                d = MatrizImgBlancoNegro[i][j - 1];
                e = MatrizImgBlancoNegro[i][j + 1];
                g = MatrizImgBlancoNegro[i + 1][j];
                resultado = p | b & g & (d | e) | d & e & (b | g);
                imagenSalida[i][j] = resultado;
            }
        }
        return imagenSalida;
	}
	
	/**
	 * 
	 * @param imgSinHuecos
	 * @return
	 */
	private int[][] eliminarUnosAislados(int [][] imgSinHuecos) {
		int[][] imagenSalida = new int[360][480];
        int p = 0, b = 0, d = 0, e = 0, g = 0, a = 0, c = 0, f = 0, h = 0;
        int width = imgSinHuecos.length;
        int height = imgSinHuecos[0].length;
        int resultado = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenSalida[i][j] = imgSinHuecos[i][j];
            }
        }
        for (int i = 10; i < width - 10; i++) {
            for (int j = 10; j < height - 10; j++) {
                p = imgSinHuecos[i][j];
                b = imgSinHuecos[i - 1][j];
                d = imgSinHuecos[i][j - 1];
                e = imgSinHuecos[i][j + 1];
                g = imgSinHuecos[i + 1][j];
                a = imgSinHuecos[i - 1][j - 1];
                c = imgSinHuecos[i - 1][j + 1];
                f = imgSinHuecos[i + 1][j - 1];
                h = imgSinHuecos[i + 1][j + 1];

                resultado = p & ((a | b | d) & (e | g | h) | (b | c | e) & (d | f | g));
                imagenSalida[i][j] = resultado;
            }
        }
        return imagenSalida;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public BufferedImage zangSuen(){
		BufferedImage img = null;
        int width = MatrizByNSinRuido.length;
        int height = MatrizByNSinRuido[0].length;
        
        int numBlanco = 0;
        int p2 = 0, p3 = 0, p4 = 0, p5 = 0, p6 = 0, p7 = 0, p8 = 0, p9 = 0, p1 = 0;
        boolean cambio = false;
        
        List<Point> pointsToChange = new LinkedList();

        for (int cont = 0; cont < width; cont++) {
            for (int cont1 = 0; cont1 < height; cont1++) {
            	MatrizZangShuen[cont][cont1] = MatrizByNSinRuido[cont][cont1];
            }
        }

        do {
            cambio = false;
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                	
                	//Ventana de vencidad.
                    p1 = MatrizZangShuen[i][j];
                    p9 = MatrizZangShuen[i - 1][j - 1];
                    p2 = MatrizZangShuen[i - 1][j];
                    p3 = MatrizZangShuen[i - 1][j + 1];
                    p8 = MatrizZangShuen[i][j - 1];
                    p4 = MatrizZangShuen[i][j + 1];
                    p7 = MatrizZangShuen[i + 1][j - 1];
                    p5 = MatrizZangShuen[i + 1][j + 1];
                    p6 = MatrizZangShuen[i + 1][j];

                    if (p1 == 1) {
                    	//Sumamos los piexeles vecinos para ver su valor total
                        numBlanco = p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
                        //Condicion 1: Que el numero de pixeles negro este entre 2 y 6
                        if (numBlanco >= 2 && numBlanco <=6) {
                        	//Condicion 2: Que total en cambios 1 0 entre los pixeles p2,p3,p4,... sea 1 
                        	if (numCambios(p2,p3,p4,p5,p6,p7,p8,p9)==1) {
                        		//Condicion 3: p2 * p3 * p6 == 1
                        		if(p2*p4*p6 == 0) {
                        			//Condicion 4: p4 * p6 * p8 == 1
                        			if(p4*p6*p8 == 0) {
                        				 cambio = true;
                                         pointsToChange.add(new Point(i, j));
                        			}
                        		}
                        	}
                        }
                    }
                }
            }

            for (Point point : pointsToChange) {
            	MatrizZangShuen[(int) point.getX()][(int) point.getY()] = 0;
            }
            pointsToChange.clear();

            for (int k = 1; k < width - 1; k++) {
                for (int l = 1; l < height - 1; l++) {
                	
                	//Ventana de vecindad
                    p1 = MatrizZangShuen[k][l];
                    p9 = MatrizZangShuen[k - 1][l - 1];
                    p2 = MatrizZangShuen[k - 1][l];
                    p3 = MatrizZangShuen[k - 1][l + 1];
                    p8 = MatrizZangShuen[k][l - 1];
                    p4 = MatrizZangShuen[k][l + 1];
                    p7 = MatrizZangShuen[k + 1][l - 1];
                    p5 = MatrizZangShuen[k + 1][l + 1];
                    p6 = MatrizZangShuen[k + 1][l];
                    
                    if (p1 == 1) {
                    	//Sumamos los piexeles vecinos para ver su valor total
                        numBlanco = p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
                        //Condicion 1: Que el numero de pixeles negro este entre 2 y 6
                        if (numBlanco >= 2 && numBlanco <=6) {
                        	//Condicion 2: Que total en cambios 1 0 entre los pixeles p2,p3,p4,... sea 1 
                        	if (numCambios(p2,p3,p4,p5,p6,p7,p8,p9)==1) {
                        		//Condicion 3: p2 * p3 * p6 == 1
                        		if(p2*p4*p8 == 0) {
                        			//Condicion 4: p4 * p6 * p8 == 1
                        			if(p2*p6*p8 == 0) {
                        				 cambio = true;
                                         pointsToChange.add(new Point(k, l));
                        			}
                        		}
                        	}
                        }
                    }
                }
            }

            for (Point point : pointsToChange) {
            	MatrizZangShuen[(int) point.getX()][(int) point.getY()] = 0;
            }
            pointsToChange.clear();

        } while (cambio);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (MatrizZangShuen[i][j] == 0) {
                	MatrizZangShuen[i][j] = 1;
                }else{
                	MatrizZangShuen[i][j] = 0;
                }
            }
        }
        
        img = convertirRGB(0, MatrizZangShuen);

        return img;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> deteccionMinutias(){
		List<Integer> listaMinutias = new LinkedList<Integer>();
		int width = MatrizZangShuen.length;
        int height = MatrizZangShuen[0].length;
        int[] neigh = new int[9];
        int p = 0;
        int resultado = 0;
        int a = 0;
        for (int k = 16; k < width - 16; k++) {
            for (int l = 16; l < height - 16; l++) {
                p = MatrizZangShuen[k][l];
                if (p == 0) {
                	neigh[7] = MatrizZangShuen[k - 1][l - 1];
                	neigh[0] = MatrizZangShuen[k - 1][l];
                	neigh[1] = MatrizZangShuen[k - 1][l + 1];
                	neigh[6] = MatrizZangShuen[k][l - 1];
                	neigh[2] = MatrizZangShuen[k][l + 1];
                	neigh[5] = MatrizZangShuen[k + 1][l - 1];
                	neigh[3] = MatrizZangShuen[k + 1][l + 1];
                	neigh[4] = MatrizZangShuen[k + 1][l];
                	neigh[8] = MatrizZangShuen[k - 1][l];
                    for (int i = 0; i < neigh.length - 1; i++) {

                        a = Math.abs(neigh[i] - neigh[i + 1]);
                        resultado = resultado + a;
                    }
                    resultado = resultado / 2;
                    if (resultado == 1 || resultado == 3) {
                    	listaMinutias.add(k);
                    	listaMinutias.add(l);
                    	listaMinutias.add(resultado);
                    	listaMinutias.add(0); //angulo 
                    }
                    resultado = 0;
                }

            }
        }
		return listaMinutias;
	}
	
	/**
	 * 
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param p5
	 * @param p6
	 * @param p7
	 * @param p8
	 * @param p9
	 * @return
	 */
	private int numCambios(int p2,int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
		int total = 0;
		if (p2 == 0 && p3 == 1)
			total++;
		if (p3 == 0 && p4 == 1)
			total++;
		if (p4 == 0 && p5 == 1)
			total++;
		if (p5 == 0 && p6 == 1)
			total++;
		if (p6 == 0 && p7 == 1)
			total++;
		if (p7 == 0 && p8 == 1)
			total++;
		if (p8 == 0 && p9 == 1)
			total++;
		if (p9 == 0 && p2 == 1)
			total++;
		
		return total;
	}

	public BufferedImage getImgOriginal() {
		return imgOriginal;
	}


	public void setImgOriginal(BufferedImage imgOriginal) {
		this.imgOriginal = imgOriginal;
	}
}
