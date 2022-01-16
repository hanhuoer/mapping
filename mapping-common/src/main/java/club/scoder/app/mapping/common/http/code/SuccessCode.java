package club.scoder.app.mapping.common.http.code;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public enum SuccessCode implements Code {

    /**
     * default ...
     */
    SUCCESS("20000", "success");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
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
