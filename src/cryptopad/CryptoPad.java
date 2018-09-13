package cryptopad;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import java.net.*;	  

 /**
 *@Programmed by Rahul Kumar And Aakanchha	.
 *@College Sliet Longoewal for the partial fulfilment of minor Project.
 *@Guide Jaswinder Kaur.
 *@Relese 1.
 *developed using Sublime TextPad
 *********************************************************************
 *This text editor named CryptoPad is developed in stages            *
 *the first stage is a simple text editor with resemblance           *
 *of MS Notepad. It is then enhanced in next version for             *
 *advanced facilities like TextPad and further for Security          *
 *implementation with the help of different encryption and decryption*
 *techniques*							     *
 ********************************************************************/



public class CryptoPad extends JFrame
{
/*********** menubar, menus and menu items***************************/
    JMenuBar menubar;  
    JMenu file,edit,help,codeMenu,decodeMenu;
    //JMenu search;
    JPopupMenu popChoice;

/********************************************************************/
    JMenuItem New,open,save,openDecode,saveCoded,
            saveAs,Exit;
    JMenuItem cut,copy,paste,
            undo,select,setFont;//items of edit menu
    JMenuItem find,Help;//items of search and help menus
    JMenuItem Compile;// item for tools menu
    JMenuItem codeCaeser,decodeCaeser,codeSubstitution,decodeSubstitution;
    JMenuItem codeMonoSubstitution,deocdeMonoSubstitution,codeVigenere,decodeVigenere;
    JMenuItem closeFileInList;

/*********************************************************************/
    TextBox textBox;
    JList fileList; 	// this will display all open files 	                						// one of which can be selected to be displayed in
 	              		// the textBox.
    boolean isSelected = false;	// a flag for determining if there is a selection in the list.
    JSplitPane splitPane; 		// a aplit window to add textBox and fileList.
    JToolBar toolBar;  		// a toolbar for commonly used menu items.
    JButton saveButton,newDocumentButton,fileOpenButton,
            cutButton,copyButton,pasteButton;
    Vector openedFiles;  	// a vector to hold all opened files
    String selectedText = null; 				// for cut paste utility
    URL currentDirURL;
    ImageIcon icon; 							// window icon
    Image image;
    JLabel statusBar;
    String currentFileName = " ";
    JFileChooser fc = new JFileChooser();

/*************************************************************************/

