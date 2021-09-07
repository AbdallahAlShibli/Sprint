package cc.omansoftware.sprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrdersActivity extends AppCompatActivity {

    private Button openUploudPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        openUploudPage = findViewById(R.id.idUpluadPhoto);
        openUploudPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), UploadImageActivity.class));
            }
        });
    }
}
