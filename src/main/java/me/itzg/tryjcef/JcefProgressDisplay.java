package me.itzg.tryjcef;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import me.friwi.jcefmaven.EnumProgress;
import me.friwi.jcefmaven.IProgressHandler;
import org.springframework.stereotype.Component;

@Component
public class JcefProgressDisplay extends JFrame implements IProgressHandler {

    private final JProgressBar progressBar;
    private final JLabel stateLabel;

    public JcefProgressDisplay() throws HeadlessException {
        super("Setup");

        getContentPane().setLayout(new BorderLayout(5,5));
        stateLabel = new JLabel("");
        progressBar = new JProgressBar(0, 100);
        getContentPane().add(stateLabel, BorderLayout.NORTH);
        getContentPane().add(progressBar, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setSize(300, 100);
    }

    @Override
    public void handleProgress(EnumProgress state, float percent) {
        SwingUtilities.invokeLater(() -> {
            if (state == EnumProgress.DOWNLOADING) {
                stateLabel.setText("Downloading...");
                if (percent >= 0) {
                    progressBar.setIndeterminate(false);
                    progressBar.setValue((int) percent);
                } else {
                    progressBar.setIndeterminate(true);
                }
                setVisible(true);
            } else if (state == EnumProgress.EXTRACTING) {
                stateLabel.setText("Extracting...");
                progressBar.setIndeterminate(true);
                setVisible(true);
            } else {
                setVisible(false);
            }
        });
    }
}