    boolean textChanged = false; // records weather the text is changed or not.

/*************************************************************************/
 
// The Constructor.
public CryptoPad()  
{
    super("CryptoPad");
    this.setDefaultCloseOperation(1);
    statusBar = new JLabel("CryptoPad");
    
/***********************************creating custom icon***************/
    icon = new ImageIcon("E:\\Java\\Project\\CryptoPad\\src\\cryptopad\\images\\ico.png");
    image = icon.getImage();
    this.setIconImage(image);
/**********************************************************************/

 //creating and pupulating the menus
    final JFileChooser fc = new JFileChooser();
    Filter filter = new Filter();
    filter.addExtention("txt");
    filter.addExtention("java");
    filter.addExtention("doc");
    filter.addExtention("class");
    fc.setFileFilter(filter);
    final Encripter encripter = new Encripter();

    file = new JMenu("File");
    edit = new JMenu("Edit");
    help = new JMenu("Help");
    codeMenu = new JMenu("Save coded");
    decodeMenu = new JMenu("Open decoded");
    
 // menuItems for file menu
    New = new JMenuItem("New");
    open = new JMenuItem("Open");
    save = new JMenuItem("Save");
    saveAs = new JMenuItem("Save as");
    openDecode = new JMenuItem("Open decoded");
    saveCoded = new JMenuItem("Save Coded");
    Exit = new JMenuItem("Exit");
    codeCaeser = new JMenuItem("Code Caeser");
    decodeCaeser = new JMenuItem("Decode Caeser");
    codeSubstitution = new JMenuItem("Code Substitution");
    decodeSubstitution = new JMenuItem("Decode Substitution");
    codeVigenere = new JMenuItem("Code Vigenere Cipher");
    decodeVigenere = new JMenuItem("Decode Vigenere Cipher");
    codeMonoSubstitution = new JMenuItem("Code MonoSubstitution");
    deocdeMonoSubstitution = new JMenuItem("Decode MonoSubstitution");
    file.add(New);
    file.add(open);
    file.add(save);
    file.add(saveAs);
    file.add(codeMenu);
    file.add(decodeMenu);
    file.add(Exit);
    codeMenu.add(codeCaeser);
    codeMenu.add(codeSubstitution);
    codeMenu.add(codeVigenere);
    codeMenu.add(codeMonoSubstitution);
    decodeMenu.add(decodeCaeser);
    decodeMenu.add(decodeSubstitution);
    decodeMenu.add(decodeVigenere);
    decodeMenu.add(deocdeMonoSubstitution);
    

 //Action Listener for Different Coded and Decoded menus. 
    codeVigenere.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            doVigenereCipher();
        }
    });

        
    decodeVigenere.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            doVigenereDecipher();
        }
    });

        
    codeMonoSubstitution.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            doMonoSubstitutionCipher();
        }
    });

        
    deocdeMonoSubstitution.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            doMonoSubstitutionDecipher();
        }
    });

        
    codeCaeser.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            File saveFile = null;
            String key =  JOptionPane.showInputDialog("Enter one alphabet key please");
            if(key.length()>1)
            {
                JOptionPane.showMessageDialog(null,"The key must be one alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if( !(Character.isUpperCase(key.charAt(0))||Character.isLowerCase(key.charAt(0))) )
            {
                JOptionPane.showMessageDialog(null,"The key must be an alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int returnVal = fc.showSaveDialog(CryptoPad.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                saveFile = fc.getSelectedFile();
                Encriptor enc = new Encriptor(key.charAt(0));
		String message = textBox.getText();
		String coded = enc.codeCaeser(message);
                try
		{
                    enc.writeBytes(coded,saveFile);
		}
		catch(FileNotFoundException fe)
                {
                    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
                }
		catch(IOException ioe)
		{
                    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    });


    codeSubstitution.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            File saveFile;
            String key =  JOptionPane.showInputDialog("Enter 26 alphabets key please./n THE ALPHABETS MUST NOT BE REPEATED");
            if(key.length()>26||key.length()<26)
            {
                JOptionPane.showMessageDialog(null,"The Key must be 26 letters","Error",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(hasRepeatedCharacters(key))
            {
                JOptionPane.showMessageDialog(null,"The Key must not have repeated Characters","Error",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int returnVal = fc.showSaveDialog(CryptoPad.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                saveFile = fc.getSelectedFile();
                Encriptor enc = new Encriptor(key);
                String message = textBox.getText();
                String coded = enc.codeSubstitutionCipher(message);
		try
		{
                    enc.writeBytes(coded,saveFile);
		}
		catch(FileNotFoundException fe)
		{
                    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
		}
		catch(IOException ioe)
		{
                    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
		}
            }
            else
            {}
        }
    });


    decodeCaeser.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            File file;
            String s = null;
            String key =  JOptionPane.showInputDialog("Enter one alphabet key please");
            if(key.length()>1)
            {
                JOptionPane.showMessageDialog(null,"The key must be one alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if( !(Character.isUpperCase(key.charAt(0))||Character.isLowerCase(key.charAt(0))) )
            {
                JOptionPane.showMessageDialog(null,"The key must be an alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int returnVal = fc.showOpenDialog(CryptoPad.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                file = fc.getSelectedFile();
                openedFiles.add(file);
                fileList.setListData(openedFiles);
                Encriptor enc2 = new Encriptor(key.charAt(0));
                try
                {
                    s = enc2.readBytes(file);
                }
                catch(FileNotFoundException fe){}
                catch(IOException ioe){}
                String message = enc2.decodeCaeser(s);
                textBox.setText(message);
            }
        }
    });

        
    decodeSubstitution.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            File file;
            String s = null;
            String key =  JOptionPane.showInputDialog("Enter 26 alphabets key please");
            if(key.length()>26||key.length()<26)
            {
                JOptionPane.showMessageDialog(null,"The Key must be 26 letters","Error",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(hasRepeatedCharacters(key))
            {
                JOptionPane.showMessageDialog(null,"The Key must not have repeated Characters","Error",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int returnVal = fc.showOpenDialog(CryptoPad.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                file = fc.getSelectedFile();
                openedFiles.add(file);
                fileList.setListData(openedFiles);
                Encriptor enc2 = new Encriptor(key);
                try{s = enc2.readBytes(file);}
                catch(FileNotFoundException fe){}
                catch(IOException ioe){}
                String message = enc2.decodeSubstitutionCipher(s);
                textBox.setText(message);
            }
        }
    });

 
 // writing event handlers for menu items

    New.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            textBox.setText(null);
        }
    });


    open.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            int returnVal = fc.showOpenDialog(CryptoPad.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                openedFiles.add(file);
                fileList.setListData(openedFiles);
                currentFileName = file.getName();
                statusBar.setText(currentFileName);
                FileReader reader = null;
                try
                {
                    reader = new FileReader(file);
                    textBox.read(reader,null);
                }
                catch(IOException ioe)
                {
                    textBox.append("unable to open file" + file.getName());
                }
            }// end if
	}// method ends
    });// class and addActionListener ends


    save.addActionListener(new ActionListener()
    {
	public void actionPerformed(ActionEvent e)
	{
            int returnVal = fc.showSaveDialog(CryptoPad.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File saveFile = fc.getSelectedFile();
		FileWriter  writer = null;
		try
		{
                    writer = new FileWriter(saveFile);
                    textBox.write(writer);
                    writer.close();
                }
		catch(IOException ex)
		{
                    textBox.setText(null);
                    textBox.append("unable to save file");
		}
            }
	}
    });


    openDecode.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent ev)
        {
            String code =  JOptionPane.showInputDialog("Enter your password please");
            if(code.equals("rahul")||code.equals("akki")||code.equals("sliet"))
            {
                int returnVal = fc.showOpenDialog(CryptoPad.this);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    openedFiles.add(file);
		    fileList.setListData(openedFiles);
		    try
		    {
                        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                        String coded = in.readUTF();
                        String decoded = encripter.decode(coded);
                        textBox.setText(decoded);
                        in.close();
                    }
                    catch(IOException e)
                    {
                        textBox.setText("Unable to open file "+ file.getName());
                    }
                }// internal if end 
            }// extral if ends
            else
            {
                JOptionPane.showMessageDialog(null,"Sorry you are not authorised for this function","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
        }// end action performed method
    });// end class and action listener method


    saveCoded.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String code =  JOptionPane.showInputDialog("Enter your password please");
            if(code.equals("rahul")||code.equals("akki")||code.equals("sliet"))
            {
                int returnVal = fc.showSaveDialog(CryptoPad.this);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File saveFile = fc.getSelectedFile();
                    try
                    {
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(saveFile)));
                        String tobeCoded = textBox.getText();
                        String coded = encripter.code(tobeCoded);
                        out.writeUTF(coded);
                        out.close();
		    }
		    catch(IOException ex)
		    {       }
		}// end if
            }
            else
                JOptionPane.showMessageDialog(null,"Sorry you are not authorised for this function","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);
        }// end action performed
    });// end class and the action listner
	

    Exit.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent ev)
        {
            //dispose();
            System.exit(0);
        }
    });

 // menuItems for Edit menu
    cut = new JMenuItem("Cut");
    copy = new JMenuItem("Copy");
    paste = new JMenuItem("Paste");
    undo = new JMenuItem("Undo");
    select = new JMenuItem("Select All");
    setFont = new JMenuItem("Font");
 
 // adding them to the edit menu
    edit.add(cut);
    edit.add(copy);
    edit.add(paste);
    edit.add(undo);
    edit.add(select);
    edit.add(setFont);

 // actions for edit menu items
    cut.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
	{
            textBox.cut();
	}
    });

    
    copy.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
	{
            textBox.copy();
	}
    });

    
    paste.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            textBox.paste();
        }
    });

    
    select.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
	{
            textBox.selectAll();
	}
    });

    
    setFont.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
	{
            final FontChooser chooser = new FontChooser(CryptoPad.this);
            boolean first = true;
            chooser.setVisible(true);
            if(chooser.getNewFont() != null)
            {
                textBox.setFont(chooser.getNewFont());
		textBox.setForeground(chooser.getNewColor());
            }
	}
    });

   
 // menu items for the help menu
    Help = new JMenuItem("About");
    help.add(Help);
    Help.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
	{
            JOptionPane.showMessageDialog(null,"This is a simple text editor made using CORE JAVA(GUI packages).\nDeveloped by Rahul Kumar and Aakanchha( HevenTech. Ltd) of SLIET Longowal","About CryptoPad",JOptionPane.INFORMATION_MESSAGE);
	}
    });

    
    menubar = new JMenuBar();
    this.setJMenuBar(menubar);
    menubar.add(file);
    menubar.add(edit);
    menubar.add(help);

