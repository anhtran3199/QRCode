package com.myapp.qrcodeapp.helpers;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.myapp.qrcodeapp.entities.Product;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class ZXingHelper {

	public static byte[] getQRCodeImage(String text, int width, int height, String correctionLevel, String img) throws IOException, WriterException {
			// Tùy chỉnh mức sửa lỗi của mã
			Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
			switch (correctionLevel){
				case "L":
					hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); //H:30% | Q:25% | M:15% | L: 7%
					break;
				case "M":
					hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); //H:30% | Q:25% | M:15% | L: 7%
					break;
				case "Q":
					hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q); //H:30% | Q:25% | M:15% | L: 7%
					break;
				case "H":
					hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //H:30% | Q:25% | M:15% | L: 7%
					break;
				default:
					hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); //H:30% | Q:25% | M:15% | L: 7%
					break;
			}

			BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

			// Load QR image
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig());

			// Load logo image
			BufferedImage overlay;
			if(img.equals("")){
				overlay = ImageIO.read(new File("src/main/resources/static/logo/Logo_20x20.jpg"));
			} else{
				overlay = ImageIO.read(new File(img));
			}

			// Calculate the delta height and width between QR code and logo
			int deltaHeight = qrImage.getHeight() - overlay.getHeight();
			int deltaWidth = qrImage.getWidth() - overlay.getWidth();

			// Initialize combined image
			BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) combined.getGraphics();

			// Write QR code to new image at position 0/0
			g.drawImage(qrImage, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			g.drawImage(overlay, deltaWidth/2, deltaHeight/2, null);
			g.dispose();

			// Write combined image as PNG to OutputStream
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(combined, "png", os);
			os.toByteArray();
			os.close();

			return os.toByteArray();
	}

	public static String ReadQRCode(Integer id){
		String res ="";
		String path = "localhost:8080/qrcode/"+id;

		try {
			BufferedImage bf = ImageIO.read(new FileInputStream(path));
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
					new BufferedImageLuminanceSource(bf)
			));

			Result rs = new MultiFormatReader().decode(bitmap);
			return rs.getText();
		} catch (IOException | NotFoundException e) {
			e.printStackTrace();
			return res;
		}
	}
}