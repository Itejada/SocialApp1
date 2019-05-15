package com.example.gerard.socialapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerard.socialapp.GlideApp;
import com.example.gerard.socialapp.R;
import com.example.gerard.socialapp.model.Post;
import com.example.gerard.socialapp.view.PostViewHolder;
import com.example.gerard.socialapp.view.fragment.PostsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PerfilActivity extends AppCompatActivity {

    public static final String MEDIA_URL="mediaUrl";
    public static final String MEDIA_TYPE="mediaType";
    private String userName,usuarioUidPost,fotoUsuario;


    DatabaseReference mReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

         userName = getIntent().getStringExtra(PostsFragment.USUARIO_NOMBRE);
         usuarioUidPost = getIntent().getStringExtra(PostsFragment.USUARIO_POST_ID );
         fotoUsuario = getIntent().getStringExtra(PostsFragment.USUARIO_FOTO);

        //((ImageView) findViewById(R.id.profileImage));
        ((TextView) findViewById(R.id.nombre_usuario)).setText(userName);
        GlideApp.with(this).load(fotoUsuario).into((ImageView) findViewById(R.id.profileImage));


        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Post>()
                .setIndexedQuery(mReference.child("posts/user-posts/").child(usuarioUidPost),
                        mReference.child("posts/data"), Post.class)
                .setLifecycleOwner(this)
                .build();
        RecyclerView recycler = findViewById(R.id.posts_usuario);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new PostViewHolder(inflater.inflate(R.layout.item_post, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder viewHolder, int position, @NonNull final Post post) {
                final String postKey = getRef(position).getKey();

                viewHolder.author.setText(post.author);
                GlideApp.with(getApplicationContext()).load(post.authorPhotoUrl).circleCrop().into(viewHolder.photo);

                if (post.likes.containsKey(firebaseUser.getUid())) {
                    viewHolder.like.setImageResource(R.drawable.heart_on);
                    viewHolder.numLikes.setTextColor(getResources().getColor(R.color.red));
                } else {
                    viewHolder.like.setImageResource(R.drawable.heart_off);
                    viewHolder.numLikes.setTextColor(getResources().getColor(R.color.grey));
                }

                viewHolder.content.setText(post.content);

                if (post.mediaUrl != null) {
                    viewHolder.image.setVisibility(View.VISIBLE);
                    if ("audio".equals(post.mediaType)) {
                        viewHolder.image.setImageResource(R.drawable.audio);
                    } else {
                        GlideApp.with(getApplicationContext()).load(post.mediaUrl).centerCrop().into(viewHolder.image);

                    }
                    viewHolder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MediaActivity.class);
                            intent.putExtra(MEDIA_URL, post.mediaUrl);
                            intent.putExtra(MEDIA_TYPE, post.mediaType);
                            startActivity(intent);
                        }
                    });
                } else {
                    viewHolder.image.setVisibility(View.GONE);
                }

                viewHolder.numLikes.setText(String.valueOf(post.likes.size()));

                viewHolder.likeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (post.likes.containsKey(firebaseUser.getUid())) {
                            mReference.child("posts/data").child(postKey).child("likes").child(firebaseUser.getUid()).setValue(null);
                            mReference.child("posts/user-likes").child(firebaseUser.getUid()).child(postKey).setValue(null);
                        } else {
                            mReference.child("posts/data").child(postKey).child("likes").child(firebaseUser.getUid()).setValue(true);
                            mReference.child("posts/user-likes").child(firebaseUser.getUid()).child(postKey).setValue(true);
                        }
                    }
                });
            }
        });
    }
}