/**********************************************************************************/

    fileList = new JList();
    openedFiles = new Vector();  // vector for holding opened files.
    fileList = new JList(openedFiles);
    fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fileList.setSelectedIndex(0);
    fileList.addListSelectionListener(new ListSelectionListener()
    {
        public void valueChanged(ListSelectionEvent e) 
	{
            if (e.getValueIsAdjusting())
	    return;
            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty())
	    {
                textBox.setText(null);
	    }
	    else
	    {
                int index = theList.getSelectedIndex();
	    	File selectedFile =  (File)openedFiles.elementAt(index);
	    	try
	    	{
                    FileReader reader2 = new FileReader(selectedFile);
	    	    textBox.read(reader2,null);
	    	    currentFileName = selectedFile.getName();
                    statusBar.setText(currentFileName);
		}
		catch(IOException ev)
		{
                    textBox.setText(null);
                    textBox.append("unable to open the file"+selectedFile);
		}
	    }
	}// method ends
    });// class and add method ends


    JScrollPane listScrollPane = new JScrollPane(fileList);
    textBox = new TextBox();
    textBox.setMargin(new Insets(5,5,5,5));
    textBox.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,18));
    JScrollPane  textScrollPane = new JScrollPane(textBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,listScrollPane, textScrollPane);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(100);
    splitPane.setDividerSize(5);

    Dimension minimumSize = new Dimension(35, 100);
    listScrollPane.setMinimumSize(minimumSize);
    textScrollPane.setMinimumSize(minimumSize);

    
  /********************************************************************************/
  
 // adding a toolbar and buttons for some common functions
    toolBar = new JToolBar();
    toolBar.setFloatable(true);

 //first button
    saveButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\save.gif"));
    saveButton.setToolTipText("Save");
    saveButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            int returnVal = fc.showSaveDialog(CryptoPad.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File saveFile = fc.getSelectedFile();
 		FileWriter  writer = null;
 		try
 		{
                    writer = new FileWriter(saveFile);
                    textBox.write(writer);
                    writer.close();
                }
		catch(IOException ex)
		{
                    textBox.setText(null);
                    textBox.append("unable to open file");
                }
            }
	}
    });
    toolBar.add(saveButton);
    toolBar.addSeparator();

    
 //second button
    newDocumentButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\new.gif"));
    newDocumentButton.setToolTipText("Open new document");
    newDocumentButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
	{
            textBox.setText(null);
	}
    });
    toolBar.add(newDocumentButton);
    toolBar.addSeparator();
 
    
 //third button
    fileOpenButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\open.gif"));
    fileOpenButton.setToolTipText("Open File");
    fileOpenButton.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent e)
	{
            int returnVal = fc.showOpenDialog(CryptoPad.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
		openedFiles.add(file);
		fileList.setListData(openedFiles);
		FileReader reader = null;
		currentFileName = file.getName();
		statusBar.setText(currentFileName);
		try
		{
                    reader = new FileReader(file);
                    textBox.read(reader,null);
                }
		catch(IOException ioe)
		{
                    textBox.append("unable to open file" + file.getName());
		}
            }// end if
	}// method ends
    });// class and addActionListener ends
    toolBar.add(fileOpenButton);
    toolBar.addSeparator();

    
 // fourth button copy
    copyButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\copy.gif"));
    copyButton.setToolTipText("Copy");
    copyButton.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e)
	{
            textBox.copy();
	}
    });
    toolBar.add(copyButton);
    toolBar.addSeparator();

    
 //fifth Button paste button
    pasteButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\paste.gif"));
    pasteButton.setToolTipText("Paste");
    pasteButton.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
	{
            textBox.paste();
	}
    });
    toolBar.add(pasteButton);
    toolBar.addSeparator();

    
 //sixth button cut Button
    cutButton = new JButton(new ImageIcon("E:\\Java Projects\\Project\\CryptoPad\\src\\cryptopad\\images\\cut.gif"));
    cutButton.setToolTipText("Cut selected text");
    cutButton.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            textBox.cut();
	}
    });
    toolBar.add(cutButton);
    toolBar.addSeparator();

    
    textBox.getDocument().addDocumentListener( new DocumentListener()
    {
        public void insertUpdate(DocumentEvent e)
        {
            textChanged = true;
            System.out.print(textBox.getText().charAt(e.getOffset()));
	}
        public void removeUpdate(DocumentEvent e)
        {
            textChanged = true;
            System.out.println(textChanged);
	}
        public void changedUpdate(DocumentEvent e)
        {
            textChanged = true;
            System.out.println(textChanged);
	}
    });


    this.addWindowListener(new WindowAdapter() 
    {
        public void windowClosing(WindowEvent e)
	{
            if(textChanged==true)
            {
                if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(null,"Do you want to save changes you have made","Alert !",JOptionPane.YES_NO_OPTION))
		{
				// code for if text has been changed
		}
            }
            System.exit(0);
        }// end of method
    });
 
 /********************************************************************************/
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.setPreferredSize(new Dimension(400, 100));
    contentPane.add(toolBar, BorderLayout.NORTH);
    contentPane.add(splitPane, BorderLayout.CENTER);
    contentPane.add(statusBar,BorderLayout.SOUTH);
    setContentPane(contentPane);

