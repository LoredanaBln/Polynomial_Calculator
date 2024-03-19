package prettyPrintTest;
import model.Monomial;
import model.Polynomial;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrettyPrintTest {
    private static int numberOfTests = 0;
    private static final int numberOfPassedTests = 0;

    @BeforeClass
    public static void init() {
        System.out.println("Initialize string");

    }

    @AfterClass
    public static void finalMethod() {
        System.out.println(numberOfTests + "has been executed and " + numberOfPassedTests + " has passed");
    }

    @Before
    public void increment() {
        numberOfTests++;
    }

    @After
    public void finished() {
        System.out.println("Test finished");
    }

    public static List<Arguments> prettyPrintTestParameters() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of("0", createPolynomial(new Double[]{0.0}, new Integer[]{0})));
        arguments.add(Arguments.of("5", createPolynomial(new Double[]{5.0}, new Integer[]{0})));
        arguments.add(Arguments.of("0.08", createPolynomial(new Double[]{0.08}, new Integer[]{0})));
        arguments.add(Arguments.of("10.08", createPolynomial(new Double[]{10.08}, new Integer[]{0})));
        arguments.add(Arguments.of("100.01", createPolynomial(new Double[]{100.01}, new Integer[]{0})));
        arguments.add(Arguments.of("1", createPolynomial(new Double[]{1.0}, new Integer[]{0})));
        arguments.add(Arguments.of("2", createPolynomial(new Double[]{2.0}, new Integer[]{0})));
        arguments.add(Arguments.of("-3", createPolynomial(new Double[]{-3.0}, new Integer[]{0})));
        arguments.add(Arguments.of("100", createPolynomial(new Double[]{100.0}, new Integer[]{0})));
        arguments.add(Arguments.of("x", createPolynomial(new Double[]{1.0}, new Integer[]{1})));
        arguments.add(Arguments.of("-x", createPolynomial(new Double[]{-1.0}, new Integer[]{1})));
        arguments.add(Arguments.of("-2*x", createPolynomial(new Double[]{-2.0}, new Integer[]{1})));
        arguments.add(Arguments.of("x^2", createPolynomial(new Double[]{1.0}, new Integer[]{2})));
        arguments.add(Arguments.of("-3*x^2", createPolynomial(new Double[]{-3.0}, new Integer[]{2})));
        arguments.add(Arguments.of("-3*x", createPolynomial(new Double[]{-3.0}, new Integer[]{1})));
        arguments.add(Arguments.of("2", createPolynomial(new Double[]{2.0}, new Integer[]{0})));
        arguments.add(Arguments.of("300*x^2", createPolynomial(new Double[]{300.0}, new Integer[]{2})));
        arguments.add(Arguments.of("x^2-1", createPolynomial(new Double[]{1.0, -1.0}, new Integer[]{2,0})));
        arguments.add(Arguments.of("2*x^2", createPolynomial(new Double[]{2.0}, new Integer[]{2})));
        arguments.add(Arguments.of("2*x^3+x^2+3*x+5", createPolynomial(new Double[]{2.0, 1.0, 3.0, 5.0}, new Integer[]{3, 2, 1, 0})));
        arguments.add(Arguments.of("2*x^4-x^2+x+6", createPolynomial(new Double[]{2.0, -1.0, 1.0, 6.0}, new Integer[]{4, 2, 1, 0})));
        return arguments;
    }
    private static Polynomial createPolynomial(Double[] coefficients, Integer[] degrees) {
        if (coefficients.length != degrees.length) {
            throw new IllegalArgumentException("Coefficients and degrees arrays must have the same length.");
        }
        Map<Integer, Monomial> monomialsMap = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < coefficients.length; i++) {
            monomialsMap.put(degrees[i], new Monomial(degrees[i], coefficients[i]));
        }
        return new Polynomial(monomialsMap);
    }

    @ParameterizedTest
    @MethodSource("prettyPrintTestParameters")
    public void prettyPrintTest(String stringPolynomial, Polynomial convertedPolynomial){
       assertEquals(stringPolynomial, convertedPolynomial.prettyPrint());
    }
}
