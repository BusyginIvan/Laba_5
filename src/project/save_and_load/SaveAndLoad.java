package project.save_and_load;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Осуществляет загрузку объекта из файла в формате XML, а также сохранение в файл.
 */
public class SaveAndLoad implements ISaver {
    private File file;
    private Class c;

    /**
     * Конструктор, позволяющий создать объект класса для работы с конкретным файлом и типом объекта.
     * @param file объект класса File.
     * @param c Class объекта, который предстоит загружать или сохранять.
     */
    public SaveAndLoad(File file, Class c) {
        this.file = file;
        this.c = c;
    }

    /**
     * Сохраняет объект в файл.
     * @param obj объект для сохранения.
     * @throws FileNotFoundException бросает, если не был найден файл для сохранения.
     * @throws JAXBException ошибка при конвертировании объекта в XML.
     */
    public void save(Object obj) throws FileNotFoundException, JAXBException {
        save(file, obj);
    }

    /**
     * Загружает и возвращает объект из XML-файла.
     * @return загруженный объект.
     */
    public Object load() {
        return load(file, c);
    }

    /**
     * Загружает объект указанного класса из указанного файла.
     * @param file файл для загрузки.
     * @param c Class загружаемого объекта.
     * @return загруженный объект.
     */
    public static Object load(File file, Class c) {
        try {
            return JAXBContext.newInstance(c).createUnmarshaller().unmarshal(new FileReader(file));
        } catch (JAXBException | FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Сохраняет переданный объект в указанный файл.
     * @param file файл для сохранения объекта.
     * @param obj сохраняемый объект.
     * @throws FileNotFoundException бросает, если файл не найден.
     * @throws JAXBException ошибка при попытке преобразования объекта в XML.
     */
    public static void save(File file, Object obj) throws FileNotFoundException, JAXBException {
        getMarshaller(obj.getClass()).marshal(obj, new FileOutputStream(file));
    }

    /**
     * Выводит XML-представление переданного объекта в стандартный поток вывода.
     * @param obj объект для преобразования в XML.
     * @return true, если при работе метода не произошло ошибки.
     */
    public static boolean print(Object obj) {
        try {
            getMarshaller(obj.getClass()).marshal(obj, System.out);
            return true;
        } catch (JAXBException e) {
            return false;
        }
    }

    private static Marshaller getMarshaller(Class c) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(c).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }
}