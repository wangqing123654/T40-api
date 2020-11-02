package com.dongyang.ui.event;

public interface TComboBoxEvent {
    /**
     * 选择项目
     */
    public static final String SELECTED = "comboBox.selected";
    /**
     * 编辑结束
     */
    public static final String EDIT_ENTER = "comboBox.editEnter";
    /**
     * 编辑取消
     */
    public static final String EDIT_ESC = "comboBox.editESC";
    /**
     * tab键
     */
    public static final String EDIT_TAB = "comboBox.editTab";
    /**
     * shift + tab键
     */
    public static final String EDIT_SHIFT_TAB = "comboBox.editShiftTab";
}
