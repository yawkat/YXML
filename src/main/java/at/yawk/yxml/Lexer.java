package at.yawk.yxml;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * A lexer that can iterate over a {@link Reader} and parses {@link Node}
 * objects.
 */
public class Lexer {
    private final char[] singleCharArray = new char[1];
    private final Reader reader;
    
    private boolean insideTag = false;
    private boolean cleanupWhitespace = false;
    
    /**
     * Default constructor.
     */
    public Lexer(Reader reader) {
        this.reader = reader;
    }
    
    /**
     * @see #Lexer(Reader)
     * @see #setCleanupWhitespace(boolean)
     */
    public Lexer(Reader reader, boolean cleanupWhitespace) {
        this(reader);
        setCleanupWhitespace(cleanupWhitespace);
    }
    
    /**
     * Create a new {@link Lexer} from the given {@link InputStream} and
     * {@link Charset} name using {@link InputStreamReader}.
     * 
     * @see #Lexer(Reader)
     */
    public Lexer(InputStream input, String charset) throws UnsupportedEncodingException {
        this(new InputStreamReader(input, charset));
    }
    
    /**
     * Create a new {@link Lexer} from the given {@link InputStream} and default
     * {@link Charset} using {@link InputStreamReader}.
     * 
     * @see #Lexer(InputStream, String)
     */
    public Lexer(InputStream input) throws UnsupportedEncodingException {
        this(input, Charset.defaultCharset().name());
    }
    
    /**
     * Create a new {@link Lexer} from the given {@link URLConnection} and
     * default {@link Charset} using {@link URLConnection#getInputStream()}.
     * 
     * @see #Lexer(InputStream)
     */
    public Lexer(URLConnection connection) throws IOException {
        this(connection.getInputStream());
    }
    
    /**
     * Create a new {@link Lexer} from the given {@link URL} and default
     * {@link Charset} using {@link URL#openConnection()}.
     * 
     * @see #Lexer(URLConnection)
     */
    public Lexer(URL url) throws IOException {
        this(url.openConnection());
    }
    
    /**
     * Return the next node.
     * 
     * @throws EOFException
     *             if there are no more elements available.
     * @throws IOException
     *             if any IO errors occur.
     */
    public Node next() throws IOException {
        while (true) {
            final Node n = next0();
            if (cleanupWhitespace) {
                if (n instanceof TextNode) {
                    n.setContent(n.getRawContent().trim());
                    if (n.getRawContent().isEmpty()) {
                        continue;
                    }
                }
            }
            // retain for compatibility
            currentNode = n;
            return n;
        }
    }
    
    private Node next0() throws IOException {
        final StringBuilder b = new StringBuilder();
        while (true) {
            char c;
            try {
                c = readCharacter();
            } catch (EOFException e) {
                if (b.length() == 0) {
                    throw new EOFException();
                }
                break;
            }
            if (insideTag) {
                if (c == '>') {
                    break;
                }
            } else {
                if (c == '<') {
                    break;
                }
            }
            b.append(c);
        }
        String element = b.toString();
        insideTag = !insideTag;
        return parseElement(element, !insideTag);
    }
    
    private Node parseElement(String element, boolean parseAsTag) {
        if (parseAsTag) {
            final char[] chr = element.toCharArray();
            if (chr.length == 0) {
                return new EmptyTagNode();
            }
            char c1 = chr[0];
            if (c1 == '/' && chr.length > 1) {
                c1 = chr[1];
            }
            if (XmlUtil.isNameStartCharacter(c1)) {
                return new TagNode(element);
            } else if (c1 == '?' && chr[chr.length - 1] == '?' && chr.length > 2) {
                return new XmlHeader(element);
            } else {
                return new UnknownTagNode(element);
            }
        } else {
            return new TextNode(element);
        }
    }
    
    private char readCharacter() throws IOException {
        if (reader.read(singleCharArray) == -1) {
            throw new EOFException();
        }
        return singleCharArray[0];
    }
    
    /**
     * If set to <code>true</code>, this will ignore whitespace-only text nodes
     * and trim any other text nodes of any leading and trailing whitespace.
     */
    public boolean isCleanupWhitespace() {
        return cleanupWhitespace;
    }
    
    /**
     * @see #isCleanupWhitespace()
     */
    public void setCleanupWhitespace(boolean cleanupWhitespace) {
        this.cleanupWhitespace = cleanupWhitespace;
    }
    
    // compatibility
    
    Node currentNode;
    
    /**
     * Continue reading the stream.
     * 
     * @return <code>false</code> if there are no new elements available.
     * @throws IOException
     */
    @Deprecated
    public boolean getNext() throws IOException {
        try {
            currentNode = next();
            return true;
        } catch (EOFException e) {
            return false;
        }
    }
    
    @Deprecated
    public String getCurrentElementContent() {
        return currentNode.getCurrentElementContent();
    }
    
    @Deprecated
    public String getCurrentElement() {
        return currentNode.getCurrentElement();
    }
    
    @Deprecated
    public boolean isEmpty() {
        return currentNode.isEmpty();
    }
    
    @Deprecated
    public boolean isTag() {
        return currentNode.isTag();
    }
    
    @Deprecated
    public boolean isEndTagOnly() {
        return currentNode.isEndTagOnly();
    }
    
    @Deprecated
    public boolean isCompactTag() {
        return currentNode.isCompactTag();
    }
    
    @Deprecated
    public String getTagName() {
        return currentNode.getTagName();
    }
    
    @Deprecated
    public String getLowercaseTagName() {
        return currentNode.getLowercaseTagName();
    }
    
    @Deprecated
    public Map<String, String> getAttributes() {
        return currentNode.getAttributesMap();
    }
    
    @Deprecated
    public String getAttribute(String key) {
        return currentNode.getAttribute(key);
    }
}
