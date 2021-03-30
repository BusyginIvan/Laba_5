package project.products.product;

import com.sun.istack.internal.NotNull;

import project.parsing.tags.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import static project.products.ElementBuilder.setField;
import static project.products.ElementBuilder.getLine;

/**
 * Класс, содержащий информацию о человеке: имя, номер паспорта, рост (если он имеет значение), масса и место нахождения (если оно имеет значение).
 * @see Product
 * @see Location
 */
public class Person implements Comparable<Person> {
    private String name;
    private Float height;
    private float weight;
    private String passportID;
    private Location location;

    private static boolean lastIsNew;
    private final static HashSet<Person> persons = new HashSet<>();

    private Person() {}

    /**
     * Поочерёдно запрашивает значения всех полей у пользователя и возвращает нового человека или ссылку на существующего.
     * Устанавливает флаг lastIsNew.
     * @param status существительное в родительном падеже, которым следует назвать человека при запросе номера паспорта.
     * @return null, если пользователь не стал вводить номер паспорта, ссылка на существующего человека с указанным
     * номером паспорта или новый человек.
     */
    public static Person newPerson(String status) {
        String passportID = getLine(
                "Введите номер паспорта " + status + " или оставьте строку пустой",
                str -> str.equals("") ? null : requireValidPassportID(str));

        if (passportID == null) return null;

        for (Person existingPerson: persons)
            if (existingPerson.getPassportID().equals(passportID)) {
                lastIsNew = false;
                return existingPerson;
            }

        Person newPerson = new Person();
        setField("Введите рост владельца или оставьте строку пустой",
                str -> newPerson.setHeight(str.equals("") ? null : Float.valueOf(str)));
        setField("Введите вес владельца",
                str -> newPerson.setWeight(Float.parseFloat(str)));
        newPerson.setLocation(Location.newLocation());
        lastIsNew = true;
        persons.add(newPerson);
        return newPerson;
    }

    /**
     * Получает значения характеристик личности из тега. Создаёт нового человека или возвращает ссылку на уже существующего.
     * Устанавливает флаг lastIsNew.
     * @param personTag тег с вложенными тегами name, passportID, weight и, возможно, height и location.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или
     * если в них содержатся некорректные данные.
     * @exception NotUniquePassportIDException если уже существует человек с таким номером паспорта, но другими характеристиками.
     * @return ссылка на нового или уже существовавшего человека или null, если в качестве тега был передан null.
     */
    public static Person newPerson(ParentTag personTag) {
        if (personTag == null)
            return null;
        String className = "Person";
        try {
            if (!personTag.getArguments().containsKey("passportID"))
                throw new InvalidTagException(className, "У тега нет аргумента с номером паспорта.");
            Person newPerson = new Person();

            newPerson.passportID = requireValidPassportID(personTag.getArguments().get("passportID"));

            newPerson.setName(Objects.requireNonNull(personTag.getNestedTagContent("name"), "name"));

            try {
                String content = personTag.getNestedTagContent("height");
                newPerson.setHeight(content == null ? null : Float.valueOf(content));
            } catch (NumberFormatException e) {
                throw new InvalidTagException(className, "Значение роста в теге записано некорректно.");
            }

            try {
                newPerson.setWeight(Float.parseFloat(Objects.requireNonNull(personTag.getNestedTagContent("weight"), "weight")));
            } catch (NumberFormatException e) {
                throw new InvalidTagException(className, "Значение веса в теге записано некорректно.");
            }

            ParentTag locationTag;
            try {
                locationTag = personTag.getNestedParentTag("location");
            } catch (InvalidTagException e) {
                throw new InvalidTagException(className, e.getMessage());
            }
            newPerson.setLocation(Location.newLocation(locationTag));

            for (Person existingPerson: persons)
                if (existingPerson.getPassportID().equals(newPerson.passportID))
                    if (existingPerson.equals(newPerson)) {
                        lastIsNew = false;
                        return existingPerson;
                    }
                    else
                        throw new NotUniquePassportIDException(newPerson.passportID);
            lastIsNew = true;
            persons.add(newPerson);
            return newPerson;
        } catch (IllegalArgumentException e) {
            throw new InvalidTagException(className, e.getMessage());
        } catch (NullPointerException e) {
            throw new InvalidTagException(className, " Отсутствует тег для поля " + e.getMessage() + ".");
        }
    }

