package com.mtj.common.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mtj.common.R;
import com.google.android.material.appbar.AppBarLayout;

public class AppBarBehavior extends AppBarLayout.Behavior {

//    private ViewPager viewPager;
//    private RadioGroup radioGroup;

    private int maxScrollH = 0;
    private int recyclerViewH = 0;

    public AppBarBehavior() {
        super();
    }

    public AppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean setTopAndBottomOffset(int offset) {
        // 重写此方法，判断offset是否超过了最大的可滑动距离，如果超过就不再滑动
        if(maxScrollH <= Math.abs(offset)){
            offset = - maxScrollH;
        }
        return super.setTopAndBottomOffset(offset);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {

        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {

        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        calculateMaxScrollH(parent, abl);
        return super.onLayoutChild(parent, abl, layoutDirection);
    }
    
    // 计算最大的可滑动距离
    private void calculateMaxScrollH(CoordinatorLayout parent, AppBarLayout child){
//        RecyclerView rv = parent.findViewById(R.id.rvHome);
//        radioGroup = child.findViewById(R.id.rg_comment);

        int parentH = parent.getMeasuredHeight();
//        /**
//         * 获取AppBarLayout可滑动的最大值，得到的值：AppBarLayout的高度去掉悬浮的那块高度
//         * 正好可以保证AppBarLayout完全隐藏掉并且悬浮布局显示
//         */
        int childScrollTotalH = child.getTotalScrollRange();
//
//        MainFragmentPagerAdapter adapter = (MainFragmentPagerAdapter) viewPager.getAdapter();
//        CommentFragment fragment = (CommentFragment) adapter.getItem(0);
//        if(fragment.getRecyclerView() != null){
//            recyclerViewH = rv.getMeasuredHeight();
//        }
        // 最大可滑动距离就是AppBarLayout的高度 + 悬浮布局高度 + recyclerView的高度 - parentH
        maxScrollH = childScrollTotalH - parentH + recyclerViewH /*+ radioGroup.getMeasuredHeight()*/;
        if(maxScrollH < 0){
            maxScrollH = 0;
        }
    }
}