package com.nutcracker.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * pdf到图像util
 *
 * @author 胡桃夹子
 * @date 2025/04/08 09:41:19
 */
@Slf4j
public class PdfToImageUtil {

    /**
     * 将 PDF 文件转换为图片
     *
     * @param pdfPath   PDF 文件路径（如：/data/test.pdf）
     * @param outputDir 输出图片的目录（如：/data/images/）
     * @param dpi       图片分辨率（建议 150-300）
     * @param format    图片格式（如 "png"、"jpg"）
     */
    public static void pdfToImages(String pdfPath, String outputDir, int dpi, String format) {
        log.info("convertPdfToImages pdfPath={},outputDir={},dpi={},format={}", pdfPath, outputDir, dpi, format);
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                BufferedImage image = renderer.renderImageWithDPI(pageIndex, dpi);
                String outputPath = String.format("%s/page_%d.%s", outputDir, pageIndex + 1, format);
                ImageIO.write(image, format, new File(outputPath));
                log.info("image:{}", outputPath);
            }
        } catch (IOException e) {
            log.error("convertPdfToImages fail, pdfPath={},outputDir={},dpi={},format={}", pdfPath, outputDir, dpi, format, e);
        }
    }

    public static void main(String[] args) {
        String pdfPath = "D:/data/1.pdf";
        String outputDir = "D:/data";
        pdfToImages(pdfPath, outputDir, 300, "png");
    }
}
