package ru.tnchk.library;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles({"test"})
@SpringBootTest
@Transactional
public
class AbstractIntegrationTest {

    @ServiceConnection
    public static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest");

    static {
        POSTGRES_CONTAINER.start();
    }
}