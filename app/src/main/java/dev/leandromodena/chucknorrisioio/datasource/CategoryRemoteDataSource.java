package dev.leandromodena.chucknorrisioio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
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

public class CategoryRemoteDataSource {

    public interface ListCategoriesCallback{
        void onSucess (List<String> response);
        void onError (String message);
        void onComplete();

    }

    public void findAll(ListCategoriesCallback callback){
        new CategoryTask(callback).execute();

    }

    private static class CategoryTask extends AsyncTask<Void, Void, List<String>>{
        private String errorMessage;

        private final ListCategoriesCallback callback;

        public CategoryTask(ListCategoriesCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> response = new ArrayList<>();
            HttpsURLConnection urlConnection = null;

            try {
                URL url = new URL(Endpoint.GET_CATEGORIES);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setReadTimeout(2000);
                urlConnection.setReadTimeout(2000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode > 400){
                    throw new IOException("Erro na comunicação com o servidor");
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader jsonReader = new JsonReader(new InputStreamReader(in));

                jsonReader.beginArray();

                while (jsonReader.hasNext()){
                    response.add(jsonReader.nextString());
                }

                jsonReader.endArray();

            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }

            return response;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if (errorMessage != null){
                Log.i("TESTE", errorMessage);
                callback.onError(errorMessage);
            }else{
                Log.i("Teste", strings.toString());
                callback.onSucess(strings);
            }
            callback.onComplete();
        }
    }
}
