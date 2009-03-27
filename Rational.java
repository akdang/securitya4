/**
 * @author Chris Cunningham
 * @author Anh Khoi Dang
 * Models rational number - defined as integer/integer
 */
public class Rational 
{
	private int numerator, denominator;
	
	/**
	 * constructs rational number
	 * @param numerator
	 * @param denominator
	 */
	public Rational(int numerator, int denominator)
	{
	
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
	}

	/**
	 * Adds right to this, updating this
	 * @param right Rational object to add to this.
	 */
	public void add(Rational right)
	{
		numerator = this.numerator*right.denominator + right.numerator*this.denominator;
		denominator = this.denominator*right.denominator;
		reduce();
	}
	
	/**
	 * reduces rational number
	 */
	public void reduce()
	{
		int gcd = gcd(numerator, denominator);
		numerator = numerator/gcd;
		denominator = denominator/gcd;
	}
	
	/**
	 * Checks if this is greater than right
	 * @param right Rational compared to
	 * @return true if this is greater than right
	 */
	public boolean greaterThan(Rational right)
	{
		int leftNumerator = this.numerator*right.denominator;
		int rightNumerator = right.numerator*this.denominator;
		if(leftNumerator > rightNumerator)
			return true;
		else
			return false;
	}
	
	/**
	 * overloaded toString
	 */
	public String toString()
	{
		return numerator + "/" + denominator;
	}
	
	//Euclidean gcd implementation
	private int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a%b);
	}
}
