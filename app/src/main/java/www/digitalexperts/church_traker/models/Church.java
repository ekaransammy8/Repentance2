package www.digitalexperts.church_traker.models;

import java.io.Serializable;
import java.util.Objects;

public class Church implements Serializable {
    public int id;
    public String name;
    public String photo;
    public String location;
    public String latitude;
    public String longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Church church = (Church) o;
        return id == church.id;  }
}
