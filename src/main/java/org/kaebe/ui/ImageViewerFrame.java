package org.kaebe.ui;

import javax.swing.*;

public class ImageViewerFrame extends JFrame {
    private static int count = 0;

    public ImageViewerFrame(String imagesPath) {
        setTitle("ImageViewerFrame");
        setSize(1920, 1080);

        switch(count){
            case 0: setLocation(1920*2,0); break;
            case 1: setLocation(1920*2,1080); break;
            case 2: setLocation(1920*3,0); break;
            case 3: setLocation(1920*3,1080); break;
        }
        count++;

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.add(new RandomImagePanel(imagesPath));
        content.add(new RandomImagePanel(imagesPath));
        add(content);

        setVisible(true);
    }
}
