package pe.gob.munichupaca.apiservice.auth.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.gob.munichupaca.apiservice.auth.domain.models.entities.UserEntity;
import pe.gob.munichupaca.apiservice.auth.domain.models.enums.RoleEnum;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.role = ?1")
    List<UserEntity> findUsersByRole(RoleEnum role);
}
