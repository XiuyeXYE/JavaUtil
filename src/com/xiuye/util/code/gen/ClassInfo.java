package com.xiuye.util.code.gen;

import com.xiuye.util.cls.XType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
	// private String fullClassName;
	private List<FieldInfo> fields;
	private List<FunctionInfo> functions;

	private String superClasses;
	private List<String> interfaces;

	{
		importPackages = XType.list();
		modifiers = XType.list();
		fields = XType.list();
		functions = XType.list();
	}
	public ClassInfo() {
		
	}

	public ClassInfo(String simpleClassName) {
		this(null,simpleClassName);
	}

	public ClassInfo(String packageName, String simpleClassName) {
		this.setPackageName(packageName);
		this.setAccess(ClassInfo.ACCESS_PUBLIC);
//		this.addModifier(ClassInfo.FINAL_MODIFIER);
		this.setType(ClassInfo.TYPE_CLASS);
		this.setName(simpleClassName);
	}

	public String getFullName() {
		if(Objects.nonNull(packageName)&&!packageName.isEmpty()) {
			return packageName + "." + name;
		}
		else {
			return name;
		}
		
	}

	public FieldInfo addField(String type, String name) {
		FieldInfo fi = new FieldInfo();
		fi.setAccess(FieldInfo.ACCESS_PRIVATE);
//		fi.addModifier(FieldInfo.STATIC_MODIFIER);
//		fi.addModifier(FieldInfo.FINAL_MODIFIER);
		fi.setType(type);
		fi.setName(name);
		this.addField(fi);
		return fi;
	}

	//default private
	public FieldInfo addField(String type, String name, String value) {
		FieldInfo fi = new FieldInfo();
		fi.setAccess(FieldInfo.ACCESS_PRIVATE);
//		fi.addModifier(FieldInfo.STATIC_MODIFIER);
//		fi.addModifier(FieldInfo.FINAL_MODIFIER);
		fi.setType(type);
		fi.setName(name);
		fi.setValue(value);
		this.addField(fi);
		String bName = XType.firstUpperCase(name);
		addMethod("void", "set" + bName, "this." + name + "=" + name + ";", type, name);
		addMethod(type, "get" + bName, "return this." + name + ";");
		return fi;
	}

	public FunctionInfo addConstructor(String methodBody, String... params) {
		return addMethod(null, name, methodBody, params);
	}

	public void ext(String... superClasses) {
		if (superClasses.length > 0) {
			if (TYPE_CLASS.equalsIgnoreCase(this.type)) {
				this.superClasses = superClasses[0];
			} else if (TYPE_INTERFACE.equalsIgnoreCase(this.type)) {
				this.interfaces = Arrays.asList(superClasses);
			}
		}

	}

	public void impl(String... interfaces) {
		this.interfaces = Arrays.asList(interfaces);
	}

	public FunctionInfo addMethod(String returnType, String name, String methodBody, String... params) {

		if (params.length % 2 != 0) {
			throw new RuntimeException("Input params's number must be even!");
		}
		FunctionInfo fi = new FunctionInfo();
		fi.setAccess(FunctionInfo.ACCESS_PUBLIC);
//		fi.addModifier(FunctionInfo.STATIC_MODIFIER);
//		fi.addModifier(FunctionInfo.FINAL_MODIFIER);
		fi.setType(returnType);
		fi.setName(name);
		for (int i = 0; i < params.length; i += 2) {
			fi.addParameter(params[i], params[i + 1]);
		}

		fi.setFunctionBody(methodBody);
		this.addFunction(fi);
		return fi;
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
		return "package " + packageName + ";\n" + combinePackages() + "\n" + XType.nvl(access, ACCESS_DEFAULT) + " "
				+ combineModifiers() + type + " " + name + combineExtends() + "\n" + codeBlockBegin() + "\n"
				+ combineFields() + "\n" + combineFunctions() + "\n" + codeBlockEnd();
	}

	@Override
	public String codeLine() {
		return code() + "\n";
	}

	public String combineExtends() {
		StringBuffer si = new StringBuffer();
		if (TYPE_CLASS.equalsIgnoreCase(this.type)) {
			if (Objects.nonNull(superClasses) && !superClasses.isEmpty()) {
				si.append(" extends ");
				si.append(superClasses);
			}
			if (Objects.nonNull(this.interfaces) && !interfaces.isEmpty()) {
				si.append(" implements ");
				for (int i = 0; i < interfaces.size() - 1; i++) {
					si.append(interfaces.get(i) + ",");
				}
				si.append(interfaces.get(interfaces.size() - 1));
			}
		} else if (TYPE_INTERFACE.equalsIgnoreCase(this.type)) {
			if (Objects.nonNull(this.interfaces) && !interfaces.isEmpty()) {
				si.append(" extends ");
				for (int i = 0; i < interfaces.size() - 1; i++) {
					si.append(interfaces.get(i) + ",");
				}
				si.append(interfaces.get(interfaces.size() - 1));
			}
		}

		return si.toString();

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

	@Override
	public boolean importPackage(String... packageName) {

		boolean ret = false;

		for (String pack : packageName) {
			ret = addImportPackage(pack);
		}

		return ret;

	}

}
