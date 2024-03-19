package controller.helper;

import model.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class PolynomialProviderImpl implements PolynomialProvider {
    StringConversionToPolynomial stringConversionToPolynomial = new StringConversionToPolynomial();

    @Override
    public List<Polynomial[]> createPolynomial(OperationType operationType) {
        return providePolynomialFromString(operationType);
    }

    public List<Polynomial[]> providePolynomialFromString(OperationType operationType) {
        List<Polynomial[]> listTestCases = new ArrayList<>();
        switch (operationType) {
            case ADDITION:
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("3*x^2+2*x+1"), createPolynomialFromString("2*x^2+3*x+1"), createPolynomialFromString("5*x^2+5*x+2")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("4*x^3+2*x^2"), createPolynomialFromString("3*x^3+2*x"), createPolynomialFromString("7*x^3+2*x^2+2*x")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("-2*x^2-3*x-1"), createPolynomialFromString("-x^2-2*x-3"), createPolynomialFromString("-3*x^2-5*x-4")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("0"), createPolynomialFromString("0"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("2*x^2+2*x+2"), createPolynomialFromString("0"), createPolynomialFromString("2*x^2+2*x+2")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("500*x^2+400*x+300"), createPolynomialFromString("-200*x^2-100*x+100"), createPolynomialFromString("300*x^2+300*x+400")));
                break;
            case SUBTRACTION:
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("5*x^2+3*x+1"), createPolynomialFromString("2*x^2+x+1"), createPolynomialFromString("3*x^2+2*x")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("4*x^3+2*x^2+2*x"), createPolynomialFromString("4*x^3+2*x^2+2*x"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("-2*x^2-2*x-1"), createPolynomialFromString("-x^2-2*x-3"), createPolynomialFromString("-x^2+2")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("0"), createPolynomialFromString("0"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("3*x^2+2*x+1"), createPolynomialFromString("0"), createPolynomialFromString("3*x^2+2*x+1")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("500*x^2+400*x+300"), createPolynomialFromString("-200*x^2-100*x+100"), createPolynomialFromString("700*x^2+500*x+200")));
                break;
            case MULTIPLICATION:
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("2*x^2+1"), createPolynomialFromString("1"), createPolynomialFromString("2*x^2+1")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("0"), createPolynomialFromString("0"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("2*x"), createPolynomialFromString("0"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("2*x^3+3*x^2+2"), createPolynomialFromString("x^2-3*x"), createPolynomialFromString("2*x^5-3*x^4-9*x^3+2*x^2-6*x")));
                listTestCases.add(createTestCaseForBinaryOperation(createPolynomialFromString("-2*x^2+3*x-4"), createPolynomialFromString("-3*x^3+5*x^2-6*x+7"), createPolynomialFromString("6*x^5-19*x^4+39*x^3-52*x^2+45*x-28")));
                break;
            case DIVISION:
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("x^3-2*x^2+6*x-5"), createPolynomialFromString("x^2-1"), createPolynomialFromString("x-2"), createPolynomialFromString("7*x-7")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("2*x^10-x^2-2*x+12"), createPolynomialFromString("x^5-3*x^2+x+1"), createPolynomialFromString("2*x^5+6*x^2-2*x-2"), createPolynomialFromString("18*x^4-12*x^3-11*x^2+2*x+14")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("5*x^2+7*x+1"), createPolynomialFromString("3*x+5"), createPolynomialFromString("1.6666666666666667*x-0.44444444444444464"), createPolynomialFromString("3.222222222222223")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("6*x^6-3*x^2+2*x+1"), createPolynomialFromString("2*x^2+2*x"), createPolynomialFromString("3*x^4-3*x^3+3*x^2-3*x+1.5"), createPolynomialFromString("-x+1")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("x^3-2*x"), createPolynomialFromString("2"), createPolynomialFromString("0.5*x^3-x"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("2*x^2-2"), createPolynomialFromString("2"), createPolynomialFromString("x^2-1"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForDivision(createPolynomialFromString("1"), createPolynomialFromString("3"), createPolynomialFromString("0.3333333333333333"), createPolynomialFromString("0")));
                break;
            case DIVISION_BY_ZERO:
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("x^3-2*x^2+6*x-5"), createPolynomialFromString("0")));
                break;
            case IMPOSSIBLE_DIVISION:
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("x^3-2*x^2+6*x-5"), createPolynomialFromString("x^4")));
                break;
            case DERIVATIVE:
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("1"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("x"), createPolynomialFromString("1")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("2*x^2"), createPolynomialFromString("4*x")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("2*x"), createPolynomialFromString("2")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("x+1"), createPolynomialFromString("1")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("0"), createPolynomialFromString("0")));
                break;
            case INTEGRATION:
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("3*x+2"), createPolynomialFromString("1.5*x^2+2*x")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("0"), createPolynomialFromString("0")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("1"), createPolynomialFromString("x")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("3*x^2+2*x+2"), createPolynomialFromString("x^3+x^2+2*x")));
                listTestCases.add(createTestCaseForUnaryOperation(createPolynomialFromString("-x^2-2*x"), createPolynomialFromString("-0.3333*x^3-x^2")));
                break;
            default:
                System.out.println("Operation type not defined.");
                break;
        }

        return listTestCases;
    }

    private Polynomial[] createTestCaseForBinaryOperation(Polynomial p1, Polynomial p2, Polynomial result) {
        return new Polynomial[]{p1, p2, result};
    }

    private Polynomial[] createTestCaseForDivision(Polynomial p1, Polynomial p2, Polynomial quotient, Polynomial remainder) {
        return new Polynomial[]{p1, p2, quotient, remainder};
    }


    private Polynomial[] createTestCaseForUnaryOperation(Polynomial p1, Polynomial result) {
        return new Polynomial[]{p1, result};
    }

    private Polynomial createPolynomialFromString(String p) {
        return stringConversionToPolynomial.makeStringAPolynomial(p);
    }
}
