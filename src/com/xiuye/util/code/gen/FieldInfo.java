package com.xiuye.util.code.gen;

import java.util.List;
import java.util.Objects;

import com.xiuye.util.cls.XType;

public class FieldInfo implements GenModifier, GenCoder {

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
	private String value;

	public FieldInfo() {
		modifiers = XType.list();
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return codeLine();
	}

	@Override
	public boolean addModifier(String modifier) {
		return modifiers.add(modifier);
	}

	public String combineModifiers() {
		if (modifiers.isEmpty())
			return " ";
		StringBuffer mc = new StringBuffer();
		modifiers.forEach(d -> {
			mc.append(d + " ");
		});
		return mc.toString();
	}

	public String code() {
		String tmp = XType.nvl(access,ACCESS_DEFAULT) + " " + combineModifiers() + type + " " + name;
		if (Objects.nonNull(value) && !value.isEmpty()) {
			tmp += " = " + value;
		}

		return tmp + ";";
	}

	public String codeLine() {
		return code() + "\n";
	}

}
