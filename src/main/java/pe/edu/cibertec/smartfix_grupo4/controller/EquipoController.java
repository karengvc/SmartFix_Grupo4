
package pe.edu.cibertec.smartfix_grupo4.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import pe.edu.cibertec.smartfix_grupo4.entity.Equipo;
import pe.edu.cibertec.smartfix_grupo4.repository.EquipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepository;

    public EquipoController(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public record EquipoRequest(
            @NotBlank String nombre,
            @NotBlank String marca,
            @NotBlank String estado) {
    }

    @GetMapping
    public List<Equipo> listar() {
        return equipoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> buscar(@PathVariable Long id) {
        return equipoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Equipo> insertar(
            @Valid @RequestBody EquipoRequest request) {

        Equipo equipo = new Equipo();
        equipo.setNombre(request.nombre());
        equipo.setMarca(request.marca());
        equipo.setEstado(request.estado());

        Equipo guardado = equipoRepository.save(equipo);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequest request) {

        return equipoRepository.findById(id)
                .map(equipo -> {
                    equipo.setNombre(request.nombre());
                    equipo.setMarca(request.marca());
                    equipo.setEstado(request.estado());

                    return ResponseEntity.ok(equipoRepository.save(equipo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!equipoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        equipoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
