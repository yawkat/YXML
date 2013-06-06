package at.yawk.yxml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link Iterator} and {@link Enumeration} implementations for {@link Lexer}.
 */
public class LexerIterator implements Iterator<Node>, Enumeration<Node> {
    private final Lexer lexer;
    private Node nextNode = null;
    
    public LexerIterator(Lexer lexer) {
        this.lexer = lexer;
    }
    
    @Override
    public boolean hasMoreElements() {
        return hasNext();
    }
    
    @Override
    public Node nextElement() {
        return next();
    }
    
    @Override
    public boolean hasNext() {
        if (nextNode != null) {
            return true;
        } else {
            parseNext();
            return nextNode != null;
        }
    }
    
    @Override
    public Node next() {
        if (nextNode == null) {
            parseNext();
        }
        if (nextNode == null) {
            throw new NoSuchElementException();
        }
        Node tmp = nextNode;
        nextNode = null;
        return tmp;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    private void parseNext() {
        try {
            nextNode = lexer.next();
        } catch (IOException e) {
            nextNode = null;
        }
    }
}
