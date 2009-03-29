/**
 * @author Chris Cunningham
 * @author Anh Khoi Dang
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
	private static String[] results;
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
			if(Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <=0)
				System.exit(0);
			
			File reference = new File(REFERENCE);
			pwLength = Integer.parseInt(args[0]);
			pwCount = Integer.parseInt(args[1]);
			rand = (args.length == 3) ? new Random(Integer.parseInt(args[2])) : new Random();
			followers = new int[26][27];
			starters = new int[27];
			counts = new int[26];
			results = new String[pwCount];
			
			read(reference); 	//reading reference file
			printFollowers();	//printing followers table
			
			int resultCount = 0;
			while(pwCount != 0)	//generating passwords
			{
				pwCount--;
				int currentPWLength = pwLength;
				String result = nextChar(starters);
				currentPWLength--;
				while(currentPWLength != 0)
				{
					char prevChar = result.charAt(result.length()-1);
					result += nextChar(followers[prevChar-'a']);
					currentPWLength--;
				}
				results[resultCount] = result;
				resultCount++;
			}
			
			//printing passwords
			System.out.println("\n"+"Passwords are:");
			for (String result: results)
				System.out.println(result);
		}
	}
	
	/**
	 * Reads in the file named "reference" as input
	 * 
	 * @param reference the input file
	 */
	private static void read(File reference) 
	{
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
			if(next.length() == 1) //skipping one letter words
				continue;
			starters[next.charAt(0)-'a']++;
			starters[26]++;		// 27th column keeps sum of 0 - 26th columns
			
			for(int i=0; i<next.length()-1; i++)
			{
				char a = next.charAt(i);
				char b = next.charAt(i+1);
				counts[a - 'a']++;
				followers[a - 'a'][b - 'a']++;
				followers[a - 'a'][26]++;		// 27th column keeps sum of 0 - 26th columns
				assert followers[a - 'a'][26] > 0;
			}
		}
	}

	
	/**
	 * Prints out the followers table.
	 */
	private static void printFollowers() 
	{
		System.out.println("\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ\tK\tL\tM\tN\tO\tP\tQ\tR\tS\tT\tU\tV\tW\tX\tY\tZ");
		char rowLetter = 'A';
		for (int[] row : followers)
		{
			System.out.print(rowLetter + ":");
			for(int col = 0; col<row.length-1; col++)
				System.out.print("\t" + row[col]);
			rowLetter++;
			System.out.println();
		}
	}
	
	
	/**
	 * Using the relevantRow, selects the next character to be used in the password.
	 * 
	 * @param relevantRow row of counts to be considered
	 * @return the next selected character
	 */
	private static String nextChar(int[] relevantRow) 
	{
		String result = "";
		int totalRowSum = relevantRow[26];
		assert totalRowSum > 0;
		
		int[] rowSums = new int[26];
		rowSums[0] = relevantRow[0];
		for(int i = 1; i<rowSums.length; i++)
		{
			rowSums[i] = rowSums[i-1] + relevantRow[i];
			assert rowSums[i] >= 0 : "Row sums overflow.";
		}

		int randomPick = rand.nextInt(totalRowSum);
		for (int i=0; i<rowSums.length; i++)
		{
			if(rowSums[i]>randomPick)
			{
				result = (char)('a'+i) + "";
				break;
			}
		}
		assert ! result.equals("") : "nextChar not generated.";
		return result;
	}
}
