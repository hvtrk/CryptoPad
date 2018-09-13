package cryptopad;
/*
* @author 
* @Rahul Kumar
* @Aakanchha
*/
public class Encripter
{
    public String code(String s)
    {
        char charArr[] = s.toCharArray();
        for(int i=0;i<s.length();i++)
        {
            charArr[i] += i;
        }
	return new String(charArr);
    }

    public String decode(String s)
    {
	char charArr[] = s.toCharArray();
	for(int i=0;i<charArr.length;i++)
	{
            charArr[i] -= i;
        }
    return new String(charArr);
    }
}