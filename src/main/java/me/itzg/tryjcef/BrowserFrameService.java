package me.itzg.tryjcef;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrowserFrameService extends JFrame {

    private final CefApp cefApp;

    public BrowserFrameService(CefApp cefApp) throws HeadlessException {
        super("Try JCEF");
        this.cefApp = cefApp;
    }

    @EventListener
    public void onWebReady(WebServerInitializedEvent event) {
        final int port = event.getWebServer().getPort();

        log.info("Web server ready at port {}", port);

        SwingUtilities.invokeLater(() -> {
            final CefClient client = cefApp.createClient();

            final CefBrowser browser = client.createBrowser("http://localhost:%d".formatted(port), false, false);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(browser.getUIComponent(), BorderLayout.CENTER);
            pack();
            setSize(800, 600);
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    log.info("Window closing");
                    cefApp.dispose();
                    dispose();
                    System.exit(0);
                }
            });
        });
    }

}
