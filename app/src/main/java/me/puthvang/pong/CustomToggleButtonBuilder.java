package me.puthvang.pong;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class CustomToggleButtonBuilder {
    
    Context context;
    
    ToggleButton toggleButton;

    //// The constructor to make the buttons easier
    public CustomToggleButtonBuilder(Context context) {
        this.context = context;
        this.toggleButton = new ToggleButton(this.context);
    }

    //// Set the min width of the toggle button
    public CustomToggleButtonBuilder minWidth(int minWidth) {
        this.toggleButton.setMinWidth(minWidth);
        return this;
    }

    //// Set the minimum width of the toggle button
    public CustomToggleButtonBuilder minimumWidth(int minimumWidth) {
        this.toggleButton.setMinimumWidth(minimumWidth);
        return this;
    }

    //// Set the min height of the toggle button
    public CustomToggleButtonBuilder minHeight(int minHeight) {
        this.toggleButton.setMinHeight(minHeight);
        return this;
    }

    //// Set the minimum height of the toggle button
    public CustomToggleButtonBuilder minimumHeight(int minimumHeight) {
        this.toggleButton.setMinimumHeight(minimumHeight);
        return this;
    }

    //// Set the x position of the toggle button
    public CustomToggleButtonBuilder x(int x) {
        this.toggleButton.setX(x);
        return this;
    }

    //// Set the y position of the toggle button
    public CustomToggleButtonBuilder y(int y) {
        this.toggleButton.setY(y);
        return this;
    }

    //// Set the background color of the toggle button
    public CustomToggleButtonBuilder backgroundColor(int backgroundColor) {
        this.toggleButton.setBackgroundColor(backgroundColor);
        return this;
    }

    //// Set the text color of the toggle button
    public CustomToggleButtonBuilder textColor(int textColor) {
        this.toggleButton.setTextColor(textColor);
        return this;
    }

    //// Set the text of the button when it is on or checked
    public CustomToggleButtonBuilder textOn(CharSequence textOn){
        this.toggleButton.setTextOn(textOn);
        return this;
    }

    //// Set the text of the button when it is off or not checked
    public CustomToggleButtonBuilder textOff(CharSequence textOff){
        this.toggleButton.setTextOff(textOff);
        return this;
    }

    //// Set the toggle button to be checked or not
    public CustomToggleButtonBuilder checked(boolean checked){
        this.toggleButton.setChecked(checked);
        return this;
    }

    //// Set the layout parameters of the toggle button
    public CustomToggleButtonBuilder layoutParams(ViewGroup.LayoutParams params){
        this.toggleButton.setLayoutParams(params);
        return this;
    }

    //// Set the text size of the toggle button
    public CustomToggleButtonBuilder textSize(int size){
        this.toggleButton.setTextSize(size);
        return this;
    }

    //// Build the toggle button and return the toggle button object
    public ToggleButton build(){
        return this.toggleButton;
    }
    
}