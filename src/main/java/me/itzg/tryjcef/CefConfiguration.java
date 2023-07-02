package me.itzg.tryjcef;

import java.io.IOException;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CefConfiguration {

    @Bean
    public CefApp cefApp(ConfigurableApplicationContext applicationContext,
        JcefProgressDisplay progressDisplay
        ) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        final CefAppBuilder builder = new CefAppBuilder();
        builder.setInstallDir(
            Path.of(System.getenv("APPDATA"), "try-jcef", "bundle")
            .toFile());
        builder.getCefSettings().windowless_rendering_enabled = false;
        builder.setProgressHandler(progressDisplay);
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(CefAppState state) {
                if (state == CefAppState.TERMINATED) {
                    applicationContext.close();
                }
            }
        });
        return builder.build();
    }
}
