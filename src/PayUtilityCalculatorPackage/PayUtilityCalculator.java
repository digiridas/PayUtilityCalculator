package PayUtilityCalculatorPackage;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayUtilityCalculator {
	public static void main(String[] args) 
	/* PayUtilityCalculator calc = new PayUtilityCalculator("1000", "1 год", "15"); */
	{
		PayUtilityCalculator calc = new PayUtilityCalculator();
	}
	
	/* Период, за который необходимо посчитать квартплату */ 
	public static final String[] PERIOD_NAMES = {"1 месяц", "3 месяца", "6 месяцев", "1 год", "2 года", "3 года", "5 лет"};
	/* Числовое представление периода */
	public static final float[] PERIOD_VALUES = {0.0833f, 0.25f, 0.5f, 1f, 2f, 3f, 5f};
	/** Сообщение об ошибке. Текст "Проверьте введенные данные!" красного цвета. */
	public static final String ERROR_MESSAGE = "<html><span style='color:red'>Проверьте введенные данные!</span></html>"; 
	
	private JLabel labelStartElectric = new JLabel("Предыдущее значение счетчика электроэнергии");
	private JTextField fieldStartElectric = new JTextField("0");
	private JLabel labelEndElectric = new JLabel("Текущее значение счетчика электроэнергии");
	private JTextField fieldEndElectric = new JTextField("0");
	
	private JLabel labelStartGas = new JLabel("Предыдущее значение счетчика газа");
	private JTextField fieldStartGas = new JTextField("0");
	private JLabel labelEndGas = new JLabel("Текущее значение счетчика газа");
	private JTextField fieldEndGas = new JTextField("0");
	
	private JLabel labelStartWater = new JLabel("Предыдущее значение счетчика воды");
	private JTextField fieldStartWater = new JTextField("0");
	private JLabel labelEndWater = new JLabel("Текущее значение счетчика воды");
	private JTextField fieldEndWater = new JTextField("0");
	
	private JFrame frame = new JFrame("Расчет квартплаты");
	private JPanel windowContent = new JPanel();
	
	private JLabel rateElectricLabel = new JLabel("Тариф за электроэнергию (квт/ч)");
	private JTextField fieldForRateElectric = new JTextField("10");
	
	private JLabel rateGasLabel = new JLabel("Тариф за газ (м3)");
	private JTextField fieldForRateGas = new JTextField("5");
	
	private JLabel rateWaterLabel = new JLabel("Тариф за воду (м3)");
	private JTextField fieldForRateWater = new JTextField("20");
	
	private JLabel periodOfPlacement = new JLabel("Период, за который необходимо посчитать кварплату:");
	private JComboBox<String> comboBox = new JComboBox<String>(PERIOD_NAMES);	
	
	private JButton button = new JButton("Рассчитать");
	private JLabel results = new JLabel("", 0);
	
	/**
	 * Конструктор без аргументов. Выводит на экран окно с полями, заполненными по умолчанию:
	 * Тариф за электроэнергию - 10
	 * Тариф за газ - 5
	 * Тариф за воду - 20
	 * Период, за который необходимо посчитать кварплату -  1 месяц
	 * Предыдущее значение счетчика электроэнергии - 0
	 * Текущее значение счетчика электроэнергии - 0
	 * Предыдущее значение счетчика газа - 0
	 * Текущее значение счетчика газа - 0
	 * Предыдущее значение счетчика воды - 0
	 * Текущее значение счетчика воды - 0
	 */
	public PayUtilityCalculator() {
		// Завершить работу программы при закрытии окна
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Задаем расположения GridLayout для компонентов панели.
		// Они будут располагаться в таблице размером 12 на 2.
		// Расстояние между ячейками таблицы - 5 пикселей по горизонтали и 5 по вертикали.
		GridLayout gl = new GridLayout(12,2,5,5);
		windowContent.setLayout(gl);

		// Добавляем компоненты на панель.
		windowContent.add(rateElectricLabel);
		windowContent.add(getFieldForRateElectric());
		
		windowContent.add(rateGasLabel);
		windowContent.add(fieldForRateGas);
		
		windowContent.add(rateWaterLabel);
		windowContent.add(fieldForRateWater);
		
		windowContent.add(periodOfPlacement);
		windowContent.add(comboBox);

		windowContent.add(labelStartElectric);
		windowContent.add(fieldStartElectric);
		windowContent.add(labelEndElectric);
		windowContent.add(fieldEndElectric);
		
		windowContent.add(labelStartGas);
		windowContent.add(fieldStartGas);
		windowContent.add(labelEndGas);
		windowContent.add(fieldEndGas);
		
		windowContent.add(labelStartWater);
		windowContent.add(fieldStartWater);
		windowContent.add(labelEndWater);
		windowContent.add(fieldEndWater);

		windowContent.add(getButton());
		windowContent.add(getResults());
		
		
		// Создаем "слушатель". Это объект вложенного класса Listener, который будет "слушать". 
		// событие "Нажатие на кнопку" и вызывать вычисления.
		getButton().setBounds(100, 205, 150, 100);
		getButton().addActionListener(new Listener());
				
		// Связываем фрейм и панель.	
		frame.setContentPane(windowContent);
		
		// Задаём размер и отображаем фрейм.
		frame.setSize(850,350);
		frame.setVisible(true);
	}
	
	/**
	 * Конструктор с аргументами. Позволяет задать значения полей по умолчанию.
	 * Если указанного срока размещения не окажется в массиве PERIOD_NAMES, 
	 * то берется срок размещения по умолчанию - "1 месяц". 
	 * @see PERIOD_NAMES 
	 */
	public PayUtilityCalculator(String sum, String period, String percent) {
		// Вызываем другой конструктор, который без аргументов.
		this();
		
		// Проверяем, что такой период есть в константе PERIOD_NAMES.		
		for (String el : PERIOD_NAMES) {
			if (el == period) {
				comboBox.setSelectedItem(period);
				break;
			}
		}		
	
	}
	
	/** Вложенный класс для обработки событий "Нажатие на кнопку". */
	private class Listener implements ActionListener {
		@Override
		// Класс реализует интерфейс ActionListener, поэтому мы должны реализовать метод actionPerformed.
		// JVM выполняет этот метод, когда пользователь нажимает на кнопку.
		public void actionPerformed(ActionEvent e) 
		{
			float startElectric;
			float endElectric;
			float startGas;
			float endGas;
			float startWater;
			float endWater;
			float rateElectric;
			float rateGas;
			float rateWater;
			float periodFloat = 0;
			
			try {
				startElectric = Float.parseFloat(fieldStartElectric.getText());
				endElectric = Float.parseFloat(fieldEndElectric.getText());
				startGas = Float.parseFloat(fieldStartGas.getText());
				endGas = Float.parseFloat(fieldEndGas.getText());
				startWater = Float.parseFloat(fieldStartWater.getText());
				endWater = Float.parseFloat(fieldEndWater.getText());
				rateElectric = Float.parseFloat(getFieldForRateElectric().getText());
				rateGas = Float.parseFloat(fieldForRateGas.getText());
				rateWater = Float.parseFloat(fieldForRateWater.getText());
				// Определяем количесто лет в периоде из константы PERIOD_VALUES.
				for (int i = 0; i < PERIOD_NAMES.length; i++) {
					if (PERIOD_NAMES[i] == comboBox.getSelectedItem()) {
						periodFloat = PERIOD_VALUES[i];
						break;
					}
				}
				
//				 Передаем данные c полей формы в метод getPayUtility для вычислений.
				String result = getPayUtility(startElectric, endElectric, startGas, endGas, startWater, endWater, rateElectric, rateGas, rateWater, periodFloat);
				
				// Выводим на форму результаты вычислений.
				getResults().setText(result);
			}
			catch(NumberFormatException exception) {
				getResults().setText(ERROR_MESSAGE);
			}

			
		}	
	}
	
	/**
	 * Метод рассчитывает сумму за коммунальные услуги.
	 * Коммунальные услуги = Тариф за газ * Разница между старыми показателями и текущими по газу +
	 * 	Тариф за электричество * Разница между старыми показателями и текущими по электричеству +
	 * 	Тариф за воду * Разница между старыми показателями и текущими по воде
	 * @return Строка с суммой. Количество округляется до двух знаком после запятой.
	 * @return Если переданные методу данные некорректны, то возвращается строка ERROR_MESSAGE.
	 * @see ERROR_MESSAGE
	 */
	public static String getPayUtility(
			Float startElectric, Float endElectric, Float startGas, Float endGas, Float startWater, Float endWater, 
			Float rateElectric, Float rateGas, Float rateWater, Float periodFloat) 
	{

	
		
		// Проверяем, что такой период есть в константе PERIOD_NAMES и что сумма не отрицательна 
		if (periodFloat == 0f | startElectric < 0 | endElectric < 0 |startGas < 0 | endGas < 0 | startWater < 0 | endWater < 0 | rateElectric < 0 | rateGas < 0 | periodFloat < 0) {
			return ERROR_MESSAGE;
		}
			 
		float resultElectric = (endElectric - startElectric) * rateElectric;
		float resultGas = (endGas - startGas) * rateGas;
		float resultWater = (endWater - startWater) * rateWater;
		float resultSum = Math.round(resultElectric + resultGas + resultWater) * periodFloat;
		// Результат форматируем и конвертируем в строку.
		return String.format("%.2f+ \" руб.\"", resultSum);
	}

	public JTextField getFieldForRateElectric() {
		return fieldForRateElectric;
	}

	public void setFieldForRateElectric(JTextField fieldForRateElectric) {
		this.fieldForRateElectric = fieldForRateElectric;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public JLabel getResults() {
		return results;
	}

	public void setResults(JLabel results) {
		this.results = results;
	}
}





