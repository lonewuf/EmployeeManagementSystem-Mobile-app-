package khan.shadik.mongoarticle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView detailAuthor, detailDescription, detailTitle;
    String intentAuthor, intentDescription, intentTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detailAuthor = (TextView) findViewById(R.id.tv_author);
        detailDescription = (TextView) findViewById(R.id.tv_title);
        detailTitle = (TextView) findViewById(R.id.tv_description);
        intentAuthor = getIntent().getStringExtra("author");
        intentDescription = getIntent().getStringExtra("description");
        intentTitle = getIntent().getStringExtra("title");
        detailAuthor.setText(intentAuthor);
        detailTitle.setText(intentTitle);
        detailDescription.setText(intentDescription);
    }
}
