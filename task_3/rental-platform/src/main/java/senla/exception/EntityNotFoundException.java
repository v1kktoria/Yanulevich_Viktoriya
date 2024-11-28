package senla.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException forParam(Object param) {
        String message = String.format(ExceptionEnum.ENTITY_NOT_FOUND.getMessage(), param);
        return new EntityNotFoundException(message);
    }
}
