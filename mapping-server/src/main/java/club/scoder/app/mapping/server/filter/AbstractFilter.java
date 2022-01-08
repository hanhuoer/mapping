package club.scoder.app.mapping.server.filter;

public abstract class AbstractFilter {

    /**
     * String matching.
     *
     * @param s given a string.
     * @param p pattern.
     * @return true if matching.
     */
    public boolean isMatch(String s, String p) {
        return true;
    }

}
