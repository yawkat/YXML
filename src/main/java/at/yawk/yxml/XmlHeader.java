package at.yawk.yxml;

/**
 * An XML header tag.
 */
public class XmlHeader extends SpecialTagNode {
    public XmlHeader(String content) {
        super(content.substring(1, content.length() - 1));
    }
}
