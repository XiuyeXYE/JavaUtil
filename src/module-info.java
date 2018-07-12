/**
 * 
 */
/**
 * @author Administrator
 *
 */
module Util {
	exports com.xiuye.util.time/* to Java10*/;
	requires junit;
	opens com.xiuye.util.test.time to junit;
}