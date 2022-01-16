package club.scoder.app.mapping.common.http.code;

import java.io.Serializable;

/**
 * @author H
 * @link https://github.com/hanhuoer/Jusic-serve
 */
public interface Code extends Serializable {

    /**
     * code
     *
     * @return String
     */
    String code();

    /**
     * message
     *
     * @return String
     */
    String message();

}
