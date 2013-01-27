package at.yawk.yxml;

import java.util.Map;

public class Node {
    private final String        currentElementData;
    private Map<String, String> attributes;
    private String              tagName;
    
    private final boolean       isTag;
    
    private Node(Lexer l) {
        this.currentElementData = l.getCurrentElementContent();
        this.isTag = l.isTag();
        this.attributes = l.getAttributesParsed();
        this.tagName = l.getTagName();
    }
    
    public static Node getCurrentNode(Lexer lexer) {
        return new Node(lexer);
    }
    
    public String getCurrentElementContent() {
        return currentElementData;
    }
    
    public String getCurrentElement() {
        return isTag() ? '<' + currentElementData + '>' : currentElementData;
    }
    
    public boolean isEmpty() {
        return !isTag() && currentElementData.trim().length() == 0;
    }
    
    public boolean isEndTagOnly() {
        return isTag() && currentElementData.length() > 0 && currentElementData.charAt(0) == '/';
    }
    
    public boolean isCompactTag() {
        return isTag() && currentElementData.length() > 0 && currentElementData.charAt(currentElementData.length() - 1) == '/';
    }
    
    public boolean isTag() {
        return isTag;
    }
    
    public String getLowercaseTagName() {
        return getTagName().toLowerCase();
    }
    
    public String getTagName() {
        if(!isTag())
            throw new IllegalStateException("Not a tag");
        parseTag();
        return tagName;
    }
    
    public Map<String, String> getAttributes() {
        if(!isTag())
            throw new IllegalStateException("Not a tag");
        parseTag();
        return attributes;
    }
    
    private void parseTag() {
        if(attributes == null) {
            attributes = Lexer.parseTag(this.currentElementData);
            this.tagName = attributes.remove(null);
        }
    }
}
