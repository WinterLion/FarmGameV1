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
import qcox.tacoma.uw.edu.farmgame.items.PlantItems;


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
            updateItemView((int)args.getInt(ARG_POSITION));
        }
    }
    public void updateItemView(int pos) {
        PlantItems aPlantItem = PlayerValues.getPlantItems(pos);
        if (aPlantItem.imageResourceIndex != -1) {
            ImageView itemImageView = (ImageView) getActivity().findViewById(R.id.item_detail_image);
            itemImageView.setImageResource(aPlantItem.imageResourceIndex);
        }
        TextView moneyTextView = (TextView) getActivity().findViewById(R.id.item_detail_money_amount);
        int money = PlayerValues.mMoney;
        moneyTextView.setText(String.valueOf(money));
        TextView itemNameTextView = (TextView) getActivity().findViewById(R.id.item_detail_name);
        itemNameTextView.setText(aPlantItem.name);
        TextView itemShortDescTextView = (TextView) getActivity().findViewById(R.id.item_detail_short_desc);
        itemShortDescTextView.setText(aPlantItem.description);
        ((TextView) getActivity().findViewById(R.id.item_detail_amount))
                .setText("You currently have: " + PlayerValues.getItemAmount(aPlantItem.name));
        TextView itemLongDescTextView = (TextView) getActivity().findViewById(R.id.item_detail_long_desc);
        itemLongDescTextView.setText("");
        TextView itemBuyCostTextView = (TextView) getActivity().findViewById(R.id.item_detail_buy_cost);
        itemBuyCostTextView.setText("Cost to Buy: " + aPlantItem.buyCost);
        TextView itemSellCostTextView = (TextView) getActivity().findViewById(R.id.item_detail_sell_cost);
        itemSellCostTextView.setText("Cost to Sell: " + aPlantItem.sellCost);
//        ((TextView) getActivity().findViewById(R.id.item_detail_pos)).setText(pos);

    }


}
