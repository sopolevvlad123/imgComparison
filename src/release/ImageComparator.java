package release;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by ursula on 24.03.2017.
 */
// x -height
// y- width



public class ImageComparator {

    private BufferedImage firstImage;
    private BufferedImage secondImage;
    private Map<Integer,List<Point>> pointMap = new HashMap<>();

    public ImageComparator(String firstImageSrc, String secondImageSrc){
        this.firstImage  = getImage(firstImageSrc);
        this.secondImage = getImage(secondImageSrc);
    }

    private    BufferedImage getImage(String imgSrc){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgSrc));
        } catch (IOException e) {
        }
        return img;
    }

    private BufferedImage getResultImage() {
        BufferedImage image = secondImage;
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(Color.RED);
        for (Rectangle rectangle : getRectangles()) {
            g2d.draw(rectangle);
        }
        return image;
    }

    public  BufferedImage RGBCompare(){

        for (int i = 0; i < firstImage.getWidth(); i ++ ){
            for (int j = 0; j < firstImage.getHeight(); j++){

                Color color1 = new Color(firstImage.getRGB(i,j));
                Color color2 = new Color(secondImage.getRGB(i,j));

                if (isColorDif(color1,color2)){
                    setPoint(new Point(i,j));
                }
            }
        }
        return getResultImage();
    }

    private boolean findNeighborPoint(Point point,List<Point> pointList){
        boolean flag = false;
        for(Point p: pointList){
            if (point.distance(p)<10){

                flag = true;
                break;
            }
        }
        if (flag){
            pointList.add(point);
        }
        return flag;
    }

    private void managePointMap( Point point ){
        boolean flag = false;
        Collection<List<Point>> pointListColl = pointMap.values();
        Iterator<List<Point>> iterator = pointListColl.iterator();
        while (iterator.hasNext()){
            if(findNeighborPoint(point,iterator.next())){
                flag = true;
                break;
            }
        }
        if (!flag) {
            List<Point> pointList = new ArrayList<>();
            pointList.add(point);
            pointMap.put(pointMap.size(), pointList);
        }
    }

    private void setPoint(Point point){
        if (pointMap.isEmpty()){
            List<Point> pointList = new ArrayList<>();
            pointList.add(point);
            pointMap.put(0,pointList);
        }else {
            managePointMap(point);
        }
    }

    private List<Rectangle> getRectangles(){
        List<Rectangle> rectangleList = new ArrayList<>();
        Collection<List<Point>> pointListColl = pointMap.values();
        Iterator<List<Point>> iterator = pointListColl.iterator();
        while(iterator.hasNext()){
            rectangleList.add(buildRectangle(iterator.next()));
        }
        return rectangleList;
    }

    private Rectangle buildRectangle(List<Point> pointList){
        int topHeight = 0;
        int topWidth = 0;
        int bottomHeight = firstImage.getHeight();
        int bottomWidth = firstImage.getWidth();

        for (Point point : pointList) {
            if (point.getX() < bottomHeight) {
                bottomHeight = (int) point.getX();
            } else {
                if (point.getX() > topHeight) {
                    topHeight = (int) point.getX();
                }
            }
            if (point.getY() < bottomWidth) {
                bottomWidth = (int) point.getY();
            } else {
                if (point.getY() > topWidth) {
                    topWidth = (int) point.getY();
                }
            }
        }

        return new Rectangle(bottomHeight, bottomWidth, topHeight - bottomHeight, topWidth - bottomWidth);
    }


   /* public BufferedImage getResultImage() {
        BufferedImage image = secondImage;
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(Color.RED);
        for (Rectangle rectangle : getRectangles()) {
            g2d.draw(rectangle);
        }
        return image;
    }*/


    private boolean isColorDif(Color color1, Color color2) {

        return (Math.abs(color1.getRed() - color2.getRed()) > 25)|| (Math.abs(color1.getGreen() - color2.getGreen()) > 25) || (Math.abs(color1.getBlue() - color2.getBlue()) > 25);
    }

}
