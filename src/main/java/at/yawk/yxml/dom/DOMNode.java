package at.yawk.yxml.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import at.yawk.yxml.Node;
import at.yawk.yxml.TagNode;
import at.yawk.yxml.TagNode.Attribute;

/**
 * A node in a DOM tree.
 */
public class DOMNode {
    private final DOMNode parent;
    private final List<DOMNode> children;
    private final Node element;
    
    public DOMNode(DOMNode parent, List<DOMNode> children, Node element) {
        assert children != null;
        this.parent = parent;
        this.children = children;
        this.element = element;
    }
    
    public DOMNode(DOMNode parent, Node element) {
        this(parent, new LinkedList<DOMNode>(), element);
    }
    
    public DOMNode(Node element) {
        this(null, element);
    }
    
    /**
     * The parent of this node or <code>null</code> if this is the root element.
     */
    public DOMNode getParent() {
        return parent;
    }
    
    /**
     * A list of all children of this node. May be empty, but not
     * <code>null</code>.
     */
    public List<DOMNode> getChildren() {
        return Collections.unmodifiableList(children);
    }
    
    /**
     * The XML element this node is about. May be <code>null</code> if this is
     * the root element.
     */
    public Node getElement() {
        return element;
    }
    
    /**
     * Append a new {@link DOMNode} for the given element to this node and
     * return it.
     */
    public DOMNode appendChild(Node element) {
        return appendChild(new DOMNode(this, element));
    }
    
    /**
     * Append a new {@link DOMNode} to this node and return it.
     */
    public <W extends DOMNode> W appendChild(W child) {
        children.add(child);
        return child;
    }
    
    /**
     * Get all children that match the given {@link DOMMatcher}. This will stop
     * searching as soon as all elements were searched or the given limit was
     * reached. If the <code>deep</code> argument equals <code>true</code>, a
     * deep search will be performed. This element will be added as well if it
     * matches the {@link DOMMatcher} and deep search is enabled.
     */
    public List<DOMNode> getChildrenForMatch(DOMMatcher matcher, int limit, boolean deep) {
        if (limit <= 0) {
            return Collections.emptyList();
        }
        final List<DOMNode> matches = new ArrayList<DOMNode>();
        if (deep) {
            if (matcher.matches(this)) {
                matches.add(this);
            }
            for (DOMNode d : getChildren()) {
                matches.addAll(d.getChildrenForMatch(matcher, limit - matches.size(), true));
            }
        } else {
            for (DOMNode d : getChildren()) {
                if (matcher.matches(d)) {
                    matches.add(d);
                    if (matches.size() >= limit) {
                        break;
                    }
                }
            }
        }
        return matches;
    }
    
    /**
     * Returns the first element found by
     * {@link #getChildrenForMatch(DOMMatcher, int, boolean)} or
     * <code>null</code> if none was found.
     * 
     * @see #getChildrenForMatch(DOMMatcher, int, boolean)
     */
    public DOMNode getChildForMatch(DOMMatcher matcher, boolean deep) {
        final List<DOMNode> l = getChildrenForMatch(matcher, 1, deep);
        return l.isEmpty() ? null : l.get(0);
    }
    
    /**
     * A matcher that can check {@link DOMNode} objects.
     */
    public static interface DOMMatcher {
        /**
         * Returns <code>true</code> if the given {@link DOMNode} matches this
         * {@link DOMMatcher}, <code>false</code> otherwise.
         */
        boolean matches(DOMNode node);
    }
    
    /**
     * Returns a matcher that will match any tags that have an attribute
     * <code>key</code> with the exact value <code>value</code>.
     */
    public static DOMMatcher getAttributeEqualsMatcher(final String key, final String value) {
        return new DOMMatcher() {
            @Override
            public boolean matches(DOMNode node) {
                final Node e = node.getElement();
                if (e instanceof TagNode) {
                    for (Attribute attribute : ((TagNode) e).getAttributes()) {
                        if (attribute.getKey().equals(key) && attribute.getValue().equals(value)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }
}
