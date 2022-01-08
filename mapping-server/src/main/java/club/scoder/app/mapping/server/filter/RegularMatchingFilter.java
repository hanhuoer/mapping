package club.scoder.app.mapping.server.filter;

import org.springframework.stereotype.Component;

@Component("regularMatchingFilter")
public class RegularMatchingFilter extends AbstractFilter {

    @Override
    public boolean isMatch(String s, String p) {
        return s.matches(p);
    }

}
