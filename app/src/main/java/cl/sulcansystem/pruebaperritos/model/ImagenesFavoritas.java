package cl.sulcansystem.pruebaperritos.model;

import java.util.List;

//Pojo para guardar los datos en la DB de Firebase
public class ImagenesFavoritas {

    private String imagen;

    public ImagenesFavoritas(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }
}
