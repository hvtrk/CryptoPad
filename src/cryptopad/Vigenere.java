package cryptopad;

import java.io.*;
/**
 *
 * @author rahul
 *
*
*This class complies with the specification of Vigenere Cipher
*It has the ability to cipher any string using a key. The key is first mapped to
*produce a cipher key set. and then that key set is used to cipher the actual text.
*/
public class Vigenere
{
  String key; // string to hold the key
 /**The constructor takes a string key as an argument */
  public Vigenere(String key)
  {
	  this.key = key;
  }
/**This function takes the message String as an argument and returns a ciphered String object.*/
 public String doVigenereCipher(String messageString)
 {
	 String kmap  = setKeyMap(key,messageString);
	 char[] keyArray = key.toCharArray();
	 char[] messageArray = messageString.toCharArray();
	 char[] keymapArray = kmap.toCharArray();
	 char[] codedArray = new char[messageArray.length];
	 for(int i=0;i<messageArray.length;i++)
	 {
		 if(Character.isUpperCase(messageString.charAt(i))||Character.isLowerCase(messageString.charAt(i)))
		  codedArray[i] = getCodedChar(messageArray[i],keymapArray[i]);
		 else
		  codedArray[i] = messageArray[i];
	 }
	 return new String(codedArray);
 }
/**This function takes a String coded as argument and returns a deciphered String.*/
 public String doVigenereDecipher(String coded)
 {
	 char[] codedArray = coded.toCharArray();
	 char[] keyArray = key.toCharArray();
	 String kmap = setKeyMap(key,coded);
	 char[] keymapArray = kmap.toCharArray();
	 char[] decodedArray = new char[coded.length()];
	 for(int i=0;i<codedArray.length;i++)
	 {
		if(Character.isUpperCase(coded.charAt(i))||Character.isLowerCase(coded.charAt(i)))
		  decodedArray[i] = getDecodedChar(codedArray[i],keymapArray[i]);
		 else
		  decodedArray[i] = codedArray[i];
	  }
	  return new String(decodedArray);
  }
  /**This function takes two characters as arguments.The first a from message String
  *and the second b from the mapped key and returns the coded character. this method
  *is private and is called in doVigenereCipher() function.
  */

 private char getCodedChar(char a,char b)// a is from the message
 {										 // while b is from the key map
	 char aa = Character.toUpperCase(a);
	 char bb = Character.toUpperCase(b);
	 char upperZ = 'Z';
	 char upperA = 'A';
	 char difference = (char)(aa - upperA);
	 char coded = (char)(bb + difference);
	 if(coded > upperZ)
	  {
	 	coded = (char) (coded - upperZ);
	    coded = (char) ((upperA + coded)-1);
	  }
	  if(Character.isUpperCase(a))
	  return coded;
	  else
	  return Character.toLowerCase(coded);
 }
/**This menthod is private and is called inside doVigenereDecipher() method
*it takes two character arguments first from coded String and the second from the
*mapped String and returns the decoded character.
*/
 private char getDecodedChar(char a, char b)// a is from the coded String
 {											// b is from the mapped String.
	 char aa = Character.toUpperCase(a);
	 char bb = Character.toUpperCase(b);
	 char upperZ = 'Z';
	 char upperA = 'A';
	 int difference = (aa - bb);
	 if(difference < 0)
	 {
	 int one = (upperZ - bb);
	 int  two =  (aa - upperA);
	 difference = (one + two + 1);
 	 }
 	 char decoded =(char) (upperA + difference);
 	 if(Character.isUpperCase(a))
 	  return decoded;
 	 else
 	  return Character.toLowerCase(decoded);
 }

/**this private method takes the key and the message Strings as arguments and
*returns a String mapped on message String according to the key.
*@param String key the key for cipher
*@param String message the String to be decoded
*@return String keymap which is a String mapping key to the message.
*/
 private String setKeyMap(String key,String message)// sets the keymap String as per the key.
 {
	 if(key.length() > message.length())
	   throw new IllegalArgumentException("key must not be longer than the text to be Ciphered.");
	 char[] keyArray = key.toCharArray();
	 char[] messageArray = message.toCharArray();
	 char[] keymapArray = new char[message.length()];
	 int i = 0;
	 int j = i;
	 while(j<messageArray.length)
	 {

	   for(i=0;i<keyArray.length;i++,j++)
	    {
	     if(j >= messageArray.length) break;
		 if(messageArray[j] != ' ')
		 keymapArray[j] = keyArray[i];
		 else{
		    keymapArray[j] = ' ';
		     i--;
	 		}
	 	}
    }// end while
    return new String(keymapArray);
 }

/*@param String s the string to be written to the file.
*@param File file the file to which the String is to be written to.
*/
 public void writeBytes(String s,File file) throws FileNotFoundException,IOException
 {
 	FileOutputStream fos = new FileOutputStream(file);
 	try{
 		fos.write(s.getBytes("ASCII"));
 		fos.close();
 	}
 	catch(UnsupportedEncodingException ex)
 	{
 		 try{
 			 fos.write(s.getBytes());
 			 fos.close();
 			 }
 		 catch(UnsupportedEncodingException ee){}

 	 }

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

}
