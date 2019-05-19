package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;

public class ContactPickerListener implements View.OnClickListener {

    private final int PICK_CONTACT=20;
    private Context context;

    public ContactPickerListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        ((Activity)context).startActivityForResult(i, PICK_CONTACT);

    }
}
