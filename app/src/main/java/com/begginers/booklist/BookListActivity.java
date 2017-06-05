package com.begginers.booklist;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import java_class.BookDataAdapter;
import java_class.Books;
import java_class.GetData;

public class BookListActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Books>>
{
    private final int LOADER_ID = 1;
    private final String BOOK_API_URL="https://www.googleapis.com/books/v1/volumes";

    private BookDataAdapter bookDataAdapter;
    LoaderManager loaderManager;
    Button search;
    EditText searchEditText;
    ProgressBar progressBar;

    RecyclerView recyclerView;

    ArrayList<Books> bookItems = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        initView();

        //setting listener to buttons
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();

                bookItems.clear();
                bookDataAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(bookDataAdapter);
                //bookItems.clear();
                loaderManager.restartLoader(LOADER_ID,null,BookListActivity.this);
            }
        });
    }

    private void hideKeyBoard()
    {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        search = (Button) findViewById(R.id.Search_button);
        searchEditText= (EditText) findViewById(R.id.search_book);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linearLayoutManager = new LinearLayoutManager(this);
        progressBar.setVisibility(View.GONE);
        loaderManager = getLoaderManager();

        loaderManager.initLoader(LOADER_ID,null,BookListActivity.this);
    }

    @Override
    public Loader<ArrayList<Books>> onCreateLoader(int id, Bundle args)
    {
        Uri baseUri = Uri.parse(BOOK_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        String bookName = searchEditText.getText().toString();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String maxResult = sharedPref.getString(
                getString(R.string.settings_max_search_result_key),
                getString(R.string.settings_max_search_result_default)
        );
        uriBuilder.appendQueryParameter("q",bookName);
        uriBuilder.appendQueryParameter("maxResults",maxResult);

        progressBar.setVisibility(View.VISIBLE);
        return new GetData(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> data)
    {
        this.bookItems=data;
        bookDataAdapter = new BookDataAdapter(this,bookItems);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookDataAdapter);

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Books>> loader)
    {
        bookDataAdapter = new BookDataAdapter(this, new ArrayList<Books>(){});
    }
}
