package org.example.api;

import jakarta.validation.Valid;
import org.example.catalogue.domain.Catalogue;
import org.example.catalogue.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/objects")
public class ApiController {

    private final Catalogue catalogue;

    public ApiController(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @BadRequestOnEmpty
    public Optional<Product> add(@Valid @RequestBody ProductNameRequest body) {
        return catalogue.add(body.name());
    }

    @GetMapping
    public List<Product> list(@RequestParam(name = "id", required = false) List<String> ids) {
        return (ids != null) ?
                catalogue.listByIds(ids):
                catalogue.list();
    }

    @GetMapping("/{id}")
    @NotFoundOnEmpty
    public Optional<Product> get(@PathVariable(name = "id") String id) {
        return catalogue.get(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        return switch (catalogue.delete(id)) {
            case DELETED -> noContent().build();
            case NOT_FOUND -> notFound().build();
        };
    }

    @PatchMapping("/{id}")
    @NotFoundOnEmpty
    public Optional<Product> updateName(@PathVariable(name = "id") String id,
                                        @Valid @RequestBody ProductNameRequest body) {
        return catalogue.updateName(id, body.name());
    }
}
