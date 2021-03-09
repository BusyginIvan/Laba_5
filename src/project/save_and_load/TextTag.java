package project.save_and_load;

public class TextTag extends Tag {
    private String content;

    public TextTag(TagMatcher matcher) {
        super(matcher);
        content = matcher.getContent();
    }
}