package PayUtilityCalculatorPackage;

import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PayUtilityCalculatorTest {

	@Test
	void testGetAction() {
		PayUtilityCalculator calc = new PayUtilityCalculator();
		calc.setFieldForRateElectric(new JTextField("-10"));
		calc.getButton().doClick();
		
		String a = calc.getResults().getText();
		Assert.assertEquals(PayUtilityCalculator.ERROR_MESSAGE, a); 
	}

}
