package de.uni_bremen.pi2;

/**
 * Die Klasse repräsentiert ein Segment in einem Lauflängenbild.
 */
class Run
{
    /** Die linke x-Koordinate des Segments. */
    final int xStart;

    /** Die x-Koordinate rechts direkt hinter dem Segment. */
    final int xEnd;

    /** Die y-Koordinate des Segments. */
    final int y;

    /** Das Elternsegment. Wenn es gleich diesem ist, ist dies die Wurzel. */
    Run parent;

    /**
     * Konstruktor.
     * @param xStart Die linke x-Koordinate des Segments.
     * @param xEnd Die x-Koordinate rechts direkt hinter dem Segment.
     * @param y Die y-Koordinate des Segments.
     */
    Run(final int xStart, final int xEnd, final int y)
    {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.y = y;
        parent = this;
    }

    /**
     * Liefert die Wurzel der Region, zu der dieses Segment gehört.
     * @return Die Wurzel.
     */
    Run getRoot()
    {
        Run run = this;
        for (; run != run.parent; run = run.parent);
        return run;
    }
}