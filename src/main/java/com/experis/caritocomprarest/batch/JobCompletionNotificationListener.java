package com.experis.caritocomprarest.batch;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener  extends JobExecutionListenerSupport {

    private final ProductRepository productRepository;

    public JobCompletionNotificationListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            productRepository.findAll().forEach(p -> log.debug(p.toString()));
        }
    }
}