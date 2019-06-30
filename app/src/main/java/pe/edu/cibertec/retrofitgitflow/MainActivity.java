package pe.edu.cibertec.retrofitgitflow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        callService();
    }

    private void callService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Postjava>> call = jsonPlaceHolderApi.getPost();
        call.enqueue(new Callback<List<Postjava>>() {
            @Override
            public void onResponse(Call<List<Postjava>> call, Response<List<Postjava>> response) {

                if (!response.isSuccessful()){
                textViewResult.setText("Code : " + response.code());
                }else
                {
                    List<Postjava> postjavas = response.body();
                    for (Postjava postjava:postjavas){
                        String context = "";
                        context += "Id:" + postjava.getId() + "\n";
                        context += "UserId:" + postjava.getUserId() + "\n";
                        context += "Title:" + postjava.getTitle() + "\n";
                        context += "Body:" + postjava.getText() + "\n";

                        textViewResult.append(context);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Postjava>> call, Throwable t) {

                textViewResult.setText(t.getMessage());
                t.printStackTrace();
            }
        });

    }

}
