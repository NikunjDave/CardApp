package com.yuyakaido.android.cardstackview.internal;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;

public class CardStackDataObserver extends RecyclerView.AdapterDataObserver {

    private final RecyclerView recyclerView;

    public CardStackDataObserver(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onChanged() {
        CardStackLayoutManager manager = getCardStackLayoutManager();
        manager.setTopPosition(0);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        // Do nothing
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        // Do nothing
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        // Do nothing
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        CardStackLayoutManager manager = getCardStackLayoutManager();
        int topPosition = manager.getTopPosition();
        if (manager.getItemCount() == 0) {
            manager.setTopPosition(0);
        } else if (positionStart < topPosition) {
            int diff = topPosition - positionStart;
            manager.setTopPosition(Math.min(topPosition - diff, manager.getItemCount() - 1));
        }
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        CardStackLayoutManager manager = getCardStackLayoutManager();
        manager.removeAllViews();
    }

    private CardStackLayoutManager getCardStackLayoutManager() {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof CardStackLayoutManager) {
            return (CardStackLayoutManager) manager;
        }
        throw new IllegalStateException("CardStackView must be set CardStackLayoutManager.");
    }

}
