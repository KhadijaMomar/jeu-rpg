package metiers;

public class Armurerie {
    private Marteau marteau;
    private Arc arc;
    private Hache hache;

    public Armurerie(Marteau marteau, Arc arc, Hache hache) {
        this.marteau = marteau;
        this.arc = arc;
        this.hache = hache;
    }

    public Marteau getMarteau() {
        return marteau;
    }

    public Arc getArc() {
        return arc;
    }

    public Hache getHache() {
        return hache;
    }
}
