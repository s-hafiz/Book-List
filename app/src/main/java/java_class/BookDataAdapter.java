package java_class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.begginers.booklist.BookDetails;
import com.begginers.booklist.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by shafiz on 5/30/2017.
 */

public class BookDataAdapter extends RecyclerView.Adapter<BookDataAdapter.BookDataHolder>
{
    private ArrayList<Books> booksArrayList = new ArrayList<>();
    private Context ctx;

    public BookDataAdapter(Context ctx, ArrayList<Books> booksArrayList)
    {
        this.booksArrayList=booksArrayList;
        this.ctx=ctx;
    }


    @Override
    public BookDataHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_book_list,parent,false);
        BookDataHolder bookDataHolder = new BookDataHolder(view,ctx);
        return bookDataHolder;
    }

    @Override
    public void onBindViewHolder(BookDataHolder holder, int position)
    {
        Books books = booksArrayList.get(position);
        holder.title.setText(books.getTitle());
        holder.publisher.setText(books.getPublisher());
        holder.bookImage.setImageDrawable(books.getTempImage());


    }

    @Override
    public int getItemCount()
    {
        return booksArrayList.size();
    }

    public class BookDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView bookImage;
        TextView title;
        TextView publisher;

        Context context;


        public BookDataHolder(View itemView,Context context) {
            super(itemView);
            this.context=context;
            bookImage = (ImageView) itemView.findViewById(R.id.image_view);
            title = (TextView) itemView.findViewById(R.id.title_textView);
            publisher = (TextView) itemView.findViewById(R.id.publisher_textView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, BookDetails.class);
            int pos = getAdapterPosition();
            intent.putExtra("title",booksArrayList.get(pos).getTitle());
            intent.putExtra("publishedDate",booksArrayList.get(pos).getPublished_date());
            intent.putExtra("rating",booksArrayList.get(pos).getRating());
            intent.putExtra("description",booksArrayList.get(pos).getDescription());
            intent.putExtra("imagePath",booksArrayList.get(pos).getImagePath());

            //passing the image
            Bitmap bitmap = ((BitmapDrawable) booksArrayList.get(pos).getTempImage()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            intent.putExtra("picture",b);

            context.startActivity(intent);



        }
    }
}
