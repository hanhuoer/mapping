package club.scoder.app.mapping.server.filter;

import org.springframework.stereotype.Component;

@Component
public class FilterAdapter {

    /**
     * regular pattern prefix.
     */
    private static final String DEFAULT_REGULAR_SYMBOL = "RE:";

    private static final int WILDCARD_MATCHING = 0;
    private static final int REGULAR_MATCHING = 1;

    private final AbstractFilter WILDCARD_MATCHING_FILTER;
    private final AbstractFilter REGULAR_MATCHING_FILTER;


    public FilterAdapter(AbstractFilter wildcardMatchingFilter, AbstractFilter regularMatchingFilter) {
        WILDCARD_MATCHING_FILTER = wildcardMatchingFilter;
        REGULAR_MATCHING_FILTER = regularMatchingFilter;
    }

    /**
     * Use the matcher for given a pattern.
     *
     * @param s given a string.
     * @param p pattern
     * @return true if matching.
     */
    public boolean match(String s, String p) {
        AbstractFilter filter;
        if (REGULAR_MATCHING == adapter(p)) {
            filter = REGULAR_MATCHING_FILTER;
            p = getRegularPattern(p);
        } else {
            filter = WILDCARD_MATCHING_FILTER;
        }
        return filter.isMatch(s, p);
    }

    private int adapter(String p) {
        if (p == null || p.length() < DEFAULT_REGULAR_SYMBOL.length()) {
            return WILDCARD_MATCHING;
        }
        String substring = p.substring(0, 3);
        return DEFAULT_REGULAR_SYMBOL.equals(substring) ? REGULAR_MATCHING : WILDCARD_MATCHING;
    }

    private String getRegularPattern(String p) {
        return p.substring(DEFAULT_REGULAR_SYMBOL.length());
    }
}
