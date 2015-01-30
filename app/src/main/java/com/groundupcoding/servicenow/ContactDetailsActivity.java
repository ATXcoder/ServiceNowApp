package com.groundupcoding.servicenow;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.models.Contact;

import java.util.List;


public class ContactDetailsActivity extends ActionBarActivity implements AsyncResponse {
    private String contactID;


    private TextView contactName;
    private TextView contactTitle;
    private TextView contactBuilding;
    private TextView contactAddress;
    private TextView contactPhone;
    private TextView contactMobile;
    private TextView contactEmail;
    private TextView contactCompany;
    private TextView contactManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);


        // Get fields
        contactName = (TextView)findViewById(R.id.contactName);
        contactTitle = (TextView)findViewById(R.id.contactTitle);
        contactBuilding =(TextView)findViewById(R.id.contactBuilding);
        contactAddress = (TextView)findViewById(R.id.contactAddress);
        contactPhone = (TextView)findViewById(R.id.contactPhone);
        contactMobile = (TextView)findViewById(R.id.contactMobile);
        contactEmail = (TextView)findViewById(R.id.contactEmail);
        contactCompany = (TextView)findViewById(R.id.contactCompany);
        contactManager = (TextView)findViewById(R.id.contactManager);

        // Get contact ID
        Bundle extras = getIntent().getExtras();
        contactID = extras.getString("ID");

        // Get Instance
        DataBaseHelper db = new DataBaseHelper(this);
        String apiURL = db.getCurrentInstance();
        apiURL = apiURL + "/sys_user.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_query=sys_id=" + contactID;
        Log.i("ServiceNow","Contact API: " + apiURL);

        // Get contact details
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;
        nh.GET(apiURL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void taskComplete(String result) {
        if(!result.isEmpty())
        {
            // Returns a Array on contacts
            Contact.Contacts contacts = new Gson().fromJson(result, Contact.Contacts.class);
            // Convert our Array of contacts to a List of Contact type
            List<Contact> contactList = contacts.getRecords();
            // Build a array of type contact that will hold one entry (that's all we should have)
            Contact[] contactArray = new Contact[1];
            // Now lets populate our contact
            contactArray = contactList.toArray(contactArray);

            Contact contact = (Contact)contactArray[0];


            // Set fields
            contactName.setText(contact.getDv_last_name() + ", " + contact.getDv_first_name());
            contactTitle.setText(contact.getDv_title());
            contactBuilding.setText(contact.getDv_building());
            contactAddress.setText(contact.getDv_street() + " "
                    + contact.getDv_city() + ", "
                    + contact.getDv_state() + " "
                    + contact.getDv_zip());
            contactPhone.setText(contact.getDv_phone());
            contactMobile.setText(contact.getDv_mobile_phone());
            contactEmail.setText(contact.getDv_email());
            contactCompany.setText(contact.getDv_company());
            contactManager.setText(contact.getDv_manager());
        }
        else
        {

        }
    }
}
