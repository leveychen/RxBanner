package cn.levey.bannerlib.indicator.draw;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.MotionEvent;

import cn.levey.bannerlib.indicator.animation.data.Value;
import cn.levey.bannerlib.indicator.draw.controller.DrawController;
import cn.levey.bannerlib.indicator.draw.controller.MeasureController;
import cn.levey.bannerlib.indicator.draw.data.Indicator;

public class DrawManager {

	private Indicator indicator;
	private DrawController drawController;
	private MeasureController measureController;
//	private AttributeController attributeController;

	public DrawManager(Indicator indicator) {
		this.indicator = indicator;
		this.drawController = new DrawController(indicator);
		this.measureController = new MeasureController();
		//this.attributeController = new AttributeController(indicator);
	}

	@NonNull
	public Indicator indicator() {
		if (indicator == null) {
			indicator = new Indicator();
		}

		return indicator;
	}

	public void setIndicator(Indicator indicator){
		this.indicator = indicator;
	}

	public void setClickListener(@Nullable DrawController.ClickListener listener) {
		drawController.setClickListener(listener);
	}

	public void touch(@Nullable MotionEvent event) {
		drawController.touch(event);
	}

	public void updateValue(@Nullable Value value) {
		drawController.updateValue(value);
	}

	public void draw(@NonNull Canvas canvas) {
		drawController.draw(canvas);
	}

	public Pair<Integer, Integer> measureViewSize(int widthMeasureSpec, int heightMeasureSpec) {
		return measureController.measureViewSize(indicator, widthMeasureSpec, heightMeasureSpec);
	}

//	public void initAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
//		attributeController.init(context, attrs);
//	}
}
