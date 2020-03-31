package com.xiuye.util.cls;


public class XMeta {
	public static Caller caller(int functionLayer) {
		StackTraceElement[] ss = Thread.currentThread().getStackTrace();
		Caller c = null;
		if(ss.length > functionLayer) {
			c = new Caller();
			c.setClassName(ss[functionLayer].getClassName());
			c.setFileName(ss[functionLayer].getFileName());
			c.setLineNumber(ss[functionLayer].getLineNumber());
			c.setMethodName(ss[functionLayer].getMethodName());
		}
		return c;
	}
	public static class Caller{
		private String className;
		private String methodName;
		private String fileName;
		private int lineNumber;
		private String classLoaderName;
		private String moduleName;
		private String moduleVersion;
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public int getLineNumber() {
			return lineNumber;
		}
		public void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}
		public String getClassLoaderName() {
			return classLoaderName;
		}
		public void setClassLoaderName(String classLoaderName) {
			this.classLoaderName = classLoaderName;
		}
		public String getModuleName() {
			return moduleName;
		}
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}
		public String getModuleVersion() {
			return moduleVersion;
		}
		public void setModuleVersion(String moduleVersion) {
			this.moduleVersion = moduleVersion;
		}
		@Override
		public String toString() {
			return "Caller [className=" + className + ", methodName=" + methodName + ", fileName=" + fileName
					+ ", lineNumber=" + lineNumber + ", classLoaderName=" + classLoaderName + ", moduleName="
					+ moduleName + ", moduleVersion=" + moduleVersion + "]";
		}
		
	}
}
