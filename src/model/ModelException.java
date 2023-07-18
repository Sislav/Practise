package model;
public class ModelException extends Exception{
    private String ErrorMessage;
    ModelException(String errorMessage){
        ErrorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return ErrorMessage;
    }
}
