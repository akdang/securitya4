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
			
			read(reference);
			printFollowers();
			
			while(pwCount != 0)
			{
				pwCount--;
				int currentPWLength = pwLength;
				String result = firstChar();
				currentPWLength--;
				while(currentPWLength != 0)
				{
					char prevChar = result.charAt(result.length()-1);
					result += nextChar(prevChar);
					currentPWLength--;
				}
				results[pwCount] = result; 
			}
			
			System.out.println("Passwords are:");
			for (String result: results)
				System.out.println(result);
		}
	}
	
	private static String firstChar() 
	{
		String result = "";
		int sumStarters = starters[26];
		
		//building starterRatios - containing ratios of starters[i]/sumStarters 
		Rational[] starterRatios = new Rational[26];
		for(int i = 0; i < starterRatios.length; i++)
			starterRatios[i] = new Rational(starters[i], sumStarters);
		//System.out.println(java.util.Arrays.toString(ratioSums));
		
		//randomly generated ratio in range of probabilities of starters
		Rational randomRatio = new Rational(rand.nextInt(sumStarters), sumStarters);
		//System.out.println(randomRatio);
		
		Rational ratioSum = starterRatios[0]; //sums ratios of starterRatios
		for(int i = 0; i < starterRatios.length; i++)
		{
			if(!ratioSum.greaterThan(randomRatio))
			{
				//System.out.println("currentcoutn at "+(char)('a'+i)+"="+currentCount); 
				ratioSum.add(starterRatios[i+1]);
			}
			else
			{
				result =  (char)('a' + i) + "";
				break;
			}
		}
		
		return result;
	}
	
	private static String nextChar(char previous) 
	{
		//relevant row for given previous char
		int[] row = followers[previous-'a'];
		
		String result = "";
		int sumRow = row[26];
		assert sumRow > 0;
		
		//building rowRatios - containing ratios of row[i]/sumRow 
		Rational[] rowRatios = new Rational[26];
		for(int i = 0; i < rowRatios.length; i++)
			rowRatios[i] = new Rational(row[i], sumRow);
		//System.out.println(java.util.Arrays.toString(ratioSums));

		//TODO breaks if no followers ( a file of one letter words )
		//randomly generated ratio in range of probabilities of row
		Rational randomRatio = new Rational(rand.nextInt(sumRow), sumRow);
		//System.out.println(randomRatio);
		
		Rational ratioSum = rowRatios[0]; //sums ratios of rowRatios
		for(int i = 0; i < rowRatios.length; i++)
		{
			if(!ratioSum.greaterThan(randomRatio))
			{
				//System.out.println("currentcoutn at "+(char)('a'+i)+"="+currentCount); 
				ratioSum.add(rowRatios[i+1]);
			}
			else
			{
				result =  (char)('a' + i) + "";
				break;
			}
		}
		
		return result;
	}

	/**
	 * @param reference
	 */
	private static void read(File reference) {
		try 
		{
			//TODO
			read = new Scanner(reference);//new Scanner("a b c d e f g h i j k l m n o p q r s t u v w x y z ");//
			read.useDelimiter("(\\s|[^a-zA-Z])+");
			if (false)
				throw new FileNotFoundException();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		while(read.hasNext())
		{
			String next = read.next().toLowerCase();
			starters[next.charAt(0)-'a']++;
			starters[26]++;		// 27th column keeps sum of 0 - 26th columns
			
			for(int i=0; i<next.length()-1; i++)
			{
				//TODO one letter words not to counts?
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
	 * 
	 */
	private static void printFollowers() {
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
}
