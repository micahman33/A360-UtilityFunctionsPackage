import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.samples.commands.NumberRounding;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Test_NumberRounding {
    @Test
    public void testNumberRounding(){
        Double inputNumber = 5.2837364;
        Double numDigits = 2.00;

        NumberRounding numberRounding = new NumberRounding();

        NumberValue result = numberRounding.action(inputNumber,numDigits);
        Assert.assertEquals(result.getAsDouble(), 5.28);
    }

}
