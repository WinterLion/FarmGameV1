package qcox.tacoma.uw.edu.farmgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import qcox.tacoma.uw.edu.farmgame.data.PlayerValues;
import qcox.tacoma.uw.edu.farmgame.items.ItemContent;

public class FarmActivity extends AppCompatActivity implements FarmFragment.OnFragmentInteractionListener,
        SiloFragment.OnFragmentInteractionListener, ItemFragment.OnListFragmentInteractionListener {

    int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);

//        final Button button_HighScore = (Button) findViewById(R.id.button_goToHighScore);
//        if (button_HighScore != null){
//            button_HighScore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
//                    startActivity(intent);
//                }
//            });
//        }
    }

    @Override
    public void onStart(){
        super.onStart();
        if (findViewById(R.id.fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FarmFragment())
                    .commit();

        }
    }

    public void viewHighScores(View v) {
        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(intent);
    }

    public void startSiloList(View v) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle args = new Bundle();
        itemFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, itemFragment)
                .addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void buyInventoryItems(View v) {
        int cost = ItemContent.ITEMS.get(mPos).buyCost;
        int numToBuy = 0;
        EditText theNumToBuy = (EditText)findViewById(R.id.num_to_buy);
        if (theNumToBuy != null) {
            String a = theNumToBuy.getText().toString();
            if (!a.isEmpty()) {
                numToBuy = Math.abs(Integer.valueOf(a));
            }
        }
        CharSequence text;
        if (numToBuy < 1) {
            text= "You must pick a valid value for the amount";
        } else if (cost * numToBuy <= PlayerValues.mMoney) {
            ItemContent.ITEMS.get(mPos).quantity += numToBuy;
            PlayerValues.mMoney -= cost * numToBuy;
            text = "You just bought " + numToBuy + " " + ItemContent.ITEMS.get(mPos).name
                    + " for " + cost * numToBuy + " coins.";
        } else {
            text = "You don't have enough money!";
        }

        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();
    }

    public void sellInventoryItems(View v) {
        int cost = ItemContent.ITEMS.get(mPos).sellCost;
        int numToSell = 0;
        EditText theNumToSell = (EditText)findViewById(R.id.num_to_sell);
        if (theNumToSell != null) {
            String a = theNumToSell.getText().toString();
            if (!a.isEmpty()) {
                numToSell = Math.abs(Integer.valueOf(a));
            }
        }
        CharSequence text;
        if (numToSell < 1) {
            text= "You must pick a valid value for the amount";
        } else if (numToSell <= ItemContent.ITEMS.get(mPos).quantity) {
            ItemContent.ITEMS.get(mPos).quantity -= numToSell;
            PlayerValues.mMoney -= cost * numToSell;
            text = "You just sold " + numToSell + " " + ItemContent.ITEMS.get(mPos).name
                    + " for " + cost * numToSell + " coins.";
        } else {
            text = "You don't have enough of that item!";
        }

        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(int position) {
        mPos = position;
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putInt(itemDetailFragment.ARG_POSITION, position);
        itemDetailFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, itemDetailFragment)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
