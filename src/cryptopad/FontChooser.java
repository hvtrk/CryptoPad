
package cryptopad;
/**
 *
 * @author rahul
 */
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;


public class FontChooser extends JDialog implements ActionListener
{
    JColorChooser colorChooser;
    JComboBox fontName;
    JCheckBox fontBold,fontItalic;
    JTextField fontSize;
    JLabel perviewLabel;
    SimpleAttributeSet attributes;
    Font newFont;
    Color newColor;


    public FontChooser(Frame parent) // constructor
    {
        super(parent,"Font Chooser",true);
	setSize(400,400);
	attributes = new SimpleAttributeSet();
        addWindowListener(new WindowAdapter()
	{
            public void windowClosing(WindowEvent we)
            {
                closeAndCancel();
            }
	});

	Container c = getContentPane();

        JPanel fontPanel = new JPanel();
	fontName = new JComboBox(new String[] {"TimesRoman","Helvetica","Courier"});

        fontName.setSelectedIndex(1);
        fontName.addActionListener(this);
        fontSize = new JTextField("12",4);
        fontSize.setHorizontalAlignment(SwingConstants.RIGHT);
        fontSize.addActionListener(this);
        fontBold = new JCheckBox("Bold");
        fontBold.setSelected(true);
        fontBold.addActionListener(this);
        fontItalic = new JCheckBox("Italic");
        fontItalic.setSelected(false);
        fontItalic.addActionListener(this);

        fontPanel.add(fontName);
        fontPanel.add(new JLabel("Size: "));
        fontPanel.add(fontSize);
        fontPanel.add(fontBold);
        fontPanel.add(fontItalic);

        // adding fontpanel to the container

        c.add(fontPanel,BorderLayout.NORTH);

	// color chooser panel

	colorChooser = new JColorChooser(Color.black);
	colorChooser.getSelectionModel().addChangeListener(
	new ChangeListener()
	{
            public void stateChanged(ChangeEvent e)
            {
                updatePerviewColor();
            }
	});

	// add color chooser to the container
	
        c.add(colorChooser,BorderLayout.CENTER);

	JPanel perviewPanel = new JPanel(new BorderLayout());
	perviewLabel = new JLabel("Sample font ABC abc");
	perviewLabel.setForeground(colorChooser.getColor());


	JButton okButton =  new JButton("OK");
	okButton.addActionListener(new ActionListener()
	{
            public void actionPerformed(ActionEvent ev)
            {
                closeAndSave();
            }
	});

	JButton cancelButton = new JButton("Cancel");
	cancelButton.addActionListener(new ActionListener()
	{
            public void actionPerformed(ActionEvent ev)
            {
                closeAndCancel();
            }
	});

	JPanel controlPanel = new JPanel();
	controlPanel.add(okButton);
	controlPanel.add(cancelButton);
	perviewPanel.add(controlPanel,BorderLayout.SOUTH);
	perviewPanel.setMinimumSize(new Dimension(100,100));
	perviewPanel.setPreferredSize(new Dimension(100,100));

        c.add(perviewPanel,BorderLayout.SOUTH);
}// constructor ends


	public void actionPerformed(ActionEvent ev)
	{
		if(!StyleConstants.getFontFamily(attributes).
		   equals(fontName.getSelectedItem()))
		   {
			   StyleConstants.setFontFamily(attributes,
			   (String)fontName.getSelectedItem());
		   }

		if(StyleConstants.getFontSize(attributes) !=
		    Integer.parseInt(fontSize.getText()))
		    {
				StyleConstants.setFontSize(attributes,Integer.parseInt(fontSize.getText()));
			}

		if(StyleConstants.isBold(attributes) != fontBold.isSelected())
		{
			StyleConstants.setBold(attributes,fontBold.isSelected());
		}


		if(StyleConstants.isItalic(attributes) != fontItalic.isSelected())
		{
					StyleConstants.setBold(attributes,fontBold.isSelected());
		}

		updatePerviewFont();
	} // end actionperformed


	protected void updatePerviewFont()
	{
		String name = StyleConstants.getFontFamily(attributes);
		boolean bold = StyleConstants.isBold(attributes);
		boolean italic = StyleConstants.isItalic(attributes);
		int size =  StyleConstants.getFontSize(attributes);

		Font f = new Font(name,(bold ? Font.BOLD :0) + (italic ? Font.ITALIC :0),size);
		perviewLabel.setFont(f);
	}


	protected void updatePerviewColor()
	{
		perviewLabel.setForeground(colorChooser.getColor());
		perviewLabel.repaint();
	}

	public Font getNewFont()
	{
		return newFont;
	}

	public Color getNewColor()
	{
		return newColor;
	}


	public AttributeSet getAttributes()
	{
		return attributes;
	}


	public void closeAndSave()
	{
		newFont  = perviewLabel.getFont();
		newColor = perviewLabel.getForeground();
		setVisible(false);
	}

	public void closeAndCancel()
	{
		newFont = null;
		newColor = null;
		setVisible(false);
	}
}// class ends
