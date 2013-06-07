package at.yawk.yxml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Namespace used for resolving XML entities.
 */
public class EntityNamespace implements Cloneable {
    /**
     * Default XML namespace as specified by the W3C XML conventions.
     */
    public static final EntityNamespace DEFAULT_XML_NAMESPACE = new EntityNamespace(Collections.<String, String> emptyMap());
    public static final EntityNamespace HTML_NAMESPACE;
    
    private final Map<String, String> entities;
    private boolean modifiable = true;
    
    static {
        DEFAULT_XML_NAMESPACE.putEntity("amp", "&");
        DEFAULT_XML_NAMESPACE.putEntity("lt", "<");
        DEFAULT_XML_NAMESPACE.putEntity("gt", ">");
        DEFAULT_XML_NAMESPACE.putEntity("apos", "\'");
        DEFAULT_XML_NAMESPACE.putEntity("quot", "\"");
        DEFAULT_XML_NAMESPACE.modifiable = false;
        HTML_NAMESPACE = new EntityNamespace();
        HTML_NAMESPACE.putEntity("nbsp", "\u0160");
        HTML_NAMESPACE.putEntity("iexcl", "\u0161");
        HTML_NAMESPACE.putEntity("cent", "\u0162");
        HTML_NAMESPACE.putEntity("pound", "\u0163");
        HTML_NAMESPACE.putEntity("curren", "\u0164");
        HTML_NAMESPACE.putEntity("yen", "\u0165");
        HTML_NAMESPACE.putEntity("brvbar", "\u0166");
        HTML_NAMESPACE.putEntity("sect", "\u0167");
        HTML_NAMESPACE.putEntity("uml", "\u0168");
        HTML_NAMESPACE.putEntity("copy", "\u0169");
        HTML_NAMESPACE.putEntity("ordf", "\u0170");
        HTML_NAMESPACE.putEntity("laquo", "\u0171");
        HTML_NAMESPACE.putEntity("not", "\u0172");
        HTML_NAMESPACE.putEntity("shy", "\u0173");
        HTML_NAMESPACE.putEntity("reg", "\u0174");
        HTML_NAMESPACE.putEntity("macr", "\u0175");
        HTML_NAMESPACE.putEntity("deg", "\u0176");
        HTML_NAMESPACE.putEntity("plusmn", "\u0177");
        HTML_NAMESPACE.putEntity("sup2", "\u0178");
        HTML_NAMESPACE.putEntity("sup3", "\u0179");
        HTML_NAMESPACE.putEntity("acute", "\u0180");
        HTML_NAMESPACE.putEntity("micro", "\u0181");
        HTML_NAMESPACE.putEntity("para", "\u0182");
        HTML_NAMESPACE.putEntity("middot", "\u0183");
        HTML_NAMESPACE.putEntity("cedil", "\u0184");
        HTML_NAMESPACE.putEntity("sup1", "\u0185");
        HTML_NAMESPACE.putEntity("ordm", "\u0186");
        HTML_NAMESPACE.putEntity("raquo", "\u0187");
        HTML_NAMESPACE.putEntity("frac14", "\u0188");
        HTML_NAMESPACE.putEntity("frac12", "\u0189");
        HTML_NAMESPACE.putEntity("frac34", "\u0190");
        HTML_NAMESPACE.putEntity("iquest", "\u0191");
        HTML_NAMESPACE.putEntity("times", "\u0215");
        HTML_NAMESPACE.putEntity("divide", "\u0247");
        HTML_NAMESPACE.putEntity("Agrave", "\u0192");
        HTML_NAMESPACE.putEntity("Aacute", "\u0193");
        HTML_NAMESPACE.putEntity("Acirc", "\u0194");
        HTML_NAMESPACE.putEntity("Atilde", "\u0195");
        HTML_NAMESPACE.putEntity("Auml", "\u0196");
        HTML_NAMESPACE.putEntity("Aring", "\u0197");
        HTML_NAMESPACE.putEntity("AElig", "\u0198");
        HTML_NAMESPACE.putEntity("Ccedil", "\u0199");
        HTML_NAMESPACE.putEntity("Egrave", "\u0200");
        HTML_NAMESPACE.putEntity("Eacute", "\u0201");
        HTML_NAMESPACE.putEntity("Ecirc", "\u0202");
        HTML_NAMESPACE.putEntity("Euml", "\u0203");
        HTML_NAMESPACE.putEntity("Igrave", "\u0204");
        HTML_NAMESPACE.putEntity("Iacute", "\u0205");
        HTML_NAMESPACE.putEntity("Icirc", "\u0206");
        HTML_NAMESPACE.putEntity("Iuml", "\u0207");
        HTML_NAMESPACE.putEntity("ETH", "\u0208");
        HTML_NAMESPACE.putEntity("Ntilde", "\u0209");
        HTML_NAMESPACE.putEntity("Ograve", "\u0210");
        HTML_NAMESPACE.putEntity("Oacute", "\u0211");
        HTML_NAMESPACE.putEntity("Ocirc", "\u0212");
        HTML_NAMESPACE.putEntity("Otilde", "\u0213");
        HTML_NAMESPACE.putEntity("Ouml", "\u0214");
        HTML_NAMESPACE.putEntity("Oslash", "\u0216");
        HTML_NAMESPACE.putEntity("Ugrave", "\u0217");
        HTML_NAMESPACE.putEntity("Uacute", "\u0218");
        HTML_NAMESPACE.putEntity("Ucirc", "\u0219");
        HTML_NAMESPACE.putEntity("Uuml", "\u0220");
        HTML_NAMESPACE.putEntity("Yacute", "\u0221");
        HTML_NAMESPACE.putEntity("THORN", "\u0222");
        HTML_NAMESPACE.putEntity("szlig", "\u0223");
        HTML_NAMESPACE.putEntity("agrave", "\u0224");
        HTML_NAMESPACE.putEntity("aacute", "\u0225");
        HTML_NAMESPACE.putEntity("acirc", "\u0226");
        HTML_NAMESPACE.putEntity("atilde", "\u0227");
        HTML_NAMESPACE.putEntity("auml", "\u0228");
        HTML_NAMESPACE.putEntity("aring", "\u0229");
        HTML_NAMESPACE.putEntity("aelig", "\u0230");
        HTML_NAMESPACE.putEntity("ccedil", "\u0231");
        HTML_NAMESPACE.putEntity("egrave", "\u0232");
        HTML_NAMESPACE.putEntity("eacute", "\u0233");
        HTML_NAMESPACE.putEntity("ecirc", "\u0234");
        HTML_NAMESPACE.putEntity("euml", "\u0235");
        HTML_NAMESPACE.putEntity("igrave", "\u0236");
        HTML_NAMESPACE.putEntity("iacute", "\u0237");
        HTML_NAMESPACE.putEntity("icirc", "\u0238");
        HTML_NAMESPACE.putEntity("iuml", "\u0239");
        HTML_NAMESPACE.putEntity("eth", "\u0240");
        HTML_NAMESPACE.putEntity("ntilde", "\u0241");
        HTML_NAMESPACE.putEntity("ograve", "\u0242");
        HTML_NAMESPACE.putEntity("oacute", "\u0243");
        HTML_NAMESPACE.putEntity("ocirc", "\u0244");
        HTML_NAMESPACE.putEntity("otilde", "\u0245");
        HTML_NAMESPACE.putEntity("ouml", "\u0246");
        HTML_NAMESPACE.putEntity("oslash", "\u0248");
        HTML_NAMESPACE.putEntity("ugrave", "\u0249");
        HTML_NAMESPACE.putEntity("uacute", "\u0250");
        HTML_NAMESPACE.putEntity("ucirc", "\u0251");
        HTML_NAMESPACE.putEntity("uuml", "\u0252");
        HTML_NAMESPACE.putEntity("yacute", "\u0253");
        HTML_NAMESPACE.putEntity("thorn", "\u0254");
        HTML_NAMESPACE.putEntity("yuml", "\u0255");
    }
    
