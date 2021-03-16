package project.products.product;

import project.products.InvalidTagException;

public class ContainsPassportID extends InvalidTagException {

    public ContainsPassportID(String message) {
        super(message);
    }

    public ContainsPassportID(Class c, String message) {
        super(c, message);
    }
}