package Package.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/*
La clase Book es la clase propietaria.
ENTIDAD B
 */

/*
La propiedad uniqueConstraints básicamente me declara que un libro debe tener un ISBN único, es decir, no pueden
haber 2 libros con el mismo ISBN.
 */

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"ISBN"})})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String ISBN;

    /*
    --------------------------------------------------------------------------------------------------------------------
    1. ManyToOne: Aquí estoy declarando que muchos libros pueden pertenecer a una biblioteca. Eso es ManyToOne.
    El Lazy es para que sólo se ejecute cuando la llamemos, no a toda hora. Sirve para ahorrar memoria.
    --------------------------------------------------------------------------------------------------------------------
    2. JoinColumn = Aquí estoy declarándole el atributo que me guarda la biblioteca a la cual está relacionado el libro.
    Básicamente es una FK.
    El JoinColumn indica que la entidad que lo contenga es la propietaria.
    --------------------------------------------------------------------------------------------------------------------
    3. JsonProperty = Evitar errores, básicamente que sea un atributo de sólo escritura. Me evita el error:
    LazyInitializationException que se genera usando LAZY (carga perezosa) en API REST
    En resumen: Spring serializa los objetos, por ende, al serializar un objeto perezoso o Lazy arrojará un error.
    Y para evitar ese error se usa el JsonProperty

    Una vez entendí:
    Lo que pasa es que si yo abro una conexión a la base de datos y hago una operación de consulta por ejemplo de un
    libro y luego cierro la conexión e intento posteriormente imprimir los datos del libro, pues no me dejará porque
    eso debo hacerlo una vez la conexión esté abierta. Sin embargo, el JsonProperty me evita este error.
    --------------------------------------------------------------------------------------------------------------------
     */
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "library_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;

}
