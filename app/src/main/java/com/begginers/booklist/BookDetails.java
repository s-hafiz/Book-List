package com.begginers.booklist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookDetails extends AppCompatActivity
{

    private ImageView imageView;
    private TextView bTitle,bDescription,publishedDate;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        initview();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            bTitle.setText(bundle.getString("title"));
            bDescription.setText(bundle.getString("description"));
            publishedDate.setText(bundle.getString("publishedDate"));

            Float rating = Float.parseFloat(bundle.get("rating").toString());
            ratingBar.setRating(rating);


            //Getting and setting the image into image view
            byte[] b = bundle.getByteArray("picture");

            Bitmap btmp = BitmapFactory.decodeByteArray(b,0,b.length);
            imageView.setImageBitmap(btmp);

        }
    }

    private void initview()
    {
        imageView = (ImageView) findViewById(R.id.detail_image_view);
        bTitle = (TextView) findViewById(R.id.detail_title_textView);
        bDescription = (TextView) findViewById(R.id.detail_description);
        publishedDate = (TextView) findViewById(R.id.date_tv);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }
}
