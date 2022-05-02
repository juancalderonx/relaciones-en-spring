package Package.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/*
ENTIDAD A
 */

@Entity
@Table(name = "libraries")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Library {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    /*
    --------------------------------------------------------------------------------------------------------------------
    Cascade = Aquí el cascade type ALL significa que si yo elimino una biblioteca también deben eliminarse sus libros.
    --------------------------------------------------------------------------------------------------------------------
    OneToMany = Significa que UNA biblioteca puede tener MUCHOS libros. Esos muchos son el atributo que es una colección.
    --------------------------------------------------------------------------------------------------------------------
    mappedBy = Este elemento indica el nombre del atributo que representa la FK en la otra entidad, es decir.
    En Book tengo un atributo que me guarda la biblioteca a la que pertenece ese libro. Bueno, pues el nombre de ese
    atributo va en el mappedBy.
    También, la entidad que tenga el mappedBy es una entidad NO PROPIETARIA.
    --------------------------------------------------------------------------------------------------------------------
    ¿Por qué usé un Set (Interface) con su implementación HashSet y no un List con ArrayList?
    Bueno, porque el Set no me permite tener elementos duplicados. Y pues yo quiero que ningún libro
    tenga el título de otro. Mientras que el List pues sí que permite duplicados.
     */
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    /*
    ¿Por qué este método set no lo creé con Lombok?
    Porque necesitaba añadirle ese foreach.
    Lo que hace ese foreach es que cada vez que se la vaya a establecer un libro a esta biblioteca pues ese libro
    sí o sí su atributo library será del tipo de esta clase.
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
            for (Book book : books) {
                book.setLibrary(this);
            }
    }

}
