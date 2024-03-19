package controller.operations;

import model.Monomial;
import model.Polynomial;

import java.text.DecimalFormat;
import java.util.*;

public class Operations {

    public Polynomial addPolynomial(Polynomial p1, Polynomial p2) {
        Map<Integer, Monomial> mapRes = new TreeMap<>(Collections.reverseOrder());
        addMonomialsToResult(p1.getMonomials(), mapRes);
        addMonomialsToResult(p2.getMonomials(), mapRes);
        if (mapRes.isEmpty()) {
            mapRes.put(0, new Monomial(0, 0.0));
        }
        return createPolynomial(mapRes);
    }

    private void addMonomialsToResult(Map<Integer, Monomial> monomials, Map<Integer, Monomial> resultMap) {
        for (Monomial monomial : monomials.values()) {
            int degree = monomial.getDegree();
            Number coefficient = monomial.getCoefficient();

            Monomial existingMonomial = resultMap.getOrDefault(degree, new Monomial(degree, 0));
            double existingCoefficient = existingMonomial.getCoefficient().doubleValue();
            double newCoefficient = coefficient.doubleValue();
            double sum = existingCoefficient + newCoefficient;
            existingMonomial.setCoefficient(sum);

            if (sum != 0.0) {
                resultMap.put(degree, existingMonomial);
            } else {
                resultMap.remove(degree);
            }
        }
    }


    public Polynomial subtractPolynomial(Polynomial p1, Polynomial p2) {
        Map<Integer, Monomial> mapRes = new TreeMap<>(Collections.reverseOrder());
        addMonomialsToResult(p1.getMonomials(), mapRes);
        subtractMonomialsFromResult(p2.getMonomials(), mapRes);
        if (mapRes.isEmpty()) {
            mapRes.put(0, new Monomial(0, 0.0));
        }
        return createPolynomial(mapRes);
    }

    private void subtractMonomialsFromResult(Map<Integer, Monomial> monomials, Map<Integer, Monomial> resultMap) {
        for (Monomial monomial : monomials.values()) {
            int degree = monomial.getDegree();
            Number coefficient = monomial.getCoefficient();

            Monomial existingMonomial = resultMap.getOrDefault(degree, new Monomial(degree, 0));
            double existingCoefficient = existingMonomial.getCoefficient().doubleValue();
            double newCoefficient = coefficient.doubleValue();
            double difference = existingCoefficient - newCoefficient;
            existingMonomial.setCoefficient(difference);

            if (difference != 0.0) {
                resultMap.put(degree, existingMonomial);
            } else {
                resultMap.remove(degree);
            }
        }
    }


    public Polynomial derivativePolynomial(Polynomial p1) {
        Map<Integer, Monomial> mapRes = new TreeMap<>(Collections.reverseOrder());
        for (Monomial monomial : p1.getMonomials().values()) {
            int degree = monomial.getDegree();
            Number coefficient = monomial.getCoefficient();
            double coef = coefficient.doubleValue();

            Monomial derivatedMonomial = new Monomial();
            derivatedMonomial.setCoefficient(coef * degree);
            if (degree >= 1) {
                derivatedMonomial.setDegree(degree - 1);
            } else {
                derivatedMonomial.setDegree(0);
            }
            if (derivatedMonomial.getCoefficient().doubleValue() != 0.0) {
                mapRes.put(degree, derivatedMonomial);
            }
        }
        if (mapRes.isEmpty()) {
            mapRes.put(0, new Monomial(0, 0.0));
        }
        return createPolynomial(mapRes);
    }

    public Polynomial integratePolynomial(Polynomial p1) {
        Map<Integer, Monomial> mapRes = new TreeMap<>(Collections.reverseOrder());
        DecimalFormat df = new DecimalFormat("#.####");
        for (Monomial monomial : p1.getMonomials().values()) {

            int degree = monomial.getDegree();
            Number coefficient = monomial.getCoefficient();
            Monomial integratedMonomial = new Monomial();
            double result = (coefficient).doubleValue() / (degree + 1);
            integratedMonomial.setCoefficient(Double.parseDouble(df.format(result)));
            integratedMonomial.setDegree(degree + 1);

            if (integratedMonomial.getCoefficient().doubleValue() != 0.0) {
                mapRes.put(degree, integratedMonomial);
            }
        }
        return createPolynomial(mapRes);
    }

