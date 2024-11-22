package projeto.com.br.form.processing.threads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDTO {
    private long totalUsers;
    private long totalForms;
    private long pendingForms;
}
