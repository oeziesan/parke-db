package Scanner;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.tensorflow.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FaceScanner extends JDialog {
    private FaceCaptureListener listener;
    private VideoCapture capture;
    private CascadeClassifier faceDetector;
    private Graph tfGraph;
    private Session tfSession;
    private volatile boolean running = true;

    // State untuk manual capture
    private volatile boolean userRequestedCapture = false;
    private volatile Rect detectedFaceRect = null;
    private volatile Mat lastFaceROI = null;

    public FaceScanner(FaceCaptureListener listener) {
        this.listener = listener;
        setTitle("Face Scanner");
        setModal(true);
        setSize(700, 580);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initOpenCVAndTF();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Stop kamera dan thread
                running = false;
                if (capture != null && capture.isOpened()) {
                    capture.release();
                }
                // Pastikan dispose tetap berjalan
                dispose();
            }
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Double safety (tidak wajib, tapi aman)
                running = false;
                if (capture != null && capture.isOpened()) {
                    capture.release();
                }
            }
        });
        startCameraLoop();
    }

    private void initOpenCVAndTF() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String cascadePath = "resources/haarcascade_frontalface_alt.xml";
        File cascadeFile = new File(cascadePath);
        if (!cascadeFile.exists() || !cascadeFile.isFile()) {
            JOptionPane.showMessageDialog(this, "File cascade TIDAK ditemukan:\n" + cascadeFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        faceDetector = new CascadeClassifier(cascadeFile.getAbsolutePath());
        if (faceDetector.empty()) {
            JOptionPane.showMessageDialog(this, "CascadeClassifier gagal memuat:\n" + cascadeFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        String modelPath = "resources/model/facenet.pb";
        File modelFile = new File(modelPath);
        if (!modelFile.exists() || !modelFile.isFile()) {
            JOptionPane.showMessageDialog(this, "File model facenet.pb TIDAK ditemukan di:\n" + modelFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        try {
            byte[] graphDef = Files.readAllBytes(Paths.get(modelPath));
            tfGraph = new Graph();
            tfGraph.importGraphDef(graphDef);
            tfSession = new Session(tfGraph);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat model TensorFlow:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void startCameraLoop() {
        if (faceDetector == null || tfSession == null) return;

        JLayeredPane layeredPane = new JLayeredPane();
        JLabel cameraLabel = new JLabel();
        cameraLabel.setBounds(10, 10, 640, 480);
        layeredPane.add(cameraLabel, JLayeredPane.DEFAULT_LAYER);

        JButton btnCapture = new JButton("Capture");
        btnCapture.setFont(new Font("Arial", Font.BOLD, 18));
        btnCapture.setBounds(270, 500, 150, 40);
        btnCapture.setEnabled(false);
        layeredPane.add(btnCapture, JLayeredPane.PALETTE_LAYER);

        setContentPane(layeredPane);

        // Event: ketika tombol capture ditekan
        btnCapture.addActionListener(e -> {
            userRequestedCapture = true;
        });

        // Thread kamera
        new Thread(() -> {
            capture = new VideoCapture(0);
            if (!capture.isOpened()) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Tidak dapat membuka kamera!", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                });
                return;
            }

            Mat frame = new Mat();
            MatOfRect faces = new MatOfRect();

            while (running) {
                if (!capture.read(frame)) continue;

                Imgproc.resize(frame, frame, new Size(640, 480));
                Mat gray = new Mat();
                Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
                Imgproc.equalizeHist(gray, gray);

                faceDetector.detectMultiScale(gray, faces);
                Rect[] facesArray = faces.toArray();

                if (facesArray.length > 0) {
                    // Wajah terdeteksi (ambil yang pertama)
                    Rect faceRect = facesArray[0];
                    lastFaceROI = new Mat(frame, faceRect);
                    detectedFaceRect = faceRect;

                    // Tampilkan kotak hijau di frame
                    Imgproc.rectangle(
                            frame,
                            new org.opencv.core.Point(faceRect.x, faceRect.y),
                            new org.opencv.core.Point(faceRect.x + faceRect.width, faceRect.y + faceRect.height),
                            new Scalar(0, 255, 0), 2
                    );
                    // Enable tombol capture
                    SwingUtilities.invokeLater(() -> btnCapture.setEnabled(true));
                } else {
                    detectedFaceRect = null;
                    lastFaceROI = null;
                    SwingUtilities.invokeLater(() -> btnCapture.setEnabled(false));
                }

                // Tampilkan preview frame ke JLabel
                BufferedImage previewImg = matToBufferedImage(frame);
                ImageIcon icon = new ImageIcon(previewImg);
                SwingUtilities.invokeLater(() -> cameraLabel.setIcon(icon));

                // Jika user sudah tekan capture & ada wajah, proses embedding
                if (userRequestedCapture) {
                    userRequestedCapture = false;
                    if (lastFaceROI != null) {
                        BufferedImage faceImg = matToBufferedImage(lastFaceROI);
                        float[] embeddingFloat = generateEmbedding(faceImg);
                        byte[] embeddingBytes = floatArrayToByteArray(embeddingFloat);
                        listener.onFaceCaptured(embeddingBytes);

                        running = false;
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "Wajah berhasil di-capture!");
                            dispose(); // tutup window setelah pesan
                        });
                        break;
                    } else {
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(this, "Tidak ada wajah terdeteksi!", "Gagal", JOptionPane.ERROR_MESSAGE)
                        );
                    }
                }
            }
            capture.release();
            dispose();
        }).start();
    }

    private float[] generateEmbedding(BufferedImage faceImg) {
        BufferedImage resized = resizeImage(faceImg, 160, 160);
        float[] inputData = bufferedImageToFloatArray(resized);
        try (Tensor<Float> inputTensor = Tensor.create(
                new long[]{1, 160, 160, 3}, FloatBuffer.wrap(inputData))) {
            Tensor<Float> outputTensor = tfSession.runner()
                    .feed("input:0", inputTensor)
                    .feed("phase_train:0", Tensor.create(false))
                    .fetch("embeddings:0")
                    .run().get(0).expect(Float.class);

            long[] shape = outputTensor.shape(); // [1, dim]
            int dim = (int) shape[1];
            float[][] embedding2D = new float[1][dim];
            outputTensor.copyTo(embedding2D);
            return embedding2D[0];
        }
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        Image tmp = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private float[] bufferedImageToFloatArray(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        float[] data = new float[width * height * 3];
        int idx = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                data[idx++] = (r - 127.5f) / 128.0f;
                data[idx++] = (g - 127.5f) / 128.0f;
                data[idx++] = (b - 127.5f) / 128.0f;
            }
        }
        return data;
    }

    private byte[] floatArrayToByteArray(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocate(arr.length * 4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for (float f : arr) {
            bb.putFloat(f);
        }
        return bb.array();
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int width = mat.cols();
        int height = mat.rows();
        byte[] b = new byte[width * height * mat.channels()];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(width, height, type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
