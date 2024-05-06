package model;

//Code taken from library app assignment
public class OperationNotAllowedException extends Exception {

    public OperationNotAllowedException(String errorMessage) {
        super(errorMessage);
    }

}