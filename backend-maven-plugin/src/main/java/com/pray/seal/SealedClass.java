package com.pray.seal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SealedClass
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 20:38
 */
public class SealedClass {
    public boolean checkedText(String text){
        SensitiveWords sensitiveWords = new SensitiveWords();
        List<String> blackWords = List.of("傻逼", "他妈的", "草");
        sensitiveWords.setSensitiveWords(blackWords);
        return sensitiveWords.isSensitive(text);
    }
    protected static class SensitiveWords{
        List<String> sensitiveWords;
        public boolean isSensitive(String text){
            return false;
        }

        public void setSensitiveWords(List<String> sensitiveWords) {
            this.sensitiveWords = sensitiveWords;
        }
    }
}
