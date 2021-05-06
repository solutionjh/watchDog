package com.nice.datafileanomalydetection.predict.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;
import java.lang.invoke.MethodHandles;

//@Component("csvFileHeaderCallback")
public class CSVFileHeaderCallback implements FlatFileHeaderCallback {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // refs #1259 Autowired 로 사용되는 Model 제거 (2020-06-02, kty)
    private final String inputItemList;

    public CSVFileHeaderCallback (String inputItemList) {
        super();
        this.inputItemList = inputItemList;
    }


    @Override
    public void writeHeader (Writer writer) throws IOException {
        logger.debug("Input Item List in Writing Header: {}", inputItemList);
        writer.write(inputItemList);
    }

}
