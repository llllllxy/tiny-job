package org.tinycloud.tinyjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 *     启动类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-05-30 12:56
 */
@Slf4j
@SpringBootApplication
public class TinyJobApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(TinyJobApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");

        log.info("\n----------------------------------------------------------\n\t" +
                "tiny-job 启动成功！\n\t" +
                "┌─┐┬ ┬┌─┐┌─┐┌─┐┌─┐┌─┐  ┌─┐┌┬┐┌─┐┬─┐┌┬┐┌─┐┌┬┐   ┬\n\t" +
                "└─┐│ ││  │  ├┤ └─┐└─┐  └─┐ │ ├─┤├┬┘ │ ├┤  ││   │\n\t" +
                "└─┘└─┘└─┘└─┘└─┘└─┘└─┘  └─┘ ┴ ┴ ┴┴└─ ┴ └─┘─┴┘   o\n\t" +
                "-------------------------------------------------------------------------\n\t" +
                "Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "Swagger-UI: http://" + ip + ":" + port + "/doc.html\n\t" +
                "-------------------------------------------------------------------------");
    }
}
