package com.Tirax.plasma;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.Tirax.plasma.R;


public class TextFont extends TextView {
    public TextFont(Context context) {
      super(context);
      Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/myfont.ttf"); 
      this.setTypeface(face); 
    }

    public TextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/myfont.ttf");
        this.setTypeface(face);
    }

    public TextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/myfont.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
    private void init(AttributeSet attrs) {
		if (attrs!=null) {
			 TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextFont);
			 String fontName = a.getString(R.styleable.TextFont_font_name);
			 if (fontName!=null) {
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
				 setTypeface(myTypeface);
			 }
			 a.recycle();
		}
	}

}