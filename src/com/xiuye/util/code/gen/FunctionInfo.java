package com.xiuye.util.code.gen;

import java.util.List;

import com.xiuye.util.cls.XType;

public class FunctionInfo implements GenModifier, GenParameter, GenCoder {

	public static class Parameter {

		private String type;
		private String name;

		public Parameter() {
		}

		public Parameter(String type, String name) {
			this.type = type;
			this.name = name;
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

		@Override
		public String toString() {
			return type + " " + name;
		}

	}

	public static final String ACCESS_PRIVATE = "private";
	public static final String ACCESS_DEFAULT = "";
	public static final String ACCESS_PROTECTED = "protected";
	public static final String ACCESS_PUBLIC = "public";

	public static final String STATIC_MODIFIER = "static";
	public static final String FINAL_MODIFIER = "final";

	private String access;
	private List<String> modifiers;
	private String type;
	private String name;
	private List<Parameter> parameters;
	private String functionBody;

	public FunctionInfo() {
		modifiers = XType.list();
		parameters = XType.list();
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

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
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

	public List<Parameter> getParameters() {
		return parameters;
	}

	public String getFunctionBody() {
		return functionBody;
	}

	public void setFunctionBody(String functionBody) {
		this.functionBody = functionBody;
	}

	@Override
	public String toString() {
		return codeLine();
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
	public boolean addParameter(String type, String name) {
		return parameters.add(new Parameter(type, name));
	}

	@Override
	public String combineParameters() {

		List<String> pc = XType.list();
		parameters.forEach(d -> {
			pc.add(d.toString());
		});

		return String.join(",", pc);
	}

	@Override
	public String code() {
		return XType.nvl(access,ACCESS_DEFAULT) + " " + combineModifiers() + XType.nvl(type, "") + " " + name + "(" + combineParameters() + ")"
				+ codeBlockBegin() + functionBody + codeBlockEnd();
	}

	@Override
	public String codeLine() {
		return code() + "\n";
	}

}
