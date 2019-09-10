package com.sangupta.htmlgen.tags.head;

import java.util.HashMap;
import java.util.Map;

public class StyleItem {
    private String name;
    private String value;
    private Map<String, String> items = new HashMap<>();

    public StyleItem(String pName, String pValue) {
        this(pName);
        value = pValue;
    }

    public StyleItem(String name) {
        this.name = name;
    }

    public StyleItem add(String key, int value) {
        return add(key, String.valueOf(value));
    }

    public StyleItem add(String key, String value) {
        items.put(key, value);
        return this;
    }

    public StyleItem addAll(String var) {
        String[] vs = var.split(";");
        for (String e : vs) {
            String[] ws = e.split(":");
            items.put(ws[0].trim(), ws[1].trim());
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("{");
        stringBuilder.append("\n");
        if(value != null){
            stringBuilder.append(value);
            stringBuilder.append("\n");
        }
        for (Map.Entry<String, String> e : items.entrySet()) {
            for(int i = 0; i < 2; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(e.getKey());
            stringBuilder.append(":");
            stringBuilder.append(e.getValue());
            stringBuilder.append(";");
            stringBuilder.append("\n");
        }
        stringBuilder.append("  }");
        return stringBuilder.toString();
    }
}
