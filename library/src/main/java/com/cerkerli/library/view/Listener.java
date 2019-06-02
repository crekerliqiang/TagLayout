package com.cerkerli.library.view;

public interface Listener {
    /**
     * TagLayout  触摸监听
     */
    interface TagLayoutClickListener {
        void onClick(int id,String text ,boolean isSelected);
    }

    /**
     * TagView 触摸监听
     */
    interface TagViewClickListener {
        void onClick(String text,boolean isSelected);
    }


}
