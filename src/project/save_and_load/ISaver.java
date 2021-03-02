package project.save_and_load;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

/**
 * Класс, реализующий этот интерфейс, может сохранить объект заранее определённого класса в некоторый конкретный файл.
 * @see SaveAndLoad
 */
public interface ISaver {
    /**
     * Созраняет переданный объект в файл.
     * @param obj сохраняемый объект.
     * @throws FileNotFoundException бросает, если файл не найден.
     * @throws JAXBException ошибка при конвертировании объекта в XML.
     */
    void save(Object obj) throws FileNotFoundException, JAXBException;
}