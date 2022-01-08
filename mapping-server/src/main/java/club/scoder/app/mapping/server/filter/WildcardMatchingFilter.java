package club.scoder.app.mapping.server.filter;

import org.springframework.stereotype.Component;

@Component("wildcardMatchingFilter")
public class WildcardMatchingFilter extends AbstractFilter {

    /**
     * Given an input string (s) and a pattern (p),
     * implement wildcard pattern matching with support for '?' and '*' where:
     * <pre>
     *     '?' Matches any single character.
     *     '*' Matches any sequence of characters (including the empty sequence).
     * </pre>
     *
     * @param s given a string
     * @param p given a pattern
     * @return true if match
     */
    @Override
    public boolean isMatch(String s, String p) {
        if (s.length() != 0 && p.length() == 0) return false;

        int slen = s.length(), plen = p.length();
        boolean[][] dp = new boolean[slen + 1][plen + 1];
        dp[0][0] = true;
        // initialization of boundary
        for (int j = 1; j <= plen && dp[0][j - 1]; j++)
            dp[0][j] = p.charAt(j - 1) == '*';

        for (int i = 1; i <= slen; i++) {
            for (int j = 1; j <= plen; j++) {
                char si = s.charAt(i - 1), pj = p.charAt(j - 1);
                if (si == pj || pj == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pj == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[slen][plen];
    }

}
