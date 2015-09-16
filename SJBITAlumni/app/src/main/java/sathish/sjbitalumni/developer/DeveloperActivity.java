package sathish.sjbitalumni.developer;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import sathish.sjbitalumni.R;

public class DeveloperActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

    }

    public static void Insert2Contacts(Context ctx, String nameSurname,
                                       String telephone) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nameSurname)
                .build());
        try {
            ContentProviderResult[] res = ctx.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {


        }

    }

    public void email (View view) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        String[] recipients = {"mun.sathish@gmail.com"};
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
         emailIntent.setType("text/plain");
    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, " - through SJBIT Alumni Android Application");
        startActivity(Intent.createChooser(emailIntent, "Send mail"));

    }

    public void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:7353578127"));
        startActivity(callIntent);
    }
    public void addToContact (View view) {
        Insert2Contacts(getApplicationContext(),"Sathish. V"+" (SJBIT Alumni)","7353578127");
        Toast.makeText(getBaseContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
    }

    public void website(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sathishmun.comule.com/"));
        startActivity(browserIntent);
    }

    public void fb (View view) {
        goToUrl ( "https://www.facebook.com/sathish.mun");
    }

    public void google (View view) {
        goToUrl ( "https://plus.google.com/u/0/+SathishMun/posts");
    }

    public void linkedin (View view) {
        goToUrl ( "https://www.linkedin.com/in/sathishmun");
    }

    public void appzoom (View view) {
        goToUrl ( "http://www.appszoom.com/android_developer/v-sathish-munna_pjcwm.html");
    }


    public void hackerearth (View view) {
        goToUrl ( "https://www.hackerearth.com/users/sathish.mun/");
    }

    public void instagram (View view) {
        goToUrl ( "https://instagram.com/sathishmun/");
    }

    public void twitter (View view) {
        goToUrl ( "https://twitter.com/mun_sathish");
    }


    public void youtube (View view) {
        goToUrl ( "https://www.youtube.com/channel/UCdHKTfk9ugtn2319tsFiCVw");
    }

    public void aboutme (View view) {
        goToUrl ( "https://about.me/mun.sathish");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
