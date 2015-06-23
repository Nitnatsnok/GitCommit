package com.konstantin.gitcommit;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.konstantin.gitcommit.database.DatabaseHelper;
import com.konstantin.gitcommit.provider.CommitsContentProvider;
import com.konstantin.gitcommit.service.CommitsLoadService;


public class CommitsListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView mCommitsListView;
    SimpleCursorAdapter mAdapter;

    String owner;
    String repo;

    String sortOrder;
    String[] fromColumns;
    int[] toViews;

    static final String[] PROJECTION = new String[] {DatabaseHelper.ID_COLUMN, DatabaseHelper.MESSAGE_COLUMN, DatabaseHelper.COMMITER_NAME_COLUMN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits_list);

        owner = getIntent().getExtras().getString("owner");
        repo = getIntent().getExtras().getString("repo");

        setTitle(":" + owner + "—:" + repo);

        mCommitsListView = (ListView) findViewById(R.id.listView);

        Intent intent = new Intent(this, CommitsLoadService.class);
        intent.putExtra("owner", owner);
        intent.putExtra("repo", repo);
        startService(intent);

        fromColumns = new String[] {DatabaseHelper.MESSAGE_COLUMN, DatabaseHelper.COMMITER_NAME_COLUMN};
        toViews = new int[] {android.R.id.text1, android.R.id.text2};

        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, fromColumns, toViews, 0);
        mCommitsListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().delete(CommitsContentProvider.CONTENT_URI, "1 = 1", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commits_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_asc: {
                sortOrder = DatabaseHelper.DATE_COLUMN + " ASC";
                getLoaderManager().restartLoader(0, null, this);
                item.setChecked(true);
                return true;
            }
            case R.id.sort_desc: {
                sortOrder = DatabaseHelper.DATE_COLUMN + " DESC";
                getLoaderManager().restartLoader(0, null, this);
                item.setChecked(true);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CommitsContentProvider.CONTENT_URI, PROJECTION, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
