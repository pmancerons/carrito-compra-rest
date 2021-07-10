package com.experis.caritocomprarest.config;

import com.experis.caritocomprarest.batch.JobCompletionNotificationListener;
import com.experis.caritocomprarest.batch.ProductItemProcessor;
import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.repositories.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ProductRepository productRepository;

    @Value("${file.input}")
    private String fileInput;

    @Value("${max.number.of.rows.to.skip}")
    private int errorsToSkip;

    @Bean
    public FlatFileItemReader reader() {
        return new FlatFileItemReaderBuilder<>().name("productItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(Product.getFiledNames())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Product.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {

        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(RepositoryItemWriter writer) {
        return stepBuilderFactory.get("step1")
                .<Product, Product> chunk(10)
                .reader(reader())
                .faultTolerant()
                .skip(DataIntegrityViolationException.class)
                .skipLimit(errorsToSkip)
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

}
