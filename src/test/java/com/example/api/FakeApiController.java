package org.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/objects")
public class FakeApiController {

    private final Map<String, Map<String,Object>> db = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String,Object> body) {
        if (!body.containsKey("name")) {
            return ResponseEntity.badRequest().build();
        }
        String id = UUID.randomUUID().toString();
        db.put(id, Map.of("id", id, "name", body.get("name"), "data", Map.of()));
        return ResponseEntity.status(201).body(db.get(id));
    }

    @GetMapping
    public List<Map<String,Object>> list(@RequestParam(required = false) List<String> id) {
        if (id != null) {
            return id.stream().map(db::get).filter(Objects::nonNull).toList();
        }
        return new ArrayList<>(db.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return Optional.ofNullable(db.get(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
