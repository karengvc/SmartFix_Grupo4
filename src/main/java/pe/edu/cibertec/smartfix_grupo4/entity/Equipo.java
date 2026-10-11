
package pe.edu.cibertec.smartfix_grupo4.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 50)
    private String estado;
}
