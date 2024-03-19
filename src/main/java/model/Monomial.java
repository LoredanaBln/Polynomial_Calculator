package model;

import java.text.DecimalFormat;
import java.util.Objects;

public class Monomial implements Comparable<Monomial> {
    private Integer degree;
    private Number coefficient;

    public Monomial() {
    }

    public Monomial(Integer degree, Number coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Number getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Number coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return coefficient + "*x^" + degree + "+";
    }

    @Override
    public int compareTo(Monomial other) {
        return other.degree.compareTo(this.degree);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Monomial monomial = (Monomial) obj;
        return degree.equals(monomial.degree) && coefficient.equals(monomial.coefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degree, coefficient);
    }
}
