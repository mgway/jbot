package io.esb.jbot.boot;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        final EmbeddedPostgres postgres = new EmbeddedPostgres(Version.V9_5_5);
        String url = postgres.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("~/.embedpostgres/extract")));

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
