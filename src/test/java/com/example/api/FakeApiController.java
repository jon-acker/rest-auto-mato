package com.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/objects")
public class FakeApiController {

    private final Map<String, Map<String, Object>> db = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("name")) {
            return ResponseEntity.badRequest().build();
        }
        String id = UUID.randomUUID().toString();
        db.put(id, Map.of("id", id, "name", body.get("name"), "createdAt", Date.from(Instant.now()), "data", Map.of()));
        return ResponseEntity.status(201).body(db.get(id));
    }

    @GetMapping
    public List<Map<String, Object>> list(@RequestParam(name = "id", required = false) List<String> ids) throws InterruptedException {
        if (ids != null) {
            return ids.stream().map(db::get).filter(Objects::nonNull).toList();
        }
        return new ArrayList<>(db.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") String id) {
        return Optional.ofNullable(db.get(id))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        return (db.remove(id) != null) ?
                noContent().build() :
                notFound().build();
    }
}