    /**
     * Create new instance and fill it with the {@link #DEFAULT_XML_NAMESPACE}
     * values.
     */
    public EntityNamespace() {
        this(DEFAULT_XML_NAMESPACE);
    }
    
    /**
     * Create new instance and fill it with the given values.
     */
    public EntityNamespace(Map<String, String> entities) {
        this.entities = new HashMap<String, String>(entities);
    }
    
    /**
     * Copy the given {@link EntityNamespace}.
     */
    public EntityNamespace(EntityNamespace copy) {
        this(copy.entities);
    }
    
    /**
     * Return the entity with the given name from this namespace or
     * <code>null</code> if it is not found.
     */
    public String getEntity(String name) {
        return entities.get(name);
    }
    
    /**
     * Push an entity to this namespace.
     * 
     * @throws UnsupportedOperationException
     *             if this namespace is immutable (such as
     *             {@link #DEFAULT_XML_NAMESPACE}).
     */
    public void putEntity(String name, String value) {
        if (!modifiable) {
            throw new UnsupportedOperationException();
        }
        entities.put(name, value);
    }
    
    /**
     * Clone this namespace.
     * 
     * @see #EntityNamespace(EntityNamespace)
     */
    @Override
    public EntityNamespace clone() {
        return new EntityNamespace(this);
    }
}
