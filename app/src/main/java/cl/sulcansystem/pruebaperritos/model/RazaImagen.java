package cl.sulcansystem.pruebaperritos.model;

import java.util.List;
import java.util.Map;

public class RazaImagen {
    private String status;
    private List<String> message;

    public String getStatus() {
        return status;
    }

    public List<String> getMessage() {
        return message;

    }

    @Override
    public String toString() {
        return "RazaImagen{" +
                "status='" + status + '\'' +
                ", message=" + message +
                '}';
    }
}
