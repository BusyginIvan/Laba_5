package project.parsing.tags;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс, представляющий тег, содержащий другие теги. Помимо имени и списка аргументов хранит список текстовых и
 * список родительских тегов, в нём содержащихся.
 */
public class ParentTag extends Tag {
    private HashSet<ParentTag> parentTags;
    private HashSet<TextTag> textTags;

    /**
     * Создаёт тег по последней находке объекта TagMatcher.
     * @param matcher объект, предварительно нашедший тег в тексте методом {@link TagMatcher#findTag()}.
     * @exception IllegalStateException бросается, если тег не искался или не был найден.
     */
    public ParentTag(TagMatcher matcher) {
        super(matcher);
        parentTags = new HashSet<>();
        textTags = new HashSet<>();
        TagMatcher contentMatcher = new TagMatcher(matcher.getContent());
        while (contentMatcher.findTag()) {
            if (contentMatcher.haveTag())
                parentTags.add(new ParentTag(contentMatcher));
            else
                textTags.add(new TextTag(contentMatcher));
        }
    }

    /**
     * Создаёт тег без аргументов и тегов внутри с указанным именем.
     * @param name имя тега.
     */
    public ParentTag(String name) {
        super(name);
        parentTags = new HashSet<>();
        textTags = new HashSet<>();
    }

    /**
     * Возвращает текстовое представление этого тега.
     * @return текстовый тег.
     */
    public String toText() {
        String result = beginTag();
        for (ParentTag parentTag: parentTags)
            result += "\n\t" + parentTag.toText().replaceAll("\n", "\n\t");
        for (TextTag textTag: textTags)
            result += "\n\t" + textTag.toText().replaceAll("\n", "\n\t");
        return result + "\n" + endTag();
    }

    /**
     * Добавляет родительский тег, если такого ещё нет.
     * @param parentTag тег, содержащий теги.
     * @return true, если тега с таким именем ещё не было.
     */
    public boolean addParentTag(ParentTag parentTag) {
        return parentTags.add(parentTag);
    }

    /**
     * Добавляет тег с текстовым содержимым, если такого ещё нет.
     * @param textTag тег с текстом.
     * @return true, если тега с таким именем ещё не было.
     */
    public boolean addTextTag(TextTag textTag) {
        return textTags.add(textTag);
    }

    /**
     * Возвращает множество родительских тегов.
     * @return множество тегов.
     */
    public HashSet<ParentTag> getParentTags() {
        return parentTags;
    }

    /**
     * Возвращает множество тегов с текстом.
     * @return множество тегов.
     */
    public HashSet<TextTag> getTextTags() {
        return textTags;
    }

    /**
     * Возвращает содержимое вложенного тега с текстом.
     * @param name имя искомого тега.
     * @return текст или null, если тега нет.
     * @exception DuplicateTagException если тегов с таким именем несколько.
     */
    public String getNestedTagContent(String name) {
        String content = null;
        for (TextTag nestedTag: getTextTags())
            if (nestedTag.getName().equals(name))
                if (content == null)
                    content = nestedTag.getContent();
                else
                    throw new DuplicateTagException(name);
        return content;
    }

    /**
     * Возвращает вложенный тег, содержащий теги.
     * @param name имя искомого тега.
     * @return тег или null, если его нет.
     * @exception DuplicateTagException если тегов с таким именем несколько.
     */
    public ParentTag getNestedParentTag(String name) {
        ParentTag nestedParentTag = null;
        for (ParentTag nestedTag: getParentTags())
            if (nestedTag.getName().equals(name))
                if (nestedParentTag == null)
                    nestedParentTag = nestedTag;
                else
                    throw new InvalidTagException("Дублируется тег " + name + ".");
        return nestedParentTag;
    }

    /**
     * Удаляет все вложенные теги с таким именем.
     * @param name имя удаляемых тегов.
     * @return true, если хоть один тег был удалён.
     */
    public boolean removeNestedParentTag(String name) {
        Iterator<ParentTag> iterator1 = parentTags.iterator();
        boolean removed = false;
        while (iterator1.hasNext()) {
            if (iterator1.next().getName().equals(name)) {
                iterator1.remove();
                removed = true;
            }
        }
        Iterator<TextTag> iterator2 = textTags.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().getName().equals(name)) {
                iterator2.remove();
                removed = true;
            }
        }
        return removed;
    }
}