package khan.shadik.mongoarticle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khan.shadik.mongoarticle.adapter.PostItemAdapter;
import khan.shadik.mongoarticle.model.PostItemModel;
import khan.shadik.mongoarticle.model.PostModel;
import khan.shadik.mongoarticle.network.BackendClient;
import khan.shadik.mongoarticle.network.NetworkCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView postListRecycler;
    private PostItemAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post");

        init();
    }


    private void init() {
        postListRecycler = (RecyclerView) findViewById(R.id.rv_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        postListRecycler.setLayoutManager(layoutManager);
        postAdapter = new PostItemAdapter(this, new ArrayList<PostItemModel>());
        postListRecycler.setAdapter(postAdapter);
        loadPost();
    }

    private void loadPost() {
        NetworkCall service = BackendClient.createRetrofitService(NetworkCall.class);
        Call<PostModel> call = service.getPosts();
        call.enqueue(new PostListCallback());
    }

    private void updatePost(List<PostItemModel> data){
        postAdapter.addAll(data);
        postAdapter.notifyDataSetChanged();
    }

    public class PostListCallback implements Callback<PostModel> {

        @Override
        public void onResponse(Call<PostModel> call, Response<PostModel> response) {
            if (response.body() != null) {
                if (response.body().isSuccess()) {
                    updatePost(response.body().getData());
                    System.out.println(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Response Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<PostModel> call, Throwable t) {

            t.printStackTrace();
        }
    }
}
