package com.kaebe.ui;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;


public class UI {

//    private final static Logger $LOGGER = Logger.getLogger(UI.class.getName()); // MUST include the magic $ identifier prefix here, DO NOT REMOVE
//    static{ $LOGGER.addHandler(ChatGPTLog.getFileHandler()); }

    private static UI instance;

    private final JFrame frame;

    public UI(){
        instance = this;

        if (!SystemTray.isSupported()) {
            //$LOGGER.log(INFO, "System tray is not supported.");
            frame = null;
            return;
        }
        FlatDarculaLaf.setup();

        //new WebcamDisplay();

//        UIManager.put( "control", new Color( 128, 128, 128) );
//        UIManager.put( "info", new Color(128,128,128) );
//        UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
//        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
//        UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
//        UIManager.put( "nimbusFocus", new Color(115,164,209) );
//        UIManager.put( "nimbusGreen", new Color(176,179,50) );
//        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
//        UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
//        UIManager.put( "nimbusOrange", new Color(191,98,4) );
//        UIManager.put( "nimbusRed", new Color(169,46,34) );
//        UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
//        UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
//        UIManager.put( "text", new Color( 230, 230, 230) );
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        // Create the tray icon
        Image trayIconImage = new ImageIcon("C:\\Users\\Chris\\IdeaProjects\\OpenYouTubeVideo\\src\\main\\resources\\house.png").getImage();
        int trayIconWidth = new TrayIcon(trayIconImage).getSize().width;
        TrayIcon trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH));


        // Create a pop-up menu
        PopupMenu popup = new PopupMenu();
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");
        popup.add(openItem);
        popup.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
        trayIcon.setPopupMenu(popup);

        // Add the tray icon to the system tray
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Preferences prefs = Preferences.userNodeForPackage(UI.class);

        // Create the JFrame
        frame = new JFrame("Home Automation");

        frame.setSize(Integer.parseInt(prefs.get("width", "1000")), Integer.parseInt(prefs.get("height", "800")));
        frame.setLocation(Integer.parseInt(prefs.get("x", "0")), Integer.parseInt(prefs.get("y", "0")));


        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //frame.setContentPane();
        frame.setVisible(true);
        // Add a window listener to handle the close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Hide the frame
                frame.setVisible(false);
                // Show th e tray icon
                trayIcon.displayMessage("Minimized to Tray", "The application has been minimized to the tray.", TrayIcon.MessageType.INFO);
            }

        });

        frame.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                saveWindowSizeToPreferences(frame);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                saveWindowSizeToPreferences(frame);
            }

            private void saveWindowSizeToPreferences(JFrame jFrame) {

                prefs.put( "x", ""+jFrame.getX());
                prefs.put( "y", ""+jFrame.getY());
                prefs.put( "width", ""+jFrame.getWidth());
                prefs.put( "height", ""+jFrame.getHeight());

//                System.out.println(Integer.parseInt(prefs.get("x", "0")));
//                System.out.println(Integer.parseInt(prefs.get("y", "0")));
//                System.out.println(Integer.parseInt(prefs.get("width", "0")));
//                System.out.println(Integer.parseInt(prefs.get("height", "0")));
            }
        });

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    frame.setVisible(true);
                    frame.toFront();
                }
            }
        });

        // Add an action listener to handle the open menu item
//        openItem.addActionListener(e -> {
//            // Show the frame
//            frame.setVisible(true);
//            // Bring the frame to the front
//            frame.toFront();
//        });
        JTabbedPane contentPane = new JTabbedPane();

//        JPanel tab1 = new JPanel();
//        tab1.setLayout(new BorderLayout());



        JPanel buttonsPanelParent = new JPanel();
        buttonsPanelParent.setLayout(new BorderLayout());

        {
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(1, 1));

            RandomImagePanel randomImagePanel = new RandomImagePanel();
            centerPanel.add(randomImagePanel);

//            RandomImagePanel randomImagePanel2 = new RandomImagePanel();
//            centerPanel.add(randomImagePanel2);


            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new GridLayout(1, 1));

            RandomImagePanel randomImagePanel3 = new RandomImagePanel();
            rightPanel.add(randomImagePanel3);

//            RandomImagePanel randomImagePanel4 = new RandomImagePanel();
//            rightPanel.add(randomImagePanel4);


            buttonsPanelParent.add(centerPanel, BorderLayout.CENTER);
            buttonsPanelParent.add(rightPanel, BorderLayout.EAST);
        }

        //JPanel buttonsPanel = new ButtonsPanel();
        //buttonsPanelParent.add(buttonsPanel, BorderLayout.WEST);

        new SecondImageViewerFrame();
        new SecondImageViewerFrame();
        new SecondImageViewerFrame();



        //tab1.add(new LogsPanel(), BorderLayout.CENTER);


        //NotificationsPanel notificationsPanel = new NotificationsPanel();
        //ImageViewerPanel imageViewerPanel = new ImageViewerPanel(frame::pack);
        //FileManager fileManager = new FileManager();


        contentPane.addTab("Buttons", buttonsPanelParent);
//        contentPane.addTab("Notifications", notificationsPanel);
//        contentPane.addTab("Images", imageViewerPanel);
//        contentPane.addTab("Images2", fileManager.getGui());
        //contentPane.addTab("Logs", tab1);

        //fileManager.showRootFile();

        frame.setContentPane(contentPane);

        //GlassPanePopup.install(frame);

        //JPanel popup2 = new JPanel();
        //popup2.add(new JLabel("Testing Popup"));
        //popup2.setSize(200,200);
        

//        JButton button2 = new JButton("Show Popup");
//        button2.addActionListener((e)->{

//            try(FrameGrabber grabber = new OpenCVFrameGrabber(0)) {
//                grabber.start();
//                org.bytedeco.javacv.Frame frame2 = grabber.grab();
//                CanvasFrame canvas = new CanvasFrame("Web Cam");
//                canvas.showImage(frame2);
//
////                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
////                IplImage img = converter.convert(frame2);
////                opencv_imgcodecs.cvSaveImage("webcamIcon.jpg", img);
//
//
//            } catch (FrameGrabber.Exception ex) {
//                ex.printStackTrace();
//            }
//
//            Webcam webcam = Webcam.getDefault();
//            webcam.open();
//
//            BufferedImage image = webcam.getImage();
//
//            try {
//                ImageIO.write(image, ImageUtils.FORMAT_JPG, new File("webcam.jpg"));
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            GlassPanePopup.showPopup(popup2, option, "ms1");
//        });
//       buttonsPanel.add(button2);



    }

    public static UI getInstance() {
        return instance;
    }

    public void openWindow(){
        // Show the frame
        frame.setVisible(true);
        // Bring the frame to the front
        frame.toFront();
    }
}
