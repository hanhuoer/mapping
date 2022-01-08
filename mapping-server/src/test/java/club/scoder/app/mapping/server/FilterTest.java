package club.scoder.app.mapping.server;

import club.scoder.app.mapping.server.filter.FilterAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class FilterTest extends MappingServerApplicationTests {

    @Autowired
    FilterAdapter filterAdapter;

    @Test
    public void test1() {
        Assert.isTrue(filterAdapter.match("", ""), "result must be true");
        Assert.isTrue(filterAdapter.match("/api", "*"), "result must be true");
        Assert.isTrue(filterAdapter.match("/api", "/*"), "result must be true");
        Assert.isTrue(!filterAdapter.match("/api", "/api/*"), "result must be false");

        Assert.isTrue(filterAdapter.match("/api", "RE:/api.*?"), "result must be true");
        Assert.isTrue(filterAdapter.match("/api/user/get", "RE:/api.*?"), "result must be true");
        Assert.isTrue(!filterAdapter.match("/api/1", "RE:/api/"), "result must be false");
        Assert.isTrue(filterAdapter.match("/api/1", "RE:/api/\\d"), "result must be true");
        Assert.isTrue(filterAdapter.match("/api/1", "RE:/api/[0-9]"), "result must be true");
        Assert.isTrue(filterAdapter.match("/api/1235432", "RE:/api/[0-9]+"), "result must be true");
    }

}
