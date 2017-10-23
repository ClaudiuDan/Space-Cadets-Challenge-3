import java.util.Map;

public class Expression {
	char[] expression = new char[10000];
	int value;
	Expression (String expression, Map<String, Integer> map, String[] variables, int limit)
	{
		expression = replaceVariables (map, variables, expression, limit);
		transformToChar(expression);
		value = firstLevel();
	}
	// I transform the expression from String to char because it is easier for me to work with chars
	void transformToChar (String expression)
	{
		int i = 0;
		for (i = 0; i < expression.length(); i++)
			this.expression[i] = expression.charAt(i);
	}
	int pos = 0;
	private int firstLevel () // this is the first level of priority, sum and difference
	{
		int s = secondLevel (); // I get the first element
		while (expression[pos] == '+' || expression[pos] == '-')
		{
			if (expression[pos] == '+') // keep adding to that element if there are pluses
			{
				pos++;
				s += secondLevel ();
			}
			if (expression[pos] == '-') // or decreasing
			{
				pos++;
				s -= secondLevel ();
			}
		}
		return s;
	}
	
	private int secondLevel () // second level of priority for products and divisions
 	{
		int p = thirdLevel ();
		while (expression[pos] == '*' || expression[pos] == '/')
		{
			if (expression[pos] == '*')
			{
				pos++;
				p *= thirdLevel ();
			}
			if (expression[pos] == '/')
			{
				pos++;
				p /= thirdLevel ();
			}
		}
		return p;
	}
	
	private int thirdLevel () // third level of priority, I take chars as they are
	{
		int sign = 1, nr;
		
		if (expression[pos] == '-') // In case there is a minus before a number
		{
			sign = -1;
			pos++;
		}
		if (expression[pos] == '(') 
		{
			pos++;
			nr = firstLevel();  // This way I deal with the high priority of new brackets
			pos++;
			return sign * nr; // and return the number from the bracketed expression
		}
		nr = 0;
		while (expression[pos] >= '0' && expression[pos] <= '9')
		{
			nr = nr * 10 + (expression[pos] - '0'); //if there only a simple number, I form it and return it
			pos++;
		}
		return nr * sign;
	}
	public int getValue ()
	{
		return value;
	}
	// I replace variables from expression with their values
	private String replaceVariables (Map<String, Integer> map, String[] variables, String expression, int limit)
	{
		int i;
		for (i = 0; i < limit; i++)
			expression = expression.replaceAll(variables[i], map.get(variables[i]).toString());
		return expression;
			
	}
}
