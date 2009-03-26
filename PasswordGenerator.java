/*
 * java PasswordGenerator [# of characters] [# of passwords] [seed #]

Your reference file name is just "reference". 
 */

import java.util.*;
import java.io.*;

public class PasswordGenerator 
{
	private static final String REFERENCE = "reference";
	private static int pwLength;
	private static int pwCount;
	private static Random rand;
	private static int[][] followers;
	private static int[] starters;
	private static int[] counts;
	private static Scanner read = null;
	
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("usage: java PasswordGenerator [# of characters] [# of passwords] [optional seed #]");
			System.exit(1);
		}
		else
		{
			File reference = new File(REFERENCE);
			pwLength = Integer.parseInt(args[0]);
			pwCount = Integer.parseInt(args[1]);
			rand = (args.length == 3) ? new Random(Integer.parseInt(args[2])) : new Random();
			followers = new int[26][26];
			starters = new int[26];
			counts = new int[26];
			try 
			{
				read = new Scanner(reference);
				read.useDelimiter("(\\s|[^a-zA-Z])+");
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			while(read.hasNext())
			{
				String next = read.next().toLowerCase();
				starters[next.charAt(0)-'a']++;
				
				for(int i=0; i<next.length()-1; i++)
				{
					//TODO one letter words not to counts?
					char a = next.charAt(i);
					char b = next.charAt(i+1);
					counts[a - 'a']++;
					followers[a - 'a'][b - 'a']++;
				}
			}
			
			System.out.println("\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\tL\tM\tN\tO\tP\tQ\tR\tS\tT\tU\tV\tW\tX\tY\tZ");
			char rowLetter = 'A';
			for (int[] row : followers)
			{
				System.out.print(rowLetter + ":");
				for(int col : row)
					System.out.print("\t" + col);
				rowLetter++;
				System.out.println();
			}
				
				
		}
	}
}
