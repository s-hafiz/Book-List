package java_class;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shafiz on 5/30/2017.
 */

public class GetData extends AsyncTaskLoader<ArrayList<Books>>
{
    private String url;
    public GetData(Context context,String url)
    {
        super(context);
        this.url=url;
    }

    @Override
    public ArrayList<Books> loadInBackground()
    {
        ArrayList<Books> booksArrayList = new ArrayList<>();

        try
        {
            //making url
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

            //accessing via get method
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }

            //Closing resource
            in.close();

            String jsonData = response.toString();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i =0;i<items.length();i++)
            {
                String title = "";
                String publisher = "";
                String publishedDate = "";
                String description = "";
                double averageRating = 0;
                String image = "";
                
                try {
                    JSONObject tempObject = items.getJSONObject(i);
                    JSONObject volumeInfo = tempObject.getJSONObject("volumeInfo");
                    
                    if (volumeInfo.has("title"))
                        title = volumeInfo.getString("title");
                    if (volumeInfo.has("publisher"))
                        publisher = volumeInfo.getString("publisher");
                    if (volumeInfo.has("publishedDate"))
                        publishedDate = volumeInfo.getString("publishedDate");
                    if (volumeInfo.has("description"))
                        description=volumeInfo.getString("description");
                    if (volumeInfo.has("averageRating"))
                        averageRating = volumeInfo.getDouble("averageRating");
                    
                    
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    //saving drawable iamge
                    if (imageLinks.has("smallThumbnail"))
                        image = imageLinks.getString("smallThumbnail");
                    
                    Books tempBook = new Books(title,publishedDate,averageRating,description,publisher,image);
                    if (!TextUtils.isEmpty(image))
                        tempBook.setTempImage(LoadImageFromWebOperation(image));
                    booksArrayList.add(tempBook);
                }
                catch (JSONException jo)
                {
                    
                }
            }
        }
        catch (IOException | JSONException e)
        {

        }
        return booksArrayList;
    }

    private static Drawable LoadImageFromWebOperation(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is,"src name");
            return d;
        }
        catch (IOException io)
        {
            io.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
