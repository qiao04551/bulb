package com.maxzuo.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * zxing生成二维码和识别二维码
 * <p>
 * Created by zfh on 2019/12/17
 */
public class ZxingExample {

    public static void main(String[] args) {
        buildCode();
        try {
            parseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码
     */
    private static void buildCode() {
        String format = "png";
        Map<EncodeHintType, Object> hints = new HashMap<>(10);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode("12345", BarcodeFormat.QR_CODE, 100, 100, hints);
            // 输出原图片
            MatrixToImageWriter.writeToPath(bitMatrix, format, new File("zxing.png").toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 识别二维码
     */
    private static void parseCode() throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        // 读取指定的二维码文件
        BufferedImage bufferedImage = ImageIO.read(new File("zxing.png"));
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        // 定义二维码参数
        Map hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
        // 输出相关的二维码信息
        System.out.println("解析结果：" + result.toString());
        System.out.println("二维码格式类型：" + result.getBarcodeFormat());
        System.out.println("二维码文本内容：" + result.getText());
        bufferedImage.flush();
    }
}
