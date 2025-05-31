package Scanner;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class FaceScanner extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV native lib
    }

    private final FaceCaptureListener listener;
    private final VideoCapture camera;
    private final JLabel videoLabel;
    private volatile boolean capturing = true;

    public FaceScanner(FaceCaptureListener listener) {
        this.listener = listener;
        this.camera = new VideoCapture(0); // 0 = default webcam

        setTitle("Scan Wajah");
        setSize(640, 480);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        videoLabel = new JLabel();
        add(videoLabel, BorderLayout.CENTER);

        JButton captureButton = new JButton("Capture Wajah");
        add(captureButton, BorderLayout.SOUTH);
        captureButton.addActionListener(e -> captureFace());

        startCameraFeed();
    }

    private void startCameraFeed() {
        Thread thread = new Thread(() -> {
            Mat frame = new Mat();
            while (capturing && camera.isOpened()) {
                camera.read(frame);
                if (!frame.empty()) {
                    ImageIcon icon = new ImageIcon(matToBufferedImage(frame));
                    videoLabel.setIcon(icon);
                }
                try {
                    Thread.sleep(33); // ~30fps
                } catch (InterruptedException ignored) {}
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void captureFace() {
        Mat frame = new Mat();
        if (camera.read(frame)) {
            byte[] faceBytes = encodeToJpegBytes(frame);
            listener.onFaceCaptured(faceBytes);
        }
        capturing = false;
        camera.release();
        dispose();
    }

    private byte[] encodeToJpegBytes(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_3BYTE_BGR;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }
}
