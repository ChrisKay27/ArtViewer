package org.kaebe.ui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomImagePanel extends JPanel {
    private String selectedImage;
    private final String imagesPath;
    private java.util.List<File> allImages = null;
    private long resetAllImagesAt; // Premature optimization, turns out this wasn't causing my memory leak
    private final JLabel label = new JLabel();

    public RandomImagePanel(String imagesPath) {
        this.imagesPath = imagesPath;
        loadImage();
        add(label);

        Timer timer = new Timer(1000 * 4, (e)->{
            loadImage();
            revalidate();
            repaint();
        });
        timer.start();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Thread(()->{
                    if( selectedImage != null ) {
                        //open containing folder
                        openContainingFolder(selectedImage);
//                        Desktop desktop = Desktop.getDesktop();
//                        try {
//                            desktop.open(new File(selectedImage));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                }).start();
            }
        });
    }

    private void openContainingFolder(String selectedImage) {
        String folder = selectedImage.substring(0, selectedImage.lastIndexOf("\\"));
        openFolderAndSelectFile(folder, selectedImage.substring(selectedImage.lastIndexOf("\\") + 1));
    }

    private void openFolderAndSelectFile(String folder,String filename) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select, " + folder + "\\" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImage() {
        selectedImage = getRandomImage();
        if( selectedImage == null )
            return;

        label.setIcon(new ImageIcon(selectedImage)); // This is causing a memory leak
    }

    private String getRandomImage() {
        updateAllImages();

        if(!allImages.isEmpty())
            return allImages.get((int) (Math.random() * allImages.size())).getAbsolutePath();
//        File[] yearsFolders = folder.listFiles();
//
//        if( yearsFolders != null && yearsFolders.length > 0) {
//            File randomYearFolder = yearsFolders[(int) (Math.random() * yearsFolders.length)];
//
//            File[] monthFolders = randomYearFolder.listFiles();
//
//            if( monthFolders != null && monthFolders.length > 0) {
//                File randomMonthFolder = monthFolders[(int) (Math.random() * monthFolders.length)];
//
//                File[] dayFolders = randomMonthFolder.listFiles();
//                if( dayFolders != null && dayFolders.length > 0){
//
//                    File randomDayFolder = dayFolders[(int) (Math.random() * dayFolders.length)];
//
//                    File[] images = randomDayFolder.listFiles();
//                    if( images != null && images.length > 0){
//
//                        return images[(int) (Math.random() * images.length)].getAbsolutePath();
//                    }
//                }
//            }
//        }
        return null;
    }

    private void updateAllImages() {
        if( resetAllImagesAt < System.currentTimeMillis() ){ // Premature optimization, turns out this wasn't causing my memory leak
            allImages = null;
            resetAllImagesAt = System.currentTimeMillis() + 1000 * 60 * 10; //reset every 10 minutes
        }
        if( allImages == null ) {
            allImages = new ArrayList<>();
            File folder = new File(imagesPath);

            File[] dayFolders = folder.listFiles();
            if (dayFolders != null) {
                for (File dayFolder : dayFolders) {
                    File[] images = dayFolder.listFiles();
                    if (images != null)
                        allImages.addAll(Arrays.asList(images));
                }
            }
        }
    }
}
