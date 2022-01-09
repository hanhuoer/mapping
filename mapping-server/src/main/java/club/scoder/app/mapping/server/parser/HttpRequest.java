package club.scoder.app.mapping.server.parser;

public interface HttpRequest extends Http {

    HttpMethod getMethod();

    String getPath();

    String getVersion();

    String getRequestURL();

    String getQueryString();

    String getHost();

    HttpRequestHeader getHeader();

}
