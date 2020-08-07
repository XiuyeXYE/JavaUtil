package com.xiuye.util.code.gen;

public interface GenImportPackages {

	String combinePackages();

	boolean addImportPackage(String packageName);

	boolean importPackage(String... packageName);

}
