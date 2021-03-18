package project.products.product;

/**
 * Исключение, связанное с невозможностью совпадения номеров паспорта у двух людей.
 */
public class NotUniquePassportIDException extends RuntimeException {
    private String passportID;

    /**
     * Создаёт исключение с сообщением ошибки и сохраняет совпавший номер паспорта.
     * @param passportID номер паспорта, на который претендует ещё один человек.
     */
    public NotUniquePassportIDException(String passportID) {
        super("Для нового человека указан уже существующий номер паспорта: " + passportID + ".");
        this.passportID = passportID;
    }

    /**
     * Возвращает номер паспорта, вызвавший конфликт.
     * @return номер паспорта.
     */
    public String getPassportID() {
        return passportID;
    }
}