package com.fts.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ImageUtils {
    private ImageUtils() {

    }

    private static final Log LOG = LogFactory.getLog(ImageUtils.class);

    /**
     * reads bufferedImage from a file
     * 
     * @param filePath
     * @return
     */
    public static BufferedImage readImage(String filePath) {
        LOG.debug("reading image from the given path : " + filePath);
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(filePath));
            LOG.debug("Image Read. Image Dimension: " + originalImage.getWidth() + "w X " + originalImage.getHeight()
                    + "h");
        } catch (IOException e) {
            LOG.error("Unable to read the image from the path : " + filePath);
            LOG.error(e.getMessage(), e);
        }
        return originalImage;
    }

    /**
     * writes text on image
     * 
     * @param image
     * @param text
     * @param font
     * @return
     */
    public static BufferedImage writeTextOnImage(BufferedImage image, String text, Font font) {
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.setColor(Color.BLACK);
        graphics2d.setFont(font);
        graphics2d.drawString(text, 5, 50);
        return image;
    }

    public static byte[] imageToByteArray(BufferedImage image, String extention) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = null;
        try {
            ImageIO.write(image, extention, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            LOG.error("Unable to write on the image");
            LOG.error(e.getMessage(), e);
        }
        return imageInByte;
    }

    /**
     * writes a buffered image to a file
     * 
     * @param buffImage
     * @param fileLocation
     * @param extension
     */
    public static void writeImage(BufferedImage buffImage, String fileLocation, String extension) {
        try {
            BufferedImage bi = buffImage;
            File outputfile = new File(fileLocation);
            ImageIO.write(bi, extension, outputfile);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static BufferedImage cropMyImage(BufferedImage bufferedImage, int cropWidth, int cropHeight, int cropStartX,
            int cropStartY) throws Exception {
        BufferedImage clipped = null;
        Dimension dimension = new Dimension(cropWidth, cropHeight);

        Rectangle clip = createClip(bufferedImage, dimension, cropStartX, cropStartY);

        try {
            int w = clip.width;
            int h = clip.height;

            LOG.debug("Crop Width " + w);
            LOG.debug("Crop Height " + h);
            LOG.debug("Crop Location " + "(" + clip.x + "," + clip.y + ")");

            clipped = bufferedImage.getSubimage(clip.x, clip.y, w, h);

            LOG.debug("Image Cropped. New Image Dimension: " + clipped.getWidth() + "w X " + clipped.getHeight() + "h");
        } catch (RasterFormatException rfe) {
            LOG.error("Raster format error: " + rfe.getMessage());
            return null;
        }
        return clipped;
    }

    /**
     * crops an original image to the crop parameters provided. If the crop
     * rectangle lies outside the rectangle (even if partially), adjusts the
     * rectangle to be included within the image area.
     * 
     * @param bufferedImage
     *            Image To Be Cropped
     * @param cropStartX
     *            Starting X-position of crop area rectangle
     * @param cropStartY
     *            Strating Y-position of crop area rectangle
     * @return
     * @throws Exception
     */
    private static Rectangle createClip(BufferedImage bufferedImage, Dimension dimension, int clipX, int clipY)
            throws Exception {
        Rectangle clip = null;

        /**
         * Some times clip area might lie outside the original image, fully or
         * partially. In such cases, this code will adjust the crop area to fit
         * within the original image. isClipAreaAdjusted flas is usded to denote
         * if there was any adjustment made.
         */
        boolean isClipAreaAdjusted = false;

        /** Checking for negative X Co-ordinate **/
        if (clipX < 0) {
            clipX = 0;
            isClipAreaAdjusted = true;
        }

        /** Checking for negative Y Co-ordinate **/
        if (clipY < 0) {
            clipY = 0;
            isClipAreaAdjusted = true;
        }

        /** Checking if the clip area lies outside the rectangle **/
        if ((dimension.width + clipX) <= bufferedImage.getWidth()
                && (dimension.height + clipY) <= bufferedImage.getHeight()) {

            /**
             * Setting up a clip rectangle when clip area lies within the image.
             */
            clip = new Rectangle(dimension);
            clip.x = clipX;
            clip.y = clipY;
        } else {

            /**
             * Checking if the width of the clip area lies outside the image. If
             * so, making the image width boundary as the clip width.
             */
            if ((dimension.width + clipX) > bufferedImage.getWidth()) {
                dimension.width = bufferedImage.getWidth() - clipX;
            }

            /**
             * Checking if the height of the clip area lies outside the image.
             * If so, making the image height boundary as the clip height.
             */
            if ((dimension.height + clipY) > bufferedImage.getHeight()) {
                dimension.height = bufferedImage.getHeight() - clipY;
            }

            /** Setting up the clip are based on our clip area size adjustment **/
            clip = new Rectangle(dimension);
            clip.x = clipX;
            clip.y = clipY;

            isClipAreaAdjusted = true;

        }
        if (isClipAreaAdjusted) {
            LOG.debug("Crop Area Lied Outside The Image." + " Adjusted The Clip Rectangle\n");
        }
        return clip;
    }

    /**
     * @param defaultCompressHeight
     * @param defaultCompressWidth
     * @return
     * @throws IOException
     */
    public static byte[] compressImageWithAspectRatio(int defaultCompressHeight, int defaultCompressWidth,
            BufferedImage bufferedImage, String fileExt) throws IOException {
        int targetHeight = defaultCompressHeight;
        int targetWidth = defaultCompressWidth;

        // TO-DO Check if the image height\width greater than the height\width,
        // if yes compress the image
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();

        /**
         * setting the image height and width in the default aspect ratio
         */
        float defaultAspectRatio = 0;
        float imageAspectRatio = 0;
        float showPictureHeight = 0;
        float showPictureWidth = 0;
        defaultAspectRatio = ((float) targetWidth / (float) targetHeight);
        imageAspectRatio = ((float) imageWidth / (float) imageHeight);
        if (imageAspectRatio <= defaultAspectRatio) {
            if (imageHeight <= targetHeight) {
                showPictureHeight = imageHeight;
                showPictureWidth = imageWidth;
            } else {
                showPictureHeight = targetHeight;
                showPictureWidth = imageWidth * ((float) targetHeight / (float) imageHeight);
            }
        } else {
            if (imageWidth <= targetWidth) {
                showPictureWidth = imageWidth;
                showPictureHeight = imageHeight;
            } else {
                showPictureWidth = targetWidth;
                showPictureHeight = imageHeight * ((float) targetWidth / (float) imageWidth);
            }
        }
        LOG.debug("defaultAspectRatio : " + defaultAspectRatio + "\t imageAspectRatio : " + imageAspectRatio);
        LOG.debug("imageHeight : " + imageHeight + "\t imageWidth : " + imageWidth);
        LOG.debug("showPictureHeight : " + showPictureHeight + "\t showPictureWidth : " + showPictureWidth);

        BufferedImage buffImage = createResizedCopy(bufferedImage, (int) showPictureWidth, (int) showPictureHeight,
                isPreserveAlpha(fileExt));

        byte[] fileBytes = ImageUtils.imageToByteArray(buffImage, fileExt);
        return fileBytes;
    }

    public static byte[] compressImageWithAspectRatio(int defaultCompressHeight, int defaultCompressWidth,
            byte[] imageBytes, String fileExt) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        return compressImageWithAspectRatio(defaultCompressHeight, defaultCompressWidth, bufferedImage, fileExt);
    }

    public static boolean isPreserveAlpha(String fileExt) {
        return ("png".equalsIgnoreCase(fileExt)) ? true : false;
    }

    /**
     * @param originalImage
     * @param scaledWidth
     * @param scaledHeight
     * @param preserveAlpha
     * @return
     */
    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight,
            boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        // int imageType = BufferedImage.TYPE_INT_RGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);

        Graphics2D graphics2D = scaledBI.createGraphics();
        /*
         * graphics2D .setBackground(Color.WHITE); graphics2D
         * .setColor(Color.WHITE);
         */
        if (preserveAlpha) {
            graphics2D.setComposite(AlphaComposite.Src);
        }
        graphics2D.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        graphics2D.dispose();
        return scaledBI;
    }

    /**
     * returns true if the given image height or width is more than the cropX,
     * cropY
     * 
     * @param fileBytes
     * @param cropX
     * @param cropY
     * @return
     */
    public static boolean isImageLargeThanCropSize(byte[] fileBytes, int cropX, int cropY) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(fileBytes));
            int sourceHeight = bufferedImage.getHeight();
            int sourceWidth = bufferedImage.getWidth();
            if (sourceHeight > cropY || sourceWidth > cropX) {
                return true;
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    public static byte[] processImage(int defaultHeight, int defaultWidth, byte[] imageBytes, int cropX, int cropY,
            String fileExt) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        BufferedImage buffImage = null;
        byte[] fileBytes = null;

        LOG.debug("processing image...");
        if (imageWidth > defaultWidth || imageHeight > defaultHeight) {
            // do compressing with as before
            LOG.debug("image height or width are greater than defaultHeight or defaultWidth");
            fileBytes = compressImageWithAspectRatio(defaultHeight, defaultWidth, bufferedImage, fileExt);

        } else if ((imageWidth > cropX || imageHeight > cropY)
                && (imageWidth < defaultWidth && imageHeight < defaultHeight)) {
            /**
             * image height or width greater than crops sizes and less than
             * defaultWidht or defaultHeight return it as it is
             */
            LOG.debug("image height or width are greater than crop sizes and less than defaultHeight or defaultWidth");
            fileBytes = imageBytes;
        } else if (imageWidth < cropX && imageHeight < cropY) {
            /**
             * image height or width less than crops sizes.
             */
            /**
             * compress with cropX and cropY
             */
            LOG.debug("image height or width are less than crop sizes. So resizing the image with cropX and cropY");
            buffImage = createResizedCopy(bufferedImage, cropX, cropY, true);
            fileBytes = ImageUtils.imageToByteArray(buffImage, fileExt);
        } else {
            fileBytes = compressImageWithAspectRatio(defaultHeight, defaultWidth, bufferedImage, fileExt);
        }

        return fileBytes;
    }

    /**
     * prepares BufferedImage from the byte array
     * 
     * @param filebytes
     * @return
     * @throws IOException
     */
    public static BufferedImage byteArrayToBufferedImage(byte[] filebytes) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(filebytes));
        return bufferedImage;
    }

    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String fileExtnsn) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileExtnsn, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] x) {
        BufferedImage bufferedImage = createResizedCopy(readImage("E:/PERSONAL/V/Xtras/footer.gif"), 50, 50, true);
        writeImage(bufferedImage, "E:/PERSONAL/V/Xtras/footer-50-50.gif", "gif");
    }

    @SuppressWarnings("resource")
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        // if (length > Integer.MAX_VALUE)
        // {
        // // File is too large
        // }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, int targetWidth, int targetHeight) {
        try {
            double determineImageScale = determineImageScale(srcImage.getWidth(), srcImage.getHeight(), targetWidth,
                    targetHeight);
            BufferedImage dstImage = scaleImage(srcImage, determineImageScale);
            return dstImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage scaleImage(BufferedImage sourceImage, double scaledWidth) {
        Image scaledImage = sourceImage.getScaledInstance((int) (sourceImage.getWidth() * scaledWidth),
                (int) (sourceImage.getHeight() * scaledWidth), Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, Color.WHITE, null);
        g.dispose();
        return bufferedImage;
    }

    private static double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
        double scalex = (double) targetWidth / sourceWidth;
        double scaley = (double) targetHeight / sourceHeight;
        return Math.min(scalex, scaley);
    }
    
    /**
     * optmize given image
     * @param sourceImage
     * @param targetWidth
     * @param targetHeight
     * @param preserveAlpha
     * @return
     */
    public static BufferedImage optimizeImg(BufferedImage sourceImage, int targetWidth, int targetHeight,
            boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

        double scale = determineImageScale(sourceImage.getWidth(), sourceImage.getHeight(), targetWidth,
                targetHeight);
        
        Image scaledImage = sourceImage.getScaledInstance((int) (sourceImage.getWidth() * scale),
                (int) (sourceImage.getHeight() * scale), Image.SCALE_SMOOTH);

        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, imageType);
        
        Graphics2D g = bufferedImage.createGraphics();
        
        if (preserveAlpha)
        {
            g.setComposite(AlphaComposite.Src);
        }
        
        //g.setBackground(Color.WHITE);
        //g.drawImage(scaledImage, 0, 0, Color.WHITE, null);
        g.drawImage(scaledImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        
        return bufferedImage;
    }
    
    /**
     * scale image with same aspect ratio of given image
     * method call : BufferedImage scaledImage = scaleImage(sourceImage, targetWidth, targetHeight, ImageUtils.isPreserveAlpha(ext));
     * @param bufferedImage
     * @param targetWidth
     * @param targetHeight
     * @param preserveAlpha
     * @return
     */
    public static BufferedImage scaleImage(BufferedImage bufferedImage, int targetWidth, int targetHeight,
            boolean preserveAlpha){
        
        // TO-DO Check if the image height\width greater than the height\width,
        // if yes compress the image
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();

        /**
         * setting the image height and width in the default aspect ratio
         */
        float defaultAspectRatio = 0;
        float imageAspectRatio = 0;
        float showPictureHeight = 0;
        float showPictureWidth = 0;
        defaultAspectRatio = ((float) targetWidth / (float) targetHeight);
        imageAspectRatio = ((float) imageWidth / (float) imageHeight);
        if (imageAspectRatio <= defaultAspectRatio)
        {
            if (imageHeight <= targetHeight)
            {
                showPictureHeight = imageHeight;
                showPictureWidth = imageWidth;
            } else
            {
                showPictureHeight = targetHeight;
                showPictureWidth = imageWidth * ((float) targetHeight / (float) imageHeight);
            }
        } else
        {
            if (imageWidth <= targetWidth)
            {
                showPictureWidth = imageWidth;
                showPictureHeight = imageHeight;
            } else
            {
                showPictureWidth = targetWidth;
                showPictureHeight = imageHeight * ((float) targetWidth / (float) imageWidth);
            }
        }
        System.out.println("defaultAspectRatio : " + defaultAspectRatio + "\t imageAspectRatio : " + imageAspectRatio);
        System.out.println("imageHeight : " + imageHeight + "\t imageWidth : " + imageWidth);
        System.out.println("showPictureHeight : " + showPictureHeight + "\t showPictureWidth : " + showPictureWidth);

        BufferedImage buffImage = optimizeImg(bufferedImage, (int) showPictureWidth, (int) showPictureHeight,
                preserveAlpha);
        return buffImage;
    }
    
    public static String getMimeTypeFromBytes(byte[] bytes)
    {
        try
        {
            MagicMatch match = Magic.getMagicMatch(bytes);
            return match.getMimeType();
        }
        catch (Exception e)
        {
            LOG.info(e.getMessage(),e);
            return null;
        }
    }
}
