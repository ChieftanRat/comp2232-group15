import minitf.*;
public class CalculatorTest extends Box 
{
    public CalculatorTest(String name) 
    {
        super(name);
    }

    public void setup()
    {
    }
    public void cleanup()
    {
    }
    public void run()
    {
        Calculator calc = new Calculator();
        output("***** Calculator Test *****");
        int z = calc.add(2,8);
        assertion(z == 10, "Sum is " + 2);
    }
}
