package com.xiuye.test.charts;

import org.junit.Test;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Symbol;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.util.EnhancedOption;

public class ChartsTest {

	@Test
	public void test() {
		// 例子：http://echarts.baidu.com/doc/example/line.html
		EnhancedOption option = new EnhancedOption();
		option.tooltip().trigger(Trigger.axis);
		option.legend("邮件营销", "联盟广告", "直接访问", "搜索引擎");
		option.toolbox().show(true);
		option.calculable(true);
		option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
		option.yAxis(new ValueAxis());
		option.series(new Line().smooth(true).name("邮件营销").stack("总量").symbol(Symbol.none).data(120, 132, 301, 134,
				new LineData(90, Symbol.droplet, 5), 230, 210));

		// 实现不了js的这个效果
		// line.itemStyle.normal.areaStyle = new AreaStyle();
		LineData lineData = new LineData(201, Symbol.star, 15);
		lineData.itemStyle().normal().label().show(true).textStyle().fontSize(20).fontFamily("微软雅黑").fontWeight("bold");
		option.series(new Line().smooth(true).name("联盟广告").stack("总量")
				.symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").symbolSize(8).data(120, 82,
						lineData, new LineData(134, Symbol.none), 190, new LineData(230, Symbol.emptypin, 8), 110));

		/*
		 * line = new Line(); line.name = "邮件营销"; line.stack = "总量"; line.symbol =
		 * Symbol.none; line.smooth = true; //实现不了js的这个效果
		 * //line.itemStyle.normal.areaStyle = new AreaStyle(); line.addData(120, 132,
		 * 301, 134,new LineData(90,Symbol.droplet,5),230,210); option.series.add(line);
		 * 
		 * line = new Line(); line.name = "邮件营销"; line.stack = "总量"; line.symbol =
		 * Symbol.none; line.smooth = true; //实现不了js的这个效果
		 * //line.itemStyle.normal.areaStyle = new AreaStyle(); line.addData(120, 132,
		 * 301, 134,new LineData(90,Symbol.droplet,5),230,210); option.series.add(line);
		 */

		option.exportToHtml("line.html");
		option.view();
	}

}