    public Polynomial multiplyPolynomial(Polynomial p1, Polynomial p2) {
        Map<Integer, Monomial> mapRes = new TreeMap<>(Collections.reverseOrder());

        for (Monomial monomialP1 : p1.getMonomials().values()) {
            int degreeP1 = monomialP1.getDegree();
            Number coefficientP1 = monomialP1.getCoefficient();

            for (Monomial monomialP2 : p2.getMonomials().values()) {
                int degreeP2 = monomialP2.getDegree();
                Number coefficientP2 = monomialP2.getCoefficient();

                Monomial resMon = new Monomial();
                Number coef = (coefficientP1).doubleValue() * (coefficientP2).doubleValue();
                resMon.setCoefficient(coef);
                resMon.setDegree(degreeP1 + degreeP2);
                if (mapRes.containsKey(degreeP1 + degreeP2)) {
                    mapRes.get(degreeP2 + degreeP1).setCoefficient(mapRes.get(degreeP2 + degreeP1)
                            .getCoefficient().doubleValue() + coef.doubleValue());
                } else {
                    if (coef.doubleValue() != 0.0) {
                        mapRes.put(degreeP1 + degreeP2, resMon);
                    } else {
                        mapRes.put(0, new Monomial(0, 0.0));
                    }
                }
            }
        }
        return createPolynomial(mapRes);
    }

    public ArrayList<Polynomial> dividePolynomial(Polynomial p1, Polynomial p2) {
        ArrayList<Polynomial> quotientAndRemainder = new ArrayList<>();

        Map<Integer, Monomial> quotientMap = computeQuotient(p1, p2);
        Polynomial remainder = computeRemainder(p1, p2, quotientMap);
        if (remainder.getMonomials().isEmpty()) {
            Map<Integer, Monomial> remainderR = new TreeMap<>(Collections.reverseOrder());
            remainderR.put(0, new Monomial(0, 0.0));
            remainder.setMonomials(remainderR);
        }
        quotientAndRemainder.add(createPolynomial(quotientMap));
        quotientAndRemainder.add(remainder);

        return quotientAndRemainder;
    }

    private Map<Integer, Monomial> computeQuotient(Polynomial p1, Polynomial p2) {
        Map<Integer, Monomial> quotientMap = new TreeMap<>(Collections.reverseOrder());
        Monomial firstMonomialP1 = p1.getMonomials().isEmpty() ? null : p1.getMonomials().values().iterator().next();
        int maxDegreeP1 = firstMonomialP1 != null ? firstMonomialP1.getDegree() : 0;
        double coef1 = firstMonomialP1 != null ? firstMonomialP1.getCoefficient().doubleValue() : 0.0;
        Monomial firstMonomialP2 = p2.getMonomials().isEmpty() ? null : p2.getMonomials().values().iterator().next();
        int maxDegreeP2 = firstMonomialP2 != null ? firstMonomialP2.getDegree() : 0;
        double coef2 = firstMonomialP2 != null ? firstMonomialP2.getCoefficient().doubleValue() : 0.0;

        if (coef2 == 0 || maxDegreeP1 < maxDegreeP2) {
            throw new IllegalArgumentException();
        }
        while (coef1 != 0.0 && maxDegreeP1 >= maxDegreeP2) {
            double quotientCoefficient = coef1 / coef2;
            int degreeDifference = maxDegreeP1 - maxDegreeP2;
            if (quotientCoefficient != 0.0) {
                Monomial quotientMonomial = new Monomial(degreeDifference, quotientCoefficient);
                quotientMap.put(degreeDifference, quotientMonomial);
            }
            Map<Integer, Monomial> intermediate = new TreeMap<>();
            if (quotientCoefficient != 0.0) {
                intermediate.put(degreeDifference, new Monomial(degreeDifference, quotientCoefficient));
            }
            p1 = subtractPolynomial(p1, multiplyPolynomial(p2, createPolynomial(intermediate)));
            p1.getMonomials().remove(maxDegreeP1);
            Monomial firstMonomialInP1 = p1.getMonomials().isEmpty() ? null : p1.getMonomials().values().iterator().next();
            maxDegreeP1 = firstMonomialInP1 != null ? firstMonomialInP1.getDegree() : 0;
            coef1 = p1.getMonomials().isEmpty() ? 0 : p1.getMonomials().values().iterator().next().getCoefficient().doubleValue();
        }
        return quotientMap;
    }

    private Polynomial computeRemainder(Polynomial p1, Polynomial p2, Map<Integer, Monomial> quotientMap) {
        Polynomial remainder = p1;
        Map<Integer, Monomial> remainderMap = new TreeMap<>(Collections.reverseOrder());
        for (Monomial monomial : quotientMap.values()) {
            Polynomial product = multiplyPolynomial(p2, createPolynomial(Collections.singletonMap(monomial.getDegree(), monomial)));
            remainder = subtractPolynomial(remainder, product);
        }
        for (Monomial monomial : remainder.getMonomials().values()) {
            if (monomial.getCoefficient().doubleValue() != 0.0) {
                remainderMap.put(monomial.getDegree(), monomial);
            }
        }
        return createPolynomial(remainderMap);
    }

    private Polynomial createPolynomial(Map<Integer, Monomial> monomials) {
        Polynomial result = new Polynomial();
        result.setMonomials(monomials);
        return result;
    }
}
