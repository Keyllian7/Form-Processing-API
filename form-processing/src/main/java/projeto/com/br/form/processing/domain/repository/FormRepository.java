package projeto.com.br.form.processing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.com.br.form.processing.domain.model.form.Form;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, String> {
    List<Form> findByEmissorId(String userId);
}
