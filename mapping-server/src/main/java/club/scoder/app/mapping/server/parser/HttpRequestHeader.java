package club.scoder.app.mapping.server.parser;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class HttpRequestHeader {

    private Map<String, String> headers;


    public HttpRequestHeader() {
        headers = emptyHeader();
    }

    public String get(String name) {
        return headers.get(name);
    }

    public Collection<String> names() {
        return headers.keySet();
    }

    protected Map<String, String> emptyHeader() {
        return Maps.newHashMap();
    }

    protected void put(String name, String value) {
        headers.put(name, value);
    }

}
