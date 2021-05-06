package com.nice.datafileanomalydetection.predict.config;

import com.nice.datafileanomalydetection.predict.service.PredictServiceConfig;
import com.nice.datafileanomalydetection.predict.service.file.CSVFileHeaderCallback;
import com.nice.datafileanomalydetection.predict.service.file.FlatFileItemReader;
import com.nice.datafileanomalydetection.predict.service.listener.*;
import com.nice.datafileanomalydetection.predict.service.mapping.JsonMapLineMapper;
import com.nice.datafileanomalydetection.predict.service.mapping.MapFieldSetMapper;
import com.nice.datafileanomalydetection.predict.service.mapping.MapLineMapper;
import com.nice.datafileanomalydetection.predict.service.processor.*;
import com.nice.datafileanomalydetection.predict.service.transform.*;
import com.nice.datafileanomalydetection.predict.service.writer.PredictItemWriterH2;
import com.nice.datafileanomalydetection.predict.service.writer.PredictJsonItemWriterH2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
@EnableAsync
public class PredictConfig {

    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @StepScope
    PredictStepExecutionListener ruleStepExecutionListener;

    @Autowired
    @StepScope
    MakeFixedToCSVFileListener makeFixedToCSVFileListener;

    @Autowired
    @JobScope
    JobCompletionListener jobCompletionListener;

    @Autowired
    @StepScope
    PredictProcessService predictProcess;

    @Autowired
    @StepScope
    MakeFixedToCSVProcessService makeFixedToCSVProcess;

    @Autowired
    @StepScope
    PredictItemWriterListener predictItemWriterListener;

    @Autowired
    @StepScope
    MakeFixedToCSVFileWriteListener makeFixedToCSVFileWriteListener;

    @Autowired
    @StepScope
    MakePaddedToNonPadListener makePaddedToNonPadListener;

    @Autowired
    @StepScope
    MakePaddedToNonPadProcessService makePaddedToNonPadProcess;

    @Autowired
    @StepScope
    MakePaddedToNonPadWriteListener makePaddedToNonPadWriteListener;

    @Autowired
    @StepScope
    MakeJSONToCSVProcessService makeJSONToCSVProcess;

    @Autowired
    @StepScope
    PredictJsonStepExecutionListener predictJsonStepExecutionListener;

    @Autowired
    @StepScope
    PredictJSONProcessService predictJSONProcess;

    @Autowired
    @StepScope
    PredictJsonItemWriterListener predictJsonItemWriterListener;

    @Autowired
    @StepScope
    PredictTableProcessService predictTableProcess;

    //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
    @Value("${json.itemlist}")
    private String jsonItemList;

    @Value("${json.itemcode}")
    private String jsonItemCode;

    @Value("${json.itemtype}")
    private String jsonItemType;

    @Value("${json.itemvalue}")
    private String jsonItemValue;

    private final String delimiter = "|";
    private final String willBeInjected = null;
    private final int willBeInjectedINT = Integer.MIN_VALUE;
    private final long willBeInjectedLONG = Long.MIN_VALUE;
    //private int coreTaskPoolSize = 4;
//    private int coreTaskPoolSize = (int) Math.ceil(Runtime.getRuntime().availableProcessors() / 2);
    //private int maxTaskPoolSize = 4;
//    private int maxTaskPoolSize = Math.max((int) Math.ceil(Runtime.getRuntime().availableProcessors() / 2), 4);
//    private int maxQueueSize = 4;
//    private int chunkAndPageSize = 1000;
    @Value("${predict.coretaskpoolsize:#{T(java.lang.Math).max(T(java.lang.Runtime).getRuntime().availableProcessors()/2,2)}}")
    private int coreTaskPoolSize;

    @Value("${predict.maxtaskpoolsize:#{T(java.lang.Math).max(${predict.coretaskpoolsize},T(java.lang.Math).max(T(java.lang.Runtime).getRuntime().availableProcessors()/2,2))}}")
    private int maxTaskPoolSize;

    @Value("${predict.maxqueuesize:4}")
    private int maxQueueSize;

    @Value("${predict.chunkandpagesize:1000}")
    private int chunkAndPageSize;

