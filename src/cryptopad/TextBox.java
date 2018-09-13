package cryptopad;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import javax.swing.JTextArea;

/**
 *
 * @author rahul
 */
class TextBox extends JTextArea implements Printable
{

 int c = 0;
	TextBox()
	{
		super();
	}


	public int print(Graphics g,PageFormat pg, int pageIndex)
	{
		if(pageIndex>0) return Printable.NO_SUCH_PAGE;
		Graphics2D g2g = (Graphics2D) g;
		g2g.translate(pg.getImageableX(),pg.getImageableY());
		// this function needs a major over haul to print the
		//   pages till the end of the document.
		paint(g2g);
		//System.out.println("calling print " + c++ + pageIndex);
		return Printable.PAGE_EXISTS;
	}
}
