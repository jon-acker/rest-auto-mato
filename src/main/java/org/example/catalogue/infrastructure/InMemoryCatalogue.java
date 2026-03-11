package org.example.catalogue.infrastructure;

import org.example.catalogue.domain.Catalogue;
import org.example.catalogue.domain.DeleteOutcome;
import org.example.catalogue.domain.Product;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCatalogue implements Catalogue {
    private final Map<String, Product> db = new ConcurrentHashMap<>();

    @Override
    public Optional<Product> add(String name) {
        boolean duplicate = db.values()
                .stream()
                .anyMatch(item -> name.equals(item.name()));

        if (duplicate) {
            return Optional.empty();
        }

        String id = UUID.randomUUID().toString();
        Product product = new Product(id, name, Date.from(Instant.now()), null, Map.of());
        db.put(id, product);
        return Optional.of(product);
    }

    @Override
    public List<Product> list() {
        return List.copyOf(db.values());
    }

    @Override
    public List<Product> listByIds(List<String> ids) {
        return ids.stream().map(db::get).filter(java.util.Objects::nonNull).toList();
    }

    @Override
    public Optional<Product> get(String id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public DeleteOutcome delete(String id) {
        return db.remove(id) != null ? DeleteOutcome.DELETED : DeleteOutcome.NOT_FOUND;
    }

    @Override
    public Optional<Product> updateName(String id, String newName) {
        Product existing = db.get(id);
        if (existing == null) {
            return Optional.empty();
        }

        Product updated = new Product(existing.id(), newName, existing.createdAt(), Date.from(Instant.now()), existing.data());
        db.put(id, updated);
        return Optional.of(updated);
    }
}
