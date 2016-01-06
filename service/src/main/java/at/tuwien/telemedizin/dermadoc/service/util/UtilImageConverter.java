package at.tuwien.telemedizin.dermadoc.service.util;

import at.tuwien.telemedizin.dermadoc.service.exception.DermadocConversionException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Lucas on 16.12.2015.
 */
public class UtilImageConverter {

    private UtilImageConverter() {   }

    public static Image byteToImage(byte[] data, String mime) throws DermadocConversionException {

        WritableImage image;

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            BufferedImage bufferedImage = ImageIO.read(bais);
            image = SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (IOException e) {
            throw new DermadocConversionException();
        }

        return image;
    }

    public static byte[] imageToByte(File file, String mime) throws DermadocConversionException {

        try {
            BufferedImage image = ImageIO.read(file);
            return imageToByte(image, mime);

        } catch (IOException e) {
            throw new DermadocConversionException();
        }
    }

    public static byte[] imageToByte(BufferedImage image, String mime) throws DermadocConversionException {

        byte[] imageInByte;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, mime, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

        } catch (IOException e) {
            throw new DermadocConversionException();
        }

        return imageInByte;
    }
}
