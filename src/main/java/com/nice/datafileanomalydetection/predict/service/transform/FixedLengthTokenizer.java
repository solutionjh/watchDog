package com.nice.datafileanomalydetection.predict.service.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.IncorrectLineLengthException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * FixedLengthTokenizer
 *
 * @author changdae cho
 * @version 1.0
 * @see <pre>
 *      Modification Information
 *
 *   date      author         			desc
 *  ------- -------------------	-----------------------------
 *  2017.04.17  cho chang dae     init
 * </pre>
 * @since 2017.04.17
 */
public class FixedLengthTokenizer extends AbstractLineTokenizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapFieldExtractor.class);
    // 최대값 도달 여부
    boolean open = false;
    // 필드 범위의 최대값
    private int maxRange = 0;
    // 필드 length 법위
    private int[] columns;

    // 2020-04-21 kty tokenizer 에 인코딩 값 설정을 위한 변수 선언
    private String encoding;

    // 2020-04-21 kty tokenizer 에 인코딩 값을 설정하는 setter 설정

    /**
     * 사용할 encoding 종류를 설정한다.
     * ex) "utf-8"
     *
     * @param encoding
     */
    public void setEncoding (String encoding) {
        this.encoding = encoding;
    }

    /**
     * 필드의 length 범위 값을 설정한다.
     * ex) "1,4,7"
     *
     * @param columns
     */
    public void setColumns (int[] columns) {
        this.columns = columns;
        calculateMaxRange(columns);
    }

    /**
     * 필드 범위의 최대값을 계산한다.
     *
     * @param columns
     */
    private void calculateMaxRange (int[] columns) {
        if (columns == null || columns.length == 0) {
            maxRange = 0;
            return;
        }

        for (int col : columns) {
            maxRange += col;
        }
    }

    /**
     * String 형태의 line을 필드 length 범위 값(columns)을 기준으로 token으로  자른다.
     *
     * @param line : ItemReader에서 읽어들인 line
     * @return List<String> : tokens
     * @throws UnsupportedEncodingException
     */
    protected List<String> doTokenize (String line) throws UnsupportedEncodingException {
        List<String> tokens = new ArrayList<String>(columns.length);
        int lineLength;
        String token;

        // 2020-04-21 kty 파일의 encoding 값을 가지고 길이 비교
        lineLength = line.getBytes(encoding).length;

        if (lineLength == 0) {
            throw new IncorrectLineLengthException("Line length must be longer than 0", maxRange, lineLength);
        }

        if (lineLength < maxRange) {
            throw new IncorrectLineLengthException("Line is shorter than max range " + maxRange + ", line length is " + lineLength, maxRange, lineLength);
        }

        if (! open && lineLength > maxRange) {
            throw new IncorrectLineLengthException("Line is longer than max range " + maxRange + ", line length is " + lineLength, maxRange, lineLength);
        }

        int beginIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < columns.length; i++) {
            endIndex = beginIndex + columns[i];
            //token = line.substring(beginIndex,endIndex);
            //token = subByteString(line, beginIndex, columns[i]);
            // 2020-04-17 kty Layout의 Byte 값를 가지고 substring 하도록 변경.
            // 2020-04-21 kty 파일의 encoding 값을 가지고 token 만들기
            token = new String(line.getBytes(encoding), beginIndex, columns[i], encoding);
            beginIndex += columns[i];
            tokens.add(token);
        }

        return tokens;
    }

    private String subByteString (String strData, int iStartPos, int iByteLength) throws UnsupportedEncodingException {
        byte[] bytTemp = null;
        int iRealStart = 0;
        int iRealEnd = 0;
        int iLength = 0;
        int iChar = 0;

        bytTemp = strData.getBytes();
        iLength = bytTemp.length;

        for (int iIndex = 0; iIndex < iLength; iIndex++) {
            if (iStartPos <= iIndex) {
                break;
            }
            iChar = bytTemp[iIndex];
            if ((iChar > 127) || (iChar < 0)) {
                // 한글의 경우(2byte 통과처리)
                // 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
                iRealStart++;
                iIndex++;
            } else {
                // 기타 글씨(1Byte 통과처리)
                iRealStart++;
            }
        }

        iRealEnd = iRealStart;
        int iEndLength = iRealStart + iByteLength;
        for (int iIndex = iRealStart; iIndex < iEndLength; iIndex++) {
            iChar = bytTemp[iIndex];
            if ((iChar > 127) || (iChar < 0)) {
                // 한글의 경우(2byte 통과처리)
                // 한글은 2Byte이기 때문에 다음 글자는 볼것도 없이 스킵한다
                iRealEnd++;
                iIndex++;
            } else {
                // 기타 글씨(1Byte 통과처리)
                iRealEnd++;
            }
        }

        return strData.substring(iRealStart, iRealEnd);
    }


}
