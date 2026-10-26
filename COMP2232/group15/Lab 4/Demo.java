import minitf.*;

public class Demo {
    public static void main(String[] args)
    {
        Environment env = new Environment();
        CalculatorTest test = new CalculatorTest("Calculator Test");
        
        env.add(test);
        env.run();
    }
}

