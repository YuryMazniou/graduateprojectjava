package ru.javawebinar.graduateprojectjava.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javawebinar.graduateprojectjava.TimingExtension;
import ru.javawebinar.graduateprojectjava.util.DateTime;
import ru.javawebinar.graduateprojectjava.util.JpaUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
abstract public class AbstractServiceTest {
    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    protected DateTime dateTime;

    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}