package operationsTest;

import controller.helper.OperationType;
import controller.helper.PolynomialProviderImpl;
import controller.operations.Operations;
import model.Monomial;
import model.Polynomial;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OperationTest {
    protected Operations op = new Operations();
    private static int numberOfTests = 0;
    private static int numberOfPassedTests = 0;

    @AfterClass
    public static void finalMethod() {
        System.out.println(numberOfTests + " has been executed and " + numberOfPassedTests + " has passed");
    }

    @Before
    public void increment() {
        numberOfTests++;
    }

    @After
    public void finished() {
        System.out.println("Test finished");
    }

    @ParameterizedTest
    @MethodSource("parametersTestAddition")
    public void testAddition(Polynomial polynomial1, Polynomial polynomial2, Polynomial expectedResult) {
        Polynomial result = op.addPolynomial(polynomial1, polynomial2);

        Map<Integer, Monomial> mapRes = result.getMonomials();
        for (Monomial monomial: mapRes.values()) {
            assertTrue(expectedResult.getMonomials().containsValue(monomial));
        }

        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestAddition() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.ADDITION);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1], arr[2]));
    }


    @ParameterizedTest
    @MethodSource("parametersTestSubtraction")
    public void testSubtraction(Polynomial polynomial1, Polynomial polynomial2, Polynomial expectedResult) {
        Polynomial result = op.subtractPolynomial(polynomial1, polynomial2);

        Map<Integer, Monomial> mapRes = result.getMonomials();
        for (Monomial monomial: mapRes.values()) {
            assertTrue(expectedResult.getMonomials().containsValue(monomial));
        }
        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestSubtraction() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.SUBTRACTION);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1], arr[2]));
    }

    @ParameterizedTest
    @MethodSource("parametersTestMultiplication")
    public void testMultiplication(Polynomial polynomial1, Polynomial polynomial2, Polynomial expectedResult) {
        Polynomial result = op.multiplyPolynomial(polynomial1, polynomial2);

        Map<Integer, Monomial> mapRes = result.getMonomials();
        for (Monomial monomial: mapRes.values()) {
            assertTrue(expectedResult.getMonomials().containsValue(monomial));
        }

        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestMultiplication() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.MULTIPLICATION);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1], arr[2]));
    }

    @ParameterizedTest
    @MethodSource("parametersTestDivision")
    public void testDivision(Polynomial polynomial1, Polynomial polynomial2, Polynomial expectedQuotient, Polynomial expectedRemainder) {
        List<Polynomial> result = op.dividePolynomial(polynomial1, polynomial2);
        Polynomial quotient = result.get(0);
        Polynomial remainder = result.get(1);
        Map<Integer, Monomial> mapResQ = quotient.getMonomials();
        Map<Integer, Monomial> mapResR = remainder.getMonomials();

        for (Monomial monomial: mapResQ.values()) {
            assertTrue(expectedQuotient.getMonomials().containsValue(monomial));
        }
        for (Monomial monomial: mapResR.values()) {
            assertTrue(expectedRemainder.getMonomials().containsValue(monomial));
        }

        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestDivision() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.DIVISION);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1], arr[2], arr[3]));
    }


    @ParameterizedTest
    @MethodSource("parametersTestDivisionByZero")
    public void testDivisionByZero(Polynomial dividend, Polynomial divisor){
        assertThrows(IllegalArgumentException.class, () -> op.dividePolynomial(dividend, divisor));
        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestDivisionByZero() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.DIVISION_BY_ZERO);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1]));
    }


    @ParameterizedTest
    @MethodSource("parametersTestImpossibleDivision")
    public void testImpossibleDivision(Polynomial dividend, Polynomial divisor) {
        assertThrows(IllegalArgumentException.class, () -> op.dividePolynomial(dividend, divisor));
        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestImpossibleDivision() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.IMPOSSIBLE_DIVISION);
            return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1]));
        }


        @ParameterizedTest
        @MethodSource("parametersTestDerivative")
        public void testDerivative(Polynomial polynomial1, Polynomial expectedResult) {
            Polynomial result = op.derivativePolynomial(polynomial1);
            Map<Integer, Monomial> mapRes = result.getMonomials();
            for (Monomial monomial: mapRes.values()) {
                assertTrue(expectedResult.getMonomials().containsValue(monomial));
            }
            numberOfPassedTests++;
        }

        public static Stream<Arguments> parametersTestDerivative() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.DERIVATIVE);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1]));
    }


    @ParameterizedTest
    @MethodSource("parametersTestIntegration")
    public void testIntegration(Polynomial polynomial1, Polynomial expectedResult) {
        Polynomial result = op.integratePolynomial(polynomial1);
        Map<Integer, Monomial> mapRes = result.getMonomials();
        for (Monomial monomial: mapRes.values()) {
            assertTrue(expectedResult.getMonomials().containsValue(monomial));
        }
        numberOfPassedTests++;
    }

    public static Stream<Arguments> parametersTestIntegration() {
        PolynomialProviderImpl polynomialProvider = new PolynomialProviderImpl();
        List<Polynomial[]> polynomials = polynomialProvider.createPolynomial(OperationType.INTEGRATION);
        return polynomials.stream().map(arr -> Arguments.of(arr[0], arr[1]));
    }
}
