package cryptopad;

import java.io.*;
/**
 *
 * @author rahul
 */

public class MonoSubstitutionCipher
{
	private String key;
	private String referenceString;
	private String alphabets ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public MonoSubstitutionCipher(String key)
	{
		this.key = key.trim();
		referenceString = getReferenceString();
	}
	private String getReferenceString()
	{
		String ref = "";
		key = key.toUpperCase();
		key = removeAllCharacters(key,' ');
		final char upperA = 'A';
		final char upperZ = 'Z';
		// removing repeated characters from the key
		while(hasRepeatedCharacters(key))
		 key = removeRepeatedChars(key);
		System.out.println(key);
		ref = key;

		char first = ref.charAt(ref.length() -1);

		int len = key.length();
		len = 26 - len;
		for(int i=0;ref.length()<27;i++)//i<=len
		{
			if(ref.indexOf(first) < 0)// if it is not a repeated character
			{						  // add it to the string ref.
				if(first > upperZ)
				{
				first = (char) (first - upperZ);
				first = (char) ((upperA + first)-1);
				ref += first;
				}
				else// adjust it to repeat characters after Z
				{
				 ref += first;
				}
			}
			first ++; // increment first

		}//for

		return removeRepeatedChars(ref);
	}


	public String code(String message)
	{
		String upperMessage = message.toUpperCase();
		String coded = "";
		for(int i=0;i<upperMessage.length();i++)
		{
			if(Character.isLowerCase(message.charAt(i)))
		     coded += Character.toLowerCase(referenceString.charAt(alphabets.indexOf(upperMessage.charAt(i))));
		    else if(Character.isUpperCase(message.charAt(i)))
		     coded += Character.toUpperCase(referenceString.charAt(alphabets.indexOf(upperMessage.charAt(i))));
		    else
		     coded += upperMessage.charAt(i);
		 }
		 return coded;
	}

	public String decode(String coded)
	{
		String upperCoded = coded.toUpperCase();
		String decoded = "";
		for(int i=0;i<coded.length();i++)
		{
			if(Character.isLowerCase(coded.charAt(i)))
			 decoded += Character.toLowerCase(alphabets.charAt(referenceString.indexOf(upperCoded.charAt(i))));
			else if(Character.isUpperCase(coded.charAt(i)))
			 decoded += Character.toUpperCase(alphabets.charAt(referenceString.indexOf(upperCoded.charAt(i))));
			else
			 decoded += upperCoded.charAt(i);
		}
		return decoded;
	}


	private boolean hasRepeatedCharacters(String key)
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

   private String removeRepeatedChars(String message)
   {
	  int index = 0;
	  for(int i=0;i<message.length();i++)
	  {
		if((index = message.indexOf(message.charAt(i),i+1)) > -1)
		{
			message = removeCharacter(message,message.charAt(index),index);
	    }
      }
      return message;
   }

   public static void main(String [] args)
   {
	   MonoSubstitutionCipher mc = new MonoSubstitutionCipher("ALIALONGLEGS");
	   String coded = mc.code("I AM a Teacher Who The Hell are You \n I Love pakistan");
	   System.out.println(coded);
	   String decoded = mc.decode(coded);
	   System.out.println(decoded);


   }

   private String removeCharacter(String message,char ch,int index)
     {
   	     StringBuffer buffer = new StringBuffer(message);
         buffer.deleteCharAt(index);
         return buffer.toString();
     }

     private String removeAllCharacters(String message,char ch)
	 {
	    	  StringBuffer buffer = new StringBuffer(message);
	    	  int index = 0;
	    	  while( (index = message.indexOf(ch,index)) > -1)
	    	  {
	    	    buffer.deleteCharAt(index);
	    	    message = buffer.toString();
	          }
	          return buffer.toString();
     }

  private int[] indexesOf(String message,char ch,int fromIndex)
  {
	  if(message.indexOf(ch,fromIndex) < 0)
	  return null;
	  int noOfch = 0;
	  char[] array = message.toCharArray();
	  for(int i=fromIndex;i<message.length();i++){
	   if(array[i] == ch)
	    noOfch++;
	  }//for

	  int[] indexArray = new int[noOfch];
	  int ii=0;
	  for(int j=fromIndex;j<indexArray.length;j++)
	  {
		  if(array[j] == ch)
		  indexArray[ii++] = j;
	  }

	  return indexArray;
  }


  public void writeBytes(String s,File file) throws FileNotFoundException
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
  		 catch(IOException eeio){}
  	 }
  	 catch(IOException eio)
  	 {
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
