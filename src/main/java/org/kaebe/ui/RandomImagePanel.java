package com.kaebe.ui;

import com.kaebe.Constants;
import com.kaebe.threading.HomeAutomationThreadPool;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomImagePanel extends JPanel{

    private String selectedImage;
    public RandomImagePanel() {
        loadImage();
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Timer timer = new Timer(1000 * 4, (e)->{
            removeAll();
            loadImage();
            revalidate();
            repaint();
        });
        timer.start();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeAutomationThreadPool.start(()->{
                    if( selectedImage != null ) {
                        //open containing folder
                        openContainingFolder(selectedImage);

                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(new File(selectedImage));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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

        JLabel label = new JLabel(new ImageIcon(selectedImage));
        //JLabel label2 = new JLabel(selectedImage.substring(selectedImage.lastIndexOf("\\") + 1));
        add(label);
        //add(label2);
    }

    private String getRandomImage() {

        java.util.List<File> allImages = new ArrayList<>();
        File folder = new File(Constants.StableDiffusionImagesFolderPath);

        File[] dayFolders = folder.listFiles();
        if( dayFolders != null && dayFolders.length > 0){

            for( File dayFolder : dayFolders ){
                File[] images = dayFolder.listFiles();
                if(images != null)
                    allImages.addAll(Arrays.asList(images));
            }

            if(!allImages.isEmpty()){
                return allImages.get((int) (Math.random() * allImages.size())).getAbsolutePath();
            }
        }

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
}
