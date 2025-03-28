package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Использование:  Main <values> <tests> <report>");
            return;
        }

        String valuesFile = args[0];
        String testsFile = args[1];
        String reportFile = args[2];

        try {
            List<Value> values = readValues(valuesFile);
            Map<Integer, String> valueMap = createValueMap(values);

            TestStructure testStructure = readTests(testsFile);
            fillValues(testStructure, valueMap);
            writeReport(reportFile, testStructure);
            System.out.println("Отчет успешно сгенерирован: " + reportFile);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static List<Value> readValues(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ValueStructure valueStructure = objectMapper.readValue(new File(filePath), ValueStructure.class);
        return valueStructure.getValues();
    }

    private static TestStructure readTests(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), TestStructure.class);
    }

    private static void fillValues(TestStructure testStructure, Map<Integer, String> valueMap) {
        if (testStructure.getTests() != null) {
            for (Test test : testStructure.getTests()) {
                fillTestValues(test, valueMap);
            }
        }
    }

    private static void fillTestValues(Test test, Map<Integer, String> valueMap) {
        if (valueMap.containsKey(test.getId())) {
            test.setValue(valueMap.get(test.getId()));
        } else {
            test.setValue("Value not found");
        }


        if (test.getValues() != null) {
            for (Test subTest : test.getValues()) {
                fillTestValues(subTest, valueMap);
            }
        }
    }

    private static Map<Integer, String> createValueMap(List<Value> values) {
        Map<Integer, String> valueMap = new HashMap<>();
        for (Value value : values) {
            valueMap.put(value.getId(), value.getValue());
        }
        return valueMap;
    }

    private static void writeReport(String filePath, TestStructure testStructure) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), testStructure);
    }


    static class Value {
        private int id;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class ValueStructure {
        private List<Value> values;

        public List<Value> getValues() {
            return values;
        }

        public void setValues(List<Value> values) {
            this.values = values;
        }
    }

    static class TestStructure {
        private List<Test> tests;

        public List<Test> getTests() {
            return tests;
        }

        public void setTests(List<Test> tests) {
            this.tests = tests;
        }
    }

    static class Test {
        private int id;
        private String title;
        private String value;
        private List<Test> values; // Для вложенных тестов

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<Test> getValues() {
            return values;
        }

            public void setValues(List<Test> values) {
                this.values = values;
            }
        }
    }