    @Bean
    @Primary
    public BatchProperties batchProperties () {
        final String SCHEMA_LOCATION = "classpath:schema/schema-h2.sql";
        BatchProperties batchProperties = new BatchProperties();
        batchProperties.setSchema(SCHEMA_LOCATION);

        return batchProperties;
    }

    @Bean
    public TaskExecutor executor () {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 기본 Thread 사이즈
        executor.setCorePoolSize(coreTaskPoolSize);
        // 최대 Thread 사이즈
        executor.setMaxPoolSize(maxTaskPoolSize);
        // Max Thread가 동작하는 경우 대기하는 Queue 사이즈
        executor.setQueueCapacity(maxQueueSize);
        executor.setThreadNamePrefix("Executor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Job predictJobFixed (Step predictStepFixed) {
        return this.jobBuilderFactory.get("predictJobFixed").listener(jobCompletionListener)
                .preventRestart()
                .start(predictStepFixed)
                .build();
    }

    @Bean
    public Job makeFixedToCSVFile (Step makeFixedToCSVFileStep) {
        return this.jobBuilderFactory.get("makeFixedToCSVFileStep").listener(jobCompletionListener)
                .preventRestart()
                .start(makeFixedToCSVFileStep)
                .build();
    }

    @Bean
    @JobScope
    public Step makeFixedToCSVFileStep () {
        return stepBuilderFactory.get("makeFixedToCSVFileStep").listener(makeFixedToCSVFileListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReaderFixed(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(makeFixedToCSVProcess)
                .writer(outputFileCSVWrite(willBeInjected, willBeInjected, willBeInjectedINT))
                .listener(makeFixedToCSVFileWriteListener)
                .taskExecutor(executor())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter outputFileCSVWrite (
            @Value("#{jobParameters['outputDataFile']}") String outputDataFile,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['totalLineCount']}") int totalLineCount) {

        MapFieldExtractor mapFieldExtractor = new MapFieldExtractor();
        mapFieldExtractor.setFloatingPointFormat("0.#################");
        mapFieldExtractor.setNames(inputItemList.split(","));

        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setFieldExtractor(mapFieldExtractor);
        delimitedLineAggregator.setDelimiter(",");

        //		CSVFileHeaderCallback csvFileHeaderCallback = new CSVFileHeaderCallback();

        FlatFileItemWriter writer = new FlatFileItemWriter();
        writer.setShouldDeleteIfExists(true);
        writer.setResource(new FileSystemResource(outputDataFile));
        writer.setLineAggregator(delimitedLineAggregator);
        // Default값이 UTF-8이나 UTF-8로 파일을 생성할 경우 N-Strategy ML에서 마트 업로드 시 오류 발생.
        // N-Strategy ML에서는 UTF-8의 경우 깨져서 보이긴 하지만 정상 동작 한다고 하나, 오류남. 깨진 테스트에 ","가 들어가 있을 수도.
        // delimitedLineAggregator의 Delimiter를  "|"로 바꿔도 N-Strategy ML에서 동일한 오류. 그래서 EUC-KR로 파일을 생성한다. 
        writer.setEncoding("EUC-KR");

        CSVFileHeaderCallback csvFileHeaderCallback = new CSVFileHeaderCallback(String.join(",", inputItemList.split(",")));
        writer.setHeaderCallback(csvFileHeaderCallback);

        return writer;
    }

    @Bean
    @JobScope
    public Step predictStepFixed () {
        return stepBuilderFactory.get("predictStepFixed").listener(ruleStepExecutionListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReaderFixed(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(predictProcess)
                .writer(outputDBInsert(willBeInjected))
                .listener(predictItemWriterListener)
                .taskExecutor(executor())
                .build();
    }

    /**
     * #refs 1299 멀티쓰레드 환경에서는 읽어오는 데이터의 순서가 보장되지 않아서, footer의 라인수를 통해 트레일러를 예외처리하는 로직이 무의미하여 'linesToFooterSkip' 파라미터 제거함 (2020-11-03, kwb)
     *
     * @param inputFileName
     * @param inputDataFileExtension
     * @param inputItemList
     * @param columnLength
     * @param delimeter
     * @param totalLineCount
     * @param linesToSkip
     * @param inputFileEncoding
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader inputFileReaderFixed (
            @Value("#{jobParameters['inputDataFile']}") String inputFileName,
            @Value("#{jobParameters['inputDataFileExtension']}") String inputDataFileExtension,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['columnLength']}") String columnLength,
            @Value("#{jobParameters['delimeter']}") String delimeter,
            @Value("#{jobParameters['totalLineCount']}") int totalLineCount,
            @Value("#{jobParameters['linesToSkip']}") int linesToSkip,
            @Value("#{jobParameters['inputFileEncoding']}") String inputFileEncoding) {

        logger.info("========================================>");

        FixedLengthTokenizer lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setColumns(Stream.of(columnLength.split(",")).mapToInt(Integer::parseInt).toArray());
        // 2020-04-21 kty 파일의 encoding 값을 tokenizer 에서 사용.
        lineTokenizer.setEncoding(inputFileEncoding);

        MapFieldSetMapper objectMapper = new MapFieldSetMapper();
        // 2020-05-19 inputItemList를 일관성 있게 fixed length 일 때 "," 로 split 하도록 변경.
        objectMapper.setNames(inputItemList.split(","));
        objectMapper.setType(MapFieldSetMapper.class);

        MapLineMapper lineMapper = new MapLineMapper();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setObjectMapper(objectMapper);
        lineMapper.setTotalItemsToRead(totalLineCount);

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new FileSystemResource(inputFileName));
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(linesToSkip); // Header는 읽기 Skip
        reader.setSaveState(Boolean.FALSE);
        reader.setExtension(inputDataFileExtension);
        reader.setEncoding(inputFileEncoding);

        return reader;
    }

    @Bean
    public Job predictJob (Step predictStep) {
        return this.jobBuilderFactory.get("predictJob").listener(jobCompletionListener)
                .preventRestart()
                .start(predictStep)
                .build();
    }

    @Bean
    @JobScope
    public Step predictStep () {
        return stepBuilderFactory.get("predictStep").listener(ruleStepExecutionListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReader(willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjected, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(predictProcess)
                .writer(outputDBInsert(willBeInjected))
                .listener(predictItemWriterListener)
                .taskExecutor(executor())
                .build();
    }

    @Bean
    @StepScope
    public PredictItemWriterH2 outputDBInsert (@Value("#{jobParameters['columnCalculateYN']}") String columnCalculateYN) {

        PredictItemWriterH2 predictItemWriterH2 = new PredictItemWriterH2();
        predictItemWriterH2.setColumnCalculateYN(columnCalculateYN);
        predictItemWriterH2.setMeanSquaredErrorMapKey(PredictServiceConfig.ROW_MEAN_SQUARD_ERROR_MAP_KEY);
        if ("Y".equals(columnCalculateYN)) {
            predictItemWriterH2.setSquaredErrorListMapKey(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY);
        }
        return predictItemWriterH2;

    }

    /**
     * #refs 1299 멀티쓰레드 환경에서는 읽어오는 데이터의 순서가 보장되지 않아서, 'linesToFooterSkip' 파라미터를 통해 트레일러를 예외처리하는 로직이 무의미하여 제거함 (2020-11-03, kwb)
     *
     * @param inputFileName
     * @param inputDataFileExtension
     * @param inputItemList
     * @param totalLineCount
     * @param delimeter
     * @param linesToSkip
     * @param inputFileEncoding
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader inputFileReader (
            @Value("#{jobParameters['inputDataFile']}") String inputFileName,
            @Value("#{jobParameters['inputDataFileExtension']}") String inputDataFileExtension,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['totalLineCount']}") int totalLineCount,
            @Value("#{jobParameters['delimeter']}") String delimeter,
            @Value("#{jobParameters['linesToSkip']}") int linesToSkip,
            @Value("#{jobParameters['inputFileEncoding']}") String inputFileEncoding) {

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(delimeter);

        MapFieldSetMapper objectMapper = new MapFieldSetMapper();
        // 2020-05-19 inputItemList를 일관성 있게 , 로 split 하도록 변경. tokenizer 사용 이유는 header 마지막에 , 로 끝나는 경우 split 사용하면 개수로 세어지지 않음.
        objectMapper.setNames(lineTokenizer.doTokenize(inputItemList).stream().toArray(String[]::new));
        if (objectMapper.getNames().length <= 1) {
            DelimitedLineTokenizer lineTokenizer2 = new DelimitedLineTokenizer(",");
            String[] names2 = lineTokenizer2.doTokenize(inputItemList).stream().toArray(String[]::new);
            if (names2.length > 1) {
                objectMapper.setNames(lineTokenizer2.doTokenize(inputItemList).stream().toArray(String[]::new));
            } else {
                logger.info("Delimiter [{}] and Comma , does not match with data", delimeter);
            }
        }

        MapLineMapper lineMapper = new MapLineMapper();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setObjectMapper(objectMapper);
        lineMapper.setTotalItemsToRead(totalLineCount);

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new FileSystemResource(inputFileName));
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(linesToSkip); // Header는 읽기 Skip
        reader.setSaveState(Boolean.FALSE);
        reader.setExtension(inputDataFileExtension);
        reader.setEncoding(inputFileEncoding);

        return reader;
    }

    @Bean
    public RestTemplate restTemplate (RestTemplateBuilder builder) {
        return builder.build();
    }

    // refs #1270 학습을 위한 csv to csv변환 메소드 추가
    @Bean
    public Job convertCSVtoTrainCSVJob (Step convertCSVtoTrainCSVStep) {
        return this.jobBuilderFactory.get("convertCSVtoTrainCSVStep").listener(jobCompletionListener)
                .preventRestart()
                .start(convertCSVtoTrainCSVStep)
                .build();
    }

    @Bean
    @JobScope
    public Step convertCSVtoTrainCSVStep () {
        return stepBuilderFactory.get("convertCSVtoTrainCSVStep").listener(makePaddedToNonPadListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReader(willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjected, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(makePaddedToNonPadProcess)
                .writer(outputFileCSVWrite(willBeInjected, willBeInjected, willBeInjectedINT))
                .listener(makePaddedToNonPadWriteListener)
                .taskExecutor(executor())
                .build();

    }

    @Bean
    public Job convertFixedToTrainCSVJob (Step convertFixedToTrainCSVStep) {
        return this.jobBuilderFactory.get("convertFixedToTrainCSVStep").listener(jobCompletionListener)
                .preventRestart()
                .start(convertFixedToTrainCSVStep)
                .build();
    }

    @Bean
    @JobScope
    public Step convertFixedToTrainCSVStep () {
        return stepBuilderFactory.get("convertFixedToTrainCSVStep").listener(makePaddedToNonPadListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReaderFixed(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(makePaddedToNonPadProcess)
                .writer(outputFileCSVWrite(willBeInjected, willBeInjected, willBeInjectedINT))
                .listener(makePaddedToNonPadWriteListener)
                .taskExecutor(executor())
                .build();
    }

    // refs #1283 JSON parsing 하는 JOB 생성. (2020-06-03, kty)
    @Bean
    public Job convertJSONToCSVJob (Step convertJSONToCSVStep) {
        return this.jobBuilderFactory.get("convertJSONToCSV").listener(jobCompletionListener)
                .preventRestart()
                .start(convertJSONToCSVStep)
                .build();
    }

    @Bean
    @JobScope
    public Step convertJSONToCSVStep () {
        return this.stepBuilderFactory.get("convertJSONToCSVStep").listener(makePaddedToNonPadListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileJSONReader(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected))
                .processor(makeJSONToCSVProcess)
                .writer(outputFileJSONToCSVWrite(willBeInjected, willBeInjected, willBeInjected))
                .listener(makePaddedToNonPadWriteListener)
                .taskExecutor(executor())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader inputFileJSONReader (
            @Value("#{jobParameters['inputDataFile']}") String inputFileName,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['keyItems']}") String keyItems,
            @Value("#{jobParameters['inputDataFileExtension']}") String inputDataFileExtension,
            @Value("#{jobParameters['inputFileEncoding']}") String inputFileEncoding) {

        logger.info("========================================>");

        //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
        JsonLineTokenizer jsonLineTokenizer = new JsonLineTokenizer();
        jsonLineTokenizer.setKeyItems(keyItems.split(",", - 1));
        jsonLineTokenizer.setEncoding(inputFileEncoding);
        jsonLineTokenizer.setJsonItemList(jsonItemList);
        jsonLineTokenizer.setJsonItemCode(jsonItemCode);
        jsonLineTokenizer.setJsonItemType(jsonItemType);
        jsonLineTokenizer.setJsonItemValue(jsonItemValue);

        MapFieldSetMapper objectMapper = new MapFieldSetMapper();
        objectMapper.setNames(inputItemList.split(",", - 1));

        JsonMapLineMapper jsonLineMapper = new JsonMapLineMapper();
        jsonLineMapper.setLineTokenizer(jsonLineTokenizer);
        jsonLineMapper.setObjectMapper(objectMapper);

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new FileSystemResource(inputFileName));
        reader.setLineMapper(jsonLineMapper);
        reader.setSaveState(Boolean.FALSE);
        reader.setExtension(inputDataFileExtension);
        reader.setEncoding(inputFileEncoding);

        return reader;

    }

    @Bean
    @StepScope
    public FlatFileItemWriter outputFileJSONToCSVWrite (
            @Value("#{jobParameters['outputDataFile']}") String outputDataFile,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['inputFileEncoding']}") String inputFileEncoding) {

        JsonMapFieldExtractor jsonMapFieldExtractor = new JsonMapFieldExtractor();
        jsonMapFieldExtractor.setFloatingPointFormat("0.#################");
        jsonMapFieldExtractor.setNames(inputItemList.split(",", - 1));

        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setFieldExtractor(jsonMapFieldExtractor);
        delimitedLineAggregator.setDelimiter(",");

        FlatFileItemWriter writer = new FlatFileItemWriter();
        writer.setShouldDeleteIfExists(true);
        writer.setResource(new FileSystemResource(outputDataFile));
        writer.setLineAggregator(delimitedLineAggregator);

        //writer.setEncoding("EUC-KR");
        writer.setEncoding(inputFileEncoding);

        CSVFileHeaderCallback csvFileHeaderCallback = new CSVFileHeaderCallback(inputItemList);
        writer.setHeaderCallback(csvFileHeaderCallback);

        return writer;
    }

    @Bean
    public Job predictJobMultiJson (Step predictStepMultiJson) {
        return this.jobBuilderFactory.get("predictJobMultiJson").listener(jobCompletionListener)
                .preventRestart()
                .start(predictStepMultiJson)
                .build();
    }

    @Bean
    @JobScope
    public Step predictStepMultiJson () {
        return stepBuilderFactory.get("predictStepMultiJson").listener(predictJsonStepExecutionListener)
                .chunk(chunkAndPageSize)
                .reader(multiJsonItemReader(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected))
                .processor(predictJSONProcess)
                .writer(outputDBInsertJson(willBeInjected))
                .listener(predictJsonItemWriterListener)
                .taskExecutor(executor())
                .build();
    }

    @Bean
    @StepScope
    public MultiResourceItemReader multiJsonItemReader (
            @Value("#{jobParameters['inputFiles']}") String inputFiles,
            @Value("#{jobParameters['inputItemList']}") String inputItemList,
            @Value("#{jobParameters['keyItems']}") String keyItems,
            @Value("#{jobParameters['inputDataFileExtension']}") String inputDataFileExtension,
            @Value("#{jobParameters['inputFileEncoding']}") String inputFileEncoding) {

        String[] inputFileList = inputFiles.split(",", - 1);

        Resource[] resources = new Resource[inputFileList.length];
        for (int i = 0; i < inputFileList.length; i++) {
            resources[i] = new FileSystemResource(inputFileList[i]);
        }

        //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
        JsonLineTokenizer jsonLineTokenizer = new JsonLineTokenizer();
        jsonLineTokenizer.setKeyItems(keyItems.split(",", - 1));
        jsonLineTokenizer.setEncoding(inputFileEncoding);
        jsonLineTokenizer.setJsonItemList(jsonItemList);
        jsonLineTokenizer.setJsonItemCode(jsonItemCode);
        jsonLineTokenizer.setJsonItemType(jsonItemType);
        jsonLineTokenizer.setJsonItemValue(jsonItemValue);

        MapFieldSetMapper objectMapper = new MapFieldSetMapper();
        objectMapper.setNames(inputItemList.split(",", - 1));
        //objectMapper.setKeys(keyItems.split(",", -1));

        JsonMapLineMapper jsonLineMapper = new JsonMapLineMapper();
        jsonLineMapper.setLineTokenizer(jsonLineTokenizer);
        jsonLineMapper.setObjectMapper(objectMapper);

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setLineMapper(jsonLineMapper);
        reader.setSaveState(Boolean.FALSE);
        reader.setExtension(inputDataFileExtension);
        reader.setEncoding(inputFileEncoding);

        MultiResourceItemReader resourceItemReader = new MultiResourceItemReader();
        resourceItemReader.setDelegate(reader);
        resourceItemReader.setResources(resources);

        return new MultiResourceItemReaderBuilder().delegate(reader).resources(resources).saveState(true).name("jsonmultireader").build();
        //		return resourceItemReader;
    }

    @Bean
    public Job predictJobJson (Step predictStepJson) {
        return this.jobBuilderFactory.get("predictJobJson").listener(jobCompletionListener)
                .preventRestart()
                .start(predictStepJson)
                .build();
    }

    @Bean
    @JobScope
    public Step predictStepJson () {
        return stepBuilderFactory.get("predictStepJson").listener(predictJsonStepExecutionListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileJSONReader(willBeInjected, willBeInjected, willBeInjected, willBeInjected, willBeInjected))
                .processor(predictJSONProcess)
                .writer(outputDBInsertJson(willBeInjected))
                .listener(predictJsonItemWriterListener)
                .taskExecutor(executor())
                .build();
    }

    @Bean
    @StepScope
    public PredictJsonItemWriterH2 outputDBInsertJson (@Value("#{jobParameters['columnCalculateYN']}") String columnCalculateYN) {

        PredictJsonItemWriterH2 predictJsonItemWriterH2 = new PredictJsonItemWriterH2();
        predictJsonItemWriterH2.setColumnCalculateYN(columnCalculateYN);
        predictJsonItemWriterH2.setMeanSquaredErrorMapKey(PredictServiceConfig.ROW_MEAN_SQUARD_ERROR_MAP_KEY);
        if ("Y".equals(columnCalculateYN)) {
            predictJsonItemWriterH2.setSquaredErrorListMapKey(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY);
            predictJsonItemWriterH2.setItemStdedProbMapKey(PredictServiceConfig.ITEM_STDED_PROB_MAP_KEY);
        }
        predictJsonItemWriterH2.setRowMeanProbMapKey(PredictServiceConfig.ROW_MEAN_PROB_MAP_KEY);
        return predictJsonItemWriterH2;

    }

    // 테스트용 table 데이터 읽어서 json 처리하는 로직으로 이상탐지
    @Bean
    public Job predictTableTestJob (Step predictTableTestStep) {
        return this.jobBuilderFactory.get("predictTableTestJob").listener(jobCompletionListener)
                .preventRestart()
                .start(predictTableTestStep)
                .build();
    }

    @Bean
    @JobScope
    public Step predictTableTestStep () {
        return stepBuilderFactory.get("predictTableTestStep").listener(predictJsonStepExecutionListener)
                .chunk(chunkAndPageSize)
                .reader(inputFileReader(willBeInjected, willBeInjected, willBeInjected, willBeInjectedINT, willBeInjected, willBeInjectedINT, willBeInjected)) // refs #1299 inputFileReader, inputFileReaderFixed의 'linesToFooterSkip' 파라미터 제거에 따른 변경
                .processor(predictTableProcess)
                .writer(outputDBInsertJson((willBeInjected)))
                .listener(predictJsonItemWriterListener)
                .taskExecutor(executor())
                .build();
    }
}
