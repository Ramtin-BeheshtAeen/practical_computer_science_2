package de.uni_bremen.pi2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Instanzen dieser Klasse bieten ein Bild in verschiedenen Formaten an.
 */
class ImageHandler
{
    /** Der Farbwert schwarzer Pixel (RGBA = (0, 0, 0, 255)). */
    private static final int BLACK = 0xff000000;

    /** Der Farbwert weißer Pixel (RGBA = (255, 255, 255, 255)). */
    private static final int WHITE = 0xffffffff;

    /** Das Originalbild. */
    private final BufferedImage original;

    /** Ein Grauwertbild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage grayscale;

    /** Ein Schwarzweißbild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage blackAndWhite;

    /** Ein segmentiertes Bild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage segmented;

    /** Zufallszahlengenerator zum Erzeugen zufälliger Farben. */
    private final Random random = new Random(0);

    /**
     * Konstruktor.
     * @param image Das Bild, das in verschiedenen Formaten bereitgestellt wird.
     */
    ImageHandler(final BufferedImage image)
    {
        original = image;
    }

    /**
     * Liefert das Originalbild.
     * @return Das Originalbild.
     */
    BufferedImage getOriginal()
    {
        return original;
    }

    /**
     * Liefert das Bild in Grauwerten.
     * Das Grauwertbild wird bei Bedarf erst berechnet.
     * @return Das Grauwertbild.
     */
    BufferedImage getGrayscale()
    {
        if (grayscale == null) {
            final BufferedImage image = getOriginal();
            grayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    grayscale.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return grayscale;
    }

    /**
     * Liefert das Bild in Schwarzweiß.
     * Das Schwarzweißbild wird bei Bedarf erst berechnet, indem für das Grauwertbild der sog. Otsu-Schwellwert
     * bestimmt wird. Alle Grautöne unterhalb des Schwellwerts werden schwarz, alle darüber weiß.
     * @return Das Schwarzweißbild.
     */
    BufferedImage getBlackAndWhite()
    {
        if (blackAndWhite == null) {
            final BufferedImage image = getGrayscale();
            blackAndWhite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            final int threshold = otsuThreshold(image);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    blackAndWhite.setRGB(x, y, (image.getRGB(x, y) & 255) <= threshold ? 0 : -1);
                }
            }
        }
        return blackAndWhite;
    }

    /**
     * Bestimmung des Otsu-Schwellwerts.
     * Vgl. z.B. http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html.
     * @param image Das Grauwertbild, zu dem der beste Schwellwert zwischen Schwarz
     *         und Weiß bestimmt wird.
     */
    private int otsuThreshold(final BufferedImage image)
    {
        final int[] histogram = new int[256];
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                ++histogram[image.getRGB(x, y) & 255];
            }
        }

        int countLow = 0; // Anzahl Pixel bis zum Schwellwert
        int countHigh = image.getWidth() * image.getHeight(); // Anzahl über Schwellwert
        int sumLow = 0; // Gewichtete Summe der Pixel bis zum Schwellwert
        int sumHigh = 0; // Gewichtete Summe der Pixel über dem Schwellwert

        for (int i = 0; i < histogram.length; ++i) {
            sumHigh += histogram[i] * i;
        }

        int bestThreshold = 0;
        double bestVar = 0;
        for (int threshold = 0; threshold < histogram.length; ++threshold) {
            countLow += histogram[threshold];
            countHigh -= histogram[threshold];
            sumLow += histogram[threshold] * threshold;
            sumHigh -= histogram[threshold] * threshold;
            if (countLow > 0 && countHigh > 0) {
                double avgLow = (double) sumLow / countLow;
                double avgHigh = (double) sumHigh / countHigh;
                double diff = avgHigh - avgLow;
                double var = (double) countLow * countHigh * diff * diff;
                if (var > bestVar) {
                    bestVar = var;
                    bestThreshold = threshold;
                }
            }
        }

        return bestThreshold;
    }

    /**
     * Liefert eine zufällige Farbe, die mindestens 50 % Sättigung und Helligkeit hat.
     * @return Die Farbe, deren Rot-, Grün- und Blauanteile in einem int kodiert sind.
     */
    private int getRandomColor()
    {
        return Color.HSBtoRGB(random.nextFloat(),
                random.nextFloat() * 0.5f + 0.5f,
                random.nextFloat() * 0.5f + 0.5f);
    }

    /**
     * Liefert ein segmentiertes Bild, bei dem alle zusammenhängenden, weißen Regionen
     * des Schwarzweißbildes mit zufälligen Farben markiert sind. Das Bild wird erst bei
     * Bedarf berechnet.
     * @return Das segmentierte Bild.
     */
    BufferedImage getSegmented()
    {
        if (segmented == null) {
            final BufferedImage image = getBlackAndWhite();
            segmented = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            final List<Run> runs = createRuns(image);
            createRegions(runs);
            drawRegions(runs, segmented);
        }
        return segmented;
    }

    /**
     * Erzeugen des Lauflängen-kodierten Schwarzweißbildes.
     * @param image Ein Schwarzweißbild.
     * @return Alle weißen Segmente.
     */
    private List<Run> createRuns(final BufferedImage image)
    {
        final List<Run> runs = new ArrayList<Run>();
        for (int y = 0; y < image.getHeight(); ++y) {
            int x = 0;
            while (x < image.getWidth()) {
                // Überspringe schwarze Pixel.
                for (; x < image.getWidth() && image.getRGB(x, y) == BLACK; ++x);
                if (x < image.getWidth()) {
                    // Erzeuge Run.
                    int xStart = x;
                    for (; x < image.getWidth() && image.getRGB(x, y) == WHITE; ++x);
                    runs.add(new Run(xStart, x, y));
                }
            }
        }
        return runs;
    }

    /**
     * Zusammenfassen der benachbarten Segmente zu Regionen mittels Union-Find.
     * @param runs Die weißen Segmente des Bildes.
     */
    private void createRegions(final List<Run> runs)
    {
        int i = 0; // Läuft vorneweg.
        int j = 0; // Bleibt eine Zeile zurück.
        while (i < runs.size()) {
            // Sind zwei Runs benachbart, werden sie vereinigt.
            if (runs.get(j).y + 1 == runs.get(i).y &&
                    runs.get(j).xStart < runs.get(i).xEnd &&
                    runs.get(i).xStart < runs.get(j).xEnd) {
                uniteRegions(runs.get(i), runs.get(j));
            }

            // Wenn j nicht in der vorherigen Zeile von i ist oder das
            // rechte Ende seines Runs das rechte Ende des Runs von i
            // noch nicht überholt hat, wird j weitergezählt, sonst i.
            if (runs.get(j).y + 1 < runs.get(i).y ||
                    runs.get(j).y + 1 == runs.get(i).y &&
                            runs.get(j).xEnd < runs.get(i).xEnd) {
                ++j;
            }
            else {
                ++i;
            }
        }
    }

    /**
     * Vereinigen zweier Regionen. Um das spätere Einfärben zu vereinfachen, wird immer
     * die Wurzel, die später im Bild kommt an die gehängt, die früher im Bild kommt.
     * Dadurch ist die Wurzel einer Region auch immer das erste Segment dieser Region
     * im Bild.
     * @param run1 Ein Segment aus der einen Region.
     * @param run1 Ein Segment aus der anderen Region.
     */
    private void uniteRegions(final Run run1, final Run run2)
    {
        final Run root1 = run1.getRoot();
        final Run root2 = run2.getRoot();
        if (root1.y < root2.y || root1.y == root2.y && root1.xStart < root2.xStart) {
            root2.parent = root1;
        }
        else {
            root1.parent = root2;
        }
    }

    /**
     * Einfärben der Regionen. Da die Wurzel immer das zuerst gefundene Segment
     * einer Region ist, wird eine neue Farbe vergeben, wenn eine Region ihre
     * eigene Wurzel ist. Ansonsten wird einfach im Bild nachgesehen, wie die Wurzel
     * eingefärbt wurde und diese Farbe verwendet.
     */
    private void drawRegions(final List<Run> runs, final BufferedImage image)
    {
        for (final Run run : runs) {
            final Run root = run.getRoot();
            final int color = root == run ? getRandomColor() : image.getRGB(root.xStart, root.y);
            for (int x = run.xStart; x < run.xEnd; ++x) {
                image.setRGB(x, run.y, color);
            }
        }
    }
}