/*********************************************************************************/
 // setting up and innitializing JPopupMenu
    popChoice = new JPopupMenu();
    popChoice.setInvoker(fileList);
    MouseListener popupListener = new PopupListener();
    fileList.addMouseListener(popupListener);
    closeFileInList = new JMenuItem("close");
    popChoice.add(closeFileInList);
    closeFileInList.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent ev)
        {   
            //JList theList = (JList)ev.getSource();
            int index = fileList.getSelectedIndex();
	    File selectedFile =  (File)openedFiles.elementAt(index);
	    openedFiles.remove(selectedFile);
	    fileList.setListData(openedFiles);
	    statusBar.setText("CryptoPad");
	}
    });

    this.pack();
    this.setBounds(50,50,600,400);
    setVisible(true);
}  
// constructor ends


    public boolean hasRepeatedCharacters(String key)
    {
        boolean repeated = false;
        key = key.toUpperCase();
        for(int i=0;i<(key.length()-1);i++)
        {
            for(int j=i+1;j<key.length();j++)
            {
                if(key.charAt(i)==key.charAt(j))
                {
                    repeated = true;
                }
            }
        }
        return repeated;
    }

    private void doVigenereCipher()
    {
        File saveFile = null;
        long beginTime = 0L,endTime  = 0L,difference = 0L;
        String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
        if(key.length()> textBox.getText().length())
        {
            JOptionPane.showMessageDialog(null,"The key must be less than the text to be coded","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
            return;
        }
        int returnVal = fc.showSaveDialog(CryptoPad.this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            saveFile = fc.getSelectedFile();
            Vigenere enc = new Vigenere (key);
            String message = textBox.getText();
            beginTime = System.currentTimeMillis();
            String coded = enc.doVigenereCipher(message);
            endTime = System.currentTimeMillis();
            try
            {
                enc.writeBytes(coded,saveFile);
            }
            catch(FileNotFoundException fe)
            {
                JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
            }
            catch(IOException ioe)
            {
                JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
            }
        }// endif
        JOptionPane.showMessageDialog(null,"The time it took to cipher is "+ difference +" milli seconds","Statistics",JOptionPane.PLAIN_MESSAGE);
    }


    private void doVigenereDecipher()
    {
        File file;
	long beginTime = 0L,endTime  = 0L,difference = 0L;
	Date beg,end;
	String s = null;
	String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
	int returnVal = fc.showOpenDialog(CryptoPad.this);
	if (returnVal == JFileChooser.APPROVE_OPTION)
   	{
            file = fc.getSelectedFile();
            openedFiles.add(file);
            fileList.setListData(openedFiles);
            Vigenere  enc = new Vigenere(key);
            try{s = enc.readBytes(file);}
            catch(FileNotFoundException fe){}
            catch(IOException ioe){}
            if(key.length() > s.length()) return;
            beginTime = System.currentTimeMillis();
            beg = new Date();
            String message = enc.doVigenereDecipher(s);
            end = new Date();
            //endTime = System.currentTimeMillis();
            difference = beg.getTime() - end.getTime();
            textBox.setText(message);
            JOptionPane.showMessageDialog(null,"The time it took to cipher is "+ difference +" milli seconds","Statistics",JOptionPane.PLAIN_MESSAGE);
	}
    }


    private void doMonoSubstitutionCipher()
    {
        File saveFile = null;
        String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
        int returnVal = fc.showSaveDialog(CryptoPad.this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            saveFile = fc.getSelectedFile();
            MonoSubstitutionCipher enc = new MonoSubstitutionCipher(key);
            String message = textBox.getText();
            String coded = enc.code(message);
            try
            {
                enc.writeBytes(coded,saveFile);
            }
            catch(FileNotFoundException fe)
            {
                JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
            }
            catch(IOException ioe)
            {
                JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
            }
        }//endif
    }


    private void doMonoSubstitutionDecipher()
    {
        File file;
        String s = null;
        String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
        int returnVal = fc.showOpenDialog(CryptoPad.this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            file = fc.getSelectedFile();
            openedFiles.add(file);
            fileList.setListData(openedFiles);
            MonoSubstitutionCipher enc = new MonoSubstitutionCipher(key);
            try{s = enc.readBytes(file);}
            catch(FileNotFoundException fe){}
            catch(IOException ioe){}
            if(key.length() > s.length()) return;
            String message = enc.decode(s);
            textBox.setText(message);
        }
    }


 /*Inner class for handeling popup menu trigger event(left click for window platform.*/
    class PopupListener extends MouseAdapter 
    {
        public void mousePressed(MouseEvent e) 
        {
            maybeShowPopup(e);
        }

    public void mouseReleased(MouseEvent e) 
    {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) 
    {
	if ((e.isPopupTrigger()) && (fileList.getSelectedIndex() != -1))
        {
            popChoice.show(e.getComponent(),e.getX(), e.getY());
        }
    }
}


/***********************************************************************
*******************************MAIN*************************************
************************************************************************/

    public static void main(String args[])
    {
        String code =  JOptionPane.showInputDialog("Enter your password please");
            if(code.equals("rahul")||code.equals("akki")||code.equals("sliet"))
            {
                System.out.println("Loading CryptoPad Please wait");
                CryptoPad myPad = new CryptoPad();  
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Sorry you are not authorised for this function","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);
                System.exit(1);
            }
    }
}

/********************************end of CryptoPad class*****************
**********************************************************************/