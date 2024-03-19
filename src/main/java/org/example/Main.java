package org.example;

import model.Polynomial;
import controller.helper.StringConversionToPolynomial;
import controller.operations.Operations;

public class Main {
    public static void main(String[] args) {
        StringConversionToPolynomial stringConversionToPolynomial1 = new StringConversionToPolynomial();
        Operations op = new Operations();

        String p1 = "x^3-2*x^2+6*x-5";
        String p2 = "x^2-1";


        Polynomial P1 = stringConversionToPolynomial1.makeStringAPolynomial(p1);
        Polynomial P2 = stringConversionToPolynomial1.makeStringAPolynomial(p2);
        System.out.println(P1.getMonomials() + "\n" +P2);

       // System.out.println(P1.getMonomials() + "\n" + P2.getMonomials());
//        System.out.println(P2.prettyPrint().equals("0"));
        //System.out.println(op.addPolynomial(P1, P2).getMonomials());

//        System.out.println("Polynomial result mukti:");
        System.out.println(op.dividePolynomial(P1, P2).get(0) + "\n" + op.dividePolynomial(P1, P2).get(1).getMonomials());

        //System.out.println(op.addPolynomial(P1, P2).getMonomials());
        // System.out.println(op.dividePolynomial(P1, P2).get(0).getMonomials());
        //System.out.println(op.derivativePolynomial(P2).prettyPrint());


    }


}