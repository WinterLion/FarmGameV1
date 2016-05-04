package qcox.tacoma.uw.edu.farmgame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import qcox.tacoma.uw.edu.farmgame.data.PlayerValues;
import qcox.tacoma.uw.edu.farmgame.items.ItemContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
    }

    public static final String ARG_POSITION = "POSITION" ;
    //private int mCurrentPosition = -1;
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateCourseItemView((int)args.getInt(ARG_POSITION));
        }
    }
    public void updateCourseItemView(int pos) {
        if (ItemContent.ITEMS.get(pos).image != -1) {
            ImageView itemImageView = (ImageView) getActivity().findViewById(R.id.item_detail_image);
            itemImageView.setImageResource(ItemContent.ITEMS.get(pos).image);
        }
        TextView moneyTextView = (TextView) getActivity().findViewById(R.id.item_detail_money_amount);
        int money = PlayerValues.mMoney;
        moneyTextView.setText(String.valueOf(money));
        TextView itemNameTextView = (TextView) getActivity().findViewById(R.id.item_detail_name);
        itemNameTextView.setText(ItemContent.ITEMS.get(pos).name);
        TextView itemShortDescTextView = (TextView) getActivity().findViewById(R.id.item_detail_short_desc);
        itemShortDescTextView.setText(ItemContent.ITEMS.get(pos).description);
        ((TextView) getActivity().findViewById(R.id.item_detail_amount))
                .setText("You currently have: " + ItemContent.ITEMS.get(pos).quantity);
        TextView itemLongDescTextView = (TextView) getActivity().findViewById(R.id.item_detail_long_desc);
        itemLongDescTextView.setText("");
        TextView itemBuyCostTextView = (TextView) getActivity().findViewById(R.id.item_detail_buy_cost);
        itemBuyCostTextView.setText("Cost to Buy: " + ItemContent.ITEMS.get(pos).buyCost);
        TextView itemSellCostTextView = (TextView) getActivity().findViewById(R.id.item_detail_sell_cost);
        itemSellCostTextView.setText("Cost to Sell: " + ItemContent.ITEMS.get(pos).sellCost);
//        ((TextView) getActivity().findViewById(R.id.item_detail_pos)).setText(pos);

    }


}
