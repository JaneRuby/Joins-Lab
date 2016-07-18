package com.example.janerubygrissom.joinslab;

// IMPORTS -- add a bunch of libraries for later use

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.janerubygrissom.joinslab.DatabaseHelper.getInstance;


// METHODS (main program)

public class MainActivity extends AppCompatActivity {

    ListView mListView;

    // 4 extra buttons
    Button mbtnAddData;
    Button mbtnSameCompany;
    Button mbtnInBoston;
    Button mbtnHighestSalary;

    TextView mtxtResult;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO why is the entire program in this method?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // start of Jane's program, for real this time...!

        //--------------------------------
        // 1st button - Add Data
        //--------------------------------

        mbtnAddData = (Button) findViewById(R.id.btnAddData);
        mbtnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AddData();

                // when the user clicks the Add Data button,
                // adds data? TODO LATER

            }
        });

        //--------------------------------
        // 2nd button - Employees Working at the Same Company
        //--------------------------------

        mbtnSameCompany = (Button) findViewById(R.id.btnSameCompany);
        mbtnSameCompany.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mListView = (ListView) findViewById(R.id.txtResult);
                Cursor cursor = getInstance(view.getContext()).getEmployeesFromSameCompany();
                CursorAdapter adapter = new CursorAdapter(MainActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {

                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        //textView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME))+cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME)));

                        String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME));
                        String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME));
                        String fullName = firstName + " " + lastName;
                        textView.setText(fullName);


                    }
                };
                mListView.setAdapter(adapter);
                // if (cursor.moveToFirst()){
                //  while(!cursor.isAfterLast()){
                //    result += cursor.getString(cursor.getColumnIndex(COL_COMPANY)) + ".";
                //  cursor.moveToNext();
                // }
                //}

            }

        });


        //--------------------------------
        // 3rd button - Companies in Boston
        //--------------------------------

        mbtnInBoston = (Button) findViewById(R.id.btnInBoston);
        mbtnInBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView = (ListView) findViewById(R.id.txtResult);
                Cursor cursor = getInstance(view.getContext()).getCompaniesInBoston();
                CursorAdapter adapter = new CursorAdapter(MainActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPANY)));
                    }
                };

                mListView.setAdapter(adapter);

            }
        });


        //--------------------------------
        // 4th button - Company with the Highest Salary
        //--------------------------------

        mbtnHighestSalary = (Button) findViewById(R.id.btnHighestSalary);
        mbtnHighestSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtxtResult = (TextView) findViewById(R.id.txtResult);
                DatabaseHelper helper = getInstance(MainActivity.this);
                Cursor cursor = helper.highestSalary();
                if (cursor.moveToFirst()) {
                    mtxtResult.setText("Company with the higest Salary: " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPANY)));
                }
            }
        });


        //--------------------------------
        // "5th button" - fab
        //--------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_employee, null);
                builder.setView(dialogView);
                builder.setTitle("Enter Your Employee Info");
                final Employee newEmployee = new Employee();

                final EditText EmpSSN = (EditText) dialogView.findViewById(R.id.txtSSN);
                final EditText firstName = (EditText) dialogView.findViewById(R.id.txtFName);
                final EditText lastName = (EditText) dialogView.findViewById(R.id.txtLName);
                final EditText birthYear = (EditText) dialogView.findViewById(R.id.txtYear);
                final EditText city = (EditText) dialogView.findViewById(R.id.txtCity);

                builder.setPositiveButton("Enter Data", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (EmpSSN.getText().toString().trim().length() == 0) {
                            EmpSSN.setError("You need to enter a SSN!");
                        } else if (firstName.getText().toString().trim().length() == 0) {
                            firstName.setError("You need to enter a First Name!");
                        } else if (lastName.getText().toString().trim().length() == 0) {
                            lastName.setError("You need to enter a Last Name");
                        } else if (birthYear.getText().toString().trim().length() == 0) {
                            birthYear.setError("You need to enter a Birth Year");
                        } else if (city.getText().toString().trim().length() == 0) {
                            city.setError("You need to enter a City!");
                        } else {

                            newEmployee.setEmpSSN(EmpSSN.getText().toString());
                            newEmployee.setFirstName(firstName.getText().toString());
                            newEmployee.setLastName(lastName.getText().toString());
                            newEmployee.setYear(birthYear.getText().toString());
                            newEmployee.setCity(city.getText().toString());

                            getInstance(view.getContext()).insertRowEmployee(newEmployee);
                            Toast.makeText(MainActivity.this, "The employee has been added!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

            }


        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void AddData() {
        DatabaseHelper mhelper = DatabaseHelper.getInstance(MainActivity.this);

        Employee employee = new Employee("John", "Smith", "NY", "1973", "123-04-5678");
        Employee employee1 = new Employee("David", "McWill", "Seattle", "1982", "123-04-5679");
        Employee employee2 = new Employee("Katerina", "Wise", "Boston", "1973", "123-04-5680");
        Employee employee3 = new Employee("Donald", "Lee", "London", "1992", "123-04-5681");
        Employee employee4 = new Employee("Gary", "Henwood", "Las Vegas", "1987", "123-04-5682");
        Employee employee5 = new Employee("Anthony", "Bright", "Seattle", "1963", "123-04-5683");
        Employee employee6 = new Employee("William", "Newey", "Boston", "1995", "123-04-5684");
        Employee employee7 = new Employee("Melony", "Smith", "Chicago", "1970", "123-04-5685");
        EmployeeJob job = new EmployeeJob("123-04-5678", "Fuzz", 60, 1);
        EmployeeJob job1 = new EmployeeJob("123-04-5679", "GA", 70, 2);
        EmployeeJob job2 = new EmployeeJob("123-04-5680", "Little Place", 120, 5);
        EmployeeJob job3 = new EmployeeJob("123-04-5681", "Macy's", 78, 3);
        EmployeeJob job4 = new EmployeeJob("123-04-5682", "New Life", 65, 1);
        EmployeeJob job5 = new EmployeeJob("123-04-5683", "Believe", 158, 6);
        EmployeeJob job6 = new EmployeeJob("123-04-5684", "Macy's", 200, 8);
        EmployeeJob job7 = new EmployeeJob("123-04-5685", "Stop", 299, 12);

        mhelper.insertRowEmployee(employee);
        mhelper.insertRowEmployee(employee1);
        mhelper.insertRowEmployee(employee2);
        mhelper.insertRowEmployee(employee3);
        mhelper.insertRowEmployee(employee4);
        mhelper.insertRowEmployee(employee5);
        mhelper.insertRowEmployee(employee6);
        mhelper.insertRowEmployee(employee7);

        mhelper.insertRowJob(job);
        mhelper.insertRowJob(job1);
        mhelper.insertRowJob(job2);
        mhelper.insertRowJob(job3);
        mhelper.insertRowJob(job4);
        mhelper.insertRowJob(job5);
        mhelper.insertRowJob(job6);
        mhelper.insertRowJob(job7);

        Toast.makeText(MainActivity.this, "You added data", Toast.LENGTH_SHORT).show();

    }

}


