import java.awt.EventQueue;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MainVideoPlayler {
    static MainWindow frame;
    public static void main(String[] args) {
        new NativeDiscovery().discover();
        if (RuntimeUtil.isWindows()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "d://VideoLAN/VLC");
        }else if (RuntimeUtil.isMac()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "/Applications/VLC.app/Contents/MacOS/lib");
        }else if (RuntimeUtil.isNix()) {
            NativeLibrary.addSearchPath(
                    RuntimeUtil.getLibVlcLibraryName(), "/home/linux/vlc/install/lib");
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new MainWindow();
                    frame.setVisible(true);
                    String options[] = {"--subsdec-encoding=GB18030"};
                    frame.getMediaPlayer().prepareMedia(
                            "",options);
                    new SwingWorker<String, Integer>() {
                        protected String doInBackground() throws Exception {
                            while (true) {
                                long total = frame.getMediaPlayer().getLength();
                                long curr = frame.getMediaPlayer().getTime();
                                float percent = ((float)curr/total);
                                publish((int)(percent*100));
                                Thread.sleep(100);
                            }
                        }
                        protected void process(java.util.List<Integer> chunks) {
                            for (int v:chunks) {
                                frame.getProgressBar().setValue(v);
                            }
                        };
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void play() {
        frame.getMediaPlayer().play();
    }
    public static void pause() {
        frame.getMediaPlayer().pause();
    }
    public static void stop() {
        frame.getMediaPlayer().stop();
    }
    public static void jumpTo(float to) {
        frame.getMediaPlayer().setTime((long)( to*frame.getMediaPlayer().getLength()));
    }
    public static void openVideo() {
        JFileChooser chooser = new JFileChooser();
        int v = chooser.showOpenDialog(null);
        if (v == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            frame.getMediaPlayer().playMedia(file.getAbsolutePath());
        }
    }
    public static void openSubtitle() {
        JFileChooser chooser = new JFileChooser();
        int v = chooser.showOpenDialog(null);
        if (v == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            frame.getMediaPlayer().playMedia(file.getAbsolutePath());
        }
        File file = chooser.getSelectedFile();
        frame.getMediaPlayer().setSubTitleFile(file);
    }
    public static void exit() {
        frame.getMediaPlayer().release();
        System.exit(0);
    }
    public static void volume(int v) {
        frame.getMediaPlayer().setVolume(v);
    }
}