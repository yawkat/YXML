package at.yawk.yxml;

/**
 * Utility class for XML operations.
 */
public class XmlUtil {
    private XmlUtil() {}
    
    /**
     * Returns <code>true</code> if the given character is a whitespace by the
     * XML standard, otherwise <code>false</code>.
     */
    public static boolean isWhitespace(char c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }
    
    /**
     * Returns <code>true</code> if the given character is a tag name start
     * character by the XML standard, otherwise <code>false</code>.
     */
    public static boolean isNameStartCharacter(char c) {
        if (c == ':' || c == '_') {
            return true;
        }
        if (intervalContains('a', 'z', c) || intervalContains('A', 'Z', c)) {
            return true;
        }
        if (intervalContains(0x00C0, 0x00D6, c) || intervalContains(0x00D8, 0x00F6, c)) {
            return true;
        }
        if (intervalContains(0x00F8, 0x02FF, c) || intervalContains(0x0370, 0x037D, c)) {
            return true;
        }
        if (intervalContains(0x037F, 0x1FFF, c) || intervalContains(0x200C, 0x200D, c)) {
            return true;
        }
        if (intervalContains(0x2070, 0x218F, c) || intervalContains(0x2C00, 0x2FEF, c)) {
            return true;
        }
        if (intervalContains(0x3001, 0xD7FF, c) || intervalContains(0xF900, 0xFDCF, c)) {
            return true;
        }
        if (intervalContains(0xFDF0, 0xFFFD, c)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns <code>true</code> if the given character is a tag name character
     * by the XML standard, otherwise <code>false</code>.
     */
    public static boolean isNameCharacter(char c) {
        if (isNameStartCharacter(c)) {
            return true;
        }
        if (c == '-' || c == '.' || c == 0xB7) {
            return true;
        }
        if (intervalContains('0', '9', c) || intervalContains(0x0300, 0x036F, c) || intervalContains(0x203F, 0x2040, c)) {
            return true;
        }
        return false;
    }
    
    /**
     * Parses an XML entity value such as <code>#xFD45</code> or
     * <code>#6582</code>. Either returns <code>-1</code> if the first character
     * was not <code>#</code>, <code>-2</code> if the first character was
     * <code>#</code> but the value could not be parsed or the character that
     * was parsed.
     */
    public static int getEntityValue(String entity) {
        final char[] entityCharacters = entity.toCharArray();
        if (entityCharacters.length >= 1) {
            if (entityCharacters[0] == '#') {
                if (entityCharacters.length >= 2) {
                    if (entityCharacters[1] == 'x') {
                        try {
                            return Integer.parseInt(entity.substring(2), 16);
                        } catch (NumberFormatException e) {}
                    } else {
                        try {
                            return Integer.parseInt(entity.substring(1), 10);
                        } catch (NumberFormatException e) {}
                    }
                }
                return -2;
            }
        }
        return -1;
    }
    
    /**
     * Returns the parsed entity code (not including the leading <code>&</code>
     * or the trailing <code>;</code>) or <code>null</code> if the code could
     * not be parsed.
     */
    public static String getEntityValue(String entity, EntityNamespace entities) {
        int e = getEntityValue(entity);
        if (e >= 0) {
            return Character.toString((char) e);
        } else if (e == -2) {
            return null;
        } else {
            return entities.getEntity(entity);
        }
    }
    
    private static boolean intervalContains(int start, int end, int search) {
        assert start <= end;
        return start <= search && end >= search;
    }
}
