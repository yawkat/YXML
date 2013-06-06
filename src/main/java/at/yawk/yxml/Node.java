package at.yawk.yxml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.yawk.yxml.TagNode.Attribute;
import at.yawk.yxml.TagNode.TagType;

/**
 * Any node in an XML document.
 */
public abstract class Node {
    private String content;
    
    public Node(String content) {
        this.setContent(content);
    }
    
    /**
     * Returns the raw content of this node, without any parsing or formatting
     * applied to it.
     */
    public String getRawContent() {
        return content;
    }
    
    protected void setContent(String content) {
        this.content = content;
    }
    
    // compatibility
    
    /**
     * @deprecated Use {@link Lexer#next()} instead.
     */
    @Deprecated
    public static Node getCurrentNode(Lexer lexer) {
        return lexer.currentNode;
    }
    
    /**
     * @deprecated Use {@link #getRawContent()} instead.
     */
    @Deprecated
    public String getCurrentElementContent() {
        return getRawContent();
    }
    
    @Deprecated
    public String getCurrentElement() {
        return isTag() ? "<" + getCurrentElementContent() + ">" : getCurrentElementContent();
    }
    
    /**
     * @deprecated Use {@link Lexer#setCleanupWhitespace(boolean)} to ignore
     *             whitespace.
     */
    @Deprecated
    public boolean isEmpty() {
        return this instanceof TextNode && ((TextNode) this).getText().trim().isEmpty();
    }
    
    /**
     * @deprecated Check for {@link TagNode} instead.
     */
    @Deprecated
    public boolean isTag() {
        return this instanceof TagNode;
    }
    
    /**
     * @deprecated Use {@link TagNode#getType()} instead.
     */
    @Deprecated
    public boolean isEndTagOnly() {
        return isTag() && ((TagNode) this).getType() == TagType.END;
    }
    
    /**
     * @deprecated Use {@link TagNode#getType()} instead.
     */
    @Deprecated
    public boolean isCompactTag() {
        return isTag() && ((TagNode) this).getType() == TagType.START_END;
    }
    
    /**
     * @deprecated Use {@link TagNode#getTagName()} instead.
     */
    @Deprecated
    public String getTagName() {
        if (!isTag()) {
            throw new IllegalStateException("Not a tag");
        }
        return ((TagNode) this).getTagName();
    }
    
    /**
     * @deprecated Use {@link TagNode#getTagName()} instead.
     */
    @Deprecated
    public String getLowercaseTagName() {
        return getTagName().toLowerCase();
    }
    
    /**
     * @deprecated Use {@link TagNode#getAttributes()} instead.
     */
    @Deprecated
    public Map<String, String> getAttributesMap() {
        if (!isTag()) {
            throw new IllegalStateException("Not a tag");
        }
        final List<Attribute> l = ((TagNode) this).getAttributes();
        final Map<String, String> m = new HashMap<String, String>(l.size());
        for (Attribute a : l) {
            m.put(a.getKey(), a.getValue());
        }
        return m;
    }
    
    /**
     * @deprecated Use {@link TagNode#getAttributes()} instead.
     */
    @Deprecated
    public String getAttribute(String key) {
        return getAttributesMap().get(key);
    }
}
