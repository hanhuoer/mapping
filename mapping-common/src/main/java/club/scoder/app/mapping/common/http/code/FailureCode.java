package club.scoder.app.mapping.common.http.code;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public enum FailureCode implements Code {

    /**
     * default
     */
    FAILURE("40000", "failure");

    private String code;
    private String message;

    FailureCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
