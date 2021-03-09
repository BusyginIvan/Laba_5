package project.save_and_load;

import java.util.HashSet;

public class ParentTag extends Tag {
    private HashSet<ParentTag> parentTags;
    private HashSet<TextTag> textTags;

    public ParentTag(TagMatcher matcher) {
        super(matcher);
        parentTags = new HashSet<>();
        textTags = new HashSet<>();
        TagMatcher childrenMatcher = new TagMatcher(matcher.getContent());
        while (childrenMatcher.findTag()) {
            if (childrenMatcher.haveTag())
                parentTags.add(new ParentTag(childrenMatcher));
            else
                textTags.add(new TextTag(childrenMatcher));
        }
    }
}