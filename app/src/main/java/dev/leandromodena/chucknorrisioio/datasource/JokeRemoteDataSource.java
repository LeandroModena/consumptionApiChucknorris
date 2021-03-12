package dev.leandromodena.chucknorrisioio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dev.leandromodena.chucknorrisioio.model.Joke;

public class JokeRemoteDataSource {
    public interface JokeCallback{
        void onSucess (Joke response);
        void onError (String message);
        void onComplete();
    }

    public void findJokeBy(JokeCallback callback, String category){
        new JokeTask(callback, category).execute();
    }

    private static class JokeTask extends AsyncTask <Void, Void, Joke>{

        String errorMessage;
        private final JokeCallback callback;
        private final String category;

        public JokeTask(JokeCallback callback, String category) {
            this.callback = callback;
            this.category = category;
        }

        @Override
        protected Joke doInBackground(Void... voids) {
            Joke joke = null;
            HttpsURLConnection urlConnection = null;

            try {
                String enpoint = String.format("%s?category=%s", Endpoint.GET_JOKE, category);
                URL url = new URL(enpoint);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setReadTimeout(2000);
                urlConnection.setReadTimeout(2000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode > 400){
                    throw new IOException("Erro na comunicação com o servidor");
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader jsonReader = new JsonReader(new InputStreamReader(in));

                //Parse Json

                jsonReader.beginObject();

                String iconUrl = null;
                String value = null;

                while (jsonReader.hasNext()){
                    JsonToken token = jsonReader.peek();

                    if (token == JsonToken.NAME){
                        String name = jsonReader.nextName();
                        if (name.equals("category"))
                            jsonReader.skipValue();
                        else if (name.equals("icon_url"))
                            iconUrl = jsonReader.nextString();
                        else if (name.equals("value"))
                            value = jsonReader.nextString();
                        else
                            jsonReader.skipValue();
                    }
                }

                joke = new Joke(iconUrl, value);
                jsonReader.endObject();

            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }
            return joke;
        }

        @Override
        protected void onPostExecute(Joke joke) {

            if (errorMessage != null){
                callback.onError(errorMessage);
            }else{
                callback.onSucess(joke);
            }
            callback.onComplete();

        }
    }
}
