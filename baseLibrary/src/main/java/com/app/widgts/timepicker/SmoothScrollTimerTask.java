package com.app.widgts.timepicker;

import java.util.TimerTask;

/**
 * @TODO<平滑滾動的實現>
 * @author 小嵩
 */
final class SmoothScrollTimerTask extends TimerTask {

    int realTotalOffset;
    int realOffset;
    int offset;
    final WheelView loopView;

    SmoothScrollTimerTask(WheelView loopview, int offset) {
        this.loopView = loopview;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滾動的範圍細分成10小份，按10小份單位來重繪
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            loopView.cancelFuture();
            loopView.handler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset;

            //這里如果不是循環模式，則點擊空白位置需要回滾，不然就會出現選到－1 item的 情況
            if (!loopView.isLoop) {
                float itemHeight = loopView.itemHeight;
                float top = (float) (-loopView.initPosition) * itemHeight;
                float bottom = (float) (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight;
                if (loopView.totalScrollY <= top||loopView.totalScrollY >= bottom) {
                    loopView.totalScrollY = loopView.totalScrollY - realOffset;
                    loopView.cancelFuture();
                    loopView.handler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            loopView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
