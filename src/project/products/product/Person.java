package project.products.product;

import com.sun.istack.internal.NotNull;
import project.products.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.util.ArrayList;

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

    private static ArrayList<String> usedPassportID = new ArrayList<>();

    /**
     * Пустой конструктор. Используется в {@link project.products.ElementBuilder}.
     */
    public Person() { }

    /**
     * Получает значения характеристик личности из тега.
     * @param personTag тег с вложенными тегами name, passportID, weight и, возможно, height и location.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или
     * если в них содержатся некорректные данные.
     */
    public Person(ParentTag personTag) {
        try {
            name: {
                for (String key : personTag.getArguments().keySet())
                    if (key.equals("name")) {
                        setName(personTag.getArguments().get(key));
                        break name;
                    }
                throw new InvalidTagException(this.getClass(), "У тега нет аргумента имени.");
            }
            try {
                String content = getChildTagContent(personTag, "height");
                setHeight(content == null ? null : Float.valueOf(content));
            } catch (NumberFormatException e) {
                throw new InvalidTagException(this.getClass(), "Значение роста в теге записано некорректно.");
            }
            try {
                setWeight(Float.valueOf(getNotNullContent(personTag, "weight")));
            } catch (NumberFormatException e) {
                throw new InvalidTagException(this.getClass(), "Значение веса в теге записано некорректно.");
            }
            setPassportID(getNotNullContent(personTag, "passportID"));
            for (ParentTag element: personTag.getParentTags())
                if (element.getName().equals("location")) {
                    setLocation(new Location(element));
                    break;
                }
        } catch (IllegalArgumentException e) {
            throw new InvalidTagException(e.getMessage());
        }
    }

    private static String getChildTagContent(ParentTag tag, String name) {
        for (TextTag element: tag.getTextTags())
            if (element.getName().equals(name))
                return element.getContent();
        return null;
    }

    private static String getNotNullContent(ParentTag tag, String fieldName) {
        String content = getChildTagContent(tag, fieldName);
        if (content == null)
            throw new InvalidTagException(Person.class, "Отсутствует тег для поля " + fieldName + ".");
        return content;
    }

    /**
     * Метод для получения тега, описывающего этого человека.
     * @return тег person, содержащий теги, соответствующие каждому непустому полю класса.
     */
    public ParentTag getTag() {
        ParentTag parentTag = new ParentTag("person");
        parentTag.addArgument("name", name);
        parentTag.addTextTag(new TextTag("passportID", passportID));
        parentTag.addTextTag(new TextTag("weight", weight + ""));
        if (!(height == null))
            parentTag.addTextTag(new TextTag("height", height + ""));
        if (!(location == null))
            parentTag.addParentTag(location.getTag());
        return parentTag;
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
     * Позволяет указать номер паспорта. Номер должен быть уникальным (ранее не использовавшимся).
     * @param passportID номер паспорта.
     * @throws IllegalArgumentException бросает, если передан null, строка менее чем из 4 символов или уже использовавшийся номер.
     */
    public void setPassportID(@NotNull String passportID) throws IllegalArgumentException {
        if (passportID == null || passportID.length() < 4)
            throw new IllegalArgumentException("Номер паспорта должен быть представлен строкой не менее чем из 4 символов!");
        if (usedPassportID.contains(passportID))
            throw new IllegalArgumentException("Номер паспорта должен быть уникальным!");
        usedPassportID.remove(this.passportID);
        usedPassportID.add(passportID);
        this.passportID = passportID;
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

    /**
     * Сравнивает с другим человеком по положению имён в алфавитном порядке.
     * @param person другой человек.
     * @return число меньше нуля, если имя этого человека предшествует имени другого; равное нулю, если они тёзки; больше нуля, если имя этого человека следует за именем другого.
     */
    @Override
    public int compareTo(Person person) {
        return name.compareTo(person.getName());
    }

    /**
     * Проверяет, представляет ли другой объект этого же человека.
     * @param o объект некоторого класса.
     * @return true, если объект ссылается на того же человека.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return passportID.equals(((Person) o).passportID);
    }
}