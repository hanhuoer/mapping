package club.scoder.app.mapping.server.parser;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpMethod {

    public static final HttpMethod GET = new HttpMethod("GET");
    public static final HttpMethod HEAD = new HttpMethod("HEAD");
    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod PUT = new HttpMethod("PUT");
    public static final HttpMethod DELETE = new HttpMethod("DELETE");
    public static final HttpMethod CONNECT = new HttpMethod("CONNECT");
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
    public static final HttpMethod TRACE = new HttpMethod("TRACE");
    public static final HttpMethod PATCH = new HttpMethod("PATCH");

    private static final Map<String, HttpMethod> HTTP_METHOD_MAP;

    static {
        HTTP_METHOD_MAP = Maps.newHashMap();
        HTTP_METHOD_MAP.put(GET.name, GET);
        HTTP_METHOD_MAP.put(HEAD.name, HEAD);
        HTTP_METHOD_MAP.put(POST.name, POST);
        HTTP_METHOD_MAP.put(PUT.name, PUT);
        HTTP_METHOD_MAP.put(DELETE.name, DELETE);
        HTTP_METHOD_MAP.put(CONNECT.name, CONNECT);
        HTTP_METHOD_MAP.put(OPTIONS.name, OPTIONS);
        HTTP_METHOD_MAP.put(TRACE.name, TRACE);
        HTTP_METHOD_MAP.put(PATCH.name, PATCH);
    }

    /**
     * http method name.
     */
    private String name;

    public HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod valueOf(String name) {
        return HTTP_METHOD_MAP.getOrDefault(name, new HttpMethod(name));
    }

    public String getName() {
        return name;
    }

}
