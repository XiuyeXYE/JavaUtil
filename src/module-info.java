/**
 * 
 */
/**
 * @author Administrator
 *
 */
module Util {
	exports com.xiuye.util.time/* to Java10*/;
	exports com.xiuye.util.log;
	exports com.xiuye.util.cls;
	requires static junit;
	requires static gson;
	opens com.xiuye.util.test.time to junit;
}