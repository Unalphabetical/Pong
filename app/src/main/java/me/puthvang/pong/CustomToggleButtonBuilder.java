package me.puthvang.pong;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class CustomToggleButtonBuilder {
    
    Context context;
    
    ToggleButton toggleButton;

    public CustomToggleButtonBuilder(Context context) {
        this.context = context;
        this.toggleButton = new ToggleButton(this.context);
    }

    public CustomToggleButtonBuilder minWidth(int minWidth) {
        this.toggleButton.setMinWidth(minWidth);
        return this;
    }

    public CustomToggleButtonBuilder minimumWidth(int minimumWidth) {
        this.toggleButton.setMinimumWidth(minimumWidth);
        return this;
    }

    public CustomToggleButtonBuilder minHeight(int minHeight) {
        this.toggleButton.setMinHeight(minHeight);
        return this;
    }

    public CustomToggleButtonBuilder minimumHeight(int minimumHeight) {
        this.toggleButton.setMinimumHeight(minimumHeight);
        return this;
    }

    public CustomToggleButtonBuilder x(int x) {
        this.toggleButton.setX(x);
        return this;
    }

    public CustomToggleButtonBuilder y(int y) {
        this.toggleButton.setY(y);
        return this;
    }

    public CustomToggleButtonBuilder backgroundColor(int backgroundColor) {
        this.toggleButton.setBackgroundColor(backgroundColor);
        return this;
    }

    public CustomToggleButtonBuilder textColor(int textColor) {
        this.toggleButton.setTextColor(textColor);
        return this;
    }

    public CustomToggleButtonBuilder textOn(CharSequence textOn){
        this.toggleButton.setTextOn(textOn);
        return this;
    }

    public CustomToggleButtonBuilder textOff(CharSequence textOff){
        this.toggleButton.setTextOff(textOff);
        return this;
    }
    
    public CustomToggleButtonBuilder checked(boolean checked){
        this.toggleButton.setChecked(checked);
        return this;
    }
    
    public CustomToggleButtonBuilder layoutParams(ViewGroup.LayoutParams params){
        this.toggleButton.setLayoutParams(params);
        return this;
    }

    public CustomToggleButtonBuilder textSize(int size){
        this.toggleButton.setTextSize(size);
        return this;
    }

    public ToggleButton build(){
        return this.toggleButton;
    }
    
}