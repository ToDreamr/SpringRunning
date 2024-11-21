package com.pray.build;

import java.util.*;

/**
 * <p>
 * 闭包验证测试
 * </p>
 *
 * &#064;author:  renyp
 * &#064;date:  2024/11/11 19:18
 */
public class ClosureTest {
    public static void main(String[] args) {

        List<String> strs = Arrays.asList("1", "2", "3");

        System.out.println(SensitiveWordContexts.getInstance()
                .initContext(new SensitiveWordInit() {
                    @Override
                    void init(Pipeline pipeline) {
                        for (String str : strs) {
                            pipeline.addLast(str);
                        }
                    }
                })
                .check("4"));

    }


    static class SensitiveWordContexts {
        private SensitiveWordContexts() {
        }

        private static final SensitiveWordContext INSTANCE = new SensitiveWordContext();

        public static SensitiveWordContexts getInstance() {
            return new SensitiveWordContexts();
        }

        public SensitiveWordContexts initContext(SensitiveWordCheck sensitiveWordPrint) {
            INSTANCE.initSensitiveWordPrint(sensitiveWordPrint);
            return this;
        }

        public boolean check(String word) {
            return INSTANCE.getSensitiveWordPrint().check(INSTANCE, word);
        }
    }

    static class SensitiveWordContext {

        private final String prefix = "---";

        public SensitiveWordCheck getSensitiveWordPrint() {
            return sensitiveWordPrint;
        }

        public void initSensitiveWordPrint(SensitiveWordCheck sensitiveWordPrint) {
            this.sensitiveWordPrint = sensitiveWordPrint;
        }

        public String prefixStr() {
            return this.prefix;
        }

        SensitiveWordCheck sensitiveWordPrint;
    }

    abstract static class SensitiveWordInit implements SensitiveWordCheck {

        abstract void init(Pipeline pipeline);

        public boolean check(SensitiveWordContext sensitiveContext, String word) {
            Pipeline pipeline = new Pipeline();
            this.init(pipeline);
            for (String str : pipeline.list()) {
                if (!str.equals(word)) {
                    continue;
                }
                return true;
            }
            return false;
        }
    }

    interface SensitiveWordCheck {
        boolean check(SensitiveWordContext sensitiveContext, String word);
    }


    static class Pipeline {
        private final List<String> queue;

        public Pipeline() {
            this.queue = new LinkedList<>();
        }

        public void addLast(String str) {
            this.queue.add(str);
        }

        public String poll() {
            // NPE
            if (this.queue.size() > 0) {
                return this.queue.remove(0);
            }
            return null;
        }

        public List<String> list() {
            List<String> view = new ArrayList<>(this.queue.size());
            for (int i = 0; i < this.queue.size(); i++) {
                view.add("a");
            }
            Collections.copy(view, this.queue);
            return view;
        }
    }
}
