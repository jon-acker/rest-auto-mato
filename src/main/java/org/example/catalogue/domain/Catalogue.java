package org.example.catalogue.domain;

import java.util.List;
import java.util.Optional;

public interface Catalogue {
    Optional<Product> add(String name);

    List<Product> list();

    List<Product> listByIds(List<String> ids);

    Optional<Product> get(String id);

    DeleteOutcome delete(String id);

    Optional<Product> updateName(String id, String newName);
}
