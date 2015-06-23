package com.konstantin.gitcommit.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.konstantin.gitcommit.database.DatabaseHelper;
import com.konstantin.gitcommit.provider.CommitsContentProvider;
import com.konstantin.gitcommit.rest.model.CommitContainer;
import com.konstantin.gitcommit.rest.service.RestClient;

import java.util.List;

import retrofit.RetrofitError;

public class CommitsLoadService extends Service {

    private RestClient restClient;

    public CommitsLoadService() {
    }

    @Override
    public void onCreate() {
        restClient = new RestClient();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new loadDataFromNetwork().execute(intent.getExtras().getString("owner"), intent.getExtras().getString("repo"));
        stopSelf();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class loadDataFromNetwork extends AsyncTask<String, Void, List<CommitContainer>> {

        @Override
        protected List<CommitContainer> doInBackground(String... params) {
            try {
                return restClient.getGitHubApiService().getListCommits(params[0], params[1]);
            } catch (RetrofitError e) {
                return null; //Есть уверенность, что это чрезвычайно плохое решение, но зато приложение не крашится...
            }
        }

        @Override
        protected void onPostExecute(List<CommitContainer> commitsList) {
            ContentValues contentValues = new ContentValues();

            //Дитя дефицита времени и неумолимо приближающегося дедлайна
            if (commitsList == null) {
                contentValues.put(DatabaseHelper.DATE_COLUMN, "unknown");
                contentValues.put(DatabaseHelper.SHA_COLUMN, "unknown");
                contentValues.put(DatabaseHelper.HTML_URL_COLUMN, "unknown");
                contentValues.put(DatabaseHelper.AUTHOR_NAME_COLUMN, "unknown");
                contentValues.put(DatabaseHelper.COMMITER_NAME_COLUMN, "То ли пользователя такого нет, то ли репозиторий не найден, то ли ещё какая-то чертовщина приключилась");
                contentValues.put(DatabaseHelper.MESSAGE_COLUMN, "Какая-то ошибка");
                getContentResolver().insert(CommitsContentProvider.CONTENT_URI, contentValues);
                return;
            }

            if (!commitsList.isEmpty()) {
                for (CommitContainer commit : commitsList) {
                    contentValues.put(DatabaseHelper.DATE_COLUMN, commit.getCommit().getCommitter().getDate().toString());
                    contentValues.put(DatabaseHelper.SHA_COLUMN, commit.getSha());
                    contentValues.put(DatabaseHelper.HTML_URL_COLUMN, commit.getHtmlUrl());
                    contentValues.put(DatabaseHelper.AUTHOR_NAME_COLUMN, commit.getCommit().getAuthor().getName());
                    contentValues.put(DatabaseHelper.COMMITER_NAME_COLUMN, commit.getCommit().getCommitter().getName());
                    contentValues.put(DatabaseHelper.MESSAGE_COLUMN, commit.getCommit().getMessage());
                    getContentResolver().insert(CommitsContentProvider.CONTENT_URI, contentValues);
                    contentValues.clear();
                }
            }
            commitsList.clear();
        }
    }
}
