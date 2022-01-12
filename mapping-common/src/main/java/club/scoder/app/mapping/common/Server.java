package club.scoder.app.mapping.common;

public interface Server {

    void start();

    void stop();

    default void refresh() {
    }

}
