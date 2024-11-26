package projeto.com.br.form.processing.threads;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.EnableAsync;
import projeto.com.br.form.processing.domain.model.form.Status;
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

    private long totalUsers;
    private long totalForms;
    private long pendingForms;

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

                        System.out.println("Total Users: " + totalUsers);
                        System.out.println("Total Forms: " + totalForms);
                        System.out.println("Pending Forms: " + pendingForms);

                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalForms() {
        return totalForms;
    }

    public long getPendingForms() {
        return pendingForms;
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
