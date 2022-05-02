package Package.Controllers;

import Package.Entities.Library;
import Package.Repositories.LibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;
    private final Logger log = LoggerFactory.getLogger(LibraryController.class);

    //Obtener todas las librerías
    @GetMapping("/getAllLibraries")
    private List<Library> findAllLibraries(){
        return libraryRepository.findAll();
    }

    //Crear una librería
    @PostMapping("/createLibrary")
    private ResponseEntity<Library> createLibrary(@Valid @RequestBody Library library){
        if(library.getId() != null){
            log.warn("¡Error! Trying create book with current ID");
            return ResponseEntity.badRequest().build();
        }
        log.info("Creating new library :D");
        Library result = libraryRepository.save(library);
        return ResponseEntity.ok(result);
    }

    //Buscar librería por ID
    @GetMapping("/getLibraryById/{id}")
    private ResponseEntity<Library> findLibraryById(@Valid @PathVariable Long id){

        Optional<Library> libraryOpt = libraryRepository.findById(id);

        if(libraryOpt.isPresent()){
            return ResponseEntity.ok(libraryOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    //Actualizar librería
    @PutMapping("/updateLibrary")
    private ResponseEntity<Library> updateLibrary(@Valid @RequestBody Library library){

        if(library.getId() == null){
            log.warn("¡Error! Trying to modify a library that doesn't exist");
            return ResponseEntity.badRequest().build();
        }

        if(!libraryRepository.existsById(library.getId())){
            log.warn("¡Error! Trying to modify a library with unknown ID");
            return ResponseEntity.notFound().build();
        }

        Library resultModify = libraryRepository.save(library);
        log.info("Library updated :D");
        return ResponseEntity.ok(library);

    }

    //Eliminar librería por ID
    @DeleteMapping("/deleteLibraryById/{id}")
    private ResponseEntity<Library> deleteLibraryById(@Valid @PathVariable Long id){

        if(!libraryRepository.existsById(id)){
            log.warn("¡ERROR! This library doesn't exist");
            return ResponseEntity.notFound().build();
        }

        log.info("Library successfully deleted");
        libraryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Eliminar todas las librerías
    @DeleteMapping("/deleteAllLibraries")
    private ResponseEntity<Library> deleteAllLibraries(){

        long amount = libraryRepository.count();

        if(amount == 0){
            log.warn("Trying to delete libraries but there are none");
            return ResponseEntity.badRequest().build();
        }

        libraryRepository.deleteAll();
        log.info("All libraries successfully removed");
        return ResponseEntity.noContent().build();

    }

}
