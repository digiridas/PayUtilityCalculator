package PayUtilityCalculatorPackage;

import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PayUtilityCalculatorTest2 {

	@Test
	void testGetPayUtility() {
		float startElectric = 50;
		float endElectric = 100;
		float startGas = 20;
		float endGas = 200;
		float startWater = 500;
		float endWater = 555;
		float rateElectric = 10;
		float rateGas = 5;
		float rateWater = 20;
		float period = 3;
		Assert.assertEquals("7500,00 руб.", PayUtilityCalculator.getPayUtility(startElectric, endElectric, startGas, endGas, startWater, endWater, rateElectric, rateGas, rateWater, period)); 
	}

}

