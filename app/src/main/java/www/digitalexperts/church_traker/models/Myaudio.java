package www.digitalexperts.church_traker.models;

public class Myaudio {
   public String name;
   public String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Myaudio myaudio = (Myaudio) o;
        return name.equals(myaudio.name) &&
                path.equals(myaudio.path);
    }

    public Myaudio(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
