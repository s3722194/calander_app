package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;

public class RemoveContactListener implements View.OnClickListener  {

    private final int REMOVE_CONTACT=10;

    private Context context;

    public RemoveContactListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        //opens contacts for the user to remove them
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        ((Activity)context).startActivityForResult(i, REMOVE_CONTACT);
    }
}
