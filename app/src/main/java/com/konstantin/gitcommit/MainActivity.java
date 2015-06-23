package com.konstantin.gitcommit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    EditText mOwnerEditText;
    EditText mRepoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOwnerEditText = (EditText) findViewById(R.id.owner_edit_text);
        mRepoEditText = (EditText) findViewById(R.id.repo_edit_text);
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

    public void onClick(View view) {
        String owner = mOwnerEditText.getText().toString();
        String repo = mRepoEditText.getText().toString();

        if (!owner.isEmpty() && !repo.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, CommitsListActivity.class);
            intent.putExtra("owner", owner);
            intent.putExtra("repo", repo);
            startActivity(intent);
        }
    }
}
