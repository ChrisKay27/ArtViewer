package org.kaebe.ui;

import com.formdev.flatlaf.FlatDarculaLaf;


public class UI {
    public UI(String imagesPath){
        FlatDarculaLaf.setup();

        new ImageViewerFrame(imagesPath);
        new ImageViewerFrame(imagesPath);
        new ImageViewerFrame(imagesPath);
        new ImageViewerFrame(imagesPath);
    }
}
