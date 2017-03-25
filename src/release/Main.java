package release;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ursula on 25.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        ImageComparator imageComparator = new ImageComparator(Constants.FIRST_IMG_SRC,Constants.SECOND_IMG_SRC);
        BufferedImage image = imageComparator.RGBCompare();
        try {
            ImageIO.write(image, "PNG", new File(Constants.RFSULT_IMG_SRC));
        } catch (IOException e) {
            e.printStackTrace();
        }

        UIBuilder uiBuilder = new UIBuilder();
        uiBuilder.buildUI();



    }
}