    /**
     * Возвращает текущее значение флага lastIsNew.
     * @return true, если при последнем вызове newPerson() был создан новый человек; false, если была возвращена лишь
     * ссылка на существующего.
     */
    public static boolean lastIsNew() {
        return lastIsNew;
    }

    /**
     * Метод для получения тега, описывающего этого человека.
     * @return тег person, содержащий теги, соответствующие каждому непустому полю класса.
     */
    public ParentTag getTag() {
        ParentTag parentTag = new ParentTag("owner");
        parentTag.addArgument("passportID", passportID);
        parentTag.addTextTag(new TextTag("name", name));
        parentTag.addTextTag(new TextTag("weight", weight + ""));
        if (!(height == null))
            parentTag.addTextTag(new TextTag("height", height + ""));
        if (!(location == null))
            parentTag.addParentTag(location.getTag());
        return parentTag;
    }

    /**
     * Возвращает ссылку на множество всех известных людей.
     * @return множество созданных объектов Person.
     */
    public static HashSet<Person> getPersons() {
        return persons;
    }

    /**
     * Позволяет указать имя человека.
     * @param name новое имя.
     * @throws IllegalArgumentException бросает, если передана пустая строка или null.
     */
    public void setName(@NotNull String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Имя человека должно быть представлено непустой строкой!");
        this.name = name;
    }

    /**
     * Позволяет указать рост.
     * @param height рост в метрах или null.
     * @throws IllegalArgumentException бросает, если передано неположительное число.
     */
    public void setHeight(Float height) throws IllegalArgumentException {
        if (height == null)  {
            this.height = null;
            return;
        }
        if (height <= 0)  throw new IllegalArgumentException("Рост человека не может быть меньше или равен нулю!");
        this.height = height;
    }

    /**
     * Позволяет указать массу.
     * @param weight масса в килограммах.
     * @throws IllegalArgumentException бросает, если передано неположительное число.
     */
    public void setWeight(float weight) throws IllegalArgumentException {
        if (weight <= 0)  throw new IllegalArgumentException("Масса человека не может быть меньше или равна нулю!");
        this.weight = weight;
    }

    /**
     * Требует соблюдения правил для номера паспорта (более 3 символов).
     * @param passportID проверяемая строка.
     * @return то же, что в аргументе.
     */
    public static String requireValidPassportID(String passportID) {
        if (passportID == null || passportID.length() < 4)
            throw new IllegalArgumentException("Номер паспорта должен быть представлен строкой не менее чем из 4 символов!");
        return passportID;
    }

    /**
     * Позволяет указать место нахождения (локацию).
     * @param location объект локации или null.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Возвращает имя.
     * @return имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает рост.
     * @return рост в метрах.
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Возвращает массу.
     * @return масса в кг.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Возвращает номер паспорта.
     * @return номер паспорта.
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Возвращает локацию.
     * @return объект класса, представляющего место нахождения человека, или null.
     */
    public Location getLocation() {
        return location;
    }

    public static boolean removePerson(String passportID) {
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPassportID().equals(passportID)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Переопределяет toString() класса Object. Возвращает строку с именем и номером паспорта.
     * @return имя и номер паспорта.
     */
    @Override
    public String toString() {
        return name + " (номер паспорта - " + passportID + ")";
    }

    /**
     * Сравнивает с другим человеком по положению имён в алфавитном порядке.
     * @param person другой человек.
     * @return число меньше нуля, если имя этого человека предшествует имени другого; равное нулю, если они тёзки; больше нуля, если имя этого человека следует за именем другого.
     * @exception NullPointerException если в аргументе null.
     */
    @Override
    public int compareTo(@NotNull Person person) {
        return name.compareTo(Objects.requireNonNull(person,
                "Ошибка сравнения одного человека с другим по имени. В качестве аргумента передан null.")
                .getName());
    }

    /**
     * Проверяет, представляет ли другой объект этого же человека. Используется для замены при создании копии человека
     * ссылкой на уже существующего.
     * @param o объект некоторого класса.
     * @return true, если передан объект класса Person и значения всех полей совпадают.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Float.compare(person.weight, weight) == 0 &&
                name.equals(person.name) &&
                Objects.equals(height, person.height) &&
                passportID.equals(person.passportID) &&
                Objects.equals(location, person.location);
    }
}