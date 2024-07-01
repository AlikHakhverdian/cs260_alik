import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.io.File;

public class ExtractFacialPixels implements PlugInFilter {
    private static final double HUE_MIN = 0;   // Example threshold values
    private static final double HUE_MAX = 50;  // Example threshold values
    private static final String INPUT_DIR = "C:\\Users\\user\\Desktop\\New folder (2)\\hue\\";
    private static final String OUTPUT_DIR = "C:\\Users\\user\\Desktop\\HW1\\face\\";

    @Override
    public int setup(String arg, ImagePlus imp) {
        return DOES_ALL; // This plugin accepts any image type
    }

    @Override
    public void run(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] rgb = ip.getPixel(x, y, null);
                float[] hsv = new float[3];
                java.awt.Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);

                if (hsv[0] < HUE_MIN / 360.0 || hsv[0] > HUE_MAX / 360.0) {
                    ip.putPixel(x, y, new int[]{255, 255, 255}); // Color non-facial pixels white
                }
            }
        }
    }

    public void processImages() {
        File inputDir = new File(INPUT_DIR);
        File outputDir = new File(OUTPUT_DIR);

        // Ensure the output directory exists
        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                System.out.println("Created output directory: " + OUTPUT_DIR);
            } else {
                System.err.println("Failed to create output directory: " + OUTPUT_DIR);
                return;
            }
        }

        File[] files = inputDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".tif")) {
                    System.out.println("Processing file: " + file.getAbsolutePath());
                    ImagePlus imp = IJ.openImage(file.getAbsolutePath());
                    if (imp != null) {
                        run(imp.getProcessor());
                        String outputFilePath = OUTPUT_DIR + file.getName();
                        IJ.save(imp, outputFilePath);
                        System.out.println("Saved processed file: " + outputFilePath);
                    } else {
                        System.err.println("Failed to open image: " + file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.err.println("No files found in the input directory.");
        }
    }

    public static void main(String[] args) {
        ExtractFacialPixels plugin = new ExtractFacialPixels();
        plugin.processImages();
    }
}
