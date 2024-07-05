package az.edu.turing.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException( String message ){
        super( message );
    }
}
