package release;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ursula on 24.03.2017.
 */

class Img extends Component {

    BufferedImage img;

    public Img(String imgSrc){
        this.img = getImg(imgSrc);
    }
    public Img(BufferedImage img){
        this.img = img;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img,0,0,null);
    }

    public  BufferedImage getImg(String imgSrc){
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(imgSrc));
        } catch (IOException e) {

        }
        return img;
    }
}

class MainWin extends JApplet {
    private String firstImgSrc;
    private String secondImgSrc;

    Img firstImg = null;
    Img secondImg = null;

  public MainWin(String firstImgSrc, String secondImgSrc){

    this.firstImgSrc = firstImgSrc;
    this.secondImgSrc = secondImgSrc;

    this.firstImg = new Img(firstImgSrc);
    this.secondImg = new Img(secondImgSrc);
  }

   public void build(){
        add(getCompImg(firstImgSrc,secondImgSrc));
   }

   public Img getCompImg(String firstImgSrc, String secondImgSrc){
        ImageComparator comp = new ImageComparator(firstImgSrc,secondImgSrc);

        return new Img(comp.RGBCompare());
   }
}


public class UIBuilder {

    public void buildUI(){
        JFrame frame = new JFrame("Image difference pointer");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        frame.setSize(1000,1000);
        MainWin win = new MainWin( Constants.FIRST_IMG_SRC, Constants.SECOND_IMG_SRC);

        win.build();
        frame.add(win);

        frame.setVisible(true);
    }


}
