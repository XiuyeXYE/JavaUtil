package com.xiuye.util.code.gen;

public interface GenCoder {

    String code();

    String codeLine();

    default String codeBlockBegin() {
        return "{";
    }

    default String codeBlockEnd() {
        return "}";
    }
}
