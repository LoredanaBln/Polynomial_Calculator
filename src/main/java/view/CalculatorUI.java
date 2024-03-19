package view;
import controller.operations.Operations;
import model.Polynomial;
import controller.helper.StringConversionToPolynomial;

import javax.swing.*;
import java.awt.event.*;

public class CalculatorUI extends JFrame {
    private JPanel panelBackground;
    private JButton derivativeButton;
    private JButton integralButton;
    private JButton divisionButton;
    private JButton additionButton1;
    private JButton subtractionButton2;
    private JButton multiplicationButton3;
    private JButton key1Button;
    private JButton key2Button;
    private JButton key3Button;
    private JButton plusButton;
    private JButton key4Button;
    private JButton key5Button;
    private JButton key6Button;
    private JButton key7Button;
    private JButton key8Button;
    private JButton key9Button;
    private JButton minusButton;
    private JButton multiplyButton;
    private JButton key0Button;
    private JButton deleteButton;
    private JButton powButton2;
    private JPanel operationsPanel;
    private JPanel digitsButtonsPanel;
    private JButton xButton2;
    private JTextField remainderTextField;
    private JTextField firstPolynomialTextField;
    private JTextField secondPolynomialTextField;
    private JTextField resultTextField;
    private JLabel calculatorTitle;
    private JPanel inputsPanel;
    private JPanel titlePanel;
    private JLabel remainderLabel;
    private JLabel resultLabel;
    private JLabel secondPolynomialLabel;
    private JLabel firstPolynomialLabel;
    private JButton commaButton;
    private String firstPolynomial;
    private String secondPolynomial;
    private final StringConversionToPolynomial stringConversionToPolynomial1 = new StringConversionToPolynomial();
    private final Operations operation = new Operations();
    private JTextField lastFocusedTextField;

    public CalculatorUI() {
        initializeUI();
        setupFocusListeners();
        setupDigitButtonListeners();
        setupOperationListeners();
        setupSignsButtonListeners();
        setupVariableButton();
        setupDeleteButton();
    }

