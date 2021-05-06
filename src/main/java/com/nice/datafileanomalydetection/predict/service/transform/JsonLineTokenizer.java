package com.nice.datafileanomalydetection.predict.service.transform;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonLineTokenizer extends AbstractLineTokenizer {

    // 기본으로 설정 가능한 quotation. (기본: 쌍따옴표)
    public static final String DEFAULT_QUOTE_CHARACTER = "\"";
    // json 에서 key 역할을 하는 code.
    private String[] keyItems;
    // 필요시 encoding 설정
    private String encoding = Charset.defaultCharset().toString();
    // Token 생성시 사용할 quotation.
    private String quoteCharacter = DEFAULT_QUOTE_CHARACTER;

    //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
    // json 에서 데이터가 담겨 있는 부분의 key
    private String jsonItemList;
    private String jsonItemCode;
    private String jsonItemType;
    private String jsonItemValue;

    public JsonLineTokenizer () {
        super();
    }

    public JsonLineTokenizer (String jsonItemList) {
        super();
        this.jsonItemList = jsonItemList;
    }

    public void setKeyItems (String[] keyItems) {
        this.keyItems = keyItems;
    }

    /**
     * Quotation 문자열을 설정한다.
     * quotation 문자는 CSV 파일에서 여러 개의 delimiter가 하나의 셀(Token)에 들어갈 경우,
     * 이를 하나의 Token 으로 묶어주기 위해 사용된다.
     * refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 변수처리, kjh, 2020-11-19
     *
     * @param quoteCharacter : quotation으로 사용 할 문자열
     */
    public final void setQuoteCharacter (String quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
    }

    public void setEncoding (String encoding) {
        this.encoding = encoding;
    }

    //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
    public void setJsonItemList (String jsonItemList) {
        this.jsonItemList = jsonItemList;
    }

    public void setJsonItemCode (String jsonItemCode) {
        this.jsonItemCode = jsonItemCode;
    }

    public void setJsonItemType (String jsonItemType) {
        this.jsonItemType = jsonItemType;
    }

    public void setJsonItemValue (String jsonItemValue) {
        this.jsonItemValue = jsonItemValue;
    }


    public List<String> doTokenize (String line) {
        List<String> tokens = new ArrayList<String>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(line);

        for (int i = 0; i < keyItems.length; i++) {
            tokens.add(element.getAsJsonObject().get(keyItems[i]).getAsString());
        }

        JsonArray allItems = element.getAsJsonObject().get(this.jsonItemList).getAsJsonArray();
        for (int i = 0; i < allItems.size(); i++) {
            JsonObject itemJson = allItems.get(i).getAsJsonObject();
            String itemType = itemJson.get(jsonItemType).getAsString();
            String itemCode = itemJson.get(jsonItemCode).getAsString();
            String itemValue = itemJson.get(jsonItemValue).getAsString();
            tokens.add(itemValue);
        }

        return tokens;

    }

    //@Override
    public Map<String, String> tokenizeJson (String line) {
        if (line == null) {
            line = "";
        }

        Map<String, String> tokens = doTokenizeJson(line);

        return tokens;
    }

    /**
     * JSON String 형태의 line을 token으로 만든다.
     * refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 변수처리, kjh, 2020-11-19
     *
     * @param line : ItemReader에서 읽어들인 Json line
     * @return List<String> : tokens
     */
    public Map<String, String> doTokenizeJson (String line) {
        ConcurrentHashMap<String, String> tokens = new ConcurrentHashMap<>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(line);

        for (int i = 0; i < keyItems.length; i++) {
            tokens.put(keyItems[i], element.getAsJsonObject().get(keyItems[i]).getAsString());
        }

        JsonArray allItems = element.getAsJsonObject().get(this.jsonItemList).getAsJsonArray();
        for (int i = 0; i < allItems.size(); i++) {
            JsonObject itemJson = allItems.get(i).getAsJsonObject();
            String itemCode = itemJson.get(jsonItemCode).getAsString();
            String itemValue = itemJson.get(jsonItemValue).getAsString();
            tokens.put(itemCode, itemValue);
        }

        return tokens;
    }

}
