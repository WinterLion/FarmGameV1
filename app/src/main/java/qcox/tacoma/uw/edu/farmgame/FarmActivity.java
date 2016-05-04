package qcox.tacoma.uw.edu.farmgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import qcox.tacoma.uw.edu.farmgame.data.PlayerValues;
import qcox.tacoma.uw.edu.farmgame.data.PlayerValuesDB;

public class FarmActivity extends AppCompatActivity implements FarmFragment.OnFragmentInteractionListener,
        SiloFragment.OnFragmentInteractionListener, ItemListFragment.OnListFragmentInteractionListener {

    int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
    }

    @Override
    public void onStart(){
        super.onStart();
        if (findViewById(R.id.fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FarmFragment())
                    .commit();

        }
        PlayerValuesDB theTask = new PlayerValuesDB();
        theTask.GetUserMoney(this);
    }

    //this is when the high score button is pressed and starts the high score activity.
    public void viewHighScores(View v) {
        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(intent);
    }

    //this starts the itemFragment which contains a list of items and how much the player has.
    public void startSiloList(View v) {
        ItemListFragment itemFragment = new ItemListFragment();
        Bundle args = new Bundle();
        itemFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, itemFragment)
                .addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    //this is when an item has its buy button pressed.
    public void buyInventoryItems(View v) {
        int cost = PlayerValues.getPlantItems(mPos).buyCost;
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
            PlayerValues.addItemAmount(PlayerValues.getPlantItems(mPos).name, numToBuy);
            PlayerValues.mMoney -= cost * numToBuy;
            PlayerValuesDB theTask = new PlayerValuesDB();
            theTask.UpdateUserMoney(this, PlayerValues.mMoney);
            text = "You just bought " + numToBuy + " " + PlayerValues.getPlantItems(mPos).name
                    + " for " + cost * numToBuy + " coins.";
        } else {
            text = "You don't have enough money!";
        }

        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();
    }

    //this is when an item has its sell button pressed.
    public void sellInventoryItems(View v) {
        int cost = PlayerValues.getPlantItems(mPos).sellCost;
        int numToSell = 0;
        EditText theNumToSell = (EditText)findViewById(R.id.num_to_sell);
        if (theNumToSell != null) {
            String a = theNumToSell.getText().toString();
            if (!a.isEmpty()) {
                numToSell = Math.abs(Integer.valueOf(a));
            }
        }
        CharSequence text;
        int currentAmount = PlayerValues.getItemAmount(PlayerValues.getPlantItems(mPos).name);
        if (numToSell < 1) {
            text= "You must pick a valid value for the amount";
        } else if (numToSell <= currentAmount) {
            PlayerValues.setItemAmount(PlayerValues.getPlantItems(mPos).name, currentAmount - numToSell);
            PlayerValues.mMoney += cost * numToSell;
            PlayerValuesDB theTask = new PlayerValuesDB();
            theTask.UpdateUserMoney(this, PlayerValues.mMoney);
            text = "You just sold " + numToSell + " " + PlayerValues.getPlantItems(mPos).name
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

    //this is when an inventory item is selected and the details need to be viewed.
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
