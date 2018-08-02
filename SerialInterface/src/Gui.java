import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.fazecast.jSerialComm.SerialPort;
public class Gui extends Frame implements ActionListener,ItemListener,ChangeListener { 
	public static TextField t;
	public static Checkbox c;
	public static int fps = 10;
	public static int cn = 1;
	private static final long serialVersionUID = 2L;
	public static String path = "";
	public static boolean dump = false;
	public static boolean algo = false;
	public static String ca = "Image Rendering";
	public static PrintWriter writer;
	public static String mainpath = "C:/GIF";
	static boolean nogui = false;
	static boolean fenable = false;
	static boolean pi = false;
	static int com = -1;
	static String animarg = "none";
	
	Gui() {
	JFrame f= new JFrame();  
	f.setTitle("SerialControllerInferface");
	f.setResizable(false);
	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	f.setSize(300,300);  
	f.setLayout(null);  
	
	Button b = new Button("Previous");  
	b.setBounds(60,120,80,30);  
	b.addActionListener(this);
	f.add(b);  
	
	b = new Button("Next");  
	b.setBounds(160,120,80,30);  
	b.addActionListener(this);
	f.add(b);
	
	c = new Checkbox("Dump", false);
	c.setBounds(220,220,80,30);
	c.addItemListener(this);
	f.add(c);
	
	Checkbox c2 = new Checkbox("Enabled", false);
	c2.setBounds(100,80,80,30);
	c2.addItemListener(this);
	f.add(c2);
	
	Choice lb = new Choice();
	lb.setBounds(70,180,140,30);
	lb.add("Image Rendering");
	lb.add("Idle");
	lb.add("Randomize");
	lb.add("Colorline");
	lb.add("Stringify");
	lb.addItemListener(this);
	f.add(lb);
	
	Choice ports = new Choice();
	ports.setBounds(70,0,140,30);
	for (int i = 0; i < SerialPort.getCommPorts().length; i++) {
		ports.add("Port : " + i);
	}
	
	JSlider fps = new JSlider(0,126,10);
	fps.addChangeListener(this);
	fps.setBounds(50,20,200,80);
	fps.setMinorTickSpacing(2);
	fps.setMajorTickSpacing(42);
	fps.setPaintTicks(true);
	fps.setPaintLabels(true);
	f.add(fps);
	
	t = new TextField();
	t.setBounds(70,200,140,30);  
	t.addActionListener(this);
	f.add(t);  
	
	ports.addItemListener(this);
	f.add(ports);
	
	//TextField
	
	f.setVisible(true); 
	}
	
	public static void main(String args[]) throws InterruptedException, IOException{  
		handleArgs(args);
		if(!nogui) {
		new Gui();
		}
		int r = 1;
		while(r != 0) {
			r=Main.main(null);
			System.out.println("Main Returned : " + r);
		}
		}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getItem() == "Dump") {
			if(e.getStateChange() == 1) {
				JFileChooser fc = new JFileChooser();
				int r =fc.showSaveDialog(new JFrame());
				if(r == JFileChooser.APPROVE_OPTION) {
					path = fc.getSelectedFile().getAbsolutePath();
					dump = true;
					System.out.println(path);
					try {
						writer = new PrintWriter(path, "UTF-8");
					} catch (FileNotFoundException e1) {
					} catch (UnsupportedEncodingException e1) {
					}
				}
				else
				{
					c.setState(false);
				}
			}
			else
			{
				dump=false;
			}
		}
		if(e.getItem() == "Enabled") {
			if(e.getStateChange() == 1) {
				Main.active = true;
			}else{
				Main.active = false;
			}
		}
		else
		{
			if(!e.getItem().toString().contains("Port : ")) {
			ca = (String) e.getItem();
			}
			else
			{
				Main.reassignPort(Integer.parseInt(e.getItem().toString().replaceAll("Port : ", "")));
			}
		}
		
	} 
	
	public void actionPerformed(ActionEvent e){  
		if(e.getActionCommand() == "Previous") {
			File f = new File(mainpath + (cn-1) + ".gif");
			if(f.exists()) {
			System.out.println("Changing Path To : " + mainpath + (cn-1) + ".gif");
			Main.path = mainpath + (cn-1) + ".gif";
			Main.cf = 0;
			cn--;
			}
			else
			{
				for (int i = 0; i < 99; i++) {
					File c = new File(mainpath + (99-i) + ".gif");
					if(c.exists()) {
					System.out.println("Changing Path To : " + mainpath + (99-i) + ".gif");
					Main.path = mainpath + (99-i) + ".gif";
					Main.cf = 0;
					cn = (99-i);
					break;
					}
				}
			}
		}
		if(e.getActionCommand() == "Next") {
			File f = new File(mainpath + (cn+1) + ".gif");
			if(f.exists()) {
			System.out.println("Changing Path To : " + mainpath + (cn+1) + ".gif");
			Main.path = mainpath + (cn+1) + ".gif";
			Main.cf = 0;
			cn++;
			}
			else
			{
				System.out.println("Changing Path To : " + mainpath + 1 + ".gif");
				Main.path = mainpath + 1 + ".gif";
				Main.cf = 0;
				cn = 1;
			}
		}
		}
	
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    fps = source.getValue();
	}

	static void handleArgs(String args[]) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals("-nogui")) {
				nogui=true;
				fenable=true;
			}
			if(args[i].equals("-com")) {
				i++;
				com = Integer.parseInt(args[i]);
			}
			if(args[i].equals("-anim")) {
				i++;
				ca = args[i];
			}
			if(args[i].equals("-fps")) {
				i++;
				fps = Integer.parseInt(args[i]);
			}
			if(args[i].equals("-animarg")) {
				i++;
				animarg=args[i];
			}
			if(args[i].equals("-baud")) {
				i++;
				Main.baud=Integer.parseInt(args[i]);
			}
			if(args[i].equals("-dump")) {
				i++;
				path=args[i];
				dump=true;
				try {
					writer = new PrintWriter(path, "UTF-8");
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
				}
			}
			if(args[i].equals("pi")) {
				pi=true;
			}
		}
	}
}
