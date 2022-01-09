package club.scoder.app.mapping.server.parser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
public class RowHttpRequestParser {

    private static final int PHASE_FIRST = 0;
    private static final int PHASE_HEADER = 1;
    private static final int PHASE_BODY = 2;


    /**
     * parse http from given byteBuf.
     *
     * @param byteBuf byteBuf
     * @return http request.
     */
    public static HttpRequest parse(ByteBuf byteBuf) {
        if (byteBuf == null) {
            return null;
        }
        HttpRequest httpRequest = null;
        try {
            if (ByteBufUtil.isText(byteBuf, StandardCharsets.UTF_8)) {
                byte[] bytes = ByteBufUtil.getBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), true);
                String rowRequestString = new String(bytes, StandardCharsets.UTF_8);
                if (rowRequestString.contains("HTTP")) {
                    httpRequest = parse(rowRequestString);
                }
            }
        } catch (Exception e) {
            log.error("http parser error, error message: {}", e.getMessage());
            return null;
        }
        return httpRequest;
    }

    /**
     * parse http from given row request.
     *
     * @param rowHttpRequestText row request.
     */
    public static HttpRequest parse(String rowHttpRequestText) {
        DefaultHttpRequest defaultHttpRequest = new DefaultHttpRequest();

        String[] split = rowHttpRequestText.split("\n");

        int phase = PHASE_FIRST;
        for (String s : split) {
            if (PHASE_FIRST == phase) {
                // method, request, version
                parseAndSetMethod(defaultHttpRequest, s);
                parseAndSetPath(defaultHttpRequest, s);
                parseAndSetVersion(defaultHttpRequest, s);
                phase = PHASE_HEADER;
            } else if (PHASE_HEADER == phase) {
                // header
                if ("".equals(s)) {
                    phase = PHASE_BODY;
                    continue;
                }
                parseAndSetHeader(defaultHttpRequest, s);
                if (s.toLowerCase(Locale.ROOT).contains("host")) {
                    parseAndSetHost(defaultHttpRequest, s);
                }
            } else {
                // body
            }
        }
        return defaultHttpRequest;
    }

    /**
     * parse and set host.
     * example: www.bing.com
     *
     * @param defaultHttpRequest http request.
     * @param str                one line of the raw request.
     */
    private static void parseAndSetHost(DefaultHttpRequest defaultHttpRequest, String str) {
        if (defaultHttpRequest == null || str == null || str.length() == 0) {
            return;
        }
        int splitIndex = str.indexOf(":");
        if (splitIndex == -1) {
            return;
        }
        String value = str.substring(++splitIndex);
        defaultHttpRequest.setHost(value);
    }

    /**
     * parse and set headers.
     * example: Content-Type: application/json
     *
     * @param defaultHttpRequest http request.
     * @param str                one line of the raw request.
     */
    private static void parseAndSetHeader(DefaultHttpRequest defaultHttpRequest, String str) {
        if (defaultHttpRequest == null || str == null || str.length() == 0) {
            return;
        }
        int splitIndex = str.indexOf(":");
        if (splitIndex == -1) {
            return;
        }
        String name = str.substring(0, splitIndex);
        String value = str.substring(++splitIndex);
        HttpRequestHeader header = defaultHttpRequest.getHeader();
        String v = header.get(name);
        if (v == null) {
            value = value.stripLeading();
            header.put(name, value);
        } else {
            v = v + ";" + value;
            header.put(name, v);
        }
    }

    /**
     * parse and set version.
     * example: HTTP/1.1
     *
     * @param defaultHttpRequest http request.
     * @param str                one line of the raw request.
     */
    private static void parseAndSetVersion(DefaultHttpRequest defaultHttpRequest, String str) {
        if (defaultHttpRequest == null || str == null || str.length() == 0) {
            return;
        }
        String[] split = str.split(" ");
        if (split.length > 2) {
            String version = split[2];
            defaultHttpRequest.setVersion(version);
        }
    }

    /**
     * parse and set path.
     * example: /user/get
     *
     * @param defaultHttpRequest http request.
     * @param str                one line of the raw request.
     */
    private static void parseAndSetPath(DefaultHttpRequest defaultHttpRequest, String str) {
        if (defaultHttpRequest == null || str == null || str.length() == 0) {
            return;
        }
        String[] split = str.split(" ");
        if (split.length > 2) {
            String pathQuery = split[1];
            defaultHttpRequest.setRequestURL(pathQuery);
            String path = pathQuery;
            if (pathQuery.contains("?")) {
                String queryString = path.substring(path.indexOf("?") + 1);
                path = path.substring(0, path.indexOf("?"));
                defaultHttpRequest.setQueryString(queryString);
                defaultHttpRequest.setPath(path);
            }
            defaultHttpRequest.setPath(path);
        }
    }

    /**
     * parse and set method.
     * example: GET
     *
     * @param defaultHttpRequest http request.
     * @param str                one line of the raw request.
     */
    private static void parseAndSetMethod(DefaultHttpRequest defaultHttpRequest, String str) {
        if (defaultHttpRequest == null || str == null || str.length() == 0) {
            return;
        }
        String[] split = str.split(" ");
        if (split.length > 2) {
            String method = split[0];
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            defaultHttpRequest.setMethod(httpMethod);
        }
    }

}
