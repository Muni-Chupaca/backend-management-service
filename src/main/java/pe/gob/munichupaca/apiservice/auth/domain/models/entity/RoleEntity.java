package pe.gob.munichupaca.apiservice.auth.domain.models.entity;


import jakarta.persistence.*;
import lombok.*;
import pe.gob.munichupaca.apiservice.auth.domain.models.enums.RoleEnum;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
