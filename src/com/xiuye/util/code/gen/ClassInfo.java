package com.xiuye.util.code.gen;

import java.util.List;

import com.xiuye.util.cls.XType;

public class ClassInfo implements GenImportPackages, GenModifier, GenField, GenFcuntion, GenCoder {

	public static final String ACCESS_PRIVATE = "private";
	public static final String ACCESS_DEFAULT = "";
	public static final String ACCESS_PROTECTED = "protected";
	public static final String ACCESS_PUBLIC = "public";

	public static final String STATIC_MODIFIER = "static";
	public static final String FINAL_MODIFIER = "final";
	public static final String TYPE_INTERFACE = "interface";
	public static final String TYPE_CLASS = "class";

	private String packageName;
	private List<String> importPackages;
	private String access;
	private List<String> modifiers;
	private String type;
	private String name;
//	private String fullClassName;
	private List<FieldInfo> fields;
	private List<FunctionInfo> functions;

	public ClassInfo() {
		importPackages = XType.list();
		modifiers = XType.list();
		fields = XType.list();
		functions = XType.list();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getImportPackages() {
		return importPackages;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FieldInfo> getFields() {
		return fields;
	}

	public boolean addField(FieldInfo field) {
		return this.fields.add(field);
	}

	public List<FunctionInfo> getFunctions() {
		return functions;
	}

	public boolean addFunction(FunctionInfo func) {
		return this.functions.add(func);
	}

	@Override
	public String toString() {
		return codeLine();
	}

	@Override
	public String code() {
		return "package " + packageName + ";\n" + combinePackages() + "\n" + XType.nvl(access,ACCESS_DEFAULT) + " " + combineModifiers() + type
				+ " " + name + "\n" + codeBlockBegin() + "\n" + combineFields() + "\n" + combineFunctions() + "\n"
				+ codeBlockEnd();
	}

	@Override
	public String codeLine() {
		return code() + "\n";
	}

	@Override
	public String combineFunctions() {
		if (functions.isEmpty())
			return "";
		StringBuffer fc = new StringBuffer();
		functions.forEach(d -> {
			fc.append("\t" + d.codeLine());
		});
		return fc.toString();
	}

	@Override
	public String combineFields() {
		if (fields.isEmpty())
			return "";
		StringBuffer fc = new StringBuffer();
		fields.forEach(d -> {
			fc.append("\t" + d.codeLine());
		});
		return fc.toString();
	}

	@Override
	public boolean addModifier(String modifier) {
		return modifiers.add(modifier);
	}

	@Override
	public String combineModifiers() {
		if (modifiers.isEmpty())
			return " ";
		StringBuffer mc = new StringBuffer();
		modifiers.forEach(d -> {
			mc.append(d + " ");
		});
		return mc.toString();
	}

	@Override
	public String combinePackages() {
		if (importPackages.isEmpty())
			return "";
		StringBuffer pc = new StringBuffer();
		importPackages.forEach(d -> {
			pc.append("import " + d + ";\n");
		});
		return pc.toString();
	}

	@Override
	public boolean addImportPackage(String packageName) {
		return importPackages.add(packageName);
	}

}
