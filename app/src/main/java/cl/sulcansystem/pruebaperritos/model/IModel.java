package cl.sulcansystem.pruebaperritos.model;

public interface IModel {

    void loadBreeds();
    void loadImages(String raza);
    void loadImagesSubraza(String raza, String subRaza);

}
