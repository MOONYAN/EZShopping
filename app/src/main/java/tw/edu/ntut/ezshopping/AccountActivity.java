package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import tw.edu.ntut.ezshopping.ModelField.User;

public class AccountActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "AccountActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient _googleApiClient;
    private FirebaseAuth _firebaseAuth;
    private FirebaseAuth.AuthStateListener _authStateListener;
    private TextView _gmailTextView;
    private TextView _uidTextView;
    private TextView _displayNameTextView;
    private View _signInButton;
    private View _signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        processViews();
        processControllers();
        initializeGoogleApi();
        initializeFirebaseAuth();
    }

    private void initializeFirebaseAuth()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }

    private void initializeGoogleApi()
    {
        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        _googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void processViews()
    {
        _gmailTextView = (TextView) findViewById(R.id.gmail_text);
        _uidTextView = (TextView) findViewById(R.id.uid_text);
        _displayNameTextView = (TextView) findViewById(R.id.display_name_text);
        _signInButton = findViewById(R.id.sign_in_button);
        _signOutButton = findViewById(R.id.sign_out_button);
    }

    private void processControllers()
    {
        _signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signIn();
            }
        });
        _signOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signOut();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        _firebaseAuth.addAuthStateListener(_authStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (_authStateListener != null)
        {
            _firebaseAuth.removeAuthStateListener(_authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                // Google Sign In failed, update UI appropriately
                // ...
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        _firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful())
                        {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn()
    {
        Log.d(TAG, "signIn:");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(_googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut()
    {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(_googleApiClient).setResultCallback(new ResultCallback<Status>()
        {
            @Override
            public void onResult(@NonNull Status status)
            {
                updateUI(null);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user)
    {
        hideProgressDialog();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferenceName), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (user != null)
        {
            editor.putString("FirebaseId", user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference("user").child(user.getUid()).setValue(new User(user));
            _gmailTextView.setText(user.getEmail());
            _uidTextView.setText(user.getUid());
            _displayNameTextView.setText(user.getDisplayName());
            _signInButton.setVisibility(View.GONE);
            _signOutButton.setVisibility(View.VISIBLE);
        }
        else
        {
            editor.clear();
            _gmailTextView.setText(null);
            _uidTextView.setText(null);
            _displayNameTextView.setText(null);
            _signInButton.setVisibility(View.VISIBLE);
            _signOutButton.setVisibility(View.GONE);
        }
        editor.apply();
    }
}