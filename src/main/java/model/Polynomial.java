package model;


import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    private Map<Integer, Monomial> monomials = new TreeMap<>();
    public final static Character VARIABLE_OF_THE_POLYNOMIAL = 'x';

    public Polynomial() {
    }

    public Polynomial(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }

    public void setMonomials(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }

    public String prettyPrint(){
        StringBuilder result = new StringBuilder();
        for (Monomial monomial : monomials.values()) {
            result.append(monomial);
        }
        if (result.toString().equals("(0.0*x^0+") || result.isEmpty() ||result.toString().equals("0.0*x^1")) {
            result = new StringBuilder("0");
        }
        else {
            result = new StringBuilder(result.toString().replaceAll("\\+-", "-")
                    .replaceAll("\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^0", "")
                    .replaceAll("([+-])1.0\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL, "$1" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL)
                    .replaceAll("([+-])0.0\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^(\\d+)?", "")
                    .replaceAll(Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^1([+-])", Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "$1")
                    .replaceAll("0([1-9])", "0.0" + "$1")
                    .replaceAll("(\\d+)\\.0", "$1")
            );
        }
        if (!result.isEmpty() && result.charAt(0) == '+') {
            result.deleteCharAt(0);
        }
        if (result.length() >1 && result.charAt(0) == '0' && result.charAt(1) == '*') {
            result = new StringBuilder("0");
        }
        if (!result.isEmpty() && result.charAt(0) == '1' && result.charAt(1) == '*') {
            result.deleteCharAt(0);
            result.deleteCharAt(0);
        }
        if (result.length() > 1) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return String.valueOf(result);
    }
    public String ui(){
        StringBuilder result = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");
        for (Monomial monomial : monomials.values()) {
            monomial.setCoefficient(Double.parseDouble(df.format(monomial.getCoefficient())));
            result.append(monomial);
        }
        if (result.toString().equals("(0.0*x^0+") || result.isEmpty() ||result.toString().equals("0.0*x^1")) {
            result = new StringBuilder("0");
        }
        else {
            result = new StringBuilder(result.toString().replaceAll("\\+-", "-")
                    .replaceAll("\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^0", "")
                    .replaceAll("([+-])1.0\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL, "$1" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL)
                    .replaceAll("([+-])0.0\\*" + Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^(\\d+)?", "")
                    .replaceAll(Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "\\^1([+-])", Polynomial.VARIABLE_OF_THE_POLYNOMIAL + "$1")
                    .replaceAll("0([1-9])", "0.0" + "$1")
                    .replaceAll("(\\d+)\\.0", "$1")
            );
        }
        if (!result.isEmpty() && result.charAt(0) == '+') {
            result.deleteCharAt(0);
        }
        if (result.length() >1 && result.charAt(0) == '0' && result.charAt(1) == '*') {
            result = new StringBuilder("0");
        }
        if (!result.isEmpty() && result.charAt(0) == '1' && result.charAt(1) == '*') {
            result.deleteCharAt(0);
            result.deleteCharAt(0);
        }
        if (result.length() > 1) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return String.valueOf(result);
    }


    @Override
    public String toString() {
        return prettyPrint();
    }

    public Map<Integer, Monomial> getMonomials() {
        return monomials;
    }


}
