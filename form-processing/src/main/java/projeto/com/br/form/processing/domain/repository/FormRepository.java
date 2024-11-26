package projeto.com.br.form.processing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.form.Status;
import projeto.com.br.form.processing.domain.model.user.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, Long> {
    @Modifying
    @Query("UPDATE Form f SET f.dataExclusao = :dataExclusao WHERE f.id = :formId")
    void marcarComoExcluido(Long formId, OffsetDateTime dataExclusao);

    @Query("SELECT f FROM Form f WHERE f.dataExclusao IS NULL")
    List<Form> buscarAtivos();

    @Query("SELECT f FROM Form f WHERE f.user = :user AND f.dataExclusao IS NULL")
    List<Form> findByUser(User user);

    @Query("SELECT f FROM Form f WHERE f.user.id = :criadorId AND f.dataExclusao IS NULL")
    List<Form> findByCriadorIdAndDataExclusaoIsNull(UUID criadorId);

}
