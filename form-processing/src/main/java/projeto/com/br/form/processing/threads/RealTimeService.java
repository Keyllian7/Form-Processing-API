package projeto.com.br.form.processing.threads;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.EnableAsync;
import projeto.com.br.form.processing.domain.enums.Status;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.service.FormService;
import projeto.com.br.form.processing.domain.service.UserService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@EnableAsync
public class RealTimeService {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private long totalUsers;
    private long totalForms;
    private long pendingForms;
    private long processedForms;
    private long activeUsers;
    private long deletedForms;
    private long inactiveUsers;
    private double averageFormsPerUser;
    private double averageProcessingTime;
    private double completionRate;

    @PostConstruct
    public void startThread() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        totalUsers = userService.listar().size();
                        totalForms = formService.listar().size();
                        pendingForms = formService.listar().stream().filter(f -> f.getStatus() == Status.PENDENTE).count();
                        processedForms = formService.listar().stream().filter(f -> f.getStatus() == Status.RESOLVIDO).count();
                        activeUsers = userRepository.findAllAtivos().size();
                        deletedForms = formService.listarTodos().stream().filter(f -> f.getDataExclusao() != null).count();
                        inactiveUsers = userRepository.findAllInativos().size();

                        averageFormsPerUser = totalUsers > 0 ? (double) totalForms / totalUsers : 0;

                        averageProcessingTime = formService.listar().stream()
                                .filter(f -> f.getDataAtualizacao() != null && f.getDataCriacao() != null)
                                .mapToDouble(f -> f.getDataAtualizacao().toEpochSecond() - f.getDataCriacao().toEpochSecond())
                                .average().orElse(0);

                        completionRate = totalForms > 0 ? (double) processedForms / totalForms * 100 : 0;

                        System.out.println("Total Users: " + totalUsers);
                        System.out.println("Total Forms: " + totalForms);
                        System.out.println("Pending Forms: " + pendingForms);
                        System.out.println("Processed Forms: " + processedForms);
                        System.out.println("Active Users: " + activeUsers);
                        System.out.println("Deleted Forms: " + deletedForms);
                        System.out.println("Inactive Users: " + inactiveUsers);
                        System.out.println("Average Forms Per User: " + averageFormsPerUser);
                        System.out.println("Average Processing Time: " + averageProcessingTime + " seconds");
                        System.out.println("Completion Rate: " + completionRate + "%");

                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
    }

    public long getTotalUsers() { return totalUsers; }
    public long getTotalForms() { return totalForms; }
    public long getPendingForms() { return pendingForms; }
    public long getProcessedForms() { return processedForms; }
    public long getActiveUsers() { return activeUsers; }
    public long getDeletedForms() { return deletedForms; }
    public long getInactiveUsers() { return inactiveUsers; }
    public double getAverageFormsPerUser() { return averageFormsPerUser; }
    public double getAverageProcessingTime() { return averageProcessingTime; }
    public double getCompletionRate() { return completionRate; }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
