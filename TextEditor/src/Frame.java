/*
 * Author: A. Vyas
 * 
 * A simple text editor I made when I first learnt to program in Java a couple years back.
 * This was my first real program and I haven't updated/ changed its code.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JMenu file;
	private JMenuBar menu;
	private JMenuItem fileNew;
	private JMenuItem fileOpen;
	private JMenuItem fileExit;
	
	private JMenu edit;
	private JMenuItem editCopy;
	private JMenuItem editPaste;
	private JMenuItem editCut;
	
	private JMenu about;
	private JMenuItem aboutHelp;
	private JMenuItem aboutAbout;

	private JTextArea textArea;
	private String s;
	private JFileChooser fileOpener;
	private JMenuItem fileSave;
	private JMenuItem fileSaveAs;
	
	public Frame(){
		s = "";
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(700, 600);
		setTitle("Anshul's Text Editor v0.1");
		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
		
		menu = new JMenuBar();
		
		file = new JMenu("File");
		menu.add(file);
		
		fileNew = new JMenuItem("New File");
		file.add(fileNew);
		fileOpen = new JMenuItem("Open");
		file.add(fileOpen);
		fileSave = new JMenuItem("Save");
		file.add(fileSave);
		fileSaveAs = new JMenuItem("Save as");
		file.add(fileSaveAs);
		fileExit = new JMenuItem("Exit");
		file.add(fileExit);
		
		edit = new JMenu("Edit");
		menu.add(edit);
		
		editCopy = new JMenuItem("Copy");
		edit.add(editCopy);
		editCut = new JMenuItem("Cut");
		edit.add(editCut);
		editPaste = new JMenuItem("Paste");
		edit.add(editPaste);
		
		
		about = new JMenu("About");
		menu.add(about);
		aboutHelp = new JMenuItem("Help");
		about.add(aboutHelp);
		aboutAbout = new JMenuItem("About");
		about.add(aboutAbout);
		
		textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.setFont(new Font("Arial", Font.BOLD, 14));
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(textArea);
		add(sp);
		
		fileNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub0
				String t = new String(textArea.getText());
				if(!t.isEmpty()){
					if(JOptionPane.showConfirmDialog(null, "Do you want to create a new file? You will lose your unsaved data.") == 0){
						textArea.setText("");
					}
				}
			}	
		});
		
		fileOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String t = new String(textArea.getText());
				if(!t.isEmpty()){
					if(JOptionPane.showConfirmDialog(null, "Do you want to open a file? You will lose you current unsaved file.") == 0){
						fileOpener = new JFileChooser();
						fileOpener.showOpenDialog(null);
						File f = fileOpener.getSelectedFile();
						String filename = f.getAbsolutePath();
						
						try{
							Path path = Paths.get(filename);
							String b = new String(Files.readAllBytes(path));
							textArea.setText(b);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Error occurred bro");
						}
					}
				} else {
					fileOpener = new JFileChooser();
					fileOpener.showOpenDialog(null);
					File f = fileOpener.getSelectedFile();
					String filename = f.getAbsolutePath();
					
					try{
						Path path = Paths.get(filename);
						String b = new String(Files.readAllBytes(path));
						textArea.setText(b);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error occurred bro");
					}
					
				}
			}
		});
		
		fileSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				File f = fileOpener.getSelectedFile();
				String filename = f.getAbsolutePath();
				try {
					s = textArea.getText();
					Formatter formatter = new Formatter(filename);
					formatter.format("%s", s);
					formatter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		
		fileSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileOpener = new JFileChooser("Select directory to save the file in...");
				fileOpener.showSaveDialog(Frame.this);
				String filename = fileOpener.getSelectedFile().getAbsolutePath();
				try {
					Formatter formatter = new Formatter(filename);
					formatter.format("%s", textArea.getText());
					formatter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		fileExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					if(!textArea.getText().isEmpty()){
						if(JOptionPane.showConfirmDialog(null, "Do you want to exit? You will lose unsaved data.") == 0){
							System.exit(1);
						}
					} else {
						System.exit(1);
					}
			}
		});
		
		editCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				textArea.copy();
			}
		});
		
		editPaste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textArea.paste();
			}
		});
		
		editCut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textArea.cut();
			}
		});
		
		aboutAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame aboutframe = new JFrame();
				aboutframe.setSize(700, 600);
				aboutframe.setTitle("About Anshul's text editor");
				aboutframe.setBackground(Color.GRAY);
				aboutframe.setForeground(Color.GRAY);
				JPanel pnew = new JPanel();
				JTextArea ta = new JTextArea();
				ta.setEditable(false);
				ta.setFont(new Font("Arial", Font.PLAIN, 16));
				ta.setText("A basic text editor. Written in Java using the Swing API");
				ta.setWrapStyleWord(true);
				pnew.add(ta, BorderLayout.CENTER);
				aboutframe.add(pnew);
				aboutframe.setVisible(true);
			}
		});
		
		aboutHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = new String("www.google.com");
				try {
					Desktop.getDesktop().browse(new URI("http://www.text-editor.org/"));
					Desktop.getDesktop().browse(new URI("http://www.ict-bone.eu/portal/help/en/eines/editor_blog.htm"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		add(menu, BorderLayout.NORTH);
				
						
		setVisible(true);
	}
}