    private void initializeUI() {
        setTitle("Polynomial Calculator");
        setContentPane(panelBackground);
        setSize(480, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        integralButton.setText("∫ dx");
        divisionButton.setText("P1÷P2");
        remainderLabel.setVisible(false);
        remainderTextField.setVisible(false);
    }

    private void setupFocusListeners() {
        firstPolynomialTextField.addFocusListener(createFocusListener(firstPolynomialTextField));
        secondPolynomialTextField.addFocusListener(createFocusListener(secondPolynomialTextField));
    }

    private FocusListener createFocusListener(JTextField textField) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lastFocusedTextField = textField;
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        };
    }

    private void setupDeleteButton() {
        deleteButton.addActionListener(e -> {
            if (lastFocusedTextField != null) {
                String text = lastFocusedTextField.getText();
                text = text.substring(0, text.length() - 1);
                lastFocusedTextField.setText(text);
            }
        });
    }

    private void setupVariableButton() {
        xButton2.addActionListener(e -> {
            if (lastFocusedTextField != null) {
                char variable = Polynomial.VARIABLE_OF_THE_POLYNOMIAL;
                String text = lastFocusedTextField.getText();
                if (!text.isEmpty() && text.charAt(text.length() - 1) == variable) {
                    text = text.substring(0, text.length() - 1) + variable;
                } else {
                    text += variable;
                }
                lastFocusedTextField.setText(text);
            }
        });
    }

    private void setupSignsButtonListeners() {
        addActionListenerToSignButton(commaButton, '.');
        addActionListenerToSignButton(minusButton, '-');
        addActionListenerToSignButton(plusButton, '+');
        addActionListenerToSignButton(multiplyButton, '*');
        addActionListenerToSignButton(powButton2, '^');
    }
    private void addActionListenerToSignButton(JButton digitButton, char character) {
        digitButton.addActionListener(e -> {
            String chars = "^*+-.";
            if (lastFocusedTextField != null) {
                String text = lastFocusedTextField.getText();
                if (text.isEmpty()) {
                    if (character == '.' || character == '*' || character == '^') {
                        JOptionPane.showMessageDialog(CalculatorUI.this, "^*. cannot be at the first of the polynomial!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (chars.contains(Character.toString(text.charAt(text.length() - 1)))) {
                        text = text.substring(0, text.length() - 1) + character;
                    } else {
                        text += character;
                    }
                    lastFocusedTextField.setText(text);
                }
            }
        });
    }

    private void setupDigitButtonListeners() {
        addActionListenerToDigitButton(key1Button, '1');
        addActionListenerToDigitButton(key2Button, '2');
        addActionListenerToDigitButton(key3Button, '2');
        addActionListenerToDigitButton(key4Button, '4');
        addActionListenerToDigitButton(key5Button, '5');
        addActionListenerToDigitButton(key6Button, '6');
        addActionListenerToDigitButton(key7Button, '7');
        addActionListenerToDigitButton(key8Button, '8');
        addActionListenerToDigitButton(key9Button, '9');
        addActionListenerToDigitButton(key0Button, '0');
    }

    private void addActionListenerToDigitButton(JButton digitButton, char character) {
        digitButton.addActionListener(e -> {
            if (lastFocusedTextField != null) {
                String text = lastFocusedTextField.getText();
                lastFocusedTextField.setText(text + character);
            }
        });
    }

    private void setupOperationListeners() {
        additionButton1.addActionListener(e -> performBinaryOperation(operation::addPolynomial));
        subtractionButton2.addActionListener(e -> performBinaryOperation(operation::subtractPolynomial));
        multiplicationButton3.addActionListener(e -> performBinaryOperation(operation::multiplyPolynomial));
        divisionButton.addActionListener(e -> performDivision());
        derivativeButton.addActionListener(e -> performUnaryOperation(operation::derivativePolynomial, false));
        integralButton.addActionListener(e -> performUnaryOperation(operation::integratePolynomial, true));
    }

    private void performBinaryOperation(BinaryOperation operation) {
        if (!validateInput(firstPolynomialTextField) || !validateInput(secondPolynomialTextField)) {
            return;
        }
        firstPolynomial = firstPolynomialTextField.getText();
        secondPolynomial = secondPolynomialTextField.getText();
        Polynomial P1 = stringConversionToPolynomial1.makeStringAPolynomial(firstPolynomial);
        Polynomial P2 = stringConversionToPolynomial1.makeStringAPolynomial(secondPolynomial);
        resultTextField.setText(operation.execute(P1, P2).ui());
        setVisibilityForUnaryOperationsOtherThanDivision();
    }

    private void performDivision() {
        if (!validateInput(firstPolynomialTextField) || !validateInput(secondPolynomialTextField)) {
            return;
        }
        firstPolynomial = firstPolynomialTextField.getText();
        secondPolynomial = secondPolynomialTextField.getText();
        Polynomial P1 = stringConversionToPolynomial1.makeStringAPolynomial(firstPolynomial);
        Polynomial P2 = stringConversionToPolynomial1.makeStringAPolynomial(secondPolynomial);

        if (secondPolynomial.equals("0")) {
            JOptionPane.showMessageDialog(CalculatorUI.this, "The divisor cannot be 0!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!P1.getMonomials().isEmpty() && P1.getMonomials().values().iterator().next().getDegree() < P2.getMonomials().values().iterator().next().getDegree() ){
            JOptionPane.showMessageDialog(CalculatorUI.this, "The degree of the divisor cannot be greater than the degree of the dividend!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        resultTextField.setText(operation.dividePolynomial(P1, P2).get(0).ui());
        remainderTextField.setText(operation.dividePolynomial(P1, P2).get(1).ui());

        setVisibilityForDivision();
    }

    private void performUnaryOperation(UnaryOperation operation, boolean isIntegration) {
        if (!validateInput(firstPolynomialTextField)) {
            return;
        }
        firstPolynomial = firstPolynomialTextField.getText();
        Polynomial P1 = stringConversionToPolynomial1.makeStringAPolynomial(firstPolynomial);
        Polynomial result = operation.execute(P1);

        String resultText;
        if (isIntegration) {
            if (firstPolynomial.equals("0")) {
                resultText = "C";
            }
            else {
                resultText = result.ui() + "+C";
            }
        } else {
            resultText = result.ui();
        }

        resultTextField.setText(resultText);
        setVisibilityForBinaryOperations();
    }

    private void setVisibilityForUnaryOperationsOtherThanDivision() {
        remainderLabel.setVisible(false);
        remainderTextField.setVisible(false);
        secondPolynomialTextField.setVisible(true);
        secondPolynomialLabel.setVisible(true);
    }

    private void setVisibilityForDivision() {
        remainderTextField.setVisible(true);
        remainderLabel.setVisible(true);
        secondPolynomialTextField.setVisible(true);
        secondPolynomialLabel.setVisible(true);
    }

    private void setVisibilityForBinaryOperations() {
        remainderLabel.setVisible(false);
        remainderTextField.setVisible(false);
        secondPolynomialTextField.setVisible(false);
        secondPolynomialLabel.setVisible(false);
    }

    public boolean validateInput(JTextField textField) {
        String input = textField.getText().trim();
        // Check if input contains more than one variable
        if (containsOnlyOneVariable(input)) {
            JOptionPane.showMessageDialog(CalculatorUI.this, "The input cannot have multiple variables!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if input is empty
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The input cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if input contains whitespace characters
        if (input.contains(" ")) {
            JOptionPane.showMessageDialog(this, "The input cannot contain whitespace characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if input contains only valid characters
        if (!input.matches("^[x0-9*\\-+^]+$")) {
            JOptionPane.showMessageDialog(this, "The input should contain only one variable (x) and valid mathematical symbols (0-9, *, -, +, ^)!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the input is a valid polynomial
        try {
            stringConversionToPolynomial1.makeStringAPolynomial(input);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid polynomial input! Check for sign order!\n Valid monomial inputs:\n [c*x^d],  [x^d],  [c*x],  [x], [c]", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean containsOnlyOneVariable(String str) {
        char variable = Polynomial.VARIABLE_OF_THE_POLYNOMIAL;
        boolean variableFound = false;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isLetter(ch)) {
                if (ch != variable) {
                    variableFound = true;
                }
            }
        }
        return variableFound;
    }

    @FunctionalInterface
    interface BinaryOperation {
        Polynomial execute(Polynomial p1, Polynomial p2);
    }

    @FunctionalInterface
    interface UnaryOperation {
        Polynomial execute(Polynomial p);
    }


    public static void main(String[] args) {
        // https://stackoverflow.com/questions/4617615/how-to-set-nimbus-look-and-feel-in-main
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {

            }
        }
        new CalculatorUI();
    }
}


