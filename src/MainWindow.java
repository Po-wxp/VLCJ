import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.binding.internal.libvlc_logo_position_e;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.ImageIcon;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    
    // Player
    EmbeddedMediaPlayerComponent playerComponent = new EmbeddedMediaPlayerComponent();

    private final JPanel panel = new JPanel();
    private JProgressBar progress;

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 712, 512);
        setTitle("VLCJ");
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // Menu
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        // Menu Item
        JMenuItem menuItem = new JMenuItem("Open File");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            //Open video
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.openVideo();
            }
        });
        //Open Subtitle
        JMenuItem menuItem_1 = new JMenuItem("Open Subtitle");
        menu.add(menuItem_1);
        menuItem_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.openSubtitle();
            }
        });
        //Exit
        JMenuItem menuItem_2 = new JMenuItem("Exit");
        menu.add(menuItem_2);
        menuItem_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainVideoPlayler.exit();
            }
        });
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        this.add(contentPane);

        JPanel videopanel = new JPanel();
        contentPane.add(videopanel, BorderLayout.CENTER);
        videopanel.setLayout(new BorderLayout(0, 0));
        playerComponent = new EmbeddedMediaPlayerComponent();
        videopanel.add(playerComponent, BorderLayout.CENTER);
        videopanel.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel controlPanel = new JPanel();
        panel.add(controlPanel);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnNewButton = new JButton("play");
        controlPanel.add(btnNewButton);

        JButton button = new JButton("pause");
        controlPanel.add(button);


        JSlider slider = new JSlider();
        slider.setValue(100);
        slider.setMaximum(120);
        controlPanel.add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                //volume Control
                MainVideoPlayler.volume(slider.getValue());
            }
        });
        //Progress Bar
        progress = new JProgressBar();
        progress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                MainVideoPlayler.jumpTo(((float)x/progress.getWidth()));
            }
        });
        progress.setStringPainted(true);
        panel.add(progress, BorderLayout.NORTH);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	// If the video is playing, pause
            	if(getMediaPlayer().isPlaying()) {
            		MainVideoPlayler.pause();
            	}
            }
        });
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
            	// If the video is pausing, play
            	if(!getMediaPlayer().isPlaying()) {
            	     MainVideoPlayler.play();
            	}
            }
        });
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
//        button_1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                MainVideoPlayler.stop();
//            }
//        });
    }
    public EmbeddedMediaPlayer getMediaPlayer() {
        return playerComponent.getMediaPlayer();
    }
    public JProgressBar getProgressBar() {
        return progress;
    }
}