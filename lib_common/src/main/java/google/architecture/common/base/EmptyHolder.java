package google.architecture.common.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class EmptyHolder extends RecyclerView.ViewHolder {

    public EmptyHolder(ViewDataBinding binding) {
        super(binding.getRoot());
    }
}
