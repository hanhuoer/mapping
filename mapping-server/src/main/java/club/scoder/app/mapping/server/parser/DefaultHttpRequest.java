package club.scoder.app.mapping.server.parser;

public class DefaultHttpRequest implements HttpRequest {

    private HttpMethod method;
    private String path;
    private String version;
    private String host;
    private String requestURL;
    private String queryString;
    private HttpRequestHeader header = new HttpRequestHeader();


    public DefaultHttpRequest() {
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    protected void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    protected void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getHost() {
        return host;
    }

    protected void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getRequestURL() {
        return requestURL;
    }

    protected void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    protected void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public HttpRequestHeader getHeader() {
        return header;
    }

    protected void setHeader(HttpRequestHeader header) {
        this.header = header;
    }

}
