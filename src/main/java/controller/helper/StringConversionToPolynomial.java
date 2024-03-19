package controller.helper;

import model.Monomial;
import model.Polynomial;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConversionToPolynomial {
    public Polynomial makeStringAPolynomial(String polynomial) {
        Polynomial p1 = new Polynomial();
        Map<Integer, Monomial> monomialMap = new TreeMap<>(Collections.reverseOrder());
        Map<Integer, Monomial> monomialMapCTest = new LinkedHashMap<>();

        Pattern pattern = Pattern.compile("([-+]?(?:\\d*\\.?\\d+)?)\\*?" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^?(\\d*)?|([-+]?\\d*\\.?\\d++)?");
        Matcher matcher = pattern.matcher(polynomial);

        while (matcher.find()) {
            String coeffStr = matcher.group(1);
            String degreeStr = matcher.group(2);
            String freeTermStr = matcher.group(3);

            Number coefficient;
            int degree;
            if (coeffStr != null || degreeStr != null) {
                coefficient = handleCoefficient(coeffStr);
                degree = handleDegree(degreeStr);
            } else {
                coefficient = handleFreeTerm(freeTermStr);
                degree = 0;
            }
            if (coefficient.doubleValue() != 0) {
                monomialMap.put(degree, new Monomial(degree, coefficient));
                monomialMapCTest.put(degree, new Monomial(degree, coefficient));
            }
        }

        p1.setMonomials(monomialMap);
        //for input testing
        if (!createPolynomial(monomialMapCTest).prettyPrint().equals(polynomial)) {
            throw new IllegalArgumentException();
        }
        if (monomialMapCTest.isEmpty()) {
            monomialMapCTest.put(0, new Monomial(0, 0.0));
        }

        if (monomialMap.isEmpty()) {
            monomialMap.put(0, new Monomial(0, 0.0));
        }
        return p1;
    }

    private Number handleCoefficient(String coeffStr) {
        if (coeffStr == null || coeffStr.isEmpty() || coeffStr.equals("+")) {
            return 1.0;
        } else if (coeffStr.equals("-")) {
            return -1.0;
        } else {
            return Double.parseDouble(coeffStr);
        }
    }

    private int handleDegree(String degreeStr) {
        if (degreeStr == null || degreeStr.isEmpty()) {
            return 1;
        } else {
            return Integer.parseInt(degreeStr);
        }
    }

    private Number handleFreeTerm(String freeTermStr) {
        if (freeTermStr != null && !freeTermStr.isEmpty()) {
            return Double.parseDouble(freeTermStr);
        } else {
            return 0.0;
        }
    }

    private Polynomial createPolynomial(Map<Integer, Monomial> monomials) {
        Polynomial result = new Polynomial();
        result.setMonomials(monomials);
        return result;
    }
}
