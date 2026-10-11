
package pe.edu.cibertec.smartfix_grupo4;

import pe.edu.cibertec.smartfix_grupo4.entity.Equipo;
import pe.edu.cibertec.smartfix_grupo4.repository.EquipoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmartFixGrupo4ApplicationTests {

    @Autowired
    private EquipoRepository equipoRepository;

    @Test
    void insertarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Equipo JUnit");
        equipo.setMarca("Dell");
        equipo.setEstado("Operativo");

        Equipo guardado = equipoRepository.save(equipo);

        assertNotNull(guardado.getId());
        equipoRepository.deleteById(guardado.getId());
    }

    @Test
    void listarEquipos() {
        assertNotNull(equipoRepository.findAll());
    }

    @Test
    void actualizarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Equipo original");
        equipo.setMarca("HP");
        equipo.setEstado("Operativo");

        Equipo guardado = equipoRepository.save(equipo);
        guardado.setNombre("Equipo actualizado");
        equipoRepository.save(guardado);

        Equipo encontrado = equipoRepository.findById(guardado.getId())
                .orElseThrow();

        assertEquals("Equipo actualizado", encontrado.getNombre());

        equipoRepository.deleteById(guardado.getId());
    }

    @Test
    void eliminarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Equipo para eliminar");
        equipo.setMarca("Acer");
        equipo.setEstado("Operativo");

        Equipo guardado = equipoRepository.save(equipo);
        Long id = guardado.getId();

        equipoRepository.deleteById(id);

        assertFalse(equipoRepository.existsById(id));
    }
}
