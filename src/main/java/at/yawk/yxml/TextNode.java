package at.yawk.yxml;

/**
 * Node only consisting of raw text.
 */
public class TextNode extends Node {
    public TextNode(String content) {
        super(content);
    }
    
    /**
     * @see #getRawContent()
     */
    public String getText() {
        return getRawContent();
    }
    
    public String getUnescapedText(EntityNamespace entities) {
        return XmlUtil.unescapeXml(getText(), entities);
    }
}
