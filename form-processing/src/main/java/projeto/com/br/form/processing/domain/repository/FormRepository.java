package projeto.com.br.form.processing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.com.br.form.processing.domain.model.form.Form;

public interface FormRepository extends JpaRepository<Form, String> {
}
