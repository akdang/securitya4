public class Rational 
{
	private int numerator, denominator;
	
	public Rational(int numerator, int denominator)
	{
	
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
	}

	public void add(Rational right)
	{
		numerator = this.numerator*right.denominator + right.numerator*this.denominator;
		denominator = this.denominator*right.denominator;
		reduce();
	}
	
	public void reduce()
	{
		int gcd = gcd(numerator, denominator);
		numerator = numerator/gcd;
		denominator = denominator/gcd;
	}
	
	public boolean lessThan(Rational right)
	{
		int leftNumerator = this.numerator*right.denominator;
		int rightNumerator = right.numerator*this.denominator;
		if(leftNumerator < rightNumerator)
			return true;
		else
			return false;
	}
	
	public boolean greaterThan(Rational right)
	{
		int leftNumerator = this.numerator*right.denominator;
		int rightNumerator = right.numerator*this.denominator;
		if(leftNumerator > rightNumerator)
			return true;
		else
			return false;
	}
	
	public String toString()
	{
		return numerator + "/" + denominator;
	}
	
	private int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a%b);
	}
	
	public static void main(String[] args)
	{
		Rational a = new Rational(4,8);
		Rational b = new Rational(1,2);
		System.out.println(a);
		System.out.println(b);
		a.add(b);
		System.out.println(a);
	}
}
