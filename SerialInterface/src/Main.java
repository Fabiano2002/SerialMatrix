

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.fazecast.jSerialComm.*;

public class Main {
	public static int[][][] mtx = new int[3][8][8];
	public static int cf = 0;
	public static String path = Gui.mainpath + "1.gif";
	public static int ov;
	public static boolean active;
	static int cp = 0;
	static byte cc = 0;
	static SerialPort p = SerialPort.getCommPorts()[cp];
	static int sl = 0;
	static byte rw = 0;
	static long execs = 0;
	static long starttime;
	static int pos = 0;
	static int pid = 64000;
	static long endd = 0;
	static int baud = 250000;
	static void getImg() throws IOException {
		ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
		File input = new File(path);
		ImageInputStream stream = ImageIO.createImageInputStream(input);
	    reader.setInput(stream);
	    int count = reader.getNumImages(true);
	        BufferedImage img = reader.read(cf);
	        for (int i = 0; i < 8; i++) {
				for (int x = 0; x < 8; x++) {
					Color color = new Color(img.getRGB(i, x),true);
					mtx[0][i][x] = (color.getAlpha()*color.getRed())/255;
					mtx[1][i][x] = (color.getAlpha()*color.getGreen())/255;
					mtx[2][i][x] = (color.getAlpha()*color.getBlue())/255;
				}
			}	
	        cf++;
	        if(cf >= count) {
	        	cf = 0;
	        }
	}
	
	static int[][][] background(int[][][] mx) {
		for (int i = 0; i < mx.length; i++) {
			for (int j = 0; j < mx[i].length; j++) {
				for (int j2 = 0; j2 < mx[i][j].length; j2++) {
					if(mx[i][j][j2] == 0) {
						mx[i][j][j2] = 1;
					}
				}
			}
		}
		return mx;
	}
	
	static int[][][] frontplace(int[][][] matrix,int[][][] background) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if(matrix[0][i][j] == 0 && matrix[1][i][j] == 0 && matrix[2][i][j] == 0 ) {
						matrix[0][i][j] = background[0][i][j];
						matrix[1][i][j] = background[1][i][j];
						matrix[2][i][j] = background[2][i][j];
					}
				}
		}
		return matrix; 
	}
	
	static void sendclean() {
		byte[] send = new byte[1];
		send[0] = 14;
		if(Gui.dump==true) {
		try {
			write(Gui.path,send,Gui.writer);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR CODE 2");
		}
		}
		p.writeBytes(send, send.length);
	}
	
	static void write(String path,byte[] txt,PrintWriter writer) throws FileNotFoundException, UnsupportedEncodingException {
		
		for (int i = 0; i < txt.length; i++) {
			int s = Byte.toUnsignedInt(txt[i]);
			writer.print(s + " ");
		}
	}
	
	static void render() {
		byte[] send = new byte[8];
		for (int i = 0; i < 8; i++) {
			if(rw != 8) {
				if(mtx[cc][rw][i] != 14) {
			send[i] = (byte) mtx[cc][rw][i];
				}
				else
				{
					send[i] = 13;
				}
			}
			if(rw == 8) {
				if(mtx[cc][7][i] != 14) {
							send[i] = (byte) mtx[cc][7][i];
						}
						else
						{
							send[i] = 13;
						}
			}
		}
		cc++;
		if(cc==3) {
			cc = 0;
			rw++;
			sl++;
		}
		if(rw==8) {
			rw=0;
			
		}
		if(Gui.dump==true) {
			try {
				write(Gui.path,send,Gui.writer);
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				System.out.println("ERROR CODE 1");
			}
		}
		p.writeBytes(send, send.length);
		if(sl==8) {
			sendclean();
			sl=0;
			
			if(Gui.fps == 0) {
				return;
			}
		}
		long startt = System.nanoTime();
		if(((startt-endd)) > (1000000000/Gui.fps)) { // IF ANIMATION BUGGING CHECK THIS FUNCTION, ANIMATION UPDATED MID FRAME !!!EXPERIMENTAL!!!
			execs++;
			System.out.print("CODE EXECUTIONS : " + execs + " | UPS : ");
			float ups = 1000000000/((System.nanoTime()-starttime));
			starttime = System.nanoTime();
			System.out.println(ups);
			endd = startt;
			pos++;
			if(pos > TextConverter.lastlength) {
				pos = 0;
			}
		if(Gui.ca.equals("Image Rendering")) {
			if(Gui.animarg != "none" ) {
				path = Gui.animarg;
			}
			try {
				getImg();
			} catch (IOException e) {
			}
		}
		if(Gui.ca.equals("Idle")) {
			mtx = Algorithm.idle();
		}
		if(Gui.ca.equals("Randomize")) {
			mtx = Algorithm.Randomize();
		}
		if(Gui.ca.equals("Stringify")) {
			int[][] cdata = new int[512][3];
			for (int j = 0; j < cdata.length; j++) {
				int[] col = new int[3];
				col = Algorithm.getFullColor(j*64);
				cdata[j][0] = col[0];
				cdata[j][1] = col[1];
				cdata[j][2] = col[2];
			}
			if(Gui.animarg != "none" ) {
				if(!TextConverter.fullstring.equals(Gui.animarg)) {
					TextConverter.setfull(Gui.animarg,cdata);
					}
					mtx = TextConverter.convert(pos);
			}
			else
			{
				if(TextConverter.fullstring != Gui.t.getText()) {
				TextConverter.setfull(Gui.t.getText(),cdata);
				}
				mtx = TextConverter.convert(pos);
			}
		}
		if(Gui.ca.equals("Colorline")) {
			mtx = Algorithm.colorline(pid);
			pid-=6;
			if((64000-pid)>= (255*3)) {
				pid = 64000;
			}
		}
		int[][][] back = new int[3][8][8];
		int col = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				for (int j2 = 0; j2 < 8; j2++) {
					back[0][j][j2] = col;
					back[1][j][j2] = col;
					back[2][j][j2] = col;
				}
			}
		}
		mtx = frontplace(mtx,back);
		}
		
	}
	
	static int reassignPort(int port) {
		if(port > SerialPort.getCommPorts().length) {
			return 1;
		}
		p = SerialPort.getCommPorts()[port];
		p.setBaudRate(baud);
		return 0;
	}
	
	static int main(String[] args) {
		System.out.println("Main Started.");
		if(Gui.com > -1) {
			reassignPort(Gui.com);
		}
		p.setBaudRate(baud); // APPROVED : 153600,200K
		p.openPort();
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					mtx[0][x][y] = 255;
					mtx[1][x][y] = 255;
					mtx[2][x][y] = 255;
				}
			}
		}
		if(p.isOpen() == false) {
			return 1;
		}
		pos = 0;
		while(p.isOpen()) {
			if(active == false && Gui.fenable == false) {
				continue;
			}
			if(Gui.fps == 0) {
				continue;
			}
				render();
		}
		return 2;
	}
}
