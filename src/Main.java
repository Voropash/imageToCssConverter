import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.bmp.BMPImageReaderSpi;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;
import com.sun.imageio.plugins.png.PNGImageReader;
import com.sun.imageio.plugins.png.PNGImageReaderSpi;
import com.sun.imageio.plugins.wbmp.WBMPImageReader;
import com.sun.imageio.plugins.wbmp.WBMPImageReaderSpi;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage img = getBufferedImage("D:/YandexDisk/Скриншоты/2.png");
        String html = "<p><style type=\"text/css\">.ico{width:" + img.getWidth() + "px;height:"+ img.getHeight() +"px;font-size:0;line-height:0;}.ico i{float:left;width:1px;height:1px;font-size:0;line-height:0;display:block;}</style><div class=\"ico\">";
        int i, j;
        for (i = 0; i < img.getHeight(); i++) {
            for (j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                html+="<i style=\"background:#" + Integer.toString(color.getRed(),16) + Integer.toString(color.getGreen(),16) + Integer.toString(color.getBlue(),16)+ "\"></i>";
            }
        }
        html+="</div></p>";
        System.out.println(html);
    }

    public static BufferedImage getBufferedImage(String fileName) throws IOException {
        File file = new File(fileName);
        ImageReader r = getImageReaderByHeader(file);
        r.setInput(new FileImageInputStream(file));
        ImageReadParam param = new ImageReadParam();
        return  r.read(0, param);
    }

    public static ImageReader getImageReaderByHeader(File file) throws IOException {
        byte [] header = new byte[10];
        new DataInputStream(new FileInputStream(file)).read(header);

        String h = new String(header).trim();
        if(h.contains("PNG")) {
            return new PNGImageReader(new PNGImageReaderSpi());
        } else if(h.contains("GIF89")) {
            return new GIFImageReader(new GIFImageReaderSpi());
        } else if(h.contains("BM")) {
            return new BMPImageReader(new BMPImageReaderSpi());
        } else if(h.contains("JFIF")){
            return new JPEGImageReader(new JPEGImageReaderSpi());
        } else {
            return new WBMPImageReader(new WBMPImageReaderSpi());
        }
    }

}