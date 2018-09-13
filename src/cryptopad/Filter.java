package cryptopad;

import java.io.*;
import java.util.*;
/*
* @author rahul
*/

public class Filter extends javax.swing.filechooser.FileFilter
{
    Vector ext = new Vector(5,5);
    public boolean accept(File f)
    {
        
        if(f.isDirectory())
	return true;
	String fileName = f.getName();
	String extention = fileName.substring(fileName.indexOf('.') + 1);
	if(ext.contains(extention))
	return true;
	else
	return false;
    }

    public void addExtention(String ex)
    {
	ext.add(ex);
    }   

    public String getDescription()
    {
    	return "filter";
    }
}

