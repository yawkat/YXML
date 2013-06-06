package at.yawk.yxml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Any tag in an XML document.
 */
public class TagNode extends Node {
    private boolean parsed = false;
    private TagType type;
    private String tagName;
    private List<Attribute> attributes;
    
    public TagNode(String content) {
        super(content);
    }
    
    /**
     * Return the type this tag is of.
     */
    public TagType getType() {
        parseIfNecessary();
        return type;
    }
    
    /**
     * Unmodified name of this tag. Using {@link String#toLowerCase()} on this
     * value is recommended in most cases.
     */
    public String getTagName() {
        parseIfNecessary();
        return tagName;
    }
    
    /**
     * List of all attributes in this node. Ordered as they were in the input
     * document.
     */
    public List<Attribute> getAttributes() {
        parseIfNecessary();
        return Collections.unmodifiableList(attributes);
    }
    
    private void parseIfNecessary() {
        if (!parsed) {
            parse();
            parsed = true;
        }
    }
    
    private void parse() {
        char[] chr = getRawContent().toCharArray();
        if (chr[0] == '/') {
            type = TagType.END;
        } else if (chr[chr.length - 1] == '/') {
            type = TagType.START_END;
        } else {
            type = TagType.START;
        }
        int i = 0;
        {
            for (; i < chr.length && !XmlUtil.isWhitespace(chr[i]); i++);
            boolean endTag = type == TagType.END;
            tagName = new String(chr, endTag ? 1 : 0, endTag ? i - 1 : i);
        }
        int len = chr.length;
        if (type == TagType.START_END) {
            len--;
        }
        attributes = new ArrayList<Attribute>();
        while (i < len) {
            for (; i < len && XmlUtil.isWhitespace(chr[i]); i++);
            int attributeNameStart = i;
            for (; i < len && !XmlUtil.isWhitespace(chr[i]) && chr[i] != '='; i++);
            String key = new String(chr, attributeNameStart, i - attributeNameStart);
            String value;
            if (len > i && chr[i] == '=') {
                i++;
                boolean quotes = chr[i] == '\'' || chr[i] == '"';
                char quotesChar = 0;
                if (quotes) {
                    quotesChar = chr[i];
                    i++;
                }
                int startValue = i;
                for (; i < len && (quotes ? chr[i] != quotesChar : !XmlUtil.isWhitespace(chr[i])); i++);
                value = new String(chr, startValue, i - startValue);
                if (quotes) {
                    i++;
                }
            } else {
                value = null;
            }
            if (!key.isEmpty()) {
                attributes.add(new Attribute(key, value));
            }
        }
    }
    
    /**
     * The type of a tag.
     */
    public static enum TagType {
        START,
        END,
        START_END;
        
        /**
         * Returns <code>true</code> if this equals {@link #START_END}
         */
        public boolean isStartAndEnd() {
            return this == START_END;
        }
        
        /**
         * Returns <code>true</code> if this tag is a start tag of any kind and
         * may contain attributes ({@link #START} and {@link #START_END}).
         */
        public boolean isStart() {
            return this == START || isStartAndEnd();
        }
        
        /**
         * Returns <code>true</code> if this tag is a end tag of any kind (
         * {@link #END} and {@link #START_END}).
         */
        public boolean isEnd() {
            return this == END || isStartAndEnd();
        }
    }
    
    /**
     * An attribute immutable.
     */
    public static final class Attribute {
        private final String key;
        private final String value;
        
        public Attribute(String key, String value) {
            this.key = key;
            this.value = value;
        }
        
        public String getKey() {
            return key;
        }
        
        public String getValue() {
            return value;
        }
        
        public String getXmlValue() {
            return getValue() == null ? getKey() : getValue();
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Attribute other = (Attribute) obj;
            if (key == null) {
                if (other.key != null) {
                    return false;
                }
            } else if (!key.equals(other.key)) {
                return false;
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else if (!value.equals(other.value)) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() {
            return "Attribute [key=" + key + ", value=" + value + "]";
        }
    }
}
