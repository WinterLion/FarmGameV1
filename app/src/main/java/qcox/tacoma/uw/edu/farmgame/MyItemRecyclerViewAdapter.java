package qcox.tacoma.uw.edu.farmgame;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import qcox.tacoma.uw.edu.farmgame.ItemFragment.OnListFragmentInteractionListener;
import qcox.tacoma.uw.edu.farmgame.items.ItemContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ItemContent.FarmItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ItemContent.FarmItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<ItemContent.FarmItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        if (mValues.get(position).image != -1) {
            holder.mItemImageView.setImageResource(mValues.get(position).image);
        }

        holder.mNameView.setText(mValues.get(position).name);
        holder.mAmountView.setText("You Have: " + mValues.get(position).quantity);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mItemImageView;
        public final TextView mNameView;
        public final TextView mAmountView;
        public ItemContent.FarmItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = (ImageView) view.findViewById(R.id.image);
            mNameView = (TextView) view.findViewById(R.id.name);
            mAmountView = (TextView) view.findViewById(R.id.amount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
