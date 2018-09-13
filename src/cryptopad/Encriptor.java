package cryptopad;
/**
 *
 * @author rahul
 */

import java.io.*;
import java.util.*;

public class Encriptor
{
    private char key;
    private String substitutionKey;
    private Hashtable codeTable  = new Hashtable(26);
    private Hashtable decodeTable = new Hashtable(26);

    public Encriptor(char key)//constructor
    {
        this.key = key;
        substitutionKey = null;
    }

    public Encriptor(String key)// overloaded Constructor
    {
        if(key.length() > 26)
	throw new IllegalArgumentException("the key must be 26 alphabets");
	if(hasRepeatedCharacters(key))
	throw new IllegalArgumentException("the key must not have repeated alphabets");
        this.substitutionKey = key;
	for(int i=0;i<key.length();i++)
	{
            codeTable.put(new Character((char)(65 + i)),new Character(Character.toUpperCase(substitutionKey.charAt(i))));
            decodeTable.put(new Character(Character.toUpperCase(substitutionKey.charAt(i))),new Character((char)(65 + i)));
	}
    }

    public void setCharKey(char key)
    {
        this.key = key;
    }

    public void setSubstitutionKey(String key)
    {
        this.substitutionKey = key;
    }

    public void writeBytes(String s,File file) throws FileNotFoundException
    {
        FileOutputStream fos = new FileOutputStream(file);
	try
        {
            fos.write(s.getBytes("ASCII"));
            fos.close();
	}
	catch(UnsupportedEncodingException ex)
	{
            try 
            {
                fos.write(s.getBytes());
		fos.close();
            }
            catch(UnsupportedEncodingException ee){}
             catch(IOException eeio){}
	}
	catch(IOException eio)
	{   }
    }

    public String readBytes(File file)throws FileNotFoundException,IOException
    {
	byte[] theData;
	FileInputStream fis = new FileInputStream(file);
	theData  = new byte[fis.available()];
	fis.read(theData);
	fis.close();
        return new String(theData);
    }

    public String codeCaeser(String s)
    {
        char[] cArray = s.toCharArray();
	char upperA = 'A';
	char upperKey = Character.toUpperCase(key);
	char offset = (char) (upperKey - upperA);
        for(int i=0;i<cArray.length;i++)
        {
            if(Character.isLowerCase(cArray[i])||Character.isUpperCase(cArray[i]))
            {
                cArray[i] = codeCharacter(cArray[i],offset);
            }
	}

	return new String(cArray);
    }

    public String decodeCaeser(String s)
    {
	char[] cArray = s.toCharArray();
	char upperA = 'A';
	char upperKey = Character.toUpperCase(key);
	char offset = (char) (upperKey - upperA);
	for(int i=0;i<cArray.length;i++)
        {
            if(Character.isLowerCase(cArray[i])||Character.isUpperCase(cArray[i]))
            {
		cArray[i] = deCodeCharacter(cArray[i],offset);
            }
	}
	return new String(cArray);
    }

    private char codeCharacter(char ch,char offset)
    {
	char upperA = 'A';
	char upperZ = 'Z';
	char length = 26;
	char coded = Character.toUpperCase(ch);
	coded += offset;
	if(coded > upperZ)
	{
            coded = (char) (coded - upperZ);
            coded = (char) ((upperA + coded)-1);
        }
	if(Character.isUpperCase(ch))
	return coded;
	else
	return Character.toLowerCase(coded);
    }
    
    private char deCodeCharacter(char ch,char offset)
    {
	char upperA = 'A';
	char coded = Character.toUpperCase(ch);
        coded -= offset;

	if(coded < upperA)
	coded =(char)(coded + 26);
	
        return (Character.isUpperCase(ch))?coded:Character.toLowerCase(coded);
    }

    public String codeSubstitutionCipher(String s)
    {
        char[] cArray = s.toCharArray();
        String coded;
        for(int i =0;i<s.length();i++)
        {
            if(Character.isUpperCase(s.charAt(i))||Character.isLowerCase(s.charAt(i)))
            {
                cArray[i] = codeSubstitutionCharacter(cArray[i]);
            }
        }
        return new String(cArray);
    }

    public String decodeSubstitutionCipher(String s)
    {
        char[] cArray = s.toCharArray();
        for(int i =0;i<s.length();i++)
        {
            if(Character.isUpperCase(s.charAt(i))||Character.isLowerCase(s.charAt(i)))
            {
                cArray[i] = decodeSubstitutionCharacter(cArray[i]);
            }
        }
        return new String(cArray);
    }

    private char codeSubstitutionCharacter(char ch)
    {
        Character key = new Character(Character.toUpperCase(ch));
	Character character = (Character) (codeTable.get(key));
	if(Character.isUpperCase(ch))
	   return Character.toUpperCase(character.charValue());
	else
	   return Character.toLowerCase(character.charValue());
    }

    private char decodeSubstitutionCharacter(char ch)
    {
	Object obj = decodeTable.get(new Character(Character.toUpperCase(ch)));
	Character character = (Character) obj;
	if(Character.isUpperCase(ch))
	 return Character.toUpperCase(character.charValue());
        else
	 return Character.toLowerCase(character.charValue());
	 //return character.charValue();
    }


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
}// end class






