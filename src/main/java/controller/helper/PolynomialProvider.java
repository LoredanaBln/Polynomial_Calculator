package controller.helper;

import controller.helper.OperationType;
import model.Polynomial;

import java.util.List;

public interface PolynomialProvider {
    List<Polynomial[]> createPolynomial(OperationType operationType);
}
