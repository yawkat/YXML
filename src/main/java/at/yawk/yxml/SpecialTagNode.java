package at.yawk.yxml;

/**
 * A tag node that has special XML properties (such as the XML header node)
 */
public class SpecialTagNode extends TagNode {
    public SpecialTagNode(String content) {
        super(content);
    }
    
    @Override
    public TagType getType() {
        return TagType.START_END;
    }
}
